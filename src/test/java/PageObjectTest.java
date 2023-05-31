import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/** Copiamos el código de los tests anteriores usando el patrón Page Object */

public class PageObjectTest {

private WebDriver driver;
private By cookiesButton=  By.className("cookie-alert-extended-button");
private By productos= By.className("campaign-overview-teaser");
private By searchBar= By.id("mainsearch-input");
private By searchButton= By.className("search-bar-container-button");
private By cartButton= By.id("add-to-cart");

private By textAppear= By.cssSelector("h4.basket-overlay__title");


    /** Driver */
    public void webDriver(WebDriver driver){
    this.driver= driver;
}

    /** Navegar a una url */
    public void searchPage(String url){
    driver.get(url);
}

    public void Maximize(){
        driver.manage().window().maximize();
    }

    /** Aceptar cookies */
    public void acceptCookies() {
        driver.findElement(cookiesButton).click();
    }

    /**
     * Listar los productos
     *
     * @return
     */

    public int listProducts(){
        List<WebElement> products = driver.findElements(productos);
        return products.size();
    }

    /** Buscar productos */

    public void search(String query){
        WebElement searchProduct= driver.findElement(searchBar);
        searchProduct.sendKeys(query);
        driver.findElement(searchButton).click();

    }

    /** Añadir al carrito */
    public void addToCart(){
        WebElement cart= driver.findElement(cartButton);
        cart.click();
    }


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
        driver = new ChromeDriver(options);

        PageObjectTest test= new PageObjectTest();
        test.webDriver(driver);
        test.Maximize();
        test.searchPage("https://www.lidl.es/es/muebles-y-decoracion-de-jardin/c627");
        test.acceptCookies();
        int listProduct= test.listProducts();
        assertEquals(8,listProduct);

    }

    /** b) Utilizamos la barra de búsqueda y testeamos el título del resultado */

    @Test
    public void showTittle(){
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        PageObjectTest test= new PageObjectTest();
        test.webDriver(driver);
        test.Maximize();
        test.searchPage("https://www.lidl.es/");
        test.acceptCookies();
        test.search("mancuernas");
        String pageTitle= driver.getTitle();
        Assertions.assertEquals("Resultado de búsqueda | Lidl", pageTitle);
    }

    /** c) Añadir un producto al carrito */

    @Test

    public void addToCartTest(){
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageObjectTest test= new PageObjectTest();
        test.webDriver(driver);
        test.Maximize();
        test.searchPage("https://www.lidl.es/es/invernadero-de-film/p37743");
        test.acceptCookies();
        test.addToCart();
        test.searchPage("https://www.lidl.es/es/basket/contents");


    }
}
