//
//  RiveViewFactory.swift
//  iosApp
//
//  Created by Mert Vurgun on 7.04.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import UIKit
import RiveRuntime

@objc public class RiveViewFactory: NSObject {

    @objc public static func createRiveView(_ fileName: String) -> UIView {
        let riveView = RiveView()

        do {
            // You can switch based on character type
            let file = try RiveFile(name: fileName)
            try riveView.configure(with: file)
        } catch {
            print("Failed to load Rive file: \(error)")
        }

        return riveView
    }
}

