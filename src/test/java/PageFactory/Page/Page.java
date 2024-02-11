package PageFactory.Page;

import PageFactory.Block.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class Page {
    protected final WebDriver driver;
    protected final Header header;

    protected Page(WebDriver driver) {
        this.driver = driver;
        this.header = PageFactory.initElements(driver, Header.class);
    }

    public Header getHeader() {
        return header;
    }
}
