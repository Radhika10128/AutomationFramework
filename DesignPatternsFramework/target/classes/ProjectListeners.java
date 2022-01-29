package automationResources;

import PageComponents.Log;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.Arrays;


public class ProjectListeners extends CommonBase implements ITestListener {

    private static long endTime;

    private static void setStartTime(long startTime) {}

    private static void setEndTime(long endTime) {
        ProjectListeners.endTime = endTime;
    }

    public void onTestStart(ITestResult result) {

        String className = result.getTestClass().getName();
        String pageName = className.split("\\.")[1];
        String groups[] = result.getMethod().getGroups();

        System.out.println("--------- Executing :- " + getSimpleMethodName(result) + " ---------");
        ExtentTestManager.createTest(pageName, result.getName(), result.getMethod().getDescription(), groups);
        ExtentTestManager.setCategoryName("Failed");
    }

    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().assignCategory("Passed");
        addExtentLabelToTest(result);
        ExtentTestManager.endTest();
    }

    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub
        //Screenshot
        ExtentTestManager.getTest().log(Status.FAIL, result.getName() + " Test is failed" + result.getThrowable());

        WebDriver driver = null;
        String testMethodName = result.getMethod().getMethodName();
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ExtentTestManager.getTest().fail("<br><font color= red>" + "Screenshot" + "</font></b>",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshotPath(testMethodName, driver)).build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        addExtentLabelToTest(result);
        ExtentTestManager.endTest();
    }

    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP, result.getName() + " Test is Skipped" + result.getThrowable());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
        setStartTime(context.getStartDate().getTime());
        setEndTime(context.getEndDate().getTime());
    }

    private synchronized String getSimpleMethodName(ITestResult result) {
        return result.getName();
    }

    private synchronized void addExtentLabelToTest(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS)
            ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
        else if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
        } else
            ExtentTestManager.getTest().skip(MarkupHelper.createLabel("Test Skipped", ExtentColor.ORANGE));
    }

}
