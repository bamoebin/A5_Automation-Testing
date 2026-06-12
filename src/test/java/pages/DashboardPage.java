package pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "ul.navbar-nav.ms-auto")
    private WebElement navbar;

    @FindBy(css = "ul.navbar-nav.ms-auto a.nav-link")
    private List<WebElement> navbarLinks;

    @FindBy(css = "li.nav-name.dropdown")
    private WebElement navUser;

    private final By userMenuTriggerBy = By.cssSelector(
        "li.nav-name.dropdown a, li.nav-name.dropdown button, li.nav-name.dropdown"
    );

    private final By logoutButtonBy = By.xpath(
        "//button[contains(@class,'dropdown-button') and contains(normalize-space(),'Keluar')]"
    );

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        // Gunakan timeout yang sedikit lebih panjang (30 detik) mengantisipasi server lambat
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public void waitForDashboard() {
        wait.until(ExpectedConditions.visibilityOf(navbar));
    }

    public List<String> getNavbarTexts() {
        wait.until(ExpectedConditions.visibilityOf(navbar));

        return navbarLinks.stream()
                .map(element -> element.getText().trim())
                .collect(Collectors.toList());
    }

    public boolean isUserNameVisible() {
        wait.until(ExpectedConditions.visibilityOf(navUser));
        return navUser.isDisplayed();
    }

    public void openUserMenu() {
        WebElement trigger = wait.until(d -> findFirstDisplayed(userMenuTriggerBy));
        trigger.click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(logoutButtonBy)
        );
    }

    public void logout() {
        wait.until(
            ExpectedConditions.elementToBeClickable(logoutButtonBy)
        ).click();
    }

    private WebElement findFirstDisplayed(By locator) {
        List<WebElement> elements = driver.findElements(locator);

        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return element;
            }
        }
        return null;
    }
}
