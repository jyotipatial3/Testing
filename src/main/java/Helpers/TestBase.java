package Helpers;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import Helpers.Config;

public class TestBase {

	public WebDriver driver;
	public TestBase testBase;

	protected static ThreadLocal<Config[]> threadLocalConfig = new ThreadLocal<Config[]>();

	@DataProvider(name = "GetTestConfig")
	public Object[][] GetTestConfig(Method method)
	{
		Config testConf = new Config(method);
		testConf.testName = method.getDeclaringClass().getName() + "." + method.getName();
		testConf.testStartTime = getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
		threadLocalConfig.set(new Config[] { testConf });
		return new Object[][] { { testConf } };
	}


	/** Generate a random Alphabets string of given length
	 * @param length Length of string to be generated
	 */
	public static String generateRandomAlphabetsString(int length)
	{
		Random rd = new Random();
		String aphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++)
		{
			sb.append(aphaNumericString.charAt(rd.nextInt(aphaNumericString.length())));
		}

		return sb.toString();
	}

	/**
	 * Generate a random Alpha-Numeric string of given length
	 * 
	 * @param length
	 *            Length of string to be generated
	 */
	public static String generateRandomAlphaNumericString(int length)
	{
		Random rd = new Random();
		String aphaNumericString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++)
		{
			sb.append(aphaNumericString.charAt(rd.nextInt(aphaNumericString.length())));
		}

		return sb.toString();
	}

	/**
	 * Generate a random Special Character string of given length
	 * 
	 * @param length
	 *            Length of string to be generated
	 */

	public static String generateRandomSpecialCharacterString(int length)
	{
		Random rd = new Random();
		String specialCharString =  "~!@#$%^*()_<>?/}[]|\";";
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++)
		{
			sb.append(specialCharString.charAt(rd.nextInt(specialCharString.length())));
		}

		return sb.toString();
	}

	public void closeBrowser(Config config)
	{
		config.driver.close();
		config.driver = null;
	}

	public static String getCurrentDateTime(String format)
	{
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result)
	{
		Config[] testConfigs = threadLocalConfig.get();
		String testcaseName = null;
		if (testConfigs != null)
			for (Config testConf : testConfigs)
			{ 
				if (testConf != null)
				{
					testcaseName = testConf.getTestName();
					closeBrowser(testConf);
				}

			}
		System.out.println("Test '" + testcaseName + "' Ended");
	}

}
