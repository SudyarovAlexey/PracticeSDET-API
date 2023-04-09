package steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.Pokemon;
import models.PokemonResource;

import java.io.IOException;
import java.util.Map;

public class Steps {

    private static final AllureRestAssured allureRestAssured = new AllureRestAssured();
    private static final RequestSpecification requestSpecification;

    static {
        requestSpecification = RestAssured.given()
                .filter(allureRestAssured)
                .baseUri("https://pokeapi.co/api/v2/")
                .contentType(ContentType.JSON);
    }

    @Step("Получение данных покемона")
    public static Pokemon getPokemon(String value) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode pokemonJson = requestSpecification
                .pathParam("pathParam", value)
                .when()
                .log().all()
                .get("pokemon/{pathParam}")
                .then()
                .statusCode(200)
                .extract().as(JsonNode.class);
        try {
            return mapper.readValue(mapper.treeAsTokens(pokemonJson), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pokemon();
    }

    @Step("Получение списка покемонов")
    public static PokemonResource getPokemonList(Map<String, Integer> requestParams) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode resourceJson = requestSpecification
                .when()
                .log().all()
                .get("pokemon?limit=" + requestParams.get("limit"))
                .then()
                .extract().as(JsonNode.class);
        try {
            return mapper.readValue(mapper.treeAsTokens(resourceJson), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PokemonResource();
    }
}
