import io.restassured.RestAssured
import org.junit.AfterClass
import org.junit.BeforeClass
import redis.clients.jedis.Jedis

abstract class RestAssuredTestSuite {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            RestAssured.baseURI = System.getenv("API_BASE_URI") ?: throw IllegalArgumentException("API_BASE_URI is required env var")
        }

        fun dropRedis() {
            val jedis = Jedis("localhost", 6379)
            jedis.flushAll()
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            dropRedis()
        }
    }

}