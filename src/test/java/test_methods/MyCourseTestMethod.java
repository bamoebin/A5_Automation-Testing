package test_methods;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_objects.MyCoursePageObject;

public class MyCourseTestMethod {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final MyCoursePageObject myCoursePageObject;

    public MyCourseTestMethod(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        this.myCoursePageObject = new MyCoursePageObject(driver);
    }

    public void openMyCourse() {
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(myCoursePageObject.myCourseMenuBy));
        menu.click();
        wait.until(driver -> findInProgressTab());
    }

    public boolean isInProgressTabActive() {
        WebElement tab = findInProgressTab();
        if (tab == null) {
            return false;
        }
        String classes = tab.getAttribute("class");
        return classes != null && classes.contains("active");
    }

    public String getEmptyMessage() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(myCoursePageObject.emptyMessageBy));
        return element.getText().trim();
    }

    public void openCompletedTab() {
        WebElement tab = wait.until(driver -> findCompletedTab());
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
        return (classes != null && classes.contains("active")) || "true".equalsIgnoreCase(selected);
    }

    public String getCompletedEmptyMessage() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(myCoursePageObject.completedEmptyMessageBy));
        return element.getText().trim();
    }

    public void openInProgressTab() {
        WebElement tab = wait.until(driver -> findInProgressTab());
        tab.click();
        wait.until(driver -> isInProgressTabActive());
    }

    public boolean isCourseCardDisplayed() {
        try {
            List<WebElement> cards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(myCoursePageObject.activeCardsLocator));
            return !cards.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getActiveCourseTitles() {
        try {
            List<WebElement> titles = driver.findElements(myCoursePageObject.activeCardsTitleLocator);
            return titles.stream().map(element -> element.getText().trim()).collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean isProgressBarPartiallyFilled() {
        try {
            List<WebElement> progressBars = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(myCoursePageObject.activeProgressBarsLocator));
            for (WebElement bar : progressBars) {
                String style = bar.getAttribute("style");
                if (style != null && style.contains("width:")) {
                    String width = style.split("width:")[1].split("%")[0].trim();
                    double percent = Double.parseDouble(width);
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
            List<WebElement> progressBars = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(myCoursePageObject.activeProgressBarsLocator));
            for (WebElement bar : progressBars) {
                String style = bar.getAttribute("style");
                if (style != null && style.contains("width:")) {
                    String width = style.split("width:")[1].split("%")[0].trim();
                    double percent = Double.parseDouble(width);
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

    private WebElement findInProgressTab() {
        List<By> locators = List.of(myCoursePageObject.inProgressTabBy, myCoursePageObject.inProgressTabAltBy);
        for (By locator : locators) {
            List<WebElement> elements = driver.findElements(locator);
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
        }
        return null;
    }

    private WebElement findCompletedTab() {
        List<By> locators = List.of(myCoursePageObject.completedTabBy, myCoursePageObject.completedTabAltBy);
        for (By locator : locators) {
            List<WebElement> elements = driver.findElements(locator);
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
        }
        return null;
    }
}
