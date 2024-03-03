package LabirintTests.Block;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header {
    private final WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    public void search(String value) throws InterruptedException {
        driver.findElement(By.cssSelector("#search-field")).clear();
        driver.findElement(By.cssSelector("#search-field")).sendKeys(value, Keys.RETURN);
        Thread.sleep(2*1000);
    }

    public String getCartCounter(){
        return driver.findElement(By.cssSelector("span.basket-in-cart-a")).getText();
    }

    public Header awaitLoadCartCounter(String textToBe){
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.textToBe(By.cssSelector("span.basket-in-cart-a"), textToBe));
        return this;
    }
}
