import io.restassured.RestAssured.*
import io.restassured.http.ContentType
import org.hamcrest.Matchers.hasKey
import org.hamcrest.Matchers.nullValue
import org.junit.Test

class UsersTest : RestAssuredTestSuite() {

    @Test()
    fun testNoPhoneNumber() {
        given()
            .body("""{ "phoneNumber": null }""")
            .contentType(ContentType.JSON)
        .When()
            .post("/users")
        .then()
            .statusCode(200)
            .body("$", hasKey("userId"))
            .body("$", hasKey("refreshToken"))
    }

    @Test()
    fun testValidPhoneNumber() {
        given()
            .body("""{ "phoneNumber": "+44 7700 900728" }""")
            .contentType(ContentType.JSON)
        .When()
            .post("/users")
        .then()
            .statusCode(202)
            .body("$", hasKey("userId"))
            .body("refreshToken", nullValue())
    }

    @Test()
    fun testInvalidPhoneNumber() {
        given()
            .body("""{ "phoneNumber": "07700 900728" }""")
            .contentType(ContentType.JSON)
        .When()
            .post("/users")
        .then()
            .statusCode(400)
    }

}