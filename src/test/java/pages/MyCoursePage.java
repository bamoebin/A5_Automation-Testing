package pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyCoursePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By myCourseMenuBy = By.xpath("//a[contains(@class,'nav-link') and normalize-space()='Kursus Saya']");
    private final By inProgressTabBy = By.id("inprogress-tab");
    private final By inProgressTabAltBy = By.xpath(
        "//*[self::a or self::button][contains(@class,'nav-link') and "
            + "contains(normalize-space(),'Dalam Progres')]");
    private final By emptyMessageBy = By.xpath(
        "//p[contains(normalize-space(),'Belum ada kursus yang sedang dijalani')]");

    public MyCoursePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public void openMyCourse() {
        wait.until(ExpectedConditions.elementToBeClickable(myCourseMenuBy)).click();
        wait.until(driver -> findInProgressTab());
    }

    public boolean isInProgressTabActive() {
        String classes = findInProgressTab().getAttribute("class");
        return classes != null && classes.contains("active");
    }

    public String getEmptyMessage() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emptyMessageBy));
        return element.getText().trim();
    }

    private WebElement findInProgressTab() {
        List<By> locators = List.of(inProgressTabBy, inProgressTabAltBy);
        for (By locator : locators) {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                WebElement element = elements.get(0);
                if (element.isDisplayed()) {
                    return element;
                }
            }
        }
        return null;
    }
}
