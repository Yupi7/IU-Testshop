package de.eyuepekici.iu_testshop.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutE2ETest {

    private static final String BASE_URL =
            System.getenv().getOrDefault("E2E_BASE_URL", "http://localhost:8080");

    private WebDriver driver;

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    void emptyCartCannotOpenPaymentPage() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(BASE_URL + "/payment");

        wait.until(ExpectedConditions.urlContains("/cart"));

        assertTrue(driver.getCurrentUrl().contains("/cart"));
    }

    @Test
    void userCanCompleteOrderWithPaypal() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(BASE_URL + "/products");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'In den Warenkorb')]")
        )).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Zum Warenkorb →")
        )).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Zur Zahlung')]")
        )).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[value='PAYPAL']")
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("paypalEmail")
        )).sendKeys("kunde@example.com");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Zahlung bestätigen')]")
        )).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"),
                "Bestellung erfolgreich"
        ));

        assertTrue(driver.getPageSource().contains("Bestellung erfolgreich"));
    }
}
