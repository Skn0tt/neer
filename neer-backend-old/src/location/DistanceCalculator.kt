package de.simonknott.neer.location

import com.grum.geocalc.Coordinate
import com.grum.geocalc.EarthCalc
import com.grum.geocalc.Point

data class Location(val latitude: Double, val longitude: Double)

object DistanceCalculator {

    private fun Location.toGeoCalcPoint() =
        Point.at(
            Coordinate.fromDegrees(this.latitude),
            Coordinate.fromDegrees(this.longitude)
        )

    /**
     * @see https://en.wikipedia.org/wiki/Vincenty's_formulae
     * @return distance in Meters
     */
    fun calculateDistance(locationA: Location, locationB: Location) =
        EarthCalc.vincentyDistance(
            locationA.toGeoCalcPoint(),
            locationB.toGeoCalcPoint()
        )

}