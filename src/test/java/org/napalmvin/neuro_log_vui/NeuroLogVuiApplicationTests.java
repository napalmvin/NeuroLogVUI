package org.napalmvin.neuro_log_vui;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(Application.class)
//@WebIntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class NeuroLogVuiApplicationTests extends AbstractVaadinSeleniumTest {

    static {
        log = LoggerFactory.getLogger(NeuroLogVuiApplicationTests.class.getName());
    }

    @BeforeClass
    public static void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "c:\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);

    }
//
//    private void handleAlert() {
//        JavascriptExecutor javascript = (JavascriptExecutor) driver;
//        javascript.executeScript("alert('Test Case Execution Is started Now..');");
//        Thread.sleep(2000);
//        driver.switchTo().alert().accept()
//    }

//    @Test
//    public void _0contextLoads() {
//        log.error(BEFORE + "_0contextLoads " + AFTER);
//    }

    @Test(timeout = Long.MAX_VALUE)
    public void _1doctorsUiLoads() throws InterruptedException {
        log.error(BEFORE + "_1doctorsUiLoads " + AFTER);
        driver.get("http://localhost:8080/neurolog_vui/#!doctors");
        log.error(BEFORE + "doctor UI loads" + AFTER);
        WebDriverWait wait = new WebDriverWait(driver, 50000);
        final WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("new_doctor")));

        log.error(BEFORE + "Find button =" + button + AFTER);
        Assert.assertNotNull(button);
    }

    @Test
    public void _2newDoctorPopupLoads() {
        log.error(BEFORE + "_2newDoctorPopupLoads " + AFTER);
        final WebElement button = findButtonByPartCaption("New doctor");
        log.error(BEFORE + "Find button =" + button + AFTER);
        button.click();
        WebDriverWait wait = new WebDriverWait(driver, 7000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("v-upload")));
    }

    @Test
    public void _3ImageUploads() throws InterruptedException {
        log.error(BEFORE + "_3ImageUploads " + AFTER);
        final WebElement fileInput = driver.findElement(By.className("gwt-FileUpload"));
        String imagePath = "e:\\Photo\\Фотик несорт\\100OLYMP\\P4112011.JPG";
        log.error(BEFORE + "ImagePath =" + imagePath + AFTER);
        fileInput.sendKeys(imagePath);

        final WebElement uploadBtn = findButtonByPartCaption("Upload");
        uploadBtn.click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        final WebElement image = driver.findElement(By.id("image"));
        Assert.assertNotNull(image);
    }

    @Test
    public void _4AddNewDoctorWorks() throws InterruptedException {
        log.error(BEFORE + "_4AddNewDoctorWorks " + AFTER);

        final WebElement firstName = driver.findElement(By.id("firstName"));
        final WebElement lastName = driver.findElement(By.id("lastName"));
        final WebElement birthDate = driver.findElement(By.id("birthDate"))
                .findElement(By.className("v-textfield"));
        final WebElement qualification = driver.findElement(By.id("qualification"));

        final WebElement gender = driver.findElement(By.id("gender"))
                .findElement(By.className("v-filterselect-input"));
        final WebElement race = driver.findElement(By.id("race"))
                .findElement(By.className("v-filterselect-input"));
        final WebElement save = driver.findElement(By.id("save"));

        firstName.sendKeys("TestEngeneerFirstName");
        lastName.sendKeys("TestEngeneerLastName");
        birthDate.clear();
        birthDate.sendKeys("1/6/13");
        qualification.sendKeys("Good test doctor");
//        gender.sendKeys("Female");
//        race.sendKeys("Antropoid");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        save.click();
        Thread.currentThread().sleep(10000);
        //----------------------------------Check presense of new doctor in grid

        ArrayList<WebElement> rows = (ArrayList<WebElement>) driver.findElements(By.className("v-grid-row"));
        WebElement row = rows.get(5);
        ArrayList<WebElement> cellElements = (ArrayList<WebElement>) row.findElements(By.className("v-grid-cell"));
        
        for (WebElement cellElement : cellElements) {
            log(cellElement);
        }
        
        Assert.assertTrue("6".equals(cellElements.get(0).getText()));
        Assert.assertTrue("TestEngeneerFirstName".equals(cellElements.get(1).getText()));
        Assert.assertTrue("TestEngeneerLastName".equals(cellElements.get(2).getText()));
        Assert.assertTrue("1969-01-01".equals(cellElements.get(3).getText()));
        Assert.assertTrue("MALE".equals(cellElements.get(4).getText()));
        Assert.assertTrue("Caucasian".equals(cellElements.get(5).getText()));
        Assert.assertNotNull(cellElements.get(6).findElement(By.tagName("img")).getAttribute("src"));
        Assert.assertTrue("Good test doctor".equals(cellElements.get(7).getText()));

    }

//    @Test
//    public void _5CancelButtonWorks() throws InterruptedException {
    //TODO Implement
//        _2newDoctorPopupLoads();
//        final WebElement canelBttn = driver.findElement(By.id("cancel"));
//        canelBttn.click();
//     }
    @AfterClass
    public static void closeBrowser() {
        driver.quit();
    }

}
