package testClasses;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import baseClass.BaseClass;
import pageObject.LoginPageObject;
import utils.ExcelUtils;

public class LoginDataDriven extends BaseClass {

    @Test
    public void loginValidation()
    {
        // Get total row count from Excel
        int rowCount = ExcelUtils.getRowCount();

        // Create SoftAssert object
        SoftAssert sa = new SoftAssert();

        // Iterate through Excel rows
        for(int i = 1; i <= rowCount; i++)
        {
            // Launch application
            driver.get("https://practicetestautomation.com/practice-test-login/");

            // Read data from Excel
            String username = ExcelUtils.getCellData(i, 0);
            String password = ExcelUtils.getCellData(i, 1);
            String expected = ExcelUtils.getCellData(i, 2);

            // Create Page Object
            LoginPageObject obj = new LoginPageObject(driver);

            // Enter username and password
            obj.enterUserName(username);
            obj.enterPassword(password);
            obj.clickSubmit();

            // Validation
            String actual;

            if(obj.geturl().contains("logged-in-successfully"))
            {
                actual = obj.geturl();
            }
            else
            {
                try
                {
                    actual = obj.errorMessage();
                }
                catch(Exception e)
                {
                    actual = "No message displayed";
                }
            }

            // Remove unwanted spaces
            String exp = expected.trim().replaceAll("\\s+", " ");
            String act = actual.trim().replaceAll("\\s+", " ");

            // Soft Assertion
            sa.assertEquals(act, exp,
                    "Validation failed at Excel Row: " + i);

            // Write actual result into Excel
            ExcelUtils.setCellData(i, 3, actual);

            // Compare expected and actual
            String status;

            if(act.equalsIgnoreCase(exp))
            {
                status = "PASS";
            }
            else
            {
                status = "FAIL";
            }

            // Write status into Excel
            ExcelUtils.setCellData(i, 4, status);

            // Print result
            System.out.println("--------------------------------");
            System.out.println("Row Number: " + i);
            System.out.println("Username: " + username);
            System.out.println("Expected: " + expected);
            System.out.println("Actual: " + actual);
            System.out.println("Status: " + status);
            System.out.println("--------------------------------");
        }

        // Execute all assertions at the end
        sa.assertAll();
    }
}