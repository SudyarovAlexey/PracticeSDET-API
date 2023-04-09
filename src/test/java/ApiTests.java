import io.qameta.allure.Description;
import models.Pokemon;
import models.Result;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.Steps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.lessThan;

public class ApiTests {

    @Test
    @Description("Сравнение веса покемона Pidgeotto и веса покемона Rattata. " +
            "Проверка наличия способности 'run-away' у покемона Rattata")
    public void compareWeightsAndCheckAbility() {
        Pokemon pidgeotto = Steps.getPokemon("pidgeotto");
        Pokemon rattata = Steps.getPokemon("rattata");
        org.hamcrest.MatcherAssert.assertThat(rattata.getWeight(), lessThan(pidgeotto.getWeight()));
        Assert.assertTrue(rattata.getAbilities()
                .stream()
                .map(e -> e.getAbility().getName()).collect(Collectors.toList())
                .contains("run-away"));
    }

    @Test
    @Description("Проверка ограничения списка (limit) покемонов и проверка наличия имени (name) у каждого покемона в" +
            "ограниченном списке")
    public void checkLimitAndNameAvailability() {
        int limit = 10;
        Map<String, Integer> requestParams = Map.of("limit", limit);
        List<Result> resultList = Steps.getPokemonList(requestParams).getResults();
        Assert.assertEquals(resultList.size(), limit);
        resultList.stream().map(Result::getName).collect(Collectors.toList()).forEach(Assert::assertNotNull);
    }
}
