package Selenide;

import com.codeborne.selenide.AssertionMode;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@ExtendWith({ScreenShooterExtension.class, SoftAssertsExtension.class})
public class LoginTest {

    @BeforeAll
    public static void setUp(){
        Configuration.browser = "edge";
        Configuration.screenshots = false;
        Configuration.headless = false;
        Configuration.timeout = 1000;
        Configuration.assertionMode = AssertionMode.SOFT;
    }

    @Test
    public void shouldLogin(){
        open("https://the-internet.herokuapp.com/login");

        $("#username").sendKeys("tomsmith");
        $("#password")
                .shouldHave(attribute("type", "password"))
                .sendKeys("SuperSecretPassword!");

        screenshot("auth");

        $(".radius").click();

        $("#flash").shouldHave(cssClass("success"));
    }
}
