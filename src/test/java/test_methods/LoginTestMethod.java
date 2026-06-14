package test_methods;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_objects.LoginPageObject;

public class LoginTestMethod {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final LoginPageObject loginPageObject;

    public LoginTestMethod(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.loginPageObject = new LoginPageObject(driver);
    }

    public void open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOf(loginPageObject.emailInput));
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(loginPageObject.emailInput)).clear();
        loginPageObject.emailInput.sendKeys(email);

        loginPageObject.passwordInput.clear();
        loginPageObject.passwordInput.sendKeys(password);

        loginPageObject.loginButton.click();
    }

    public boolean isLoginFormVisible() {
        wait.until(ExpectedConditions.visibilityOf(loginPageObject.emailInput));
        return loginPageObject.emailInput.isDisplayed();
    }

    public boolean isLoginErrorDisplayed() {
        try {
            WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginPageObject.errorPopupBy)
            );
            return popup.getText().contains("Alamat email tidak ditemukan");
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
