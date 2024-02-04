package TheInternetTests.Extensions;

import TheInternetTests.WebElementInterfaceTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WatcherTakeScreenshotAfterFail implements TestWatcher {
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {

        WebDriver driver = null;
        try {
            var t = context.getTestInstances().get().getAllInstances().get(0);
            driver = ((WebElementInterfaceTest) t).driver;

            File screenshot = driver.findElement(By.cssSelector("html")).getScreenshotAs(OutputType.FILE);
            try {
                Files.move(screenshot.toPath(), Path.of("./src/main/resources/screenshots/" + screenshot.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("watcher");
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
