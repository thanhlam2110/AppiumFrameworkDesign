package lamnguyen;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.service.DriverService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {
	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	@BeforeClass
	public void ConfigureAppium() throws MalformedURLException {
		String appiumJS = "C:\\Users\\ASUS\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
		String appiumServerIP = "127.0.0.1";
		String appiumServerConnection = "http://127.0.0.1:4723";
		service = new AppiumServiceBuilder().withAppiumJS(new File(appiumJS))
										.withIPAddress(appiumServerIP)
										.usingPort(4723)
										.build();
		service.start();
		// 2. Appium code -> AppiumServer -> Phone
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("RF8NB1NDPXA"); // Virtual phone in android studio or real device name
		options.setChromedriverExecutable("C:\\Users\\ASUS\\eclipse-workspace\\Appium\\src\\test\\java\\resources\\chromedriver.exe");
		String apkPath = "C:\\Users\\ASUS\\eclipse-workspace\\Appium\\src\\test\\java\\resources\\General-Store.apk";
		options.setApp(apkPath);
		driver = new AndroidDriver(new URL(appiumServerConnection), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //waiting 10s
		
	}
	public void longPressAction(WebElement ele) {
		((JavascriptExecutor)driver).executeScript(
				"mobile: longClickGesture", 
				ImmutableMap.of(
							"elementId",
							((RemoteWebElement)ele).getId(),
							"duration",2000
						)
				);
	}
	public void scrollToEndAction() throws InterruptedException {
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
					"left", 100, "top", 100, "width", 200, "height", 200,
					"direction", "down",
					"percent", 3.0
					));
		}while(canScrollMore);
		Thread.sleep(2000);
	}
	public void scrollOneMore() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
					"left", 100, "top", 100, "width", 200, "height", 200,
					"direction", "down",
					"percent", 3.0
					));
	}
	public void swipeAction(WebElement ele, String directiion) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
			    //"left", 100, "top", 100, "width", 200, "height", 200,
				"elementId", ((RemoteWebElement)ele).getId(),
			    "direction", directiion,
			    "percent", 0.75
			));
	}
	public Double getFormettedAmount(String amount) {
		Double price = Double.parseDouble(amount.substring(1));
		return price;
	}
	@AfterClass
	public void tearDown() {
		// 4. Stop app
		driver.quit(); // app run and quit 
		// 5. Stop server
		service.stop();
	}

}
