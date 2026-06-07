package pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyCoursePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By myCourseMenuBy =
        By.xpath("//a[contains(@class,'nav-link') and normalize-space()='Kursus Saya']");

    private final By inProgressTabBy = By.xpath(
        "//*[self::a or self::button][contains(@class,'nav-link') and "
            + "contains(normalize-space(),'Dalam Progres')]");

    private final By inProgressTabAltBy = By.id("inprogress-tab");

    private final By emptyMessageBy = By.xpath(
        "//p[contains(normalize-space(),'Belum ada kursus yang sedang dijalani')]");

    private final By completedTabBy = By.id("completed-tab");

    private final By completedTabAltBy = By.xpath(
        "//*[self::a or self::button][contains(@class,'nav-link') and "
            + "contains(normalize-space(),'Selesai')]");

    private final By completedEmptyMessageBy = By.xpath(
        "//p[contains(normalize-space(),'Belum ada kursus yang selesai')]");

    /*
     * ============================================================
     * Tambahan untuk test case progress bar dan kartu kursus
     * ============================================================
     */

    private final By activeCardsLocator = By.cssSelector(
        "[role='tabpanel']:not([hidden]):not([aria-hidden='true']) .custom-card"
    );

    private final By activeProgressBarsLocator = By.cssSelector(
        "[role='tabpanel']:not([hidden]):not([aria-hidden='true']) .progress-bar-fill"
    );

    private final By activeCardsTitleLocator = By.cssSelector(
        "[role='tabpanel']:not([hidden]):not([aria-hidden='true']) .card-title"
    );

    public MyCoursePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    /*
     * ============================================================
     * Fitur awal (kesepakatan tim)
     * ============================================================
     */

    public void openMyCourse() {

        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(myCourseMenuBy));
        menu.click();

        wait.until(driver -> findInProgressTab());
    }

    public boolean isInProgressTabActive() {
        WebElement tab = findInProgressTab();

        if (tab == null) {
            return false;
        }

        String classes = tab.getAttribute("class");

        return classes != null
            && classes.contains("active");
    }

    public String getEmptyMessage() {
        WebElement element =
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                emptyMessageBy));

        return element.getText().trim();
    }

    public void openCompletedTab() {
        WebElement tab =
            wait.until(driver -> findCompletedTab());

        tab.click();

        wait.until(driver -> isCompletedTabActive());
    }

    public boolean isCompletedTabActive() {

        WebElement tab = findCompletedTab();

        if (tab == null) {
            return false;
        }

        String classes = tab.getAttribute("class");
        String selected = tab.getAttribute("aria-selected");

        return (classes != null && classes.contains("active"))
            || "true".equalsIgnoreCase(selected);
    }

    public String getCompletedEmptyMessage() {

        WebElement element =
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                completedEmptyMessageBy));

        return element.getText().trim();
    }

    /*
     * ============================================================
     * Tambahan untuk TC-FR05-03 & TC-FR05-05
     * ============================================================
     */

    public void openInProgressTab() {

        WebElement tab =
            wait.until(driver -> findInProgressTab());

        tab.click();

        wait.until(driver -> isInProgressTabActive());
    }

    public boolean isCourseCardDisplayed() {

        try {

            List<WebElement> cards =
                wait.until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(activeCardsLocator));

            return !cards.isEmpty();

        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getActiveCourseTitles() {

        try {

            List<WebElement> titles =
                driver.findElements(activeCardsTitleLocator);

            return titles.stream()
                .map(element -> element.getText().trim())
                .collect(Collectors.toList());

        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean isProgressBarPartiallyFilled() {

        try {

            List<WebElement> progressBars =
                wait.until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(
                        activeProgressBarsLocator));

            for (WebElement bar : progressBars) {

                String style = bar.getAttribute("style");

                if (style != null && style.contains("width:")) {

                    String width =
                        style.split("width:")[1]
                             .split("%")[0]
                             .trim();

                    double percent =
                        Double.parseDouble(width);

                    if (percent > 0 && percent < 100) {
                        return true;
                    }
                }
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProgressBarFull() {

        try {

            List<WebElement> progressBars =
                wait.until(ExpectedConditions
                    .presenceOfAllElementsLocatedBy(
                        activeProgressBarsLocator));

            for (WebElement bar : progressBars) {

                String style = bar.getAttribute("style");

                if (style != null && style.contains("width:")) {

                    String width =
                        style.split("width:")[1]
                             .split("%")[0]
                             .trim();

                    double percent =
                        Double.parseDouble(width);

                    if (percent == 100) {
                        return true;
                    }
                }
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasCourseCardInCompletedTab() {
        return isCourseCardDisplayed();
    }

    /*
     * ============================================================
     * Helper method
     * ============================================================
     */

    private WebElement findInProgressTab() {

        List<By> locators =
            List.of(inProgressTabBy, inProgressTabAltBy);

        for (By locator : locators) {

            List<WebElement> elements =
                driver.findElements(locator);

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
        }

        return null;
    }

    private WebElement findCompletedTab() {

        List<By> locators =
            List.of(completedTabBy, completedTabAltBy);

        for (By locator : locators) {

            List<WebElement> elements =
                driver.findElements(locator);

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
        }

        return null;
    }
}