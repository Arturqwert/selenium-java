package MyCucumber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {
    public WebDriver instance;

    public WebDriver getInstance(){
        if(instance == null){
            instance = new ChromeDriver();
            instance.manage().window().maximize();
        }
        return instance;
    }
}
