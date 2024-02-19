package ScreenshotTests;

import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import static com.galenframework.api.Galen.checkLayout;

public class GalenTest {
    @Test
    public void githubTest() throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://github.com/login");
        driver.findElement(By.cssSelector("#login_field")).sendKeys("TestUser");


        LayoutReport report = checkLayout(driver, "src/test/resources/specs/github_login.spec", List.of("desktop"));
        List<GalenTestInfo> tests = new LinkedList<>();
        GalenTestInfo test = GalenTestInfo.fromString("Sign in page on desktop");
        test.getReport().layout(report, "form check");
        tests.add(test);
        new HtmlReportBuilder().build(tests, "galen/reports");

        driver.quit();
    }
}