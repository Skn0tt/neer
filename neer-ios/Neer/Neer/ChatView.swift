//
//  Chat.swift
//  Neer
//
//  Created by Simon Knott on 11.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI
import MessageKit

class ChatViewController {
    /*
    let ownUsername: String
    let chat: Chat
    
    init(ownUsername: String, chat: Chat) {
        self.ownUsername = ownUsername
        self.chat = chat
    }
    
    func currentSender() -> SenderType {
        return Sender(id: ownUsername, displayName: ownUsername)
    }
    
    func messageForItem(at indexPath: IndexPath, in messagesCollectionView: MessagesCollectionView) -> MessageType {
        return chat.getMessages()[indexPath.section]
    }
    
    func numberOfSections(in messagesCollectionView: MessagesCollectionView) -> Int {
        return chat.getMessages().count
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
 */
}

extension ChatViewController: MessageInputBarDelegate {
    func inputBar(_ inputBar: MessageInputBar, didPressSendButtonWith text: String) {
        print(text)
    }
}

struct ChatView: View {
    var body: some View {
        Text("Chat Here")
    }
    
}

struct ChatView_Previews: PreviewProvider {
    static var previews: some View {
        ChatView(/*
            ownUsername: "Joseph",
            with: Friend(username: "Horst", isDesired: true)
        */)
    }
}
