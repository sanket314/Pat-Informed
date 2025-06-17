package testcases;

import org.testng.annotations.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import Pages.HomePage;
import base.BaseTest;

public class DashboardTest extends BaseTest {
	
	HomePage homepage;
	
	@BeforeClass
	public void setup() {
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		homepage = new HomePage(driver,loc);
	}
	
    @DataProvider(name = "searchKeywords")
    public Object[][] provideSearchKeywords() {
        String keywords = System.getProperty("keywords", "");
        String[] keywordArray = keywords.split(",", -1);

        Object[][] data = new Object[keywordArray.length][1];
        for (int i = 0; i < keywordArray.length; i++) {
            data[i][0] = keywordArray[i]; 
        }

        return data;

    }
	
		
    @Test(dataProvider = "searchKeywords")
    public void testSearchFunctionality(String keyword) {

    	homepage.searchPatent(keyword);
    	
    	try {
    	    
    	    WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("AlertAcceptbtn"))));
    	    
    	    acceptButton.click();
    	    System.out.println("Terms accepted successfully.");
    	    
    	} 
    	catch (Exception e) 
    	{
    		
    	} 
    	
    	   List<WebElement> noResults = driver.findElements(By.xpath(loc.getProperty("NoResult")));
           if (!noResults.isEmpty() && noResults.get(0).isDisplayed()) {
               System.out.println("No patent for this keyword: " + keyword);
               Assert.fail("No patent for this keyword: " + "\n"+ "\n"+ keyword);
           }
    	
        homepage.waitForResultsToLoad();
        homepage.clickFirstResultPatent();
        
              
        List<WebElement> boxes = driver.findElements(By.xpath(loc.getProperty("JurisdictionBoxes")));
        
        boxes.sort(Comparator.comparingInt(e -> e.getLocation().getY()));

        
        System.out.println("Number of boxes found: " + boxes.size());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("Keyword  - " + keyword );


        for (WebElement box : boxes) 
        {
            List<LocalDate> foundDates = new ArrayList<>();
            String[] lines = box.getText().split("\n");

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i].toLowerCase();

                if (line.startsWith("publication date") || line.startsWith("filing date") || line.startsWith("grant date")) {
                    boolean dateFound = false;

                    String[] parts = lines[i].split("\\s+");
                    
                    for (String part : parts) 
                    {
                        if (part.matches("\\d{4}-\\d{2}-\\d{2}"))
                        {
                            foundDates.add(LocalDate.parse(part, formatter));
                            dateFound = true;
                            break;
                        }
                    }

                    if (!dateFound && i + 1 < lines.length) 
                    {
                        String nextLine = lines[i + 1].trim();
                        if (nextLine.matches("\\d{4}-\\d{2}-\\d{2}.*")) 
                        {
                            foundDates.add(LocalDate.parse(nextLine.substring(0, 10), formatter));
                        }
                    }
                }
            }

        
            if (foundDates.size() >= 2) 
            {
            	 LocalDate d1 = foundDates.get(0);
                 LocalDate d2 = foundDates.get(1);
                
                 int yearDiff = d2.getYear() - d1.getYear();
                 int monthDiff = d2.getMonthValue() - d1.getMonthValue();
                 
                 int totalMonths = yearDiff * 12 + monthDiff;
                 
                 long daysBetween = ChronoUnit.DAYS.between(d1, d2);
                 
                System.out.println("Dates found: " + d1 + " and " + d2);
                System.out.println("Months Difference: " + Math.abs(totalMonths));
                System.out.println("Days Difference: " + Math.abs(daysBetween) +"\n");

                break; 
            }
        }
	
    }
}      
        


