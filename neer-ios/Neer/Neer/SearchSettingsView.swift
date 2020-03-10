//
//  SearchSettingsView.swift
//  Neer
//
//  Created by Simon Knott on 11.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI

struct SearchSettingsTab: View {
    var body: some View {
        VStack {
            SearchRadiusSlider()
            FriendList(friends: [Friend(username: "hans", isDesired: false)])
        }
    }
}

struct SearchSettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SearchSettingsTab()
    }
}
