package Selenide.POM.Page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AuthPage {

    private SelenideElement email = $("#username");
    private SelenideElement pass = $("#password");
    private SelenideElement login = $(".radius");

    public void open(){
        Selenide.open("https://the-internet.herokuapp.com/login");
    }

    public SelenideElement setLogin(String login) {
        return email.val(login);
    }

    public SelenideElement setPass(String pass){
        return this.pass.val(pass);
    }

    public void login(){
        login.click();
    }

}
