package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GamePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(css = "#navigation > div.grid-x.navigation-wrapper > div.cell.small-16.large-10.xlarge-8.secondary-nav-item.secondary-nav-right > div.header-search-wrapper.algolia-search-enable > div > div")
    private WebElement searchBar;

    @FindBy(css = "#searchbox > div > form > input")
    private WebElement searchBarInput;

    @FindBy(xpath = "/html/body/div[1]/div[4]/div[4]/div/div[1]/div/section/div/div[3]/div/div[3]/form/fieldset/div/div/div[2]/div[2]/button")
    public WebElement likeButton;

    @FindBy(css = "#add-to-cart")
    private WebElement addToCartButton;

    @FindBy(css = "#product-added-popup")
    public WebElement addedToCartPopup;

    @FindBy(css = "#product-added-popup > button")
    public WebElement addedToCartPopupCloseButton;

    public GamePage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = wait;
    }

    public void NavigateToGamePage() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        AccountConnectionPage accountConnectionPageObject = new AccountConnectionPage(driver, wait);
        accountConnectionPageObject.Login(accountConnectionPageObject.username, accountConnectionPageObject.password);

        wait.until(ExpectedConditions.visibilityOf(searchBar));
        searchBarInput.sendKeys("The Division 2 Standard edition");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[3]/div[1]/ul/li/div/a/div[1]/img")));

        WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[3]/div[1]/ul/li/div/a/div[1]/div/div[2]/div/div[2]/button"));
        Actions actions = new Actions(driver);
        actions.moveToElement(button).click().build().perform();
    }

    public void BuyGameTwice() throws Exception {
        Actions actions = new Actions(driver);
        actions.moveToElement(addToCartButton).click().build().perform();

        wait.until(ExpectedConditions.visibilityOf(addedToCartPopup));
        actions.moveToElement(addedToCartPopupCloseButton).click().build().perform();

        wait.until(ExpectedConditions.visibilityOf(addToCartButton));
        actions.moveToElement(addToCartButton).click().build().perform();
    }
}
