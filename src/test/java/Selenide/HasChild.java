package Selenide;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementCondition;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;

public class HasChild extends WebElementCondition {
    private final String tagName;

    private HasChild(String tagName) {
        super("HasChild " + tagName);
        this.tagName = tagName;
    }

    public static WebElementCondition hasChildWithTag(String tagName) {
        return new HasChild(tagName);
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, WebElement element) {
        try {
            WebElement tagElement = element.findElement(By.cssSelector("*"));
            String tag = tagElement.getTagName();
            System.out.println(tag);
            return new CheckResult(tag.equalsIgnoreCase(tagName), "Имя тега " + tagName + " не соответствует " + tag);
        } catch (NoSuchElementException nosuch) {
            return CheckResult.rejected("No such element exception", null);
        }
    }
}
