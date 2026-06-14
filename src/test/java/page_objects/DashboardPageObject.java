package page_objects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPageObject {
    @FindBy(css = "ul.navbar-nav.ms-auto")
    public WebElement navbar;

    @FindBy(css = "ul.navbar-nav.ms-auto a.nav-link")
    public List<WebElement> navbarLinks;

    @FindBy(css = "li.nav-name.dropdown")
    public WebElement navUser;

    public final By userMenuTriggerBy = By.cssSelector(
        "li.nav-name.dropdown a, li.nav-name.dropdown button, li.nav-name.dropdown"
    );

    public final By logoutButtonBy = By.xpath(
        "//button[contains(@class,'dropdown-button') and contains(normalize-space(),'Keluar')]"
    );

    public DashboardPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
