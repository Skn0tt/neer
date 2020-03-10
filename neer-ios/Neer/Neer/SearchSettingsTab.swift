//
//  SearchSettingsTab.swift
//  Neer
//
//  Created by Simon Knott on 11.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI

struct SearchSettingsTab: View {
    var body: some View {
        VStack() {
            SearchRadiusSlider()
                .edgesIgnoringSafeArea(.top)
            Spacer(minLength: CGFloat(20.0))
            FriendList()
        }
    }
}

struct SearchSettingsTab_Previews: PreviewProvider {
    static var previews: some View {
        SearchSettingsTab()
            .environmentObject(getMockFriendsController())
    }
}
