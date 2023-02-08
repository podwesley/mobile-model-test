package com.example;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
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

    /**
     * Hooks reseta o app toda vez que um teste é executado , método mais rápido.
     */
    @After
    public void tearDown() {
        driver.resetApp();
    }

    public BaseTest acessarMenuPrincipal(String menu) {
        MobileElement element = (MobileElement) driver.findElement(By.xpath("//*[@text='" + menu + "']"));
        element.click();
        return this;
    }

    public BaseTest buttonCheckedAndSwitch(MobileElement element, String value) {

        if (!element.getAttribute("checked").equals(value))
            element.click();
        Assert.assertEquals(value, element.getAttribute("checked"));
        return this;
    }

    public BaseTest clicarPorTexto(String menu) {
        MobileElement element = (MobileElement) driver.findElement(By.xpath("//*[@text='" + menu + "']"));
        element.click();
        return this;
    }

    public BaseTest click(WebElement element) {
        aguardarElemento(element).click();
        return this;
    }

    public BaseTest click(By by) {
        driver.findElement(by).click();
        return this;
    }

    public BaseTest type(By by, String value) {
        driver.findElement(by).sendKeys(value);
        return this;
    }


    /***
     * This method types the value into a field of type input
     * @param element
     * @param value
     * @return
     */
    public BaseTest typeValue(WebElement element, String value) {
        element.sendKeys(value);
        return this;
    }

    public void xpathExamples() {

        /**
         * //android.widget.TextView[starts-with(@text, 'Console:')]
         */

    }

    public MobileElement elementBytext(String text) {
        return (MobileElement) driver.findElement(By.xpath("//*[@text='" + text + "']"));
    }

    public MobileElement elementByClass(String className) {
        return (MobileElement) driver.findElement(By.className(className));
    }

    public MobileElement elementByAccessibilityId(String id) {
        return (MobileElement) driver.findElement(MobileBy.AccessibilityId(id));
    }

    public MobileElement elementByXpath(String xpath) {
        return (MobileElement) driver.findElement(By.xpath(xpath));
    }

    public String obterTexto(String texto) {
        return driver.findElement(By.xpath("//*[@text='" + texto + "']")).getText();
    }

    public String obterTexto(By By) {
        return driver.findElement(By).getText();
    }

    public void aguardarElementoSumir(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public BaseTest aguardarElementoByXpath(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        return this;
    }

    public WebElement aguardarElemento(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }


    public boolean existeElementoPorTexto(String texto) {
        List elements = driver.findElements(By.xpath("//*[@text='" + texto + "']"));
        return elements.size() > 0;
    }

    public BaseTest efetuarValidacao(String texto) {
        Assert.assertTrue("Não achou nenhum elemento: ", existeElementoPorTexto(texto));
        return this;
    }

    public BaseTest efetuarValidacao(By by, String text) {
        Assert.assertEquals("Não achou nenhum elemento: ", driver.findElement(by).getText(), text);
        return this;
    }

    /***
     * clicar em uma cordenada especifica
     * @param x
     * @param y
     * @return
     */
    public BaseTest tapCordinate(int x, int y) {
        new TouchAction(driver).tap(x, y).perform();
        return this;
    }

    public BaseTest esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /***
     * Clica em um seekbar.
     * @param posicao
     * @return
     */
    public BaseTest clicarSeekBar(double posicao) {

        int delta = 50;


        WebElement seek = driver.findElement(MobileBy.AccessibilityId("slid"));
        int y = seek.getLocation().y + (seek.getSize().height / 2);

        int xInicial = seek.getLocation().x + delta;
        int x = (int) (xInicial + ((seek.getSize().width - 2 * delta) * posicao));

        new TouchAction(driver).tap(x, y).perform();

        return this;
    }

    public BaseTest cliqueLongo() {
        new TouchAction(driver)
                .longPress(driver.findElement(By.xpath("//*[@text='Clique Longo']")))
                .release()
                .perform();
        return this;
    }


    /***
     * Scroll movimento baixo / cima
     * @param inicio
     * @param fim
     */
    public void scroll(double inicio, double fim) {

        Dimension size = driver.manage().window().getSize();

        int x = size.width / 2;
        int start_y = (int) (size.height * inicio);
        int end_y = (int) (size.height * fim);

        new TouchAction(driver)
                .press(x, start_y)
                .waitAction(Duration.ofMillis(500))
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    /***
     * Swipe movimento esquerda / direita
     * @param inicio
     * @param fim
     */
    public void swipe(double inicio, double fim) {

        Dimension size = driver.manage().window().getSize();

        int y = size.height / 2;
        int start_x = (int) (size.width * inicio);
        int end_x = (int) (size.width * fim);

        new TouchAction(driver)
                .press(start_x, y)
                .waitAction(Duration.ofMillis(500))
                .moveTo(end_x, y)
                .release()
                .perform();
    }

    /**
     * Swipe personalizado no webElement. 
     * @param element
     * @param inicio
     * @param fim
     */
    private void swipe(MobileElement element, double inicio, double fim) {

        int y = element.getLocation().y + ( element.getSize().height / 2);
        int start_x = (int) (element.getSize().width * inicio);
        int end_x = (int) (element.getSize().width * fim);

        new TouchAction(driver)
                .press(start_x, y)
                .waitAction(Duration.ofMillis(500))
                .moveTo(end_x, y)
                .release()
                .perform();
    }

    public BaseTest scrollDown() {
        scroll(0.9, 0.1);
        return this;
    }

    public BaseTest scrollUp() {
        scroll(0.1, 0.9);
        return this;
    }

    public BaseTest swipeLeft() {
        swipe(0.1, 0.9);
        return this;
    }

    public BaseTest swipeRight() {
        swipe(0.9, 0.1);
        return this;
    }

    public BaseTest swipeLeft(String text) {
        swipe(elementByXpath("//*[@text='"+text+"']/.."),0.1, 0.9);
        return this;
    }

    public BaseTest swipeRight(String text) {
        swipe(elementByXpath("//*[@text='"+text+"']/.."),0.9, 0.1);
        return this;
    }

    public BaseTest dragAndDrop() {

        return this;
    }


}
