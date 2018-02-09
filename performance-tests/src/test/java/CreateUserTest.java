import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.*;

public class CreateUserTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @Before
    public void before() throws Throwable {
        List<User> users = when().get("/users")
                                 .then()
                                 .extract()
                                 .response()
                                 .jsonPath()
                                 .getList("", User.class);
        for (User user : users) {
            delete("/users/" + user.getId()).then()
                                            .statusCode(204);
        }
    }

    @Test
    @PerfTest(invocations = 100, threads = 20, rampUp = 1000)
    @Required(max = 250, average = 50)
    public void createUser() {
        String uniqueEmail = MessageFormat.format("tester{0}@user.com", UUID.randomUUID()
                                                                            .toString()
                                                                            .substring(0, 5));
        User newUser = new User();
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newUser.setEmailAddress(uniqueEmail);
        newUser.setProfilePicture("http://www.gravatar.com/avatar/327b0fc14f66878633ee3633a36d4889.png");

        given().contentType("application/json")
               .body(newUser)
               .when()
               .post("/users")
               .then()
               .statusCode(200);
    }
}