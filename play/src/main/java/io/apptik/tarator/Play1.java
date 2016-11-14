package io.apptik.tarator;

import com.android.builder.internal.testing.SimpleTestCallable;
import com.android.builder.testing.ConnectedDeviceProvider;
import com.android.builder.testing.api.DeviceConnector;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.utils.ILogger;
import com.android.utils.StdLogger;
import com.google.common.collect.ImmutableList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.android.ddmlib.AndroidDebugBridge.getBridge;

public class Play1 {

    /**
     * SimpleTestCallable (ShardedTestCallable) does the actual job of package, test and
     * reports handling.
     * It uses DeviceConnector, RemoteAndroidTestRunner, CustomTestRunListener.
     * It does:
     * install packages
     * creates RemoteAndroidTestRunner
     * run the runner providing CustomTestRunListener
     * gets results from output
     * gets the coverage to /data/local/tmp/ , pulls it then cleans up
     * uninstall packages
     * <p>
     * ***************************************
     * <p>
     * TaratorTestRunner is responsible to run tests on all devices.
     * It is calling SimpleTestCallable
     * It is called by DeviceProviderInstrumentTestTask which is a Gradle VerificationTask and
     * used by the plugin
     */


    private static ConnectedDeviceProvider deviceProvider;
    private static String projectName = "app";
    private static String variantName = "";
    private static File testApk = new File("app/build/outputs/apk/app-debug-androidTest.apk");
    private static File testedApk = new File("app/build/outputs/apk/app-debug.apk");
    private static CucumberTestData testData;
    private static File resultsDir = new File("tmp");
    private static File coverageDir = new File("tmp");
    private static int timeoutInMs = 5000;
    private static Collection<String> installOptions = new ArrayList<>();
    private static ILogger logger = new StdLogger(StdLogger.Level.VERBOSE);

    public static void main(String[] args) throws Exception {
        testData = new CucumberTestData(
                "io.apptik.tarator.test","io.apptik.tarator","io.apptik.tarator.CucRunner",
                testApk, testedApk
        );
        initDevices();
        runInstr();
        Nuts.terminate();
    }

    private static void runInstr() throws Exception {
        AndroidDebugBridge adb =  getBridge();
        IDevice[] devs = adb.getDevices();
        if(devs !=null) {
            for (IDevice dev : devs) {
                CucTestCallable testCallable = new CucTestCallable(
                        new TaratorDevice(dev,logger,timeoutInMs,TimeUnit.MILLISECONDS)
                        , projectName, variantName, testData,
                        resultsDir, coverageDir, timeoutInMs, installOptions, logger);
                testCallable.call();
            }
        }
    }
    private static void initDevices() throws Exception {
        AndroidDebugBridge adb = Nuts.initAdbSync(timeoutInMs);
        IDevice[] devs = adb.getDevices();
        if(devs !=null) {
            for (IDevice dev : devs) {
                System.out.println("DEVICE: " + dev);
                dev.uninstallPackage(testData.getApplicationId());
                dev.uninstallPackage(testData.getTestedApplicationId());
            }
        }
    }

    private static void runInstr2() throws Exception {
        List<? extends DeviceConnector> devs = Nuts.deviceConnectors(timeoutInMs,logger);
        for (DeviceConnector dev : devs) {
            SimpleTestCallable testCallable = new SimpleTestCallable(
                    dev, projectName, variantName,testData.getTestApk(),
                    ImmutableList.<File>of(testedApk), testData,
                    resultsDir, coverageDir, timeoutInMs, installOptions, logger);
            testCallable.call();
        }
    }
    private static void initDevices2() throws Exception {
        List<? extends DeviceConnector> devs = Nuts.deviceConnectors(timeoutInMs,logger);
        for (DeviceConnector dev : devs) {
            System.out.println("DEVICE: " + dev);
            dev.uninstallPackage(testApk.getAbsolutePath(), timeoutInMs, logger);
            dev.uninstallPackage(testedApk.getAbsolutePath(), timeoutInMs, logger);
        }
    }
}
