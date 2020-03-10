//
//  FriendList.swift
//  Neer
//
//  Created by Simon Knott on 10.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI

struct FriendListHeader: View {
    var body: some View {
        HStack {
            Text("Friend")
                .foregroundColor(.gray)
            Spacer()
            Text("Want to see?")
                .foregroundColor(.gray)
        }
        .padding(.horizontal, 16)
    }
}

struct FriendRow: View {
    @ObservedObject var friend: Friend
    
    var body: some View {
        Toggle(isOn: $friend.isDesired) {
            Text(friend.getDisplayName())
        }
    }
}

struct FriendList: View {
    @EnvironmentObject var friendsController: FriendsController
    
    @State var alertedFriend: Friend? = nil
    
    // TODO: Sort friends by "isDesired" when listing them
    
    var body: some View {
        VStack {
            FriendListHeader()
            List(friendsController.friends) { friend in
                FriendRow(friend: friend)
                    .contextMenu {
                        Button(action: { self.alertedFriend = friend }) {
                            Image(systemName: "pencil")
                            Text("Set Nickname")
                        }
                        Button(action: {}) {
                            Image(systemName: "person.badge.minus")
                            Text("Remove")
                        }
                    }
            }
            .alert(item: $alertedFriend) { _ in
                Alert(title: Text("Important message"), message: Text("Wear sunscreen"), dismissButton: .default(Text("Got it!")))
            }
        }
        
    }
}

struct FriendList_Previews: PreviewProvider {
    static var previews: some View {
        FriendList()
            .environmentObject(getMockFriendsController())
    }
}
