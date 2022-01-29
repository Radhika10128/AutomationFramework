package automationResources;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentLoggerReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {
    static ExtentReports extent;

    public static ExtentReports getReportObject() {

        createExtentReportFolder();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        Date date = new Date();
        String actualDate = format.format(date);

        String path = System.getProperty("user.dir") + "\\ExtentReport\\Report_" + actualDate + ".html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setEncoding("utf-8");
        reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        reporter.config().setReportName("Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Radhika");
        return extent;
    }

    public static void createExtentReportFolder() {
        String path = System.getProperty("user.dir");
        String extentReportFolderPath = path + "\\ExtentReport";
        File folder = new File(extentReportFolderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }
}
