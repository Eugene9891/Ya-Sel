package com.example.tests;

import java.util.regex.Pattern;
import java.util.Collections;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class Test1 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.gecko.driver", "C:\\distr\\geckodriver.exe");
    driver =new FirefoxDriver();
    baseUrl = "https://market.yandex.ru/";
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void test1() throws Exception {
	  //Перейти по адресу https://market.yandex.ru/
	driver.get(baseUrl);
	driver.getPageSource().contains("market.yandex.ru");
	WebElement element = driver.findElement(By.xpath("//a[@class='logo logo_type_link logo_part_market']"));
	Assert.assertEquals("Маркет", element.getText());
	//Перейти в раздел "Электроника" -> "Мобильные телефоны"
	driver.getPageSource().contains("Электроника");
	driver.findElement(By.linkText("Электроника")).click();
	driver.findElement(By.linkText("Мобильные телефоны")).click();
	driver.getPageSource().contains("Цена");
	//НВвести Цену, руб. "от" значение 5125
    driver.findElement(By.id("glf-pricefrom-var")).click();
    driver.findElement(By.id("glf-pricefrom-var")).clear();
    driver.findElement(By.id("glf-pricefrom-var")).sendKeys("5125");
	driver.getPageSource().contains("5125");
	//Ввести Цену, руб. "до" значение 10123
    driver.findElement(By.id("glf-priceto-var")).click();
    driver.findElement(By.id("glf-priceto-var")).clear();
    driver.findElement(By.id("glf-priceto-var")).sendKeys("10123");
	driver.getPageSource().contains("10123");
	//Кликнуть на чекбокс "В продаже"
    if ( !driver.findElement(By.id("glf-onstock-select")).isSelected() )
    {
         driver.findElement(By.id("glf-onstock-select")).click();
    }
    if ( !driver.findElement(By.id("glf-onstock-select")).isSelected() )
    {
	    System.out.println("Can't select the checkbox");   
	    driver.close();
    }
    //Раскрыть блок "Тип"
    driver.findElement(By.xpath("//div[5]/div/h4")).click();
	driver.getPageSource().contains("Телефон для детей");
    //Кликнуть на селектбокс "смартфон"
    driver.findElement(By.id("glf-4940921-13475069")).click();
    if ( !driver.findElement(By.id("glf-4940921-13475069")).isSelected() )
    {
	    System.out.println("Can't select the checkbox");   
	    driver.close();
    }
    //Кликнуть на селектбокс "Android"
    driver.findElement(By.id("glf-13476053-select")).click();
    if ( !driver.findElement(By.id("glf-13476053-select")).isSelected() )
    {
	    System.out.println("Can't select the checkbox");   
	    driver.close();
    }
    
    Thread.sleep(1000);
    WebDriverWait wait = new WebDriverWait(driver, 5); 
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='snippet-card__view']")));
    //Случано выбрать 3 устройства
    //Array creation
    ArrayList<Integer> array = new ArrayList<Integer>();
    for(int i=1; i<13; i++)
    {
        array.add(i);
    }
    Collections.shuffle(array);
    
    //get devices
    int num=0;
    for (int a=1; a<13; a++) 
    {
    	if (num>2) break;
    	int i=array.get(a);
    	double n = Double.parseDouble(driver.findElement(By.xpath("(//div[@class='snippet-card__view'])["+i+"]")).getText());
    	if (n>3.6 && n<4.4)
    	{
	    	num++;
		    System.out.println("i(phone)="+i);
    	    System.out.println("n(rating)="+n);
    	    String Text1=driver.findElement(By.xpath("(//span[@class='snippet-card__header-text'])["+i+"]")).getText();
    	    System.out.println("Model:"+Text1);
    	    String Text2=driver.findElement(By.xpath("(//div[@class='snippet-card__info'])["+i+"]")).getText();   	    
    	    System.out.println("Price:"+Text2);    	    
    	}
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

