//
//  MapView.swift
//  Neer
//
//  Created by Simon Knott on 12.01.20.
//  Copyright © 2020 Simon Knott. All rights reserved.
//

import SwiftUI
import MapKit

struct MapView: UIViewRepresentable {
    var location: CLLocationCoordinate2D
    var radius: Int
    
    func makeUIView(context: Context) -> MKMapView {
        let view = MKMapView(frame: .zero)
        
        view.delegate = context.coordinator
        
        return view
    }

    func updateUIView(_ view: MKMapView, context: Context) {
        let region = MKCoordinateRegion(center: location, latitudinalMeters: Double(radius * 4), longitudinalMeters: Double(radius * 4))
        view.setRegion(region, animated: true)
    
        view.removeOverlays(view.overlays)
        let overlay = MKCircle(center: location, radius: Double(radius))
        view.addOverlay(overlay)
    }
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    class Coordinator: NSObject, MKMapViewDelegate {
        var mapview: MapView

        init(_ mapview: MapView) {
            self.mapview = mapview
        }
        
        func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer {
            if overlay is MKCircle {
                let renderer = MKCircleRenderer(overlay: overlay)
                // TODO: Animate Radar
                renderer.strokeColor = .red
                renderer.lineWidth = 1
                renderer.fillColor = UIColor(red: 255, green: 0, blue: 0, alpha: 0.2)
                return renderer
            }
            
            return MKOverlayRenderer()
        }
        
        
    }
}

struct MapView_Previews: PreviewProvider {
    static var previews: some View {
        MapView(
            location: CLLocationCoordinate2D(
                latitude: 34.011_286,
                longitude: -116.166_868
            ),
            radius: 500
        )
    }
}
