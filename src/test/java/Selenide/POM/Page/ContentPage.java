package Selenide.POM.Page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Selenide.$;

public class ContentPage {

    private SelenideElement flash = $("#flash");

    public void authSholdBeSuccess(){
        flash.shouldHave(cssClass("success"));
    }
}
