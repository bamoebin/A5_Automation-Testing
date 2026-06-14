package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import test_methods.DashboardTestMethod;
import test_methods.LoginTestMethod;
import utils.TestConfig;

public class LogoutSteps {

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
