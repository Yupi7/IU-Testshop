package de.eyuepekici.iu_testshop.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
        driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:" + port + "/products");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("keyword")))
                .sendKeys("Laptop");

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".search-box button")))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-card button")))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Zum Warenkorb →")))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("form[action='/payment'] button")))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='PAYPAL']")))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("paypalEmail")))
                .sendKeys("kunde@example.com");

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("form[action='/payment/confirm'] button")))
                .click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"),
                "Bestellung erfolgreich"
        ));

        assertTrue(driver.getPageSource().contains("Bestellung erfolgreich"));
    }
}