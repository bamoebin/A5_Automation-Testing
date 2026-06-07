package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import pages.LoginPage;

public class LogoutSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private final String baseUrl = "https://polban-space.cloudias79.com/jtk-learn/";

    @Before
    public void setUp() {
        driver = createDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        loginPage = new LoginPage(driver);
    }

    private WebDriver createDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        return new ChromeDriver(chromeOptions);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("user sudah login sebagai pelajar")
    public void userAlreadyLoginAsStudent() {
        loginPage.open(baseUrl);
        loginPage.login("satria@example.com", "Kitten026");
        loginPage.waitForDashboard();
    }

    @When("user membuka menu akun")
    public void userOpenAccountMenu() {
        loginPage.openUserMenu();
    }

    @And("user menekan tombol Keluar")
    public void userClickLogout() {
        loginPage.logout();
    }

    @Then("user kembali ke halaman login")
    public void userBackToLoginPage() {
        Assert.assertTrue(loginPage.isLoginFormVisible());
    }
}
