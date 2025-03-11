import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Test_Example extends TestBase{


// test report time-3.29 //
    static ExtentReports report;

    static ExtentTest test;

    static ExtentReports extent = new ExtentReports();




    @Test

    public void test_Google_Example1() throws InterruptedException {

        WebDriver driver = getDriver();
        driver.get("https://www.google.com");
        driver.findElement (By.name("q")).sendKeys("Sri Lanka");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        Thread.sleep(10000);
        driver.quit();
    }







    @Test

    public void test_calorie_calculator() throws InterruptedException, IOException, CsvValidationException {

// Data Driven Testing with CSV //

        String CSVFile = "C:\\Users\\Rumal Tharinda\\Desktop\\testing\\Project\\TestAutomationproject101\\src\\main\\resources\\DataFiles\\CalculatorValues.csv";

        CSVReader reader = new CSVReader(new FileReader(CSVFile));
        String[] cell;

        while ((cell = reader.readNext()) != null) {
            if (cell.length < 3) {
                System.out.println("Skipping row due to insufficient data: " + Arrays.toString(cell));
                continue; // Skip this row if it has less than 3 elements
            }

            String age = cell[0];
            String height = cell[1];
            String weight = cell[2];

// End Using CSV data file //

            WebDriver driver = getDriver();
            driver.get("https://www.calculator.net/calorie-calculator.html");


            //Generate Extent Reports  ( Report ) time-2.47
            ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
            extent.attachReporter(spark);
            test = extent.createTest("Validate calculator functionality", "This is to calculate calories");
            //Generate Extent Reports  ( Report )


            // press clear button
            driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[5]/form/table[4]/tbody/tr[3]/td[2]/input[4]")).click();

            // Enter age
            driver.findElement(By.id("cage")).sendKeys(age);

            //radio button gender
            driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[5]/form/table[1]/tbody/tr[2]/td[2]/label[2]/span")).click();
            //End radio button gender

            // Enter Height
            driver.findElement(By.name("cheightmeter")).sendKeys(height);

            // Enter Weight
            driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[5]/form/table[3]/tbody/tr[2]/td[2]/input")).sendKeys(weight);


            // Dropdown //

            // Locate the dropdown element
            WebElement dropdownElement = driver.findElement(By.id("cactivity"));

            // Use Select class to interact with the dropdown
            Select dropdown = new Select(dropdownElement);

            // Select by value
            dropdown.selectByValue("1.55");  // Selects "Active: daily exercise or intense exercise 3-4 times/week"

            // OR Select by visible text
            // dropdown.selectByVisibleText("Active: daily exercise or intense exercise 3-4 times/week");

            // OR Select by index (0-based)
            // dropdown.selectByIndex(4);  // Selects the 5th option

            // End Dropdown //


            Thread.sleep(3000);

            // Press Calculate button
            driver.findElement(By.name("x")).click();


            // ## Assert Results ## ( check result by comparison )  //

            // creating a variable to store expected result  to comparison
            String ExpectedTitle = "Result"; // expected result //

            // Getting actual result from web ( Note: it should be a WebElement so define ass one then assign that to a variable called 'ActualTitle' in second line )
            WebElement titleElement = driver.findElement(By.xpath("//*[@id=\"content\"]/h2[1]")); //location//
            String ActualTitle = titleElement.getText();  // Actual result //

            // Printing Expected value and Actual Value
            System.out.println("This is the actual result: " + ActualTitle);
            System.out.println("This is expected title: " + ExpectedTitle);


            // Compare actual vs expected result ( Note: actual should be in left and expected should be in right )
            Assert.assertEquals(ActualTitle, ExpectedTitle);

            // ## End Assert Results ## ( check result by comparison )  //


            //  report - flush the data to report  //
            extent.flush();


            Thread.sleep(10000);
            driver.quit();
        }

    }





    @Test

    public void test_PropertyData() throws InterruptedException, IOException, CsvValidationException {

// Data Driven Testing with Property File //

        //Create and load property file
        Properties prop = new Properties();
        FileInputStream input = new FileInputStream("C:\\Users\\Rumal Tharinda\\Desktop\\testing\\Project\\TestAutomationproject101\\src\\main\\resources\\DataFiles\\testdata.properties");
        prop.load(input);


        Properties prop2 = new Properties();
        FileInputStream input2 = new FileInputStream("C:\\Users\\Rumal Tharinda\\Desktop\\testing\\Project\\TestAutomationproject101\\src\\main\\resources\\DataFiles\\testlocator.properties");
        prop2.load(input2);

// End Using Property data file //

            WebDriver driver = getDriver();
            driver.get(prop.getProperty("url"));


            //Generate Extent Reports  ( Report ) time-2.47
            ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
            extent.attachReporter(spark);
            test = extent.createTest("Validate calculator functionality", "This is to calculate calories");
            //Generate Extent Reports  ( Report )


            // press clear button
            driver.findElement(By.xpath(prop2.getProperty("clearbt_xpath"))).click();

            // Enter age
            driver.findElement(By.id(prop2.getProperty("age_id"))).sendKeys(prop.getProperty("age"));

            //radio button gender
            driver.findElement(By.xpath(prop2.getProperty("gender_xpath"))).click();
            //End radio button gender

            // Enter Height
            driver.findElement(By.name(prop2.getProperty("height_name"))).sendKeys(prop.getProperty("height"));

            // Enter Weight
            driver.findElement(By.xpath(prop2.getProperty("weight_xpath"))).sendKeys(prop.getProperty("weight"));


            // Dropdown //

            // Locate the dropdown element
            WebElement dropdownElement = driver.findElement(By.id(prop2.getProperty("activity_id")));

            // Use Select class to interact with the dropdown
            Select dropdown = new Select(dropdownElement);

            // Select by value
            dropdown.selectByValue("1.55");  // Selects "Active: daily exercise or intense exercise 3-4 times/week"

            // OR Select by visible text
            // dropdown.selectByVisibleText("Active: daily exercise or intense exercise 3-4 times/week");

            // OR Select by index (0-based)
            // dropdown.selectByIndex(4);  // Selects the 5th option

            // End Dropdown //


            Thread.sleep(3000);

            // Press Calculate button
            driver.findElement(By.name(prop2.getProperty("calculatebt_name"))).click();


            // ## Assert Results ## ( check result by comparison )  //

            // creating a variable to store expected result  to comparison
            String ExpectedTitle = "Result"; // expected result //

            // Getting actual result from web ( Note: it should be a WebElement so define ass one then assign that to a variable called 'ActualTitle' in second line )
            WebElement titleElement = driver.findElement(By.xpath("//*[@id=\"content\"]/h2[1]")); //location//
            String ActualTitle = titleElement.getText();  // Actual result //

            // Printing Expected value and Actual Value
            System.out.println("This is the actual result: " + ActualTitle);
            System.out.println("This is expected title: " + ExpectedTitle);


            // Compare actual vs expected result ( Note: actual should be in left and expected should be in right )
            Assert.assertEquals(ActualTitle, ExpectedTitle);

            // ## End Assert Results ## ( check result by comparison )  //


            //  report - flush the data to report  //
            extent.flush();


            Thread.sleep(1000);
            driver.quit();
        }


}