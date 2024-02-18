package Selenide;

import com.codeborne.selenide.*;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static Selenide.HasChild.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class CollectionTest {

    @BeforeAll
    public static void setUp() {
        Configuration.screenshots = false;
        Configuration.savePageSource = false;
        SelenideLogger.addListener("Allure",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false));
    }

    @Test
    public void collectionTest() {
        step("Перейти на страницу.", () -> {
            open("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
        });

        step("Открыть список.", () -> {
            $("#my-dropdown-1").click();
        });

        ElementsCollection elements = $(".dropdown-menu.show")
                .$$("li");

        step("Спиосок содержит 4 ссылки.", () -> {
            elements
                    .filterBy(hasChildWithTag("a"))
                    .shouldHave(CollectionCondition.size(45));
        });

        step("Список содержит 5 элементов.", () -> {
            elements.shouldBe(CollectionCondition.size(5));
        });
    }
}
