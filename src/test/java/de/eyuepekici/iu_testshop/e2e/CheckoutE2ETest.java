package de.eyuepekici.iu_testshop.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

        WebElement searchInput = wait.until(
                d -> d.findElement(By.name("keyword"))
        );
        searchInput.sendKeys("Laptop");

        WebElement searchButton = driver.findElement(
                By.cssSelector(".search-box button")
        );
        searchButton.click();

        WebElement addToCartButton = wait.until(
                d -> d.findElement(By.cssSelector(".product-card button"))
        );
        addToCartButton.click();

        WebElement cartLink = wait.until(
                d -> d.findElement(By.linkText("Zum Warenkorb →"))
        );
        cartLink.click();

        WebElement paymentButton = wait.until(
                d -> d.findElement(By.cssSelector("form[action='/payment'] button"))
        );
        paymentButton.click();

        WebElement paypalOption = wait.until(
                d -> d.findElement(By.cssSelector("input[value='PAYPAL']"))
        );
        paypalOption.click();

        WebElement paypalEmail = wait.until(
                d -> d.findElement(By.name("paypalEmail"))
        );
        paypalEmail.sendKeys("kunde@example.com");

        WebElement confirmButton = driver.findElement(
                By.cssSelector("form[action='/payment/confirm'] button")
        );
        confirmButton.click();

        wait.until(d -> d.getPageSource().contains("Bestellung erfolgreich"));

        assertTrue(driver.getPageSource().contains("Bestellung erfolgreich"));
    }
}