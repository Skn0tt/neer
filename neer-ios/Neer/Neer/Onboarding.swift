//
//  Onboarding.swift
//  Neer
//
//  Created by Simon Knott on 10.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI

struct Onboarding: View {
    @State var username: String = ""
    
    var body: some View {
        VStack {
            Text("please set your username")
            TextField("Your Username", text: $username)
            Button(action: self.onPressOK) {
                Text("OK")
            }
        }
    }
    
    func onPressOK() {
        createAccount(username: username)
    }
    
    func getPushNotificationToken() -> String {
        return "mocktoken"
    }
    
    func createAccount(username: String) {
        // TODO: Use some http package
    }
}

struct Onboarding_Previews: PreviewProvider {
    static var previews: some View {
        Onboarding()
    }
}
