package modulos.produto;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import telas.LoginTela;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@DisplayName("Testes Mobile do Módulo de Produto")
public class ProdutoTest {
    private WebDriver app;

    @BeforeEach
    public void beforeEach() throws MalformedURLException {
        // Abrir o App
        DesiredCapabilities capacidades = new DesiredCapabilities();
        capacidades.setCapability("deviceName", "Pixel 4 Android 11");
        capacidades.setCapability("platform", "Android");
        capacidades.setCapability("udid" ,"emulator-5554");
        capacidades.setCapability("appPackage", "com.lojinha");
        capacidades.setCapability("appActivity", "com.lojinha.ui.MainActivity");
        capacidades.setCapability("app" , "C:\\Lojinha\\lojinha-nativa.apk");

       this.app = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub") , capacidades);
        app.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }
    @DisplayName("Validação do Valor de Produto Não Permitido")
    @Test
    public void testValidacaoDoValorDeProdutoNaoPermitido ()  {


        
        // Fazer Login
        String mensagemApresentada = new LoginTela(app)
                .preencherUsuario("admin")
                .preencherSenha("admin")
                .submeterLogin()
                .abrirTelaAdicaoProduto()
                .preencherNomeProduto("iPhone")
                .preencherValorProduto("70001")
                .preencherCoresProduto("preto,branco")
                .submissaoComErro()
                .obterMensagemDeErro();



        // Validar que a mensagem de valor inválido foi apresentada

        Assertions.assertEquals("o valor do produto deve estar entre R$ 0,01 e R$ 7.000,00" , mensagemApresentada);
    }

    @AfterEach
    public void afterEach(){
        app.quit();
    }
}
