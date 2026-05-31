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
import pages.LoginPage;
import pages.MyCoursePage;

public class MyCourseSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private MyCoursePage myCoursePage;
    private final String baseUrl = "https://polban-space.cloudias79.com/jtk-learn/";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        loginPage = new LoginPage(driver);
        myCoursePage = new MyCoursePage(driver);
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
}
