package LabirintTests.Page;

import LabirintTests.Block.Header;
import org.openqa.selenium.WebDriver;

public abstract class Page {
    protected final WebDriver driver;
    protected final Header header;

    protected Page(WebDriver driver) {
        this.driver = driver;
        this.header = new Header(driver);
    }

    public Header getHeader() {
        return header;
    }
}
