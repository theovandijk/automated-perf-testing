import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class OtherTest {


    @Before
    public void before() {
    }

    @Test
    public void getAllUsers() {
        given().when()
               .get("/users")
               .then()
               .statusCode(200);
    }

    @Test
    public void getAllBooks() {
        given().when()
               .get("/books")
               .then()
               .statusCode(200);
    }

}