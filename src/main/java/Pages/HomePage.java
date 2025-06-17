package Pages;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage  extends BasePage{

	public HomePage(WebDriver driver, Properties loc) {
		super(driver, loc);
	}

	public void searchPatent(String Patent) {
        type("Searchbox", Patent);  
        if (Patent.isEmpty()) {
            click("SearchButton");
        }
	}
	
	
	public void waitForResultsToLoad() {
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loc.getProperty("FirstResult"))));
	}
	public void clickFirstResult() {
        click("FirstResult");  
    }
	
	public void clickFirstResultPatent() {
		 WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("FirstResultPatent"))));
		 clickable.click();
	}
	
	
}
