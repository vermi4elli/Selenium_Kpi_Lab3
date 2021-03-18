import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.AccountConnectionPage;
import pageobject.GamePage;
import pageobject.WishlistPage;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {
    public WebDriver driver;

    @Before
    public void SetUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikit\\OneDrive\\Рабочий стол\\KPI\\Lab3\\src\\main\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("Tests start");
    }

    @Test
    public void a_testLoggingIn() throws Exception {
        driver.get("https://store.ubi.com/ru/account-connection");
        WebDriverWait wait = new WebDriverWait(driver, 20);

        AccountConnectionPage accountConnectionPageObject = new AccountConnectionPage(driver, wait);
        accountConnectionPageObject.Login(accountConnectionPageObject.username, accountConnectionPageObject.password);

        Assert.assertEquals(accountConnectionPageObject.avatar.getAttribute("data-tc100"), "myaccount");
    }

    @Test
    public void b_testAddingElementsToWishlist() throws Exception {
        driver.get("https://store.ubi.com/ru/account-connection");
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WishlistPage wishlistPageObject = new WishlistPage(driver);

        wishlistPageObject.addFirstCrossSellListElementToWishlist();

        while (wishlistPageObject.GetWishlistElements().size() <= wishlistPageObject.amount) {
            wishlistPageObject.NavigateToWishlist();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#navigation > div.grid-x.navigation-wrapper > div.cell.small-16.large-10.xlarge-8.secondary-nav-item.secondary-nav-right > div.header-search-wrapper.algolia-search-enable > div > div")));
        }
        Assert.assertTrue(wishlistPageObject.GetWishlistElements().size() > wishlistPageObject.amount);
    }

    @Test
    public void c_testAddingExistingElementsToWishlist() throws Exception {
        driver.get("https://store.ubi.com/ru/account-connection");
        WebDriverWait wait = new WebDriverWait(driver, 20);

        GamePage gamePageObject = new GamePage(driver, wait);
        gamePageObject.NavigateToGamePage();

        String[] classNames = gamePageObject.likeButton.getAttribute("class").split("\\s+");
        Assert.assertEquals("product-added", classNames[classNames.length - 1]);
    }

    @Test
    public void d_testDeletingExistingElementsFromWishlist() throws Exception {
        driver.get("https://store.ubi.com/ru/account-connection");
        WebDriverWait wait = new WebDriverWait(driver, 20);

        WishlistPage wishlistPageObject = new WishlistPage(driver);
        wishlistPageObject.deleteWishlistElementIfExists();

        while (wishlistPageObject.GetWishlistElements().size() >= wishlistPageObject.amount) {
            wishlistPageObject.NavigateToWishlist();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#navigation > div.grid-x.navigation-wrapper > div.cell.small-16.large-10.xlarge-8.secondary-nav-item.secondary-nav-right > div.header-search-wrapper.algolia-search-enable > div > div")));
        }
        Assert.assertTrue(wishlistPageObject.GetWishlistElements().size() < wishlistPageObject.amount);
    }

    @Test
    public void e_testBuyGameTwice_FAIL() throws Exception {
        driver.get("https://store.ubi.com/ru/account-connection");
        WebDriverWait wait = new WebDriverWait(driver, 20);

        GamePage gamePageObject = new GamePage(driver, wait);
        gamePageObject.NavigateToGamePage();

        gamePageObject.BuyGameTwice();
        Assert.assertTrue(gamePageObject.addedToCartPopup.isDisplayed());
    }

    @After
    public void close() {
        System.out.println("Test end");
        driver.close();
    }

}