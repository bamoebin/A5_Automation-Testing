package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;

public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private final String baseUrl = "https://polban-space.cloudias79.com/jtk-learn/";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("user berada di halaman login")
    public void userOnLoginPage() {
        loginPage.open(baseUrl);
    }

    @When("user login dengan email {string} dan password {string}")
    public void userLogin(String email, String password) {
        loginPage.login(email, password);
        loginPage.waitForDashboard();
    }

    @Then("dashboard tampil dengan navbar yang berisi menu utama dan nama akun")
    public void verifyDashboard() {
        List<String> navbar = loginPage.getNavbarTexts();
        Assert.assertTrue(navbar.contains("Beranda"));
        Assert.assertTrue(navbar.contains("Kursus Saya"));
        Assert.assertTrue(navbar.contains("Riwayat Kuis"));
        Assert.assertTrue(loginPage.isUserNameVisible());
    }
}
