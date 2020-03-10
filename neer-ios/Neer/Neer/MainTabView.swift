//
//  MainTabView.swift
//  Neer
//
//  Created by Simon Knott on 11.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI

struct MainTabView: View {
    var body: some View {
        TabView {
            SearchSettingsTab()
                .tabItem {
                    Image(systemName: "person.3.fill")
                    Text("Search Settings")
                }
            MatchesTab()
                .tabItem {
                    Image(systemName: "bolt")
                    Text("Matches")
                }
        }
        .edgesIgnoringSafeArea(.top)
    }
}

struct MainTabView_Previews: PreviewProvider {
    static var previews: some View {
        MainTabView()
        .environmentObject(getMockFriendsController())
    }
}
