package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import pages.LoginPage;
import pages.MyCoursePage;

public class MyCourseSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private MyCoursePage myCoursePage;
    private final String baseUrl = "https://polban-space.cloudias79.com/jtk-learn/";

    @Before
    public void setUp() {
        driver = createDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        loginPage = new LoginPage(driver);
        myCoursePage = new MyCoursePage(driver);
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

    @Given("user sudah login")
    public void userAlreadyLogin() {
        loginPage.open(baseUrl);
        loginPage.login("farras", "farras");
        loginPage.waitForDashboard();
    }

    @Given("user sudah login sebagai pelajar untuk melihat tab selesai")
    public void userAlreadyLoginAsStudentForCompletedTab() {
        loginPage.open(baseUrl);
        loginPage.login("satria@example.com", "Kitten026");
        loginPage.waitForDashboard();
    }

    @When("user membuka menu Kursus Saya")
    public void openMyCourseMenu() {
        myCoursePage.openMyCourse();
    }

    @Then("tab Dalam Progres aktif")
    public void verifyInProgressTab() {
        Assert.assertTrue(myCoursePage.isInProgressTabActive());
    }

    @And("muncul pesan kosong kursus")
    public void verifyEmptyMessage() {
        Assert.assertEquals("Belum ada kursus yang sedang dijalani.", myCoursePage.getEmptyMessage());
    }

    @When("user membuka tab Selesai")
    public void openCompletedTab() {
        myCoursePage.openCompletedTab();
    }

    @Then("muncul pesan kosong kursus selesai")
    public void verifyCompletedEmptyMessage() {
        Assert.assertEquals("Belum ada kursus yang selesai.", myCoursePage.getCompletedEmptyMessage());
    }
}
