package Selenide.POM;
import Selenide.POM.Page.AuthPage;
import Selenide.POM.Page.ContentPage;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.impl.SelenidePageFactory;
import org.junit.jupiter.api.Test;

//@ExtendWith(BrowserPerTestStrategyExtension.class) поднимает браузер по дефолту на каждый тест
public class LoginTest {

    private final String EMAIL = "tomsmith";
    private final String PASS = "SuperSecretPassword!";

    @Test
    public void shouldLogin(){
        AuthPage page = new SelenidePageFactory().page(WebDriverRunner.driver(), AuthPage.class);
        //AuthPage page = new AuthPage();

        page.open();
        page.setLogin(EMAIL);
        page.setPass(PASS);
        page.login();

        new ContentPage().authSholdBeSuccess();
    }
}
