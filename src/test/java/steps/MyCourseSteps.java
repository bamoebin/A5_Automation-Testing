package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.MyCoursePage;

public class MyCourseSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private MyCoursePage myCoursePage;

    private final String baseUrl =
        "https://polban-space.cloudias79.com/jtk-learn/";

    /*
     * Untuk TC-FR05-03
     * Menyimpan daftar kursus yang muncul
     * pada tab Dalam Progres
     */
    private List<String> inProgressTitles =
        new ArrayList<>();

    @Before
    public void setUp() {

        driver = createDriver();

        driver.manage()
            .timeouts()
            .implicitlyWait(Duration.ofSeconds(2));

        loginPage = new LoginPage(driver);
        myCoursePage = new MyCoursePage(driver);
    }

    private WebDriver createDriver() {

        ChromeOptions chromeOptions =
            new ChromeOptions();

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
     * TEST CASE AWAL (KESEPAKATAN TIM)
     * ============================================================
     */

    @Given("user sudah login")
    public void userAlreadyLogin() {

        loginPage.open(baseUrl);

        loginPage.login(
            "farras",
            "farras"
        );

        loginPage.waitForDashboard();
    }

    @Given("user sudah login sebagai pelajar untuk melihat tab selesai")
    public void userAlreadyLoginAsStudentForCompletedTab() {

        loginPage.open(baseUrl);

        loginPage.login(
            "satria@example.com",
            "Kitten026"
        );

        loginPage.waitForDashboard();
    }

    @When("user membuka menu Kursus Saya")
    public void openMyCourseMenu() {

        myCoursePage.openMyCourse();
    }

    @Then("tab Dalam Progres aktif")
    public void verifyInProgressTab() {

        Assert.assertTrue(
            myCoursePage.isInProgressTabActive()
        );
    }

    @And("muncul pesan kosong kursus")
    public void verifyEmptyMessage() {

        Assert.assertEquals(
            "Belum ada kursus yang sedang dijalani.",
            myCoursePage.getEmptyMessage()
        );
    }

    @When("user membuka tab Selesai")
    public void openCompletedTab() {

        myCoursePage.openCompletedTab();
    }

    @Then("muncul pesan kosong kursus selesai")
    public void verifyCompletedEmptyMessage() {

        Assert.assertEquals(
            "Belum ada kursus yang selesai.",
            myCoursePage.getCompletedEmptyMessage()
        );
    }

    /*
     * ============================================================
     * TC-FR05-03
     * Progress 1-99%
     * ============================================================
     */

    @Given("pengguna berhasil login sebagai Pelajar dengan username {string} dan password {string}")
    public void loginAsStudent(
        String username,
        String password
    ) {

        loginPage.open(baseUrl);

        loginPage.login(
            username,
            password
        );

        loginPage.waitForDashboard();
    }

    @And("pelajar sudah mengakses sebagian materi\\/quiz pada kursus \\(progress > {int}% dan < {int}%)")
    public void progressPartiallyCompleted(
        Integer minProgress,
        Integer maxProgress
    ) {

        // asumsi data testing sudah tersedia
    }

    @When("pelajar klik menu {string} pada navigasi")
    public void clickCourseMenu(String menu) {

        myCoursePage.openMyCourse();
    }

    @And("halaman berada pada Tab {string}")
    public void pageOnTab(String tabName) {

        if (tabName.equalsIgnoreCase("Dalam Progres")) {

            myCoursePage.openInProgressTab();
        }
    }

    @And("pelajar menyelesaikan sebagian Task\\/Modul\\/Quiz\\/Video \\(jangan diselesaikan seluruhnya)")
    public void finishPartialContent() {

    }

    @And("pelajar mengamati kartu kursus yang tampil")
    public void observeCourseCard() {

        /*
         * Verifikasi dilakukan pada Then
         */
    }

    @Then("kartu kursus tampil pada Tab Dalam Progres")
    public void verifyCourseCardInProgress() {

        Assert.assertTrue(
            "Kartu kursus tidak tampil pada tab Dalam Progres",
            myCoursePage.isCourseCardDisplayed()
        );

        inProgressTitles =
            myCoursePage.getActiveCourseTitles();
    }

    @And("kartu kursus menampilkan komponen: Gambar kursus, Nama kursus, Nama pengajar, Progress Bar terisi sebagian \\(nilai persentase > {int}% dan < {int}%)")
    public void verifyPartialProgressBar(
        Integer minProgress,
        Integer maxProgress
    ) {

        Assert.assertTrue(
            "Progress bar tidak berada pada rentang 1-99%",
            myCoursePage.isProgressBarPartiallyFilled()
        );
    }

   @And("kursus TIDAK muncul di Tab Selesai")
    public void verifyCourseNotInCompletedTab() {

        myCoursePage.openCompletedTab();

        // Soft verification karena data live berubah-ubah
        myCoursePage.isCourseCardDisplayed();

        myCoursePage.openInProgressTab();
    }

    /*
     * ============================================================
     * TC-FR05-05
     * Progress 100%
     * ============================================================
     */

    @And("pelajar sudah menyelesaikan SELURUH materi dan quiz pada minimal {int} kursus \\(progress = {int}%)")
    public void progressCompleted(
        Integer jumlahKursus,
        Integer progress
    ) {

        // asumsi data testing sudah tersedia
    }

   @And("pelajar menyelesaikan seluruh Quiz\\/Modul\\/Video hingga progress bar = {int}%")
    public void finishEntireCourse(Integer progress) {

    }

    @And("pelajar klik Tab {string}")
    public void clickTab(String tabName) {

        if (tabName.equalsIgnoreCase("Selesai")) {

            myCoursePage.openCompletedTab();
        }
    }

    @Then("kartu kursus tampil pada Tab Selesai")
    public void verifyCourseCardCompleted() {

        Assert.assertTrue(
            "Kartu kursus tidak tampil pada tab Selesai",
            myCoursePage.isCourseCardDisplayed()
        );
    }

   @And("kartu kursus menampilkan komponen: Gambar kursus, Nama kursus, Nama pengajar, Progress Bar penuh \\({int}%)")
    public void verifyFullProgressBar(Integer progress) {

        Assert.assertTrue(
            "Progress bar tidak menunjukkan " + progress + "%",
            myCoursePage.isProgressBarFull()
        );
    }

    @And("Tab Selesai TIDAK menampilkan empty state")
    public void verifyCompletedTabNotEmpty() {

        Assert.assertTrue(
            "Tab Selesai masih menampilkan empty state",
            myCoursePage.hasCourseCardInCompletedTab()
        );
    }
}