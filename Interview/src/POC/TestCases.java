package POC;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestCases {
 
	WebDriver driver;
	
	
	 @DataProvider(name = "loginDetails")
	 
	  public static Object[][] credentials() {
	  
	        return new Object[][] { { "testuser_1", "Test@123" }, { "testuser_2", "Test@456" }};
	 
	  }
	
	
  @BeforeTest
  @Parameters("browser")
  public void startTest(String browser) throws Exception {

	  if(browser.equalsIgnoreCase("firefox")) {
		  System.setProperty("webdriver.firefox.marionette", ".\\Drivers\\geckodriver.exe");
		  driver = new FirefoxDriver();
	  }
	  if(browser.equalsIgnoreCase("chrome")) {
		  System.setProperty("webdriver.chrome.driver",".\\Drivers\\chromedriver.exe");
		  driver = new ChromeDriver();
	  }
	  else if(browser.equalsIgnoreCase("Edge")) {
		  System.setProperty("webdriver.edge.driver",".\\Drivers\\MicrosoftWebDriver.exe");
		  driver =new EdgeDriver();
		  
	  }
	  else {
		  throw new Exception("Browser is not valid");
	  }
	  
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  driver.manage().window().maximize();
	  
  }

  
  
  @Test(dataProvider = "loginDetails")
  public void loginToFacebook(String uName, String pWord) {
	  driver.get("https://www.facebook.com/");
	  driver.findElement(By.id("email")).sendKeys(uName);
	  driver.findElement(By.id("pass")).sendKeys(pWord);
	  driver.findElement(By.id("loginbutton")).click();
	  if(driver.getCurrentUrl().contains("login_attempt")) {
		  System.out.println("Login falied with invalid data,  Username : "+ uName + "and Password : " + pWord);
	  }
	  else {
		  System.out.println("Logged in sucessfully with valid data,  Username : "+ uName + "and Password : " + pWord);
	  }
  }
  
  
  @Test(dataProvider = "loginDetails")
  public void loginWithDynamicXpaths(String uName, String pWord) {
	  driver.get("https://www.facebook.com/");
	  driver.findElement(By.xpath("//input[@id = 'email']")).sendKeys(uName);   // xpath with id(and xpath with name is //input[@name = 'email'])
	  driver.findElement(By.xpath("//input[@id = 'email']/following::input[1]")).sendKeys(pWord); //xpath using following and preceding nodes
	  driver.findElement(By.xpath("//input[contains(@value, 'Log In')][@type = 'submit']")).click(); //xpath using multiple attributes
	  if(driver.getCurrentUrl().contains("login_attempt")) {
		  System.out.println("Login falied with invalid data,  Username : "+ uName + "and Password : " + pWord);
	  }
	  else if(driver.getCurrentUrl().contains("login/help")) {
		  System.out.println("Login falied with invalid data,  Username : "+ uName + "and Password : " + pWord);
	  }
	  else {
		  System.out.println("Logged in sucessfully with valid data,  Username : "+ uName + "and Password : " + pWord);
	  }
  }
  
  
  @Test(dataProvider = "loginDetails")
  public void loginWithAbsoluteXpaths(String uName, String pWord) {
	  driver.get("https://www.facebook.com/");
	  driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(uName); 
	  driver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys(pWord);
	  driver.findElement(By.xpath("//*[@id=\"u_0_2\"]")).click();
	  if(driver.getCurrentUrl().contains("login_attempt")) {
		  System.out.println("Login falied with invalid data,  Username : "+ uName + "and Password : " + pWord);
	  }
	  else if(driver.getCurrentUrl().contains("login/help")) {
		  System.out.println("Login falied with invalid data,  Username : "+ uName + "and Password : " + pWord);
	  }
	  else {
		  System.out.println("Logged in sucessfully with valid data,  Username : "+ uName + "and Password : " + pWord);
	  }
  }
  
  @Test
  public void verifyFontColorOfLogInButton() {
	  driver.get("https://www.facebook.com/");
	  WebElement logInBtn = driver.findElement(By.id("loginbutton"));
	  
	  String fontSize = logInBtn.getCssValue("font-size");
	  System.out.println("Font Size -> "+ fontSize);

	  String fontColour = logInBtn.getCssValue("color");
	  System.out.println("Font Size -> "+ fontColour);

	  String fontBtnTxtAlign = logInBtn.getCssValue("text-align");
	  System.out.println("Font Text Alignment -> "+fontBtnTxtAlign);
	  
	  // we can verify these values using Assert or Verify commands or if condition
	  
  }
  
  
  
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
