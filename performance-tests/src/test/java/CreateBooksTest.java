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

public class CreateBooksTest {

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @Before
    public void before() {
        List<Book> books = when().get("/books")
                                 .then()
                                 .extract()
                                 .response()
                                 .jsonPath()
                                 .getList("", Book.class);
        for (Book book : books) {
            delete("/books/" + book.getId()).then()
                                            .statusCode(204);
        }
    }

    @Test
    @PerfTest(invocations = 100, threads = 20, rampUp = 1000)
    @Required(max = 250, average = 60)
    public void createBook() {
        String uniqueIsbn = MessageFormat.format("012-{0}", UUID.randomUUID()
                                                                .toString()
                                                                .substring(0, 10));

        Book book = new Book();
        book.setTitle("The title");
        book.setAuthor("Tester");
        book.setPublisher("deTesters");
        book.setIsbn(uniqueIsbn);
        book.setPublicationDate("2017-11-01");

        given().contentType("application/json")
               .body(book)
               .when()
               .post("/books")
               .then()
               .statusCode(200);
    }
}