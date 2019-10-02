package selenium.test.flipkart;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import selenium.framework.BaseClass;
import selenium.framework.CommonUtils;
import selenium.objects.flipkart.ShoesObject;
import selenium.pages.flipkart.ShoesPage;

public class Shoes extends BaseClass implements ShoesObject
{
    private CommonUtils util;
    private WebDriver driver;
    private ShoesPage shoesPage;
    private String mainWindow;

    @BeforeClass()
    public void init(){
        driver=getDriver();
        util=new CommonUtils();
        logger = getInstance().createTest("Flipkart Shoes Filter", "verifying application");
        shoesPage=new ShoesPage();
        mainWindow=driver.getWindowHandle();
    }

    @Test()
    public void flipkartShoes(){
        driver.get("https://www.flipkart.com");
        util.infoPass("launched flipkart application");
        if (util.isElementDisplayed(BUTTON_LoginPopupClose)) {
            util.click(BUTTON_LoginPopupClose);
        }
        util.sendValue(TEXTBOX_Search, "shoes");
        util.click(BUTTON_Search);
        shoesPage.verifyResultsListed("shoes");
        util.sendValue(TEXTBOX_BrandSearch, "Puma");
        util.click(CHECKBOX_BrandPuma);
        shoesPage.verifyBrandsListed("Puma");
        util.selectDropdownValue(DROPDOWN_MaxPrice, "₹1500");
        shoesPage.verifyPrice(0, 1500);
        shoesPage.openFirstResult();
        util.switchToNewTab(mainWindow);
        shoesPage.selectShoeSize();
        util.click(BUTTON_BuyNow);
        util.verifyElementDisplayed(TEXT_LoginOrSignUp);
        util.closeWindow();
        util.switchToWindow(mainWindow);
    }
}