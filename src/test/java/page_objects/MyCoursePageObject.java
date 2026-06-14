package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MyCoursePageObject {

    public final By myCourseMenuBy =
        By.xpath("//a[contains(@class,'nav-link') and normalize-space()='Kursus Saya']");

    public final By inProgressTabBy = By.xpath(
        "//*[self::a or self::button][contains(@class,'nav-link') and "
            + "contains(normalize-space(),'Dalam Progres')]");

    public final By inProgressTabAltBy = By.id("inprogress-tab");

    public final By emptyMessageBy = By.xpath(
        "//p[contains(normalize-space(),'Belum ada kursus yang sedang dijalani')]");

    public final By completedTabBy = By.id("completed-tab");

    public final By completedTabAltBy = By.xpath(
        "//*[self::a or self::button][contains(@class,'nav-link') and "
            + "contains(normalize-space(),'Selesai')]");

    public final By completedEmptyMessageBy = By.xpath(
        "//p[contains(normalize-space(),'Belum ada kursus yang selesai')]");

    public final By activeCardsLocator = By.cssSelector(
        "[role='tabpanel']:not([hidden]):not([aria-hidden='true']) .custom-card"
    );

    public final By activeProgressBarsLocator = By.cssSelector(
        "[role='tabpanel']:not([hidden]):not([aria-hidden='true']) .progress-bar-fill"
    );

    public final By activeCardsTitleLocator = By.cssSelector(
        "[role='tabpanel']:not([hidden]):not([aria-hidden='true']) .card-title"
    );

    public MyCoursePageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
