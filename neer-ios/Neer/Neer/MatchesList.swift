//
//  MatchesList.swift
//  Neer
//
//  Created by Simon Knott on 11.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI

struct MatchesList: View {
    var matches: [Friend]
    
    var body: some View {
        NavigationView {
            List(matches) { match in
                NavigationLink(destination: ChatView()) {
                    Text(match.getDisplayName())
                        .padding(10)
                }
            }
            .navigationBarTitle("Matches")
        }
    }
}

struct MatchesList_Previews: PreviewProvider {
    static var previews: some View {
        MatchesList(matches: [Friend(username: "match1", isDesired: true)])
    }
}
