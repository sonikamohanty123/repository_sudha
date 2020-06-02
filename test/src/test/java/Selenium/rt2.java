package Selenium;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.annotations.BeforeTest;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
public class rt2 {
	WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;

	@BeforeTest
	public void beforeTest() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") 
				+ "/extent-reports/" + new SimpleDateFormat("hh-mm-ss-ms-dd-MM-yyyy")
				.format(new Date(0)) + ".html");
		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "GFT NexGen Testing Stream");
		extent.setSystemInfo("Environment", "Automation Testing Selenium");
		extent.setSystemInfo("User Name", "Pratiksha Daptare");

		htmlReporter.config().setDocumentTitle("AGP-BatchReport");
		htmlReporter.config().setReportName("AGP-Batch-extentReport");
		htmlReporter.config().setTheme(Theme.STANDARD);

		System.setProperty("webdriver.chrome.driver", "Resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		System.out.println("open browser");
		//implicitwait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void f() throws InterruptedException {
		logger = extent.createTest("passTest");
		driver.get("https://lkmdemoaut.accenture.com/TestMeApp/login.htm");
		driver.findElement(By.xpath("//input[contains(@id,'userName')]")).sendKeys("Lalitha");
		driver.findElement(By.name("password")).sendKeys("Password123");
		driver.findElement(By.xpath("//input[contains(@type,'submit')]")).click();

		WebElement abt =  driver.findElement(By.xpath("//span[contains(.,'AboutUs')]"));
		WebElement ofc = driver.findElement(By.xpath("//span[contains(.,'Our\n" + 
				"												Offices')]"));
		WebElement blore = driver.findElement(By.xpath("//span[contains(.,'Chennai')]"));

		Actions act = new Actions(driver);
		act.moveToElement(abt).click().pause(2000).perform();
		act.moveToElement(ofc).click().pause(2000).perform();
		act.moveToElement(blore).click().pause(2000).perform();
		Thread.sleep(2000);

		Set<String> allWH = driver.getWindowHandles();
		Thread.sleep(2000);

		for (String singleH:allWH) {
			driver.switchTo().window(singleH);
			Thread.sleep(2000);
			System.out.println("Child window handle is: "+singleH);
		}
		WebElement frame = driver.findElement(By.name("main_page"));
		driver.switchTo().frame(frame);

		String address=driver.findElement(By.tagName("address")).getText();
		System.out.println(address);

		logger.log(Status.PASS, MarkupHelper.createLabel("test case passed is Pass test", ExtentColor.GREEN));



	}
	@AfterMethod
	public void getResult(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		}else if (result.getStatus() == ITestResult.SKIP){
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		}
	}

	@AfterTest
	public void afterTest() throws InterruptedException {
		Thread.sleep(2000);
		driver.quit();
		System.out.println("closed all tabs");
		extent.flush();

	}

}


