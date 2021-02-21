package test;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;



public class LoginTest {
	
	WebDriver driver;
	SoftAssert assetobj;
	
	@BeforeMethod
	public void setup() {
		
		System.setProperty("webdriver.chrome.driver", "/home/nikunjshah204gm/Downloads/chromedriver");
		
		System.setProperty("webdriver.gecko.driver", "/home/nikunjshah204gm/Downloads/geckodriver");
		
		driver = new ChromeDriver();
		
		driver.get("https://www.simplilearn.com/");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);	
		
	}
	


	@Parameters({"UserName","Password"})
	
	@Test
	public void Testcase1(String UserName, String Password) {
		
		WebElement lnkLogin = driver.findElement(By.className("login"));
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(lnkLogin));
		
		lnkLogin.click();
		
		WebElement editUsername = driver.findElement(By.name("user_login"));
		
		editUsername.sendKeys(UserName);
		
		WebElement editPWd = driver.findElement(By.name("user_pwd"));
		
		editPWd.sendKeys(Password);
		
		WebElement btnLogin = driver.findElement(By.name("btn_login"));
		
		btnLogin.click();
		
		WebElement error = driver.findElement(By.id("msg_box"));
		
		String actMsg = error.getText();
		String expMsg = "The email or password you have entered is invalid.";
		
		//Assert.assertEquals(actMsg, expMsg);
		
		assetobj = new SoftAssert();
		
		assetobj.assertEquals(actMsg, expMsg);
		
		System.out.println("After Assertion");
	
		
	}
	
	
	@AfterMethod
	public void teardown() {
	
		try {
			
			assetobj.assertAll();
			
		}catch(Exception e) {
			
			System.out.println("Failiure Happend");
			
		} finally {
			
			driver.quit();
			
		}
		
		
		
		
		
	}

}
