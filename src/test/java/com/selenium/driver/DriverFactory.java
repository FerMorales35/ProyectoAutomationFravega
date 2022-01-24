package com.selenium.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.Reporter;

public class DriverFactory {
	private enum browsers {
		EXPLORER, FIREFOX, CHROME
	};

	public static WebDriver getBrowser(WebDriver driver, ITestContext context) {
		
		String browser = context.getCurrentXmlTest().getParameter("NombreNavegador");
		String url = context.getCurrentXmlTest().getParameter("url");

		switch (browsers.valueOf(browser)) {

		case CHROME:// Using WebDriver
		{
			System.setProperty("webdriver.chrome.driver", "C:\\chrome\\chromedriver.exe");
			Reporter.log("Abrimos el navegador " + browser);
			driver = new ChromeDriver();
			break;
		}
		case FIREFOX:// Using WebDriver
		{
			System.setProperty("webdriver.gecko.driver", "C:\\firefox\\geckodriver.exe");
			Reporter.log("Abrimos el navegador " + browser);
			driver = new FirefoxDriver();
			break;
		}

		default:
			Reporter.log("Selecciona Chrome por defecto");
			System.setProperty("webdriver.chrome.driver", "C:\\chrome\\chromedriver.exe");
			Reporter.log("Abrimos el navegador " + browser);
			driver = new ChromeDriver();
			break;

		}
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
}
