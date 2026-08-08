package de.eyuepekici.iu_testshop.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CheckoutE2ETest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
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

        driver.get("http://localhost:" + port + "/products");

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
