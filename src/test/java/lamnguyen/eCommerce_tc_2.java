package lamnguyen;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class eCommerce_tc_2 extends BaseTest{
	@Test
	public void FillForm() throws InterruptedException {
		//1. login page
		driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Nguyen Tran Thanh Lam");
		driver.hideKeyboard();
		driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
		driver.findElement(By.id("android:id/text1")).click();
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Australia\"));")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		// 2. add 2 products to cart
		driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click(); // add first product
		driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click(); // after "Add to cart" the text change to "Added to cart"
		// 3. Go to cart
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();	
		//Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains(
				driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")), 
				"text", 
				"Cart")
				);
		List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
		int productCount = productPrices.size();
		double sum = 0;
		for (int i = 0; i<productCount;i++) {
			String amountString = productPrices.get(i).getText();
			Double price = Double.parseDouble(amountString.substring(1));
			sum = sum + price;
		}
		System.out.print("Total= "+ Double.toString(sum));
		String displaySum = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();	
		Double displayFormattedSum  = getFormettedAmount(displaySum);
		Assert.assertEquals(sum, displayFormattedSum);
		// 4. Long press "term condition"
		WebElement termEle = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
		longPressAction(termEle);
		driver.findElement(By.id("android:id/button1")).click();
		// 5. Check box
		driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
		// 6. Buy button
		driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
		Thread.sleep(3000);
		// 7. Hybrid app (have browser in app)
		// 7.1 get context name
		Set<String> contexts = driver.getContextHandles();
		for(String contextName: contexts) {
			System.out.print("ContextName: " +contextName +"\n");
		}
		driver.context("WEBVIEW_com.androidsample.generalstore");
		// 7.2 Agree privacy
		driver.findElement(By.xpath("//div[@class='QS5gu SSSe5e']")).click();
		Thread.sleep(3000);
		try {
			driver.findElement(By.xpath("//div[@class='QS5gu SSSe5e']")).click();
			Thread.sleep(3000);
		}catch (Exception e) {
			System.out.println("Not found element");
		}
		driver.findElement(By.cssSelector("button[class='tHlp8d']")).click();
		Thread.sleep(3000);
		// 7.3. Input
		driver.findElement(By.name("q")).sendKeys("Nikola Tesla");
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		Thread.sleep(10000); 
		
		
	}
}
