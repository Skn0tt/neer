import com.grum.geocalc.Coordinate
import com.grum.geocalc.EarthCalc
import com.grum.geocalc.Point

data class Location(val latitude: Double, val longitude: Double)

private fun Location.toGeoCalcPoint() =
    Point.at(
        Coordinate.fromDegrees(this.latitude),
        Coordinate.fromDegrees(this.longitude)
    )

fun calculateDistance(locationA: Location, locationB: Location) =
    EarthCalc.vincentyDistance(
        locationA.toGeoCalcPoint(),
        locationB.toGeoCalcPoint()
    )