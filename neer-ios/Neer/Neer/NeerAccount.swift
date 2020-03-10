//
//  NeerAccount.swift
//  Neer
//
//  Created by Simon Knott on 12.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import Foundation
import KeychainAccess
import Just
import SwiftyRSA

private var keychain = Keychain(service: "de.simonknott.neer")

private let private_cert_key = "private_cert"

private func getPrivateCert() -> String? {
    return keychain[string: private_cert_key]
}

private func setPrivateCert(_ cert: String) {
    keychain[private_cert_key] = cert
}

private let username_key = "username"

private func setUsername(_ v: String) {
    keychain[username_key] = v
}

func getUsername() -> String? {
    return keychain[username_key]
}

func sign(string: String, withCert cert: String) -> String {
    let key = try! PublicKey(base64Encoded: cert)
    let clear = try! ClearMessage(string: string, using: .utf8)
    let encrypted = try! clear.encrypted(with: key, padding: .PKCS1)
    return "\(string):\(encrypted)"
}

func generateRandomString(length: Int) -> String {
    let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return String((0..<length).map { _ in letters.randomElement()! })
}

func createAuthToken() -> String {
    let rand = generateRandomString(length: 12)
    let cert = getPrivateCert()!
    let signature = sign(string: rand, withCert: cert)
    return "\(rand):\(signature)"
}

func hasAccount() -> Bool {
    return getPrivateCert() != nil;
}

func signUp(witUsername username: String, onSuccess: @escaping () -> Void) {
    getFCMToken() { token in
        Just.post(
            "\(serverBaseUrl)/createAccount",
            requestBody: "\(username):\(token)".data(using: .utf8)
        ) { r in
            if r.ok {
                let privCert = r.content!
                setUsername(username)
                setPrivateCert(String(data: privCert, encoding: .utf8)!)
                onSuccess()
            }
        }
    }
}
