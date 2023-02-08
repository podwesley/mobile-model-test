package com.example;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

public class MainTest extends BaseTest {

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

    @Test
    public void cadastroEsperas() {

        String console = "PS4";

        acessarMenuPrincipal("Formulário").
                click(elementByClass("android.widget.Spinner")).
                click(elementBytext(console)).
                click(elementBytext("SALVAR DEMORADO"));

        String text = obterTexto("Console: ps4");

        Assert.assertEquals("Console: ps4", text);
    }

    @Test
    public void deveAguardarSplashSumir() {

        acessarMenuPrincipal("Splash");
        Assert.assertTrue("Não achou ", isTelaSplashVisivel("Splash!"));
        aguardarElementoSumir(elementBytext("Splash!"));
    }

    private boolean isTelaSplashVisivel(String texto) {
        return existeElementoPorTexto(texto);
    }

    @Test
    public void deveConfirmarAlerta() {

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
    public void acessarAbas() {

        acessarMenuPrincipal("Abas")
                .click(elementBytext("Aba 1"))
                .efetuarValidacao("Este é o conteúdo da Aba 1")
                .click(elementBytext("Aba 2"))
                .efetuarValidacao("Este é o conteúdo da Aba 2");

    }

    @Test
    public void options() {

        acessarMenuPrincipal("Accordion");
        MobileElement mobileElement = elementBytext("Opção 1");
        click(mobileElement)
                .efetuarValidacao("Esta é a descrição da opção 1");

    }

}