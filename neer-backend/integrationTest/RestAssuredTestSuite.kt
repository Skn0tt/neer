import io.restassured.RestAssured
import org.junit.BeforeClass

abstract class RestAssuredTestSuite {

    companion object {
        @BeforeClass
        @JvmStatic
        fun startServer() {
            RestAssured.baseURI = "http://localhost"
            RestAssured.port = 3000
        }
    }

}