package PageFactory.Page;

import PageFactory.Page.Page;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class MainPage extends Page {
    private final String url = "https://www.labirint.ru/";
    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void getWithConditions(){
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().addCookie(new Cookie("cookie_policy", "1"));
    }

    public void get(){
        driver.get(url);
    }
}
