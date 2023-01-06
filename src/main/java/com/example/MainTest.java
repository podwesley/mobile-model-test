package com.example;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
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
        desiredCapabilities.setCapability("appium:deviceName", "emulator"); //para ver os nomes dos dispositivos: adb devices
        desiredCapabilities.setCapability("appium:automationName", "uiautomator2");
        desiredCapabilities.setCapability("appium:appPackage", "com.ctappium");
        desiredCapabilities.setCapability("appium:appActivity", "com.ctappium.MainActivity");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, Utils.findFilesResources("CTAppium_2_0.apk")); // Verifica se o app esta instalado caso negativo instala caso positivo reseta o app.

        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver<MobileElement>(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // remover este wait implicito.

    }

    @Test
    public void editText() {

        //android.widget.TextView[@text='Formulário']
        List<MobileElement> elements = driver.findElements(By.className("android.widget.TextView"));

        elements.get(1).click();
        WebElement nome = driver.findElement(MobileBy.AccessibilityId("nome"));
        nome.sendKeys("wesley");
        Assert.assertEquals("wesley", nome.getText());

    }

    @Test
    public void iteracaoCombo () {

    }

    //@After
    public void tearDown() {
        driver.quit();
    }
}