package io.apptik.tarator;

import com.android.builder.core.DefaultApiVersion;
import com.android.builder.model.ApiVersion;
import com.android.builder.testing.TestData;
import com.android.builder.testing.api.DeviceConfigProvider;
import com.android.ide.common.process.ProcessException;
import com.android.ide.common.process.ProcessExecutor;
import com.android.utils.ILogger;
import com.google.common.collect.ImmutableList;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CucumberTestData implements TestData {

    private final String appId;
    private final String testedAppId;
    private final String cucumberRuuner;
    private final Map<String, String> instrArgs = new HashMap<>();
    private final File testedApk;
    private final File testApk;


    public static CucumberTestData fromTestData(TestData testData, File testedApk, File testApk) {
        return new CucumberTestData(
                testData.getApplicationId(),
                testData.getTestedApplicationId(),
                testData.getInstrumentationRunner(),
                testedApk,
                testApk

        );
    }

    public CucumberTestData(String appId, String testedAppId, String cucumberRuuner,
                            File testedApk, File testApk) {
        this.appId = appId;
        this.testedAppId = testedAppId;
        this.cucumberRuuner = cucumberRuuner;
        this.testedApk = testedApk;
        this.testApk = testApk;
    }

    @Override
    public String getApplicationId() {
        return appId;
    }

    @Override
    public String getTestedApplicationId() {
        return testedAppId;
    }

    @Override
    public String getInstrumentationRunner() {
        return cucumberRuuner;
        //  return "android.support.test.runner.AndroidJUnitRunner";
    }

    @Override
    public Map<String, String> getInstrumentationRunnerArguments() {
        return instrArgs;
    }

    @Override
    public boolean isTestCoverageEnabled() {
        return false;
    }

    @Override
    public ApiVersion getMinSdkVersion() {
        return new DefaultApiVersion(19);
    }

    @Override
    public boolean isLibrary() {
        return false;
    }

    @Override
    public ImmutableList<File> getTestedApks(ProcessExecutor processExecutor, File
            splitSelectExe, DeviceConfigProvider deviceConfigProvider, ILogger logger)
            throws ProcessException {
        return ImmutableList.of(testedApk);
    }

    @Override
    public String getFlavorName() {
        return "";
    }

    @Override
    public File getTestApk() {
        return testApk;
    }

    @Override
    public List<File> getTestDirectories() {
        return null;
    }
}
