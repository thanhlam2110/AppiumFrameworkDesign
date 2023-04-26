package lamnguyen.pageObjects.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage {
	AndroidDriver driver;
	public FormPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	//driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Nguyen Tran Thanh Lam");
	@AndroidFindBy(id="com.androidsample.generalstore:id/nameField")
	private WebElement nameFiled;
	//driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
	@AndroidFindBy(xpath="//android.widget.RadioButton[@text='Female']")
	private WebElement femaleOption;
}
