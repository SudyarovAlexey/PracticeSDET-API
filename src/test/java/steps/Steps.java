package steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import models.Pokemon;
import models.PokemonResource;
import models.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Steps {

    @Step("Получение данных покемона")
    public static JsonNode getPokemon(RequestSpecification requestSpecification, String value) {
        return requestSpecification
                .pathParam("pathParam", value)
                .when()
                .log().all()
                .get("pokemon/{pathParam}")
                .then()
                .statusCode(200)
                .extract().as(JsonNode.class);
    }

    @Step("Получение значения веса покемона")
    public static int getPokemonWeight(JsonNode node) {
        ObjectMapper mapper = new ObjectMapper();
        int weight = 0;
        try {
            weight = mapper.readValue(mapper.treeAsTokens(node), new TypeReference<Pokemon>() {
            }).getWeight();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return weight;
    }

    @Step("Проверка наличия способности")
    public static List<String> getAbilityList(JsonNode node) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> resultList = new ArrayList<>();
        try {
            Pokemon pokemon = mapper.readValue(mapper.treeAsTokens(node), new TypeReference<>() {
            });
            resultList = pokemon
                    .getAbilities()
                    .stream()
                    .map(e -> e.getAbility().getName())
                    .collect(Collectors.toList());
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Step("Получение размера полного списка покемонов")
    public static int getSizePokemonList(RequestSpecification requestSpecification) {
        return requestSpecification
                .when()
                .log().all()
                .get("pokemon")
                .then()
                .extract().response().getBody()
                .as(PokemonResource.class)
                .getCount();
    }

    @Step("Получение части списка покемонов")
    public static JsonNode getPokemonSublist(RequestSpecification requestSpecification, int count) {
        return requestSpecification
                .when()
                .log().all()
                .get("pokemon?offset=" + count + "&limit=1000")
                .then()
                .extract().as(JsonNode.class);
    }

    @Step("Получение списка имен покемонов")
    public static List<String> getNameList(JsonNode resourceJson) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> nameList = new ArrayList<>();
        try {
            PokemonResource resource = mapper.readValue(mapper.treeAsTokens(resourceJson), new TypeReference<>() {
            });
            nameList = resource.getResults().stream().map(Result::getName).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameList;
    }
}
