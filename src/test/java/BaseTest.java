import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    protected final RequestSpecification requestSpecification = given()
            .baseUri("https://pokeapi.co/api/v2/")
            .contentType(ContentType.JSON);
}