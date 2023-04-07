package steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.Pokemon;
import models.PokemonResource;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Steps {

    private static final AllureRestAssured allureRestAssured = new AllureRestAssured();

    @Step("Получение данных покемона")
    public static Pokemon getPokemon(String value) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode pokemonJson = given()
                .filter(allureRestAssured)
                .baseUri("https://pokeapi.co/api/v2/")
                .contentType(ContentType.JSON)
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
        JsonNode resourceJson = given()
                .filter(allureRestAssured)
                .baseUri("https://pokeapi.co/api/v2/")
                .contentType(ContentType.JSON)
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
