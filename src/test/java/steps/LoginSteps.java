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
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import pages.LoginPage;

public class LoginSteps {
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
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        switch (browser) {
            case "edge":
                String edgeDriverPath = System.getProperty(
                        "edge.driver.path",
                        "C:\\Program Files\\edgedriver_win32\\msedgedriver.exe"
                );
                System.setProperty("webdriver.edge.driver", edgeDriverPath);
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                return new EdgeDriver(edgeOptions);
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                return new ChromeDriver(chromeOptions);
        }
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
