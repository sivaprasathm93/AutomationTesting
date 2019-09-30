package selenium.objects.flipkart;

import org.openqa.selenium.By;

public interface ShoesObject {
    By BUTTON_LoginPopupClose=By.xpath("//button[contains(@class,'dH8')]");
    By BUTTON_Search=By.xpath("//input[@title='Search for products, brands and more']/parent::div/following-sibling::button");
    By BUTTON_BuyNow=By.xpath("//button[contains(.,'BUY NOW')]");

    By TEXTBOX_Search=By.xpath("//input[@title='Search for products, brands and more']");
    By TEXTBOX_BrandSearch=By.xpath("//div[.='Brand']/parent::section//input[@type='text']");

    By LINK_AllResults=By.xpath("//a[.='Home']/../../../../../following-sibling::div//a[string-length(@title)>0]");
    By LINK_ShoesSize=By.xpath("//span[contains(@id,'Size')]/following-sibling::ul/li/a");

    By TEXT_Brand=By.xpath("//a[.='Home']/../../../../../following-sibling::div//a[string-length(@title)>0]/preceding-sibling::div");
    By TEXT_Price=By.xpath("//a[.='Home']/../../../../../following-sibling::div//a/div/div[contains(@class,'_1vC')]");
    By TEXT_LoginOrSignUp=By.xpath("//span[.='Login or Signup']");

    By CHECKBOX_BrandPuma=By.xpath("//div[@title='Puma']//input[@type='checkbox']");

    By DROPDOWN_MaxPrice=By.xpath("(//div[.='Price']/../following-sibling::div//select)[2]");

}
