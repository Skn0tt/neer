//
//  FCMToken.swift
//  Neer
//
//  Created by Simon Knott on 12.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import Foundation
import FirebaseInstanceID

func getFCMToken(handler: @escaping (String) -> Void) {
    InstanceID.instanceID().instanceID { (result, error) in
        if let error = error {
            print("Error fetching remote instance ID: \(error)")
        } else if let result = result {
            print("Remote instance ID token: \(result.token)")
            handler(result.token)
        }
    }
}
