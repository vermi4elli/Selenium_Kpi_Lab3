package pageobject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountConnectionPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public final String password = "password";
    public final String username = "n.molodtsov21@gmail.com";

    @FindBy(id = "loginiframe")
    private WebElement loginFrame;

    @FindBy(xpath = "/html/body/app-component/div/app-login-component/main/app-login-shared-component/section/form/app-login-email/div/input")
    private WebElement email_;

    @FindBy(xpath = "/html/body/app-component/div/app-login-component/main/app-login-shared-component/section/form/app-login-password/div/input")
    private WebElement pass_;

    @FindBy(id = "accountMenu")
    public WebElement avatar;

    @FindBy(css = "#navigation > div.grid-x.navigation-wrapper > div.cell.small-8.large-12.xlarge-14.secondary-nav-item.secondary-nav-left > div.nav-spacing-left > div > div > a")
    private WebElement homeLink;

    public AccountConnectionPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = wait;
    }

    public void NavigateToHomePage() throws Exception {
        homeLink.click();
    }

    public void Login(String email, String pass) throws Exception {
        driver.switchTo().frame(loginFrame);
        wait.until(ExpectedConditions.visibilityOf(email_));

        email_.sendKeys(email);
        pass_.sendKeys(pass + Keys.ENTER);

        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.attributeContains(avatar, "data-tc100", "myaccount"));
    }
}
