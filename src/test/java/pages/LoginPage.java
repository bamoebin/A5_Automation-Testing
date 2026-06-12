package pages;

import java.time.Duration;
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

    // Tambahan untuk validasi login gagal
    private final By errorPopupBy = By.className("swal2-popup");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // Waktu tunggu disetel 30 detik untuk mengakomodasi jaringan/server lambat
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
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

    public boolean isLoginFormVisible() {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        return emailInput.isDisplayed();
    }

    // Tambahan untuk skenario login gagal
    public boolean isLoginErrorDisplayed() {
        try {
            WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorPopupBy)
            );

            return popup.getText().contains("Alamat email tidak ditemukan");
        } catch (Exception e) {
            return false;
        }
    }

    // Tambahan untuk verifikasi redirect
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}