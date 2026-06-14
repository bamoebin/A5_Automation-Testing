package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.LoginPage;
import utils.TestConfig;

public class LogoutSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    private LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(Hooks.getDriver());
        }
        return loginPage;
    }

    private DashboardPage getDashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage(Hooks.getDriver());
        }
        return dashboardPage;
    }

    @Given("User sudah login dengan role Pelajar")
    public void userAlreadyLoginAsStudent() {
        getLoginPage().open(TestConfig.BASE_URL);
        getLoginPage().login("satria@example.com", "Kitten026");
        getDashboardPage().waitForDashboard();
    }

    @When("User menekan drop down Nama Akun")
    public void userOpenAccountMenu() {
        getDashboardPage().openUserMenu();
    }

    @And("User menekan tombol \"Keluar\"")
    public void userClickLogout() {
        getDashboardPage().logout();
    }

    @Then("User kembali ke halaman login")
    public void userBackToLoginPage() {
        Assert.assertTrue(getLoginPage().isLoginFormVisible());
    }
}
