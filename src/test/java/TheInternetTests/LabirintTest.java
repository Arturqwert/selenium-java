package TheInternetTests;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumJupiter.class)
public class LabirintTest {

    private final String url = "https://www.labirint.ru/";

    @Test
    public void shouldBeEquivalentAmountGoodsInBasketAndOnPage(ChromeDriver driver) throws InterruptedException {
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().addCookie(new Cookie("cookie_policy", "1"));


        driver.findElement(By.cssSelector("#search-field")).sendKeys("java", Keys.RETURN);
        driver.findElement(By.cssSelector(".sorting-value")).click();
        driver.findElement(By.cssSelector("[data-event-content='высокий рейтинг']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[@class='filter-reset__content' and text()[contains(., 'жидаются')]]/div[@class='filter-reset__icon header-sprite']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[@class='filter-reset__content' and text()[contains(., 'Нет в продаже')]]/div[@class='filter-reset__icon header-sprite']")).click();
        Thread.sleep(2000);

        List<WebElement> btns = driver.findElements(By.cssSelector(".btn-tocart"));

        for (WebElement btn : btns) {
            btn.click();
        }

        String test = String.valueOf(btns.size());

        String goodsFromBtns = Integer.toString(btns.size());
        String goodsInBasket = driver.findElement(By.cssSelector("span.basket-in-cart-a")).getText();

        assertEquals(goodsFromBtns, goodsFromBtns);

    }
}
