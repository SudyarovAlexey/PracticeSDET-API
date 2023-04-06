import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.Steps;

import java.util.List;

import static org.hamcrest.Matchers.lessThan;

public class ApiTests extends BaseTest {

    @Test
    @Description("Сравнение веса покемона Pidgeotto и веса покемона Rattata")
    public void compareWeights() {
        JsonNode pidgeottoNode = Steps.getPokemon(requestSpecification, "pidgeotto");
        JsonNode rattataNode = Steps.getPokemon(requestSpecification, "rattata");
        int weightPidgeotto = Steps.getPokemonWeight(pidgeottoNode);
        int weightRattata = Steps.getPokemonWeight(rattataNode);
        org.hamcrest.MatcherAssert.assertThat(weightRattata, lessThan(weightPidgeotto));
    }

    @Test
    @Description("Проверка наличия способности run-away у покемона Rattata")
    public void hasAbilityRunAway() {
        JsonNode rattataNode = Steps.getPokemon(requestSpecification, "rattata");
        List<String> list = Steps.getAbilityList(rattataNode);
        Assert.assertTrue(list.contains("run-away"));
    }

    @Test
    @Description("Получение списка покемонов и проверка наличия их имен (not null)")
    public void checkList() {
        int countPokemon = Steps.getSizePokemonList(requestSpecification);
        for (int i = 1; i < countPokemon + 100; i = i + 100) {
            JsonNode resourceJson = Steps.getPokemonSublist(requestSpecification, i);
            List<String> nameList = Steps.getNameList(resourceJson);
            nameList.forEach(Assert::assertNotNull);
        }
    }
}
