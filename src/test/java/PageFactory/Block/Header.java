package PageFactory.Block;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header {
    private final WebDriver driver;
    @FindBy(css = "#search-field")
    private WebElement search;
    @FindBy(css = "span.basket-in-cart-a")
    private WebElement cartIcon;

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    public void search(String value)
    {
        search.clear();
        search.sendKeys(value, Keys.RETURN);
    }

    public int getCartCounter(){
        return Integer.parseInt(cartIcon.getText());
    }

    public Header awaitLoadCartCounter(int textToBe){
        new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.textToBe(By.cssSelector("span.basket-in-cart-a"), Integer.toString(textToBe)));
        return this;
    }
}
