package MyCucumber;

import io.cucumber.java.ru.Дано;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class MainPageStepdefs {
    private WebDriver driver;
    private final String url = "https://www.labirint.ru/";

    public MainPageStepdefs(DriverManager driver) {
        this.driver = driver.getInstance();
    }

    @Дано("пользователь перешел на главную страницу")
    public void goToPage(){
        driver.get(url);
        driver.manage().addCookie(new Cookie("cookie_policy", "1"));
    }
}
