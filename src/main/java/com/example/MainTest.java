package com.example;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainTest {

    private AndroidDriver driver;

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

    @Test
    public void editText() {

        //android.widget.TextView[@text='Formulário']
        List<MobileElement> elements = driver.findElements(By.className("android.widget.TextView"));

        elements.get(1).click();
        MobileElement nome = (MobileElement) driver.findElement(MobileBy.AccessibilityId("nome"));
        nome.sendKeys("wesley");
        Assert.assertEquals("wesley", nome.getText());

    }

    @Test
    public void iteracaoCombo() {

        System.out.println("usando xpath");
        // class[@attr='node detail']
        //android.widget.TextView[@text='Formulário']
        acessarMenuPrincipal("Formulário");
        MobileElement console = (MobileElement) driver.findElement(MobileBy.AccessibilityId("console"));
        console.click();

        MobileElement popup = (MobileElement) driver.findElement(MobileBy.xpath("//android.widget.CheckedTextView[@text='PS4']"));

        Assert.assertEquals("PS4", popup.getText());
        popup.click();

    }

    @Test
    public void iteracaoSwichCheckBox() {

        acessarMenuPrincipal("Formulário");

        MobileElement check = (MobileElement) driver.findElement(By.className("android.widget.CheckBox"));

        Assert.assertEquals("false", check.getAttribute("checked"));

        MobileElement switc = (MobileElement) driver.findElement(MobileBy.AccessibilityId("switch"));

        Assert.assertEquals("true", switc.getAttribute("checked"));
        System.out.println("teste");


        //Verificar estados alterados
        check.click();
        switc.click();
        Assert.assertEquals("true", check.getAttribute("checked"));
        Assert.assertEquals("false", switc.getAttribute("checked"));

    }

    @Test
    public void iteracaoSwichCheckBoxMelhorado() {

        acessarMenuPrincipal("Formulário");

        MobileElement check = (MobileElement) driver.findElement(By.className("android.widget.CheckBox"));
        MobileElement switc = (MobileElement) driver.findElement(MobileBy.AccessibilityId("switch"));

        buttonCheckedAndSwitch(check, "false");
        buttonCheckedAndSwitch(switc, "false");

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

    public void xpathExamples() {

        /**
         * //android.widget.TextView[starts-with(@text, 'Console:')]
         */

    }

    //@After
    public void tearDown() {
        driver.quit();
    }
}