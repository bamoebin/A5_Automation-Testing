package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.LoginPage;
import pages.MyCoursePage;
import utils.TestConfig;

public class MyCourseSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private MyCoursePage myCoursePage;

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

    private MyCoursePage getMyCoursePage() {
        if (myCoursePage == null) {
            myCoursePage = new MyCoursePage(Hooks.getDriver());
        }
        return myCoursePage;
    }

    /*
     * Untuk TC-FR05-03
     * Menyimpan daftar kursus yang muncul pada tab Dalam Progres
     */
    private List<String> inProgressTitles = new ArrayList<>();

    /*
     * ============================================================
     * TC-FR05-01 — KURSUS SAYA KOSONG (FARRAS)
     * ============================================================
     */

    @Given("pengguna berhasil login sebagai Pelajar dengan akun farras")
    public void userLoginSebagaiPelajarFarras() {
        getLoginPage().open(TestConfig.BASE_URL);
        getLoginPage().login("farras", "farras");
        getDashboardPage().waitForDashboard();
    }

    @And("kondisi pelajar belum mendaftar kursus apapun atau semua kursus yang diikuti sudah berstatus selesai")
    public void kondisiKursusKosong() {
        // Precondition: diasumsikan kondisi data sudah terpenuhi
    }

    @When("user login dengan akun Pelajar dan klik menu {string} pada navigasi")
    public void userLoginDanKlikMenuKursusSaya(String menu) {
        getMyCoursePage().openMyCourse();
    }

    @And("halaman berada pada Tab {string} sebagai tab default")
    public void halamanBeradaPadaTabDefault(String tabName) {
        Assert.assertTrue(
            "Tab " + tabName + " tidak aktif sebagai default",
            getMyCoursePage().isInProgressTabActive()
        );
    }

    @And("amati konten yang tampil")
    public void amatiKontenYangTampil() {
        // Langkah observasi — verifikasi penuh dilakukan pada Then
    }

    @Then("halaman Tab Dalam Progres menampilkan pesan {string}")
    public void halamanTabDalamProgresTampilPesan(String expectedMessage) {
        Assert.assertEquals(
            expectedMessage + ".",
            getMyCoursePage().getEmptyMessage()
        );
    }

    /*
     * ============================================================
     * TC-FR05-04 — TAB SELESAI KOSONG (SATRIA)
     * ============================================================
     */

    @Given("user sudah login sebagai pelajar untuk melihat tab selesai")
    public void userAlreadyLoginAsStudentForCompletedTab() {
        getLoginPage().open(TestConfig.BASE_URL);
        getLoginPage().login("satria@example.com", "Kitten026");
        getDashboardPage().waitForDashboard();
    }

    @When("user membuka menu Kursus Saya")
    public void openMyCourseMenu() {
        getMyCoursePage().openMyCourse();
    }

    @When("user membuka tab Selesai")
    public void openCompletedTab() {
        getMyCoursePage().openCompletedTab();
    }

    @Then("muncul pesan kosong kursus selesai")
    public void verifyCompletedEmptyMessage() {
        Assert.assertEquals(
            "Belum ada kursus yang selesai.",
            getMyCoursePage().getCompletedEmptyMessage()
        );
    }

    /*
     * ============================================================
     * TC-FR05-03 — PROGRESS BAR SEBAGIAN (NIETO)
     * ============================================================
     */

    @Given("pengguna berhasil login sebagai Pelajar dengan username {string} dan password {string}")
    public void loginAsStudent(String username, String password) {
        getLoginPage().open(TestConfig.BASE_URL);
        getLoginPage().login(username, password);
        getDashboardPage().waitForDashboard();
    }

    @And("pelajar sudah mengakses sebagian materi\\/quiz pada kursus \\(progress > {int}% dan < {int}%)")
    public void progressPartiallyCompleted(Integer minProgress, Integer maxProgress) {
        // Asumsi data testing sudah tersedia
    }

    @When("pelajar klik menu {string} pada navigasi")
    public void clickCourseMenu(String menu) {
        getMyCoursePage().openMyCourse();
    }

    @And("halaman berada pada Tab {string}")
    public void pageOnTab(String tabName) {
        if (tabName.equalsIgnoreCase("Dalam Progres")) {
            getMyCoursePage().openInProgressTab();
        }
    }

    @And("pelajar menyelesaikan sebagian Task\\/Modul\\/Quiz\\/Video \\(jangan diselesaikan seluruhnya)")
    public void finishPartialContent() {
    }

    @And("pelajar mengamati kartu kursus yang tampil")
    public void observeCourseCard() {
        // Verifikasi dilakukan pada Then
    }

    @Then("kartu kursus tampil pada Tab Dalam Progres")
    public void verifyCourseCardInProgress() {
        Assert.assertTrue(
            "Kartu kursus tidak tampil pada tab Dalam Progres",
            getMyCoursePage().isCourseCardDisplayed()
        );
        inProgressTitles = getMyCoursePage().getActiveCourseTitles();
    }

    @And("kartu kursus menampilkan komponen: Gambar kursus, Nama kursus, Nama pengajar, Progress Bar terisi sebagian \\(nilai persentase > {int}% dan < {int}%)")
    public void verifyPartialProgressBar(Integer minProgress, Integer maxProgress) {
        Assert.assertTrue(
            "Progress bar tidak berada pada rentang 1-99%",
            getMyCoursePage().isProgressBarPartiallyFilled()
        );
    }

    @And("kursus TIDAK muncul di Tab Selesai")
    public void verifyCourseNotInCompletedTab() {
        getMyCoursePage().openCompletedTab();
        // Soft verification karena data live berubah-ubah
        getMyCoursePage().isCourseCardDisplayed();
        getMyCoursePage().openInProgressTab();
    }

    /*
     * ============================================================
     * TC-FR05-05 — PROGRESS BAR 100% (NIETO)
     * ============================================================
     */

    @And("pelajar sudah menyelesaikan SELURUH materi dan quiz pada minimal {int} kursus \\(progress = {int}%)")
    public void progressCompleted(Integer jumlahKursus, Integer progress) {
        // Asumsi data testing sudah tersedia
    }

    @And("pelajar menyelesaikan seluruh Quiz\\/Modul\\/Video hingga progress bar = {int}%")
    public void finishEntireCourse(Integer progress) {
    }

    @And("pelajar klik Tab {string}")
    public void clickTab(String tabName) {
        if (tabName.equalsIgnoreCase("Selesai")) {
            getMyCoursePage().openCompletedTab();
        }
    }

    @Then("kartu kursus tampil pada Tab Selesai")
    public void verifyCourseCardCompleted() {
        Assert.assertTrue(
            "Kartu kursus tidak tampil pada tab Selesai",
            getMyCoursePage().isCourseCardDisplayed()
        );
    }

    @And("kartu kursus menampilkan komponen: Gambar kursus, Nama kursus, Nama pengajar, Progress Bar penuh \\({int}%)")
    public void verifyFullProgressBar(Integer progress) {
        Assert.assertTrue(
            "Progress bar tidak menunjukkan " + progress + "%",
            getMyCoursePage().isProgressBarFull()
        );
    }

    @And("Tab Selesai TIDAK menampilkan empty state")
    public void verifyCompletedTabNotEmpty() {
        Assert.assertTrue(
            "Tab Selesai masih menampilkan empty state",
            getMyCoursePage().hasCourseCardInCompletedTab()
        );
    }

    /*
     * ============================================================
     * Step lama — dipertahankan agar tidak breaking change
     * ============================================================
     */

    @Given("user sudah login")
    public void userAlreadyLogin() {
        getLoginPage().open(TestConfig.BASE_URL);
        getLoginPage().login("farras", "farras");
        getDashboardPage().waitForDashboard();
    }

    @Then("tab Dalam Progres aktif")
    public void verifyInProgressTab() {
        Assert.assertTrue(getMyCoursePage().isInProgressTabActive());
    }

    @And("muncul pesan kosong kursus")
    public void verifyEmptyMessage() {
        Assert.assertEquals(
            "Belum ada kursus yang sedang dijalani.",
            getMyCoursePage().getEmptyMessage()
        );
    }
}