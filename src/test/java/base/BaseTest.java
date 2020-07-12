package base;

import manager.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class BaseTest {
    public ExtentReports report = null;
    public ExtentTest test = null;
    public SoftAssert softAssert = null;

    public String browser = null;
    public String testerRole = null;

    /**
     *
     * @param result
     * @param context
     *
     * initializes all the required variables before the test methods run
     */
    @BeforeMethod(alwaysRun = true)
    public void init(ITestContext context, ITestResult result) {
        report = ExtentManager.getReport();
        test = report.createTest(result.getMethod().getMethodName().toUpperCase());
        result.setAttribute("testObject", test);
        softAssert = new SoftAssert();

        //reading parameter from testng.xml
        browser = context.getCurrentXmlTest().getParameter("browser");
        testerRole = getParamBasedOnGroup(context, "testerRole");
    }

    /**
     * wraps up the required actions after test methods' execution is completed
     */
    @AfterMethod(alwaysRun = true)
    public void wrapUp() {
        report.flush();
    }

    /**
     *
     * @param message
     *
     * logs the message as info
     */
    public void log(String message) {
        test.log(Status.INFO, message);
    }

    /**
     *
     * @param message
     *
     * logs the message as error
     */
    public void error(String message) {
        test.log(Status.ERROR, message);
    }

    /**
     *
     * @param message
     *
     * logs the message as success
     */
    public void pass(String message) {
        test.log(Status.PASS, message);
    }

    /**
     *
     * @param message
     *
     * logs the message as failure
     */
    public void fail(String message) { // only fails in extent reports
        test.log(Status.FAIL, message);
    }

    /**
     *
     * @param message
     *
     * solves the "errors going at the end of the output" problem
     * fails in both soft assert and extent report but continues the testing
     */
    public void softAssertCont(String message) {
        fail(message);
        softAssert.fail(message);
    }

    /**
     *
     * @param message
     *
     * solves the "errors going at the end of the output" problem
     * fails in both soft assert and extent report and stops the testing
     */
    public void softAssertStop(String message) {
        fail(message);
        softAssert.fail(message);
        softAssert.assertAll();
    }

    /**
     *
     * @param context
     * @param groupNameInitial
     * @return String
     *
     * Get the value of a parameter from xml file and set it as a variable based on the groups of the test methods
     */
    private String getParamBasedOnGroup(ITestContext context, String groupNameInitial) {
        /*
         This is being called from @BeforeMethod
         So every time it gets called there will only one test method in the context
         Get the groups of the test method in the context
        */
        for(String g: context.getAllTestMethods()[0].getGroups()) {
            if(g.startsWith(groupNameInitial))
                return context.getCurrentXmlTest().getParameter(g);
        }
        return null;
    }
}
