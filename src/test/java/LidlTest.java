
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LidlTest {

    private WebDriver driver;

    /** Cerramos la aplicación */
    @AfterEach
    public void TearDown(){
        driver.quit();
    }

    /** a) listar los productos de una categoría y comprobar que se cargue el mismo número
     * de productos que se cargan en la web */

    @Test
    public void ListProductsTest(){

        ChromeOptions options = new ChromeOptions();

        /** Abrir chrome y dirigirnos a la categoría de productos indicada */


        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.lidl.es/es/muebles-y-decoracion-de-jardin/c627");

        /** Aceptamos cookies */

        WebElement cookies = driver.findElement(By.className("cookie-alert-extended-button"));
        cookies.click();

        /** Comprobamos que se cargan los 8 resultados de la categoría. */

        List<WebElement> productos = driver.findElements(By.className("campaign-overview-teaser"));
        assertEquals(productos.size(), 8);



    }



    /** b) Utilizamos la barra de búsqueda y testeamos el título del resultado */

    @Test
    public void showTittle(){

        /** Abrimos chrome y máximizamos la ventana */

        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        /** Entramos a la página de Lidl */

        driver.get("https://www.lidl.es/");

        /** Aceptamos cookies */

        WebElement cookies = driver.findElement(By.className("cookie-alert-extended-button"));
        cookies.click();

        /** Escribimos en la barra de búsqueda */

        WebElement searchBar= driver.findElement(By.id("mainsearch-input"));
        searchBar.sendKeys("mancuernas");

        /** Buscamos el producto */
        WebElement searchButton= driver.findElement(By.className("search-bar-container-button"));
        searchButton.click();

        /** Testeamos el título del resultado */

        String pageTitle= driver.getTitle();
        assertEquals("Resultado de búsqueda | Lidl", pageTitle);

    }




    /** c) Añadir un producto al carrito y testar que se haya añadido */

    @Test

    public void addToCartTest(){

        /** Abrimos chrome y lo maximizamos */
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        /** Nos metemos en la página del producto */

        driver.get("https://www.lidl.es/es/invernadero-de-film/p37743");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        /** Aceptamos cookies */

        WebElement cookies = driver.findElement(By.className("cookie-alert-extended-button"));
        cookies.click();

        /** Añadimos el producto al carrito */

        WebElement cartButton= driver.findElement(By.id("add-to-cart"));
        cartButton.click();

        /** En mi caso, para testear que el producto se ha añadido correctamente a la cesta
         * he comprobado que aparece el mensaje que se muestra al añadir un producto
         * y en el caso de que se muestre ese mensaje finaliza el test correctamente */

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4.basket-overlay__title")));
        WebElement textAppear = driver.findElement(By.cssSelector("h4.basket-overlay__title"));
        assertEquals("¡Buena elección! El artículo ha sido añadido a tu cesta de la compra.",
                textAppear.getText());


        /** Ir a la cesta */

        driver.get("https://www.lidl.es/es/basket/contents");

    }
}
