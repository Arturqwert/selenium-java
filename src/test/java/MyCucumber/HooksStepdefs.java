package MyCucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HooksStepdefs {
    private WebDriver driver;
    public HooksStepdefs(DriverManager driver) {
        this.driver = driver.getInstance();
    }

    @After
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}
