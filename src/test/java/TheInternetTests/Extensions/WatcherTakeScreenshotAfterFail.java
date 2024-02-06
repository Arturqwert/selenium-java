package TheInternetTests.Extensions;

import TheInternetTests.Infrastructure.WebDriverProvider;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WatcherTakeScreenshotAfterFail implements TestWatcher {
    private WebDriver driver = null;
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {

        try {
            setDriver(context);
            takeScreenshot();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            closeDriver();
        }
    }

    private void setDriver(ExtensionContext context) {
        var instance = context.getTestInstances().get().getAllInstances().get(0);
        driver = ((WebDriverProvider)instance).getDriver();
    }

    private void takeScreenshot() {
        try {
            File screenshot = driver.findElement(By.cssSelector("html")).getScreenshotAs(OutputType.FILE);
            Files.move(screenshot.toPath(), Path.of("./src/main/resources/screenshots/"
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm_dd_MM_yyyy"))
                    + screenshot.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        setDriver(context);
        closeDriver();
    }

    private void closeDriver() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}