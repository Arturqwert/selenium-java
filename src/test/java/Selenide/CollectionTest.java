package Selenide;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CollectionTest {

    @Test
    public void collectionTest(){
        open("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");

        $("#my-dropdown-1").click();

        ElementsCollection elements = $(".dropdown-menu.show").$$("li");

        elements.shouldBe(CollectionCondition.size(5));
    }
}
