package SeleniumCodeChallenge;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Day1 {
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
		String Xpath_current = "//div[@class='maincounter-number']/span[@class='rts-counter' and @rel='current_population']";
		String Xpath_todayNannual = "//div[@class='label-counter']/parent::div/div/div[@class='sec-counter']";
		System.setProperty("webdriver.chrome.driver",".\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(4,TimeUnit.SECONDS);
		driver.get("https://www.worldometers.info/world-population/");
		int i = 0;
		while(i<=20) {
		i++;
		System.out.println("_____________");
		printPopulation(Xpath_current);
		printPopulation(Xpath_todayNannual);
		Thread.sleep(1000);
		}
		driver.quit();
	}
	
	public static void printPopulation(String locator) {
		List<WebElement> list = driver.findElements(By.xpath(locator));
		for(WebElement e : list) {
			System.out.println(e.getText());
		}
	}
}
