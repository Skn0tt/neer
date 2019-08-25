import io.restassured.RestAssured
import org.junit.BeforeClass

abstract class RestAssuredTestSuite {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            RestAssured.baseURI = System.getenv("API_BASE_URI") ?: throw IllegalArgumentException("API_BASE_URI is required env var")
        }
    }

}