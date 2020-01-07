package selenium.framework;

import com.aventstack.extentreports.Status;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommonUtils extends  BaseClass{
    private WebDriverWait wait;

    public WebElement getWebElement(By locator){
        WebElement element= null;
        try{
            element=getDriver().findElement(locator);
        }catch (StaleElementReferenceException sere){
            waitUntilVisibilityOfElement(locator);
            element=getDriver().findElement(locator);
        }catch (NoSuchElementException nsee){
            System.out.println("Please check your locator :"+nsee);
            infoFail("Please check your locator :"+nsee);
        }catch (Exception e){
            System.out.println("exception is"+e);
            infoFail("exception is"+e);
        }
        return element;
    }

    /**
     * To click an element
     * @param locator xpath
     */
    public void click(By locator) {
        try{
            getWebElement(locator).click();
            infoPass(locator+" is clicked");
        }catch (ElementNotInteractableException enie){
            JavascriptExecutor js=(JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].click();", getWebElement(locator));
        }
    }

    /**
     * To enter value in text box
     * @param locator xpath
     * @param textToEnter text to enter
     */
    public void sendValue(By locator, String textToEnter) {
        getWebElement(locator).clear();
        getWebElement(locator).sendKeys(textToEnter);
        infoPass(locator+" is entered with "+textToEnter);
    }

    /**
     * To read a static element
     * @param locator xpath
     * @return String
     */
    public String getText(By locator) {
        String eleText=getWebElement(locator).getText();
        infoPass(locator+" element text is "+eleText);
        return eleText;
    }

    /**
     * To wait until visibility of element located
     * @param locator by value
     */
    public void waitUntilVisibilityOfElement(By locator){
        wait=new WebDriverWait(getDriver(),60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        infoPass("waiting for element to be visible "+locator);
    }

    public void waitUntilElementToBeClickable(By locator){
        wait=new WebDriverWait(getDriver(),60);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        infoPass("waiting for element to be clickable "+locator);
    }

    /**
     * To read a text box value
     * @param locator xpath
     * @return String
     */
    public String getTextBoxValue(By locator) {
        String textBoxValue= getWebElement(locator).getAttribute("value");
        infoPass("text box value for "+locator+" is "+textBoxValue);
        return textBoxValue;
    }

    public void infoPass(String comments){
        logger.log(Status.PASS, comments);
    }

    public void infoFail(String comments){
        logger.log(Status.FAIL, comments);
    }

    public boolean isElementDisplayed(By locator){
        try{
            getDriver().findElement(locator);
            return true;
        }catch (NoSuchElementException nsee){
            return  false;
        }
    }

    public List<String> getElementsText(By locator) {
        List<WebElement> allElements=getAllWebElement(locator);
        List<String> allText=new ArrayList<>();
        for (WebElement eachElement:allElements){
            allText.add(eachElement.getText());
        }
        infoPass(locator+" element texts are "+allText);
        return allText;
    }

    public void selectDropdownValue(By locator, String option){
        waitUntilVisibilityOfElement(locator);
        Select select=new Select(getWebElement(locator));
        select.selectByVisibleText(option);
        infoPass(locator+" option selected is "+option);
    }

    public List<WebElement> getAllWebElement(By locator){
        wait=new WebDriverWait(getDriver(),120);
        List<WebElement> allElements=new ArrayList<>();
        try{
            allElements=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        }catch (StaleElementReferenceException sere){
            allElements=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        }
        infoPass("waiting for element to be visible "+locator);
        return allElements;
    }

    public void switchToNewTab(String mainWindow){
        for(String childWindow : getDriver().getWindowHandles()){
            if(!childWindow.equals(mainWindow))
                switchToWindow(childWindow);
        }
    }

    public void switchToWindow(String window){
        getDriver().switchTo().window(window);
        infoPass("Switch to window "+getDriver().getTitle());
    }

    public void closeWindow(){
        getDriver().close();
    }

    public void verifyElementDisplayed(By locator){
        if (getWebElement(locator).isDisplayed()){
            infoPass(locator+ " is displayed");
            Assert.assertTrue(true,locator+" is displayed");
        }else {
            infoFail(locator+ " is not displayed");
            Assert.assertTrue(false,locator+" is not displayed");
        }
    }

    public void pressTABKey(By locator) {
        getWebElement(locator).sendKeys(Keys.TAB);
        infoPass("TAB key is clicked");
    }

    /**
     * To enter value in text box
     * @param locator xpath
     * @param expectedColorHexadecimal text to enter
     */
    public void verifyColor(By locator, String expectedColorHexadecimal) {
        String actualColor=getWebElement(locator).getAttribute("color");
        Assert.assertEquals(expectedColorHexadecimal,actualColor,"Color is matching");
        infoPass("color is matching expected: "+expectedColorHexadecimal+" actual color: "+actualColor);
    }

    public void selectDropdown(By locator,By dropDownValue){
        WebElement element=getWebElement(locator);
        Actions act = new Actions(getDriver());
        act.moveToElement(element).click().build().perform();
        act.click(getWebElement(dropDownValue)).build().perform();
    }

    public void mouseClick(By locator){
        WebElement element=getWebElement(locator);
        Actions act = new Actions(getDriver());
        act.moveToElement(element).click().build().perform();

    }

    public void verifyUrl(String expectedUrl){
       String actualUrl=getDriver().getCurrentUrl();
       if(expectedUrl.equals(actualUrl)){
           infoPass("expected "+expectedUrl+" is matching with actual "+actualUrl);
           Assert.assertTrue(true,"expected "+expectedUrl+" is matching with actual "+actualUrl);
       }else{
           infoFail("expected "+expectedUrl+" is not matching with actual "+actualUrl);
           Assert.assertTrue(false,"expected "+expectedUrl+" is not matching with actual "+actualUrl);
       }
       Assert.assertEquals(expectedUrl,actualUrl,"URL is matching");
    }

    public void verifySearchResults(By locator,int expectedCount){
        int actualResultsCount=getDriver().findElements(locator).size();
        if(expectedCount==actualResultsCount){
            infoPass("expected "+expectedCount+" is matching with actual "+actualResultsCount);
            Assert.assertTrue(true,"expected "+expectedCount+" is matching with actual "+actualResultsCount);
        }else{
            infoFail("expected "+expectedCount+" is not matching with actual "+actualResultsCount);
            Assert.assertTrue(false,"expected "+expectedCount+" is not matching with actual "+actualResultsCount);
        }
    }

    public void clickJS(By locator)throws InterruptedException{
        waitThread(2);
        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        js.executeScript("arguments[0].click();", getWebElement(locator));
    }

    public void waitThread(long seconds){
        long startTime= System.currentTimeMillis()/1000;
        long endTime=startTime+seconds;
        do{
            System.out.println("wait explicit....");
        }while(endTime>=System.currentTimeMillis()/1000);
    }

}