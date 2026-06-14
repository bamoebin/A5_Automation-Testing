package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import test_methods.DashboardTestMethod;
import test_methods.LoginTestMethod;
import utils.TestConfig;

public class LoginSteps {

    private LoginTestMethod loginPage;
    private DashboardTestMethod dashboardPage;

    private LoginTestMethod getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginTestMethod(Hooks.getDriver());
        }
        return loginPage;
    }

    private DashboardTestMethod getDashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardTestMethod(Hooks.getDriver());
        }
        return dashboardPage;
    }

    // Digunakan oleh skenario login gagal
    private String email;
    private String password;

    /*
     * ============================================================
     * TC 1.2.1 — LOGIN BERHASIL
     * ============================================================
     */

    @Given("user belum login")
    public void userBelumLogin() {
        // Precondition: sesi browser baru dibuat oleh Hooks, user belum login
    }

    @And("user membuka alamat situs JTK Learn")
    public void userMembukaSitusJTKLearn() {
        getLoginPage().open(TestConfig.BASE_URL);
    }

    @And("tersedia akun pelajar terdaftar")
    public void tersediaAkunPelajarTerdaftar() {
        // Precondition: akun pelajar diasumsikan sudah tersedia di sistem
    }

    @When("user mengisi field Email dengan email pelajar valid {string}")
    public void userIsiEmail(String email) {
        this.email = email;
    }

    @And("user mengisi field Kata Sandi dengan password yang benar {string}")
    public void userIsiPassword(String password) {
        this.password = password;
    }

    @And("user klik tombol \"Masuk\"")
    public void userKlikMasuk() {
        getLoginPage().login(email, password);
        getDashboardPage().waitForDashboard();
    }

    @And("user cek header navigasi")
    public void userCekHeaderNavigasi() {
        // Observasi dilakukan — verifikasi penuh pada Then
    }

    @Then("dashboard tampil dengan navbar yang berisi menu utama dan nama akun")
    public void verifyDashboard() {
        List<String> navbar = getDashboardPage().getNavbarTexts();
        Assert.assertTrue(navbar.contains("Beranda"));
        Assert.assertTrue(navbar.contains("Kursus Saya"));
        Assert.assertTrue(navbar.contains("Riwayat Kuis"));
        Assert.assertTrue(getDashboardPage().isUserNameVisible());
    }

    /*
     * ============================================================
     * TC 1.2.2 — LOGIN GAGAL
     * ============================================================
     */

    @Given("pengguna telah berada di halaman login JTK Learn")
    public void penggunaDiHalamanLogin() {
        getLoginPage().open(TestConfig.BASE_URL);
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
        getLoginPage().login(email, password);
    }

    @Then("sistem tidak mengarahkan pengguna ke halaman dashboard")
    public void tidakKeDashboard() {
        Assert.assertFalse(
            "User tidak boleh masuk dashboard",
            getLoginPage().getCurrentUrl().contains("dashboard")
        );
    }

    @And("sistem menampilkan notifikasi login gagal")
    public void notifikasiLoginGagal() {
        Assert.assertTrue(
            "Notifikasi login gagal tidak muncul",
            getLoginPage().isLoginErrorDisplayed()
        );
    }

    /*
     * ============================================================
     * Step lama — dipertahankan agar tidak breaking change
     * ============================================================
     */

    @Given("user berada di halaman login")
    public void userOnLoginPage() {
        getLoginPage().open(TestConfig.BASE_URL);
    }

    @When("user login dengan email {string} dan password {string}")
    public void userLogin(String email, String password) {
        getLoginPage().login(email, password);
        getDashboardPage().waitForDashboard();
    }
}