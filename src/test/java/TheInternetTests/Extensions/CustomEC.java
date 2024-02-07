package TheInternetTests.Extensions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomEC {
    public static ExpectedCondition<WebElement> cssPropertyToBe(By locator, String cssProperty, String cssValueToBe){
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver input) {
                WebElement element = input.findElement(locator);
                String cssValue = input.findElement(locator).getCssValue(cssProperty);
                System.out.println(cssValue);
                if(cssValue.equals(cssValueToBe)) {
                    return element;
                }

                return null;
            }
        };
    }
}
