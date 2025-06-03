package Pages;

import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	
	
	public WebDriver driver;
    public WebDriverWait wait;
    public Properties loc;

    public BasePage(WebDriver driver, Properties loc) {
        this.driver = driver;
        this.loc = loc;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getElement(String propertyKey) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loc.getProperty(propertyKey))));
    }
    
    public void click(String propertyKey) {
        By locator = By.xpath(loc.getProperty(propertyKey));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void type(String propertyKey, String text) {
        By locator = By.xpath(loc.getProperty(propertyKey));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }



}
