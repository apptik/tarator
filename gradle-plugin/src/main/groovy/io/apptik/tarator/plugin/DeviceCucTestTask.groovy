package io.apptik.tarator.plugin

import com.android.build.gradle.internal.tasks.DeviceProviderInstrumentTestTask
import com.android.build.gradle.internal.test.report.ReportType
import com.android.build.gradle.internal.test.report.TestReport
import com.android.builder.internal.testing.SimpleTestCallable
import com.android.builder.testing.SimpleTestRunner
import com.android.builder.testing.TestRunner
import com.android.builder.testing.api.DeviceException
import com.android.builder.testing.api.TestException
import com.android.utils.FileUtils
import com.google.common.collect.ImmutableList
import org.gradle.api.GradleException
import org.gradle.internal.logging.ConsoleRenderer

class DeviceCucTestTask extends DeviceProviderInstrumentTestTask {


    @Override
    protected void runTests() throws DeviceException, IOException, InterruptedException,
            TestRunner.NoAuthorizedDeviceFoundException, TestException {
        File resultsOutDir = getResultsDir();
        FileUtils.cleanOutputDir(resultsOutDir);

        File coverageOutDir = getCoverageDir();
        FileUtils.cleanOutputDir(coverageOutDir);

        boolean success = false;
        // If there are tests to run, and the test runner returns with no results, we fail (since
        // this is most likely a problem with the device setup). If no, the task will succeed.
        if (!testsFound()) {
            getLogger().info("No tests found, nothing to do.");
            // If we don't create the coverage file, createXxxCoverageReport task will fail.
            File emptyCoverageFile = new File(coverageOutDir, SimpleTestCallable.FILE_COVERAGE_EC);
            emptyCoverageFile.createNewFile();
            success = true;
        } else {
            File testApk = testData.getTestApk();
            String flavor = getFlavorName();

            final TestRunner testRunner;
            testRunner = new SimpleTestRunner(
                    getSplitSelectExec(),
                    getProcessExecutor(),
                    enableSharding,
                    numShards);
            deviceProvider.init();

            Collection<String> extraArgs = installOptions == null || installOptions.isEmpty() ? ImmutableList.<String>of() : installOptions;
            try {
                success = testRunner.runTests(getProject().getName(), flavor,
                        testApk,
                        testData,
                        deviceProvider.getDevices(),
                        deviceProvider.getMaxThreads(),
                        deviceProvider.getTimeoutInMs(),
                        extraArgs,
                        resultsOutDir,
                        coverageOutDir,
                        getILogger());
            } finally {
                deviceProvider.terminate();
            }

        }

        // run the report from the results.
        File reportOutDir = getReportsDir();
        FileUtils.cleanOutputDir(reportOutDir);

        TestReport report = new TestReport(ReportType.SINGLE_FLAVOR, resultsOutDir, reportOutDir);
        report.generateReport();

        if (!success) {
            testFailed = true;
            String reportUrl = new ConsoleRenderer().asClickableFileUrl(
                    new File(reportOutDir, "index.html"));
            String message = "There were failing tests. See the report at: " + reportUrl;
            if (getIgnoreFailures()) {
                getLogger().warn(message);
                return;

            } else {
                throw new GradleException(message);
            }
        }

        testFailed = false;
    }
}