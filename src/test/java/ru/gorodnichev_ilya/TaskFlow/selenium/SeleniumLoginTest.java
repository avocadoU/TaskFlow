package ru.gorodnichev_ilya.TaskFlow.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SeleniumLoginTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    void testLoginAndLogout() {
        driver.get("http://localhost:8080/login");

        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("admin");

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("admin123");

        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/view/tasks"));
        assertTrue(driver.getCurrentUrl().contains("/view/tasks"), "Должны перейти на страницу задач");

        driver.get("http://localhost:8080/logout");

        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"), "Должны вернуться на страницу входа");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}