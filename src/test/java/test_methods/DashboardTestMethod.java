package test_methods;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_objects.DashboardPageObject;

public class DashboardTestMethod {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final DashboardPageObject dashboardPageObject;

    public DashboardTestMethod(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.dashboardPageObject = new DashboardPageObject(driver);
    }

    public void waitForDashboard() {
        wait.until(ExpectedConditions.visibilityOf(dashboardPageObject.navbar));
    }

    public List<String> getNavbarTexts() {
        wait.until(ExpectedConditions.visibilityOf(dashboardPageObject.navbar));

        return dashboardPageObject.navbarLinks.stream()
                .map(element -> element.getText().trim())
                .collect(Collectors.toList());
    }

    public boolean isUserNameVisible() {
        wait.until(ExpectedConditions.visibilityOf(dashboardPageObject.navUser));
        return dashboardPageObject.navUser.isDisplayed();
    }

    public void openUserMenu() {
        WebElement trigger = wait.until(d -> findFirstDisplayed(dashboardPageObject.userMenuTriggerBy));
        trigger.click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(dashboardPageObject.logoutButtonBy)
        );
    }

    public void logout() {
        wait.until(
            ExpectedConditions.elementToBeClickable(dashboardPageObject.logoutButtonBy)
        ).click();
    }

    private WebElement findFirstDisplayed(By locator) {
        List<WebElement> elements = driver.findElements(locator);

        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return element;
            }
        }
        return null;
    }
}
