package SeleniumCodeChallenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Day2 {
	static WebDriver driver;
	static String[] carouselNameList = { "Recommended For You", "Save big on mobiles & tablets", "Top picks in electronics",
			"Top picks in laptops", "Limited time offers", "Health care essentials" };

	public static void main(String[] args) throws InterruptedException {
		ArrayList<String> items = new ArrayList<String>();
		int n = carouselNameList.length;
		ListIterator<String> li ;
		System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.noon.com/uae-en/");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		for (int i = 0; i < n; i++) {
			items = fetchList(carouselNameList[i]);
			System.out.println("#########################   " + carouselNameList[i] + "  ##############################");
			//Thread.sleep(200);
			li  = items.listIterator();
			System.out.println("Number of items = " + items.size());
			while (li.hasNext()) {
				System.out.println(li.next());
			}
			System.out.println("---------------------------------------------------------------------");
		}
		driver.quit();
	}

	static ArrayList<String> fetchList(String carouselName) throws InterruptedException {
		ArrayList<String> result = new ArrayList<String>();
		List<WebElement> elementList = driver.findElements(By.xpath("//h3[contains(text(),'" + carouselName + "')]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (elementList.isEmpty()) {
			while (elementList.isEmpty()) {
				js.executeScript("window.scrollBy(0,600)");
				elementList = driver.findElements(By.xpath("//h3[contains(text(),'" + carouselName + "')]"));
			}
		}
		js.executeScript("arguments[0].scrollIntoView(true)", elementList.get(0));
		String text = elementList.get(0).getText();
		List<WebElement> items = driver.findElements(By.xpath("//div[@class='lazyload-wrapper']//h3[contains(text(),'"
				+ text
				+ "')]/parent::div/parent::div/following-sibling::div//div[contains(@class,'swiper-slide')]/div/a/div/div[2]/div/div[@class='e3js0d-11 liZdac']"));
		int size = items.size();
		for (int i = 0; i < size; i++) {
			if (items.get(i).getText().isEmpty() && !driver
					.findElement(By.xpath("//div[@class='lazyload-wrapper']//h3[contains(text(),'" + text + "')]"
							+ "/parent::div/parent::div/following-sibling::div/div[2]"))
					.getAttribute("class").contains("swiper-button-disabled")) {
				driver.findElement(By.xpath("//div[@class='lazyload-wrapper']//h3[contains(text(),'" + text
						+ "')]/parent::div/parent::div/following-sibling::div/div[2]")).click();
				Thread.sleep(100);
			} else {
				result.add(items.get(i).getText());
			}
		}
		Collections.sort(result);
		return result;
	}
}
