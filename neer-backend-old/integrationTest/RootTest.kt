import org.junit.Test
import io.restassured.RestAssured.*
import org.hamcrest.Matchers.*

class RootTest : RestAssuredTestSuite() {
    @Test()
    fun testRoot() {
        get("/")
            .then()
            .statusCode(200)
            .contentType("text/plain")
            .body(equalTo("Hello World"))
    }
}