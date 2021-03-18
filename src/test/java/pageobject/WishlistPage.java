package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WishlistPage {
    protected WebDriver driver;
    public int amount = 0;

    @FindBy(xpath = "/html/body/div[1]/div[4]/div[2]/div/div[2]/div/div/div/div/div[1]/div/li/div/div[1]")
    private WebElement firstCrossSellListElement;

    @FindBy(xpath = "/html/body/div[1]/div[4]/div[2]/div/div[1]/div[4]/ul/li/div/div[2]/div[1]/div[2]/div[1]/a")
    private WebElement firstWishlistElementEditLink;

    @FindBy(css = "#add-wishlist-popin > div.wishlist-dialog-content-wrapper > div.wishlist-dialog-buttons > div:nth-child(2) > button")
    private WebElement deleteFromWishlistButton;

    @FindBy(css = "#add-wishlist-popin > button")
    private WebElement closeWindow;

    @FindBy(css = "#wishlist-status-icon")
    private WebElement wishlistIcon;

    public WishlistPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = ThreadGuard.protect(driver);;
    }

    public List<WebElement> GetWishlistElements() {
        return driver.findElements(By.cssSelector("#wishlist-items > div.items-in-wishlist.section-container-spacing > ul > li"));
    }

    public void NavigateToWishlist() {
        wishlistIcon.click();
    }

    public void addFirstCrossSellListElementToWishlist() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        AccountConnectionPage accountConnectionPageObject = new AccountConnectionPage(driver, wait);
        accountConnectionPageObject.Login(accountConnectionPageObject.username, accountConnectionPageObject.password);

        NavigateToWishlist();

        wait.until(ExpectedConditions.visibilityOf(firstCrossSellListElement));

        amount = GetWishlistElements().size();

        WebElement button = firstCrossSellListElement.findElement(By.tagName("button"));
        Actions action = new Actions(driver);
        action.moveToElement(button).click();
    }

    public void deleteWishlistElementIfExists() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        AccountConnectionPage accountConnectionPageObject = new AccountConnectionPage(driver, wait);
        accountConnectionPageObject.Login(accountConnectionPageObject.username, accountConnectionPageObject.password);

        NavigateToWishlist();
        while (GetWishlistElements().size() <= 1) {
            NavigateToWishlist();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#navigation > div.grid-x.navigation-wrapper > div.cell.small-16.large-10.xlarge-8.secondary-nav-item.secondary-nav-right > div.header-search-wrapper.algolia-search-enable > div > div")));
        }
        amount = GetWishlistElements().size();

        Actions builder = new Actions(driver);

        builder.moveToElement(firstWishlistElementEditLink).click().build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#add-wishlist-popin")));
        builder.moveToElement(deleteFromWishlistButton).click().build().perform();
        builder.moveToElement(closeWindow).click().build().perform();
    }
}
