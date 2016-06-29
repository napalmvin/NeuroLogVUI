/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

/**
 *
 * @author LOL
 */
public abstract class AbstractVaadinSeleniumTest {

    static final  String BEFORE = "\033[31m";
    static final String AFTER = "\033[0m";

    protected static WebDriver driver;
    protected static Logger log;

//    public WebElement findButtonByCaption(String caption) {
//        final List<WebElement> buttons
//                = driver.findElements(By.className("v-button"));
//        for (final WebElement button : buttons) {
//            if (button.getText().equals(caption)) {
//                return button;
//            }
//        }
//        return null;
//    }
//
    public WebElement findButtonByPartCaption(String caption) {
        final List<WebElement> buttons
                = driver.findElements(By.className("v-button"));
        log.error(BEFORE+"findButtonByPartCaption (" + caption + ") Elements size:" + buttons.size()+AFTER);
        for (final WebElement button : buttons) {
            log.error(BEFORE+"findButtonByPartCaption:"+button.getText()+AFTER);
            if (button.getText().contains(caption)) {
                return button;
            }
        }
        return null;
    }
    
    private  void log(String str){
        log.error(BEFORE+">>>"+str+AFTER);
    }
    
    protected  void log(WebElement webEl){
        log.error(BEFORE+">>>>>>\ntagname:"+webEl.getTagName()+"|n"
                +webEl.getText()+AFTER);
    }
}
