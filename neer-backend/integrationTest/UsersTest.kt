import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.*
import org.hamcrest.Matchers.hasKey
import org.hamcrest.Matchers.nullValue
import org.junit.Test

class UsersTest : RestAssuredTestSuite() {

    @Test()
    fun testNoPhoneNumber() {
        Given {
            body("""{ "phoneNumber": null }""")
            contentType(ContentType.JSON)
        } When {
            post("/users")
        } Then {
            statusCode(200)
            body("$", hasKey("userId"))
            body("$", hasKey("refreshToken"))
        }
    }

    @Test()
    fun testValidPhoneNumber() {
        Given {
            body("""{ "phoneNumber": "+44 7700 900728" }""")
            contentType(ContentType.JSON)
        } When {
            post("/users")
        } Then {
            statusCode(202)
            body("$", hasKey("userId"))
            body("refreshToken", nullValue())
        }
    }

    @Test()
    fun testInvalidPhoneNumber() {
        Given {
            body("""{ "phoneNumber": "07700 900728" }""")
            contentType(ContentType.JSON)
        } When {
            post("/users")
        } Then {
            statusCode(400)
        }
    }

}