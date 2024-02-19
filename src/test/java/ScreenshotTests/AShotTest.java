package ScreenshotTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.comparison.ImageMarkupPolicy;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class AShotTest {
//    @BeforeAll
//    public static void generateImageToBe() throws IOException, InterruptedException {
//        WebDriver driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        driver.manage().window().maximize();
//        driver.get("https://github.com/login");
//
//        Thread.sleep(3000);
//
//        Screenshot screenshot = new AShot()
//                .takeScreenshot(driver);
//        BufferedImage asIs = screenshot.getImage();
//        ImageIO.write(asIs, "png", Files.createFile(Path.of("to_be_page.png")).toFile());
//
//        driver.quit();
//    }

    @BeforeAll
    public static void generateFormToBe() throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://github.com/login");

        Thread.sleep(3000);

        WebElement form = driver.findElement(By.cssSelector(".auth-form-body"));

        Screenshot screenshot = new AShot()
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(driver, form);
        BufferedImage asIs = screenshot.getImage();
        ImageIO.write(asIs, "png", Files.createFile(Path.of("src/test/resources/specs/img/form_to_be.png")).toFile());

        driver.quit();
    }

    @Test
    public void githubPageTest() throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://github.com/login");
        driver.findElement(By.cssSelector("#login_field")).sendKeys("TestUser");
        Thread.sleep(3000);

        // get page as is
        Screenshot screenshot = new ru.yandex.qatools.ashot.AShot().takeScreenshot(driver);
        BufferedImage asIs = screenshot.getImage();

        // get page to be
        BufferedImage toBe = ImageIO.read(new File("to_be_page.png"));

        // get diff
        ImageDiffer differ = new ImageDiffer().withDiffMarkupPolicy(new ImageMarkupPolicy());
        ImageDiff imageDiff = differ.makeDiff(toBe, asIs);

        String filename = System.currentTimeMillis() + ".png";
        ImageIO.write(imageDiff.getMarkedImage(), "png", Files.createFile(Path.of("marked" + filename)).toFile());
        ImageIO.write(imageDiff.getTransparentMarkedImage(), "png", Files.createFile(Path.of("transparent" + filename)).toFile());

        driver.quit();

        assertFalse(imageDiff.hasDiff());
    }

    @Test
    public void formTest() throws InterruptedException, IOException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://github.com/login");
        driver.findElement(By.cssSelector("#login_field")).sendKeys("TestUser");
        Thread.sleep(3000);

        // get page as is

        WebElement form = driver.findElement(By.cssSelector(".auth-form-body"));

        Screenshot screenshot = new AShot()
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(driver, form);
        BufferedImage asIs = screenshot.getImage();

        // get page to be
        BufferedImage toBe = ImageIO.read(new File("to_be_page.png"));

        // get diff
        ImageDiffer differ = new ImageDiffer().withDiffMarkupPolicy(new ImageMarkupPolicy());
        ImageDiff imageDiff = differ.makeDiff(toBe, asIs);

        String filename = System.currentTimeMillis() + ".png";
        ImageIO.write(imageDiff.getMarkedImage(), "png", Files.createFile(Path.of("marked" + filename)).toFile());
        ImageIO.write(imageDiff.getTransparentMarkedImage(), "png", Files.createFile(Path.of("transparent" + filename)).toFile());

        driver.quit();

        assertFalse(imageDiff.hasDiff());
    }
}