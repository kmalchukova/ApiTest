import io.restassured.RestAssured
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Test

class ApiTest {

    @Test
    fun testApiPostAndGet(){
        val order = "[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"username\": \"kate24\",\n" +
                "    \"firstName\": \"Kate\",\n" +
                "    \"lastName\": \"Lake\",\n" +
                "    \"email\": \"kate24@mail.com\",\n" +
                "    \"password\": \"1234\",\n" +
                "    \"phone\": \"8956235\",\n" +
                "    \"userStatus\": 0\n" +
                "  }\n" +
                "]"
        RestAssured.given()
            .log().all()
            .request()
            .contentType(io.restassured.http.ContentType.JSON)
            .body(order)
            .post("https://petstore.swagger.io/v2/user/createWithArray")
            .then()

        RestAssured.given()
            .request()
            .contentType(io.restassured.http.ContentType.JSON)
            .get("https://petstore.swagger.io/v2/user/kate24")
            .then().log().all()
            .assertThat().statusCode(200)
            .and()
            .body("email", notNullValue())
    }

    @Test
    fun testApiPutAndDelete(){
        RestAssured.given()
            .request()
            .contentType(io.restassured.http.ContentType.JSON)
            .get("https://petstore.swagger.io/v2/user/login?username=kate&password=fff")
            .then().log().all()
            .assertThat().statusCode(200)

        val user = "{\n" +
                "  \"id\": 0,\n" +
                "  \"username\": \"Mika\",\n" +
                "  \"firstName\": \"string\",\n" +
                "  \"lastName\": \"string\",\n" +
                "  \"email\": \"string\",\n" +
                "  \"password\": \"string\",\n" +
                "  \"phone\": \"string\",\n" +
                "  \"userStatus\": 0\n" +
                "}"

        RestAssured.given()
            .request()
            .contentType(io.restassured.http.ContentType.JSON)
            .body(user)
            .put("https://petstore.swagger.io/v2/user/mika")
            .then().log().all()
            .assertThat().statusCode(200)

        RestAssured.given()
            .request()
            .contentType(io.restassured.http.ContentType.JSON)
            .body(user)
            .delete("https://petstore.swagger.io/v2/user/lena")
            .then().log().all()
            .assertThat().statusCode(404)
    }
}