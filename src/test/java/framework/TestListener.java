package framework;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.printf("======================================== STARTING TEST %s ========================================\n",
                result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        System.out.printf("======================================== FINISHED TEST %s (Duration: %ss)========================================\n",
                result.getName(),getExecutionTime(result));
    }

    public void onTestFailure(ITestResult result) {
        takeScreenshot();
        System.out.printf("======================================== FAILED TEST %s (Duration: %ss)========================================\n",
                result.getName(),getExecutionTime(result));
    }

    public void onTestSkipped(ITestResult result) {
        System.out.printf("======================================== SKIPPING TEST %s ========================================\n",
                result.getTestName());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.printf("======================================== TEST %s FAILED IN ALLOWED %% ========================================\n",
                result.getName());
    }

    public void onStart(ITestContext iTestContext) {}

    public void onFinish(ITestContext iTestContext) {}

    private long getExecutionTime(ITestResult iTestResult){
        return TimeUnit.MILLISECONDS.toSeconds(iTestResult.getEndMillis() - iTestResult.getStartMillis());
    }

    @Attachment(value = "Last screen state", type = "image/png")
    private static byte[] takeScreenshot() {
        return ((TakesScreenshot) Browser.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Expected result", fileExtension = ".txt")
    public static byte[] expectedResult(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/test/resources", resourceName));
    }

    @Attachment(value = "Actual result", fileExtension = ".txt")
    public static byte[] actualResult(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/test/resources", resourceName));
    }
}
