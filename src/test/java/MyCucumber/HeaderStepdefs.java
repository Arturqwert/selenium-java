package MyCucumber;

import io.cucumber.java.ru.Дано;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HeaderStepdefs {
    private WebDriver driver;
    public HeaderStepdefs(DriverManager driver) {
        this.driver = driver.getInstance();
    }

    @Дано("пользователь ввел слово {string} в строку поиска")
    public void pasteWordInSearch(String term){
        driver.findElement(By.cssSelector("#search-field")).sendKeys(term);
    }

    @Дано("пользователь нажал кнопку поиск")
    public void goSearch(){
        driver.findElement(By.cssSelector("#search-field")).sendKeys(Keys.RETURN);
    }

}
