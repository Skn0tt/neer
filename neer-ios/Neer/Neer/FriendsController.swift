//
//  FriendsController.swift
//  Neer
//
//  Created by Simon Knott on 12.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import Combine
import Just
import UIKit
import MessageKit

class StringMessage : MessageType {
    
    var kind: MessageKind
    var messageId: String
    var sender: SenderType
    var sentDate: Date
    
    init(text: String, id: String, date: Date, username: String, displayname: String) {
        self.kind = .text(text)
        self.messageId = id
        self.sentDate = date
        self.sender = Sender(id: username, displayName: username)
    }
    
}

extension Date {
    var iso8601: String {
        let formatter = ISO8601DateFormatter()
        return formatter.string(from: self)
    }
}

class Chat : ObservableObject, Codable {
    let friend: Friend
    
    var theirMessages: [ Date : String ] = [:]
    var ourMessages:   [ Date : String ] = [:]
    
    init(friend: Friend) {
        self.friend = friend
    }
    
    func getMessages() -> [MessageType] {
        func getStringMessage(username: String, displayname: String?) -> (_ date: Date, _ value: String) -> StringMessage {
            {  date, value in StringMessage(
                    text: value,
                    id: date.iso8601 + username,
                    date: date,
                    username: username,
                    displayname: displayname ?? username
                )
            }
        }
        
        return theirMessages.map(getStringMessage(username: friend.username, displayname: friend.getDisplayName())) + ourMessages.map(getStringMessage(username: getUsername() ?? "test", displayname: nil))
    }
    
    func receive(message: String, sentDate: Date) {
        theirMessages[sentDate] = message
    }
    
    func send(message: String) {
        let currentDate = Date()
        ourMessages[currentDate] = message
        
        // TOOD: send to server
    }
}

final class Friend : ObservableObject, Codable, Identifiable {
    
    @Published var username: String
    @Published var nickname: String?
    @Published var isDesired: Bool
    @Published var chat: Chat
    @Published var matches: Bool = false
    
    init(_ username: String, _ nickname: String?, _ isDesired: Bool, _ chat: Chat, _ matches: Bool) {
        self.username = username
        self.isDesired = isDesired
        self.nickname = nickname
        self.chat = chat
        self.matches = matches
    }
    
    convenience init(username: String, isDesired: Bool, matches: Bool) {
        self.init(username, nil, isDesired, Chat(friend: self), matches)
    }
    
    convenience init(username: String, isDesired: Bool) {
        self.init(username: username, isDesired: isDesired, matches: false)
    }
    
    // MARK: Decodable
    required convenience init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        let username = try container.decode(String.self, forKey: .username)
        let nickname = try container.decodeIfPresent(String.self, forKey: .nickname)
        let isDesired = try container.decode(Bool.self, forKey: .isDesired)
        let matches = try container.decode(Bool.self, forKey: .matches)
        
        let chatContainer = try container.nestedContainer(keyedBy: ChatKeys.self, forKey: .chat)
        let chat = try chatContainer.decode(Chat.self, forKey: .chat)
        
        self.init(username, nickname, isDesired, chat, matches)
    }
    
    enum CodingKeys: String, CodingKey {
        case username, nickname, isDesired, matches, chat
    }
    
    enum ChatKeys: String, CodingKey {
        case chat
    }
    
    // MARK: Encodable
    func encode(to encoder: Encoder) throws {
        var baseContainer = encoder.container(keyedBy: CodingKeys.self)
        try baseContainer.encode(self.username, forKey: .username)
        try baseContainer.encodeIfPresent(self.nickname, forKey: .nickname)
        try baseContainer.encode(self.isDesired, forKey: .isDesired)
        try baseContainer.encode(self.matches, forKey: .matches)
        var chatContainer = baseContainer.nestedContainer(keyedBy: ChatKeys.self, forKey: .chat)
        try chatContainer.encode(chat, forKey: .chat)
    }
    
    func getDisplayName() -> String {
        return nickname ?? username
    }
    
    func setNickname(_ v: String) {
        self.nickname = v
    }
    
    func toggleDesired() {
        isDesired = !isDesired

        Just.put(
            "\(serverBaseUrl)/friends/\(username)/\(isDesired ? "desired" : "undesired")",
            headers: [ "Authorization" : createAuthToken() ]
        ) { r in
            if !r.ok {
                // undo
                self.isDesired = !self.isDesired
            }
        }
        
    }
    
}

extension Friend : Hashable {
    func hash(into hasher: inout Hasher) {
        hasher.combine(username)
        hasher.combine(nickname)
        hasher.combine(isDesired)
    }
}

extension Friend : Equatable {
    static func == (lhs: Friend, rhs: Friend) -> Bool {
        return lhs.username == rhs.username
    }
}

class FriendsController : ObservableObject {
    @Published var friends: [Friend] = []
    
    convenience init(friends: [Friend]) {
        self.init()
        self.friends = friends
    }
    
    func addFriend(username: String, onFinish: @escaping (_ success: Bool) -> Void) {
        let isAlreadyAFriend = friends.contains { $0.username == username }
        if isAlreadyAFriend { return }
        
        Just.post(
            "\(serverBaseUrl)/friends",
            headers: [ "Authorization" : createAuthToken() ],
            requestBody: username.data(using: .utf8)
        ) { r in
            if r.ok {
                self.friends.append(Friend(username: username, isDesired: true))
                onFinish(true)
            } else {
                onFinish(false)
            }
        }
    }
    
    func remove(friend: Friend) {
        if let index = friends.firstIndex(of: friend) {
            friends.remove(at: index)
            
            Just.delete(
                "\(serverBaseUrl)/friends/\(friend.username)",
                headers: [ "Authorization" : createAuthToken() ]
            ) { r in
                if !r.ok {
                    self.friends.insert(friend, at: index)
                }
            }
        }
    }
}

private let userDefaults = UserDefaults.standard

func saveFriendsControllerToPersistence(_ fc: FriendsController) {
    let encoder = JSONEncoder()
    let serialized = try! encoder.encode(fc.friends)
    userDefaults.set(serialized, forKey: "friends")
}

func getFriendsControllerFromPersistence() -> FriendsController? {
    let friends = userDefaults.data(forKey: "friends")
    if friends == nil {
        return FriendsController()
    }
    
    let decoder = JSONDecoder()
    let decodedFriends = try! decoder.decode([Friend].self, from: friends!)
    
    return FriendsController(friends: decodedFriends)
}

private var fcFromPersistence: FriendsController? = nil
func getFriendsController() -> FriendsController {
    if (fcFromPersistence != nil) {
        fcFromPersistence = getFriendsControllerFromPersistence() ?? FriendsController()
    }
    
    return fcFromPersistence!
}

func getMockFriendsController() -> FriendsController {
    let fc = FriendsController()
    fc.friends.append(Friend(username: "Anton", isDesired: true, matches: true))
    fc.friends.append(Friend(username: "Berta", isDesired: false))
    fc.friends.append(Friend(username: "Carla", isDesired: true, matches: true))
    fc.friends.append(Friend(username: "Dora", isDesired: true))
    return fc
}
