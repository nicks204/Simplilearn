package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ApachePOI {
	
	WebDriver driver;
	SoftAssert assetobj;
	
	XSSFWorkbook wb;
	XSSFSheet sheet1;
	
	@BeforeMethod
	public void setup() {
		
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		
		driver = new ChromeDriver();
		
		driver.get("https://www.simplilearn.com/");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);	
		
	}
	

	
	@Test
	public void Testcase1() throws IOException {
		
		
		FileInputStream fis = new FileInputStream("exceldata.xlsx");
		
		wb = new XSSFWorkbook(fis);
		
		sheet1 = wb.getSheet("data");
		
		int RowCount = sheet1.getLastRowNum();
		
		String username =null ;
		String pwd = null;
		
		for(int i=0;i<RowCount;i++) {
			
			String Testname = sheet1.getRow(i).getCell(0) .getStringCellValue();
			
			if(Testname.equals("mytest")) {
				
				username = sheet1.getRow(i).getCell(1).getStringCellValue();
				
				pwd = sheet1.getRow(i).getCell(2).getStringCellValue();
				
			}	
			
		}
		
		
		
		WebElement lnkLogin = driver.findElement(By.className("login"));
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(lnkLogin));
		
		lnkLogin.click();
		
		WebElement editUsername = driver.findElement(By.name("user_login"));
		
		editUsername.sendKeys(username);
		
		WebElement editPWd = driver.findElement(By.name("user_pwd"));
		
		editPWd.sendKeys(pwd);
		
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
