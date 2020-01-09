package location

import de.simonknott.neer.location.DistanceCalculator
import de.simonknott.neer.location.Location
import testutil.IsCloseTo.closeTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class DistanceCalculatorTest(
    private val a: Location,
    private val b: Location,
    private val expectedDistanceInKm: Double
) {

    @Test
    fun testCalculateDistance() {
        val actualInM = DistanceCalculator.calculateDistance(a, b)
        assertThat(actualInM, closeTo(expectedDistanceInKm * 1000, .001))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: calculateDistance({0}, {1})={2}")
        fun data(): Iterable<Array<Any>> {
            return arrayListOf(
                arrayOf(
                    Location(27.09353, 128.31938),
                    Location(-35.83661, 10.53900),
                    14131.51
                ),
                arrayOf(
                    Location(50.729617, 7.089073),
                    Location(50.726270, 7.080236),
                    0.72590
                )
            )
        }
    }

}