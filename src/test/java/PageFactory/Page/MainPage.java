package PageFactory.Page;

import PageFactory.Page.Page;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

public class MainPage extends Page {
    private final String url = "https://www.labirint.ru/";
    public MainPage(WebDriver driver) {
        super(driver);
    }


    @Step("Открыть главную страницу.")
    public void getWithConditions(){
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().addCookie(new Cookie("cookie_policy", "1"));
        getScreenshot();
    }

    public void get(){
        driver.get(url);
    }

    @Attachment(value = "Скрин главной страницы.", type = "image/png")
    public byte[] getScreenshot(){
        return driver.findElement(By.cssSelector("body")).getScreenshotAs(OutputType.BYTES);
    }
}
