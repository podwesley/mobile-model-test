package com.example;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

public class MainTest extends BaseTest {

    @Test
    public void editTextTest() {

        //android.widget.TextView[@text='Formulário']
        List<MobileElement> elements = driver.findElements(By.className("android.widget.TextView"));

        elements.get(1).click();
        MobileElement nome = (MobileElement) driver.findElement(MobileBy.AccessibilityId("nome"));
        nome.sendKeys("wesley");
        Assert.assertEquals("wesley", nome.getText());

    }

    @Test
    public void iteracaoComboTest() {

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
    public void iteracaoSwichCheckBoxTest() {

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
    public void iteracaoSwichCheckBoxMelhoradoTest() {

        acessarMenuPrincipal("Formulário");

        MobileElement check = (MobileElement) driver.findElement(By.className("android.widget.CheckBox"));
        MobileElement switc = (MobileElement) driver.findElement(MobileBy.AccessibilityId("switch"));

        buttonCheckedAndSwitch(check, "false");
        buttonCheckedAndSwitch(switc, "false");

    }

    @Test
    public void cadastroEsperasTest() {

        String console = "PS4";

        acessarMenuPrincipal("Formulário").
                click(elementByClass("android.widget.Spinner")).
                click(elementBytext(console)).
                click(elementBytext("SALVAR DEMORADO"));

        String text = obterTexto("Console: ps4");

        Assert.assertEquals("Console: ps4", text);
    }

    @Test
    public void deveAguardarSplashSumirTest() {

        acessarMenuPrincipal("Splash");
        Assert.assertTrue("Não achou ", isTelaSplashVisivel("Splash!"));
        aguardarElementoSumir(elementBytext("Splash!"));
    }

    private boolean isTelaSplashVisivel(String texto) {
        return existeElementoPorTexto(texto);
    }

    @Test
    public void deveConfirmarAlertaTest() {

        acessarMenuPrincipal("Alertas")
                .click(elementBytext("ALERTA CONFIRM"));

        String tituloAlerta = obterTexto(By.id("android:id/alertTitle"));
        Assert.assertEquals("Info", tituloAlerta);

        click(elementBytext("CONFIRMAR"));
        String mensagemAlerta = obterTexto(By.id("android:id/message"));
        Assert.assertEquals("Confirmado", mensagemAlerta);

        click(elementBytext("SAIR"));

    }


    @Test
    public void acessarAbasTest() {

        acessarMenuPrincipal("Abas")
                .click(elementBytext("Aba 1"))
                .efetuarValidacao("Este é o conteúdo da Aba 1")
                .click(elementBytext("Aba 2"))
                .efetuarValidacao("Este é o conteúdo da Aba 2");

    }

    @Test
    public void optionsTest() {

        acessarMenuPrincipal("Accordion");
        MobileElement mobileElement = elementBytext("Opção 1");
        click(mobileElement)
                .efetuarValidacao("Esta é a descrição da opção 1");

    }

    @Test
    public void datePickerTest() {

        acessarMenuPrincipal("Formulário")
                .click(elementBytext("01/01/2000"))
                .click(elementBytext("2000"))
                .click(elementBytext("2004"))
                .click(elementBytext("OK"))
                .efetuarValidacao("01/01/2004");

    }

    @Test
    public void timePickerTest() {

        acessarMenuPrincipal("Formulário")
                .click(elementBytext("12:00"))
                .click(elementByAccessibilityId("12"))
                .click(elementByAccessibilityId("35"))
                .click(elementBytext("OK"))
                .efetuarValidacao("12:35");

    }


    @Test
    public void clicarEmCordenadaTest() {

        acessarMenuPrincipal("Alertas")
                .click(elementBytext("ALERTA SIMPLES"))
                .esperar(2);

        MobileElement info = elementBytext("Info");

        tapCordinate(200, 200)
                .aguardarElementoSumir(info);
    }

    @Test
    public void seekBarTest() {

        acessarMenuPrincipal("Formulário")
                .clicarSeekBar(0.05)
                .click(elementBytext("SALVAR"))
                .efetuarValidacao("Slider: 5");


    }

    @Test
    public void cliqueLongoTest () {

        acessarMenuPrincipal("Cliques")
                .cliqueLongo()
                .efetuarValidacao(By.xpath("(//android.widget.TextView)[3]"), "Clique Longo");

    }

    @Test
    public void cliqueDuploTest () {

        acessarMenuPrincipal("Cliques")
                .click(elementBytext("Clique Duplo"))
                .click(elementBytext("Clique Duplo"))
                .efetuarValidacao(By.xpath("(//android.widget.TextView)[3]"), "Clique Duplo");

    }

    @Test
    public void scrollDownTest() {

        aguardarElementoByXpath("//*[@text='Formulário']")
                .scrollDown(0.9, 0.1) //90% e 10%
                .acessarMenuPrincipal("Opção bem escondida")
                .efetuarValidacao("Sucesso")
                .efetuarValidacao("Você achou essa opção")
                .click(elementBytext("OK"));

    }
}