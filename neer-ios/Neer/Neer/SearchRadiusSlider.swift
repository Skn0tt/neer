//
//  SearchRadiusSlider.swift
//  Neer
//
//  Created by Simon Knott on 11.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI
import MapKit

func formatAsDistance(_ dist: Double) -> String {
    if (dist < 1000) {
        return String(Int(dist)) + " m"
    } else {
        return String(dist / 1000.0) + " km"
    }
}

struct SearchRadiusSlider: View {
    @State var searchRadius = 500.0
    
    // TODO: Add Label
    
    func onChange(v: Bool) -> Void {
        // TODO: Send change to backend
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            MapView(location: CLLocationCoordinate2D(
            latitude: 52.394553, longitude: 13.1288944), radius: Int(searchRadius))
            VStack(alignment: .leading) {
                Text("Search Radius")
                    .foregroundColor(.gray)
                HStack {
                    Slider(
                        value: $searchRadius,
                        in: 500.0...10000.0,
                        step: 500.0,
                        onEditingChanged: onChange
                    )
                    Text(formatAsDistance(searchRadius))
                }
            }
            .padding(.horizontal, 16)
            
        }
    }
}

struct SearchRadiusSlider_Previews: PreviewProvider {
    static var previews: some View {
        SearchRadiusSlider()
    }
}
