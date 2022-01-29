package automationResources;

import PageComponents.Log;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Reporter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtentTestManager {

    static Map<String, ExtentTest> feature = new HashMap<String, ExtentTest>();
    static ExtentReports extent = ExtentReporter.getReportObject();
    private static final ThreadLocal<String> categoryName = new ThreadLocal<>();
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static Map<ExtentTest, List<String>> categories = new HashMap<>();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized void endTest() {
        extent.flush();
    }

    public synchronized static void createTest(String className, String testName, String description, String groups[]) {
        if (feature.get(className) == null)
            feature.putIfAbsent(className, extent.createTest(className));
        extentTestMap.put((int) Thread.currentThread().getId(), feature.get(className).createNode(testName));

        categories.put(getTest(), Arrays.asList(groups));
    }

    public static ThreadLocal<String> getCategoryName() {
        return categoryName;
    }

    public static boolean isCategory(String groupNameForScenario) {
        try {
            String groupName = "";
            groupName = categories.get(getTest()).stream().filter(group -> group.equalsIgnoreCase(groupNameForScenario)).findFirst().get();
            return groupName != null ? groupName.equalsIgnoreCase(groupNameForScenario) : false;
        } catch (Exception e) {
            System.out.println("Category name missing");
            return false;
        }
    }

    public static void setCategoryName(String categoryName) {
        if (isCategory("Sanity"))
            getTest().assignCategory("Sanity");
        if (isCategory("Regression"))
            getTest().assignCategory("Regression");

        if ((getTest().getStatus().toString()).equalsIgnoreCase("PASSED")) {
            getTest().assignCategory("Passed_Cases");
        } else if ((getTest().getStatus().toString()).equalsIgnoreCase("FAILED")) {
            getTest().assignCategory("Failed_Cases");
        } else if ((getTest().getStatus().toString()).equalsIgnoreCase("SKIPPED")) {
            getTest().assignCategory("Skipped_Cases");
        }
    }

    public synchronized static void reporterLog(String log) {
        Log.info(log);
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.PASS, log);
            Reporter.log(log + "<br/>");
        }
    }
}
