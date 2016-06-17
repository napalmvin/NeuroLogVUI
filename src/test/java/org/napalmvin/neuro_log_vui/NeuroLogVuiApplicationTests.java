package org.napalmvin.neuro_log_vui;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
@FixMethodOrder()
public class NeuroLogVuiApplicationTests extends AbstractVaadinSeleniumTest {

    static {
        log = LoggerFactory.getLogger(NeuroLogVuiApplicationTests.class.getName());
    }

    @BeforeClass
    public static void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "c:\\chromedriver.exe");
        driver = new ChromeDriver();

//        driver.manage().timeouts().implicitlyWait(21, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);

    }
//
//    private void handleAlert() {
//        JavascriptExecutor javascript = (JavascriptExecutor) driver;
//        javascript.executeScript("alert('Test Case Execution Is started Now..');");
//        Thread.sleep(2000);
//        driver.switchTo().alert().accept()
//    }

    @Test
    public void _0contextLoads() {
    }

    @Test(timeout = Long.MAX_VALUE)
    public void _1doctorsUiLoads() throws InterruptedException {
        driver.get("http://localhost:8080/neurolog_vui/#!doctors");

        WebDriverWait wait = new WebDriverWait(driver, 50000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("new_doctor")));
        log.error(BEFORE + "doctor UI loads" + AFTER);
        final WebElement button = findButtonByPartCaption("New doctor");
        log.error(BEFORE + "Find button =" + button + AFTER);
        Assert.assertNotNull(button);
    }

    @Test
    public void _2newDoctorPopupLoads() {
        final WebElement button = findButtonByPartCaption("New doctor");
        log.error(BEFORE + "Find button =" + button + AFTER);
        button.click();
        WebDriverWait wait = new WebDriverWait(driver, 7000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("v-upload")));
    }

    @Test

    public void _3ImageUploads() throws InterruptedException {
        final WebElement fileInput = driver.findElement(By.className("gwt-FileUpload"));
        String imagePath = "e:\\Photo\\Фотик несорт\\100OLYMP\\P4112011.JPG";
        log.error(BEFORE + "ImagePath =" + imagePath + AFTER);
        fileInput.sendKeys(imagePath);

        final WebElement uploadBtn = findButtonByPartCaption("Upload");
        uploadBtn.click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        
        final WebElement image = driver.findElement(By.id("new_doctor_image"));
        Assert.assertNotNull(image);
        Thread.currentThread().sleep(15000);
    }

    @AfterClass
    public static void closeBrowser() {
        driver.quit();
    }
}
