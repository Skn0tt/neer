//
//  MatchesTab.swift
//  Neer
//
//  Created by Simon Knott on 11.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI

struct MatchesTab: View {
    @EnvironmentObject var friendsController: FriendsController
    var body: some View {
        MatchesList(matches: friendsController.friends.filter { $0.matches })
    }
}

struct MatchesTab_Previews: PreviewProvider {
    static var previews: some View {
        MatchesTab()
            .environmentObject(getMockFriendsController())
    }
}
