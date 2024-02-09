package LabirintTests.Page;

import LabirintTests.Block.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class MainPage extends Page{
    private final String url = "https://www.labirint.ru/";
    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void get(){
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().addCookie(new Cookie("cookie_policy", "1"));
    }
}
