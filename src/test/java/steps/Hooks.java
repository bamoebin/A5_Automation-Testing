package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Hooks terpusat — mengelola siklus hidup WebDriver untuk semua skenario.
 *
 * Setiap skenario Cucumber akan mendapat satu instance browser baru (@Before)
 * dan menutupnya setelah skenario selesai (@After).
 *
 * Steps classes TIDAK perlu lagi mendefinisikan @Before/@After sendiri.
 * Cukup panggil Hooks.getDriver() untuk mendapatkan WebDriver aktif.
 */
public class Hooks {

    private static WebDriver driver;

    @Before
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        driver.manage()
            .timeouts()
            .implicitlyWait(Duration.ofSeconds(2));
    }

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Mengembalikan WebDriver aktif yang dipakai oleh skenario yang sedang berjalan.
     *
     * @return instance WebDriver
     */
    public static WebDriver getDriver() {
        return driver;
    }
}
