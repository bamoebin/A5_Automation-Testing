package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

    private final String baseUrl =
            "https://polban-space.cloudias79.com/jtk-learn/";

    // digunakan oleh skenario login gagal
    private String email;
    private String password;

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

    /*
     * ============================================================
     * LOGIN BERHASIL
     * ============================================================
     */

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

    /*
     * ============================================================
     * LOGIN GAGAL
     * ============================================================
     */

    @Given("pengguna telah berada di halaman login JTK Learn")
    public void penggunaDiHalamanLogin() {
        loginPage.open(baseUrl);
    }

    @When("pengguna mengisi field username dengan username tidak terdaftar {string}")
    public void isiUsernameTidakTerdaftar(String username) {
        this.email = username;
    }

    @And("pengguna mengisi field password dengan {string}")
    public void isiPassword(String password) {
        this.password = password;
    }

    @And("pengguna menekan tombol Login")
    public void tekanTombolLogin() {
        loginPage.login(email, password);
    }

    @Then("sistem tidak mengarahkan pengguna ke halaman dashboard")
    public void tidakKeDashboard() {

        Assert.assertFalse(
                "User tidak boleh masuk dashboard",
                loginPage.getCurrentUrl().contains("dashboard")
        );
    }

    @And("sistem menampilkan notifikasi login gagal")
    public void notifikasiLoginGagal() {

        Assert.assertTrue(
                "Notifikasi login gagal tidak muncul",
                loginPage.isLoginErrorDisplayed()
        );
    }
}