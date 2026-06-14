package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageObject {
    @FindBy(css = "input[type='email'][placeholder='Masukkan email']")
    public WebElement emailInput;

    @FindBy(css = "input[type='password'][placeholder='Masukan kata sandi']")
    public WebElement passwordInput;

    @FindBy(css = "button.btn-danger.fw-bold[type='submit']")
    public WebElement loginButton;

    public final By errorPopupBy = By.className("swal2-popup");

    public LoginPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
