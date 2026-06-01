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

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "input[type='email'][placeholder='Masukkan email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password'][placeholder='Masukan kata sandi']")
    private WebElement passwordInput;

    @FindBy(css = "button.btn-danger.fw-bold[type='submit']")
    private WebElement loginButton;

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

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOf(emailInput));
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(emailInput)).clear();
        emailInput.sendKeys(email);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
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
        WebElement trigger = wait.until(driver -> findFirstDisplayed(userMenuTriggerBy));
        trigger.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButtonBy));
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButtonBy)).click();
    }

    public boolean isLoginFormVisible() {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        return emailInput.isDisplayed();
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
