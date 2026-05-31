package pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyCoursePage {
    private final WebDriverWait wait;

    @FindBy(xpath = "//a[contains(@class,'nav-link') and normalize-space()='Kursus Saya']")
    private WebElement myCourseMenu;

    @FindBy(id = "inprogress-tab")
    private WebElement inProgressTab;

    @FindBy(xpath = "//p[contains(normalize-space(),'Belum ada kursus yang sedang dijalani')]")
    private WebElement emptyMessage;

    public MyCoursePage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        PageFactory.initElements(driver, this);
    }

    public void openMyCourse() {
        wait.until(ExpectedConditions.elementToBeClickable(myCourseMenu)).click();
        wait.until(ExpectedConditions.visibilityOf(inProgressTab));
    }

    public boolean isInProgressTabActive() {
        String classes = inProgressTab.getAttribute("class");
        return classes != null && classes.contains("active");
    }

    public String getEmptyMessage() {
        wait.until(ExpectedConditions.visibilityOf(emptyMessage));
        return emptyMessage.getText().trim();
    }
}
