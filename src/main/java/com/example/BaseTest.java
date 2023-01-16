package com.example;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:deviceName", "emulator-5554"); //para ver os nomes dos dispositivos: adb devices
        desiredCapabilities.setCapability("appium:automationName", "uiautomator2");
        desiredCapabilities.setCapability("appium:appPackage", "com.ctappium");
        desiredCapabilities.setCapability("appium:appActivity", "com.ctappium.MainActivity");
        //desiredCapabilities.setCapability(MobileCapabilityType.APP, Utils.findFilesResources("CTAppium_2_0.apk")); // Verifica se o app esta instalado caso negativo instala caso positivo reseta o app.
        desiredCapabilities.setCapability("newCommandTimeout", 1000); // wait for 1000 milliseconds to run new command

        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver<MobileElement>(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // remover este wait implicito.

    }

    public void acessarMenuPrincipal(String menu) {
        MobileElement element = (MobileElement) driver.findElement(By.xpath("//*[@text='" + menu + "']"));
        element.click();
    }

    public void buttonCheckedAndSwitch (MobileElement element, String value) {

        if(!element.getAttribute("checked").equals(value))
            element.click();
        Assert.assertEquals(value, element.getAttribute("checked"));
    }

    public void click(MobileElement element) {
        element.click();
    }

    /***
     * This method types the value into a field of type input
     * @param element
     * @param value
     * @return
     */
    public void typeValue(MobileElement element, String value) {
        element.sendKeys(value);
    }

    public void xpathExamples() {

        /**
         * //android.widget.TextView[starts-with(@text, 'Console:')]
         */

    }
}
