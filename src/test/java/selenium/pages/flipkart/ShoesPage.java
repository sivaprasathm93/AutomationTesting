package selenium.pages.flipkart;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import selenium.framework.BaseClass;
import selenium.framework.CommonUtils;
import selenium.objects.flipkart.ShoesObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ShoesPage extends BaseClass implements ShoesObject {

    private CommonUtils util;

    public ShoesPage() {
        util = new CommonUtils();
    }

    public void verifyResultsListed(String resultToBeValidated) {
        List<String> allText = util.getElementsText(LINK_AllResults);
        List<String> validResults = new ArrayList<>();
        for (String each : allText) {
            if (each.toLowerCase().contains(resultToBeValidated.toLowerCase())) {
                validResults.add(each);
            }
        }
        if (validResults.size() > 0) {
            util.infoPass(validResults + " contains word " + resultToBeValidated);
            Assert.assertTrue(validResults.size() > 0);
        } else {
            util.infoFail(allText + " not contains word " + resultToBeValidated);
            Assert.assertTrue(validResults.size() > 0);
        }
    }

    public void verifyBrandsListed(String resultToBeValidated) {
        List<String> allText = util.getElementsText(TEXT_Brand);
        if (Collections.frequency(allText, resultToBeValidated) == allText.size()) {
            Assert.assertTrue(true, resultToBeValidated + " Brand Filter is working");
            util.infoPass(resultToBeValidated + " Brand Filter is working");
        } else {
            Assert.assertTrue(false, resultToBeValidated + " Brand filter is not working");
            util.infoFail(resultToBeValidated + " Brand filter is not working");
        }
    }

    public void verifyPrice(Integer minimumPrice, Integer maximumPrice) {
        List<String> allText = util.getElementsText(TEXT_Price);
        List<Integer> validPrice = new ArrayList<>();
        List<Integer> invalidPrice = new ArrayList<>();
        for (String eachPrice : allText) {
            int price = Integer.valueOf(eachPrice.replaceAll("\\D", ""));
            if (price > minimumPrice && price < maximumPrice) {
                validPrice.add(price);
            } else
                invalidPrice.add(price);
        }
        if (invalidPrice.size() > 0) {
            Assert.assertTrue(false, "Price Filter is not working and all prices are " + invalidPrice + "not between minimum " + minimumPrice + " and maximum price " + maximumPrice);
            util.infoFail("Price Filter is not working and all prices are " + invalidPrice + "not between minimum " + minimumPrice + " and maximum price " + maximumPrice);

        } else {
            Assert.assertTrue(true, "Price Filter is working and all prices are " + validPrice + " between minimum " + minimumPrice + " and maximum price " + maximumPrice);
            util.infoPass("Price Filter is working and all prices are " + validPrice + " between minimum " + minimumPrice + " and maximum price " + maximumPrice);
        }
    }

    public void openFirstResult() {
        List<WebElement> allText = util.getAllWebElement(LINK_AllResults);
        allText.get(0).click();
    }

    public void selectShoeSize() {
        List<WebElement> allSize = util.getAllWebElement(LINK_ShoesSize);
        for (WebElement each:allSize){
            if(each.isEnabled()){
                String sizeText=each.getText();
                each.click();
                util.infoPass("Available size is: "+sizeText);
                break;
            }
        }
    }




}