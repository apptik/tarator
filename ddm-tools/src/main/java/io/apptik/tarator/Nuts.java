package io.apptik.tarator;

import com.android.builder.testing.ConnectedDeviceProvider;
import com.android.builder.testing.api.DeviceConnector;
import com.android.builder.testing.api.DeviceException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.FileListingService.FileEntry;
import com.android.ddmlib.IDevice;
import com.android.utils.ILogger;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.android.ddmlib.FileListingService.TYPE_DIRECTORY;

public class Nuts {

    private static ConnectedDeviceProvider deviceProvider;
    private Nuts() {}

    public static void waitForAdb(AndroidDebugBridge adb, long timeoutMs) {
        long sleepTimeMs = TimeUnit.SECONDS.toMillis(1);
        while (!adb.hasInitialDeviceList() && timeoutMs > 0) {
            try {
                Thread.sleep(sleepTimeMs);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            timeoutMs -= sleepTimeMs;
        }
        if (timeoutMs <= 0 && !adb.hasInitialDeviceList()) {
            throw new RuntimeException("Timeout getting device list.", null);
        }
    }

    public static AndroidDebugBridge initAdb() {
        File adbFile = SdkHelper.getAdb();
        AndroidDebugBridge.initIfNeeded(false);
        return AndroidDebugBridge.createBridge(adbFile.getAbsolutePath(), false);
    }

    public static AndroidDebugBridge initAdbSync(long timeoutMs) {
        AndroidDebugBridge adb = initAdb();
        waitForAdb(adb,timeoutMs);
        return adb;
    }

    public static IDevice[] devices() {
        return devices(AndroidDebugBridge.getBridge());
    }

    public static IDevice[] devices(AndroidDebugBridge adb) {
        return adb.getDevices();
    }

    public static List<? extends DeviceConnector> deviceConnectors(int timeoutMs, ILogger logger)
            throws DeviceException {
        if(deviceProvider==null) {
            File adbFile = SdkHelper.getAdb();
            System.out.println("adb: " + adbFile.getAbsolutePath());
            deviceProvider =
                    new ConnectedDeviceProvider(adbFile, timeoutMs, logger);
            deviceProvider.init();
        }
        return deviceProvider.getDevices();
    }

    public static void terminate() {
        if(deviceProvider!=null) {
            terminate();
        }
        AndroidDebugBridge.terminate();
    }

    static FileEntry obtainDirectoryFileEntry(String path) {
        try {
            FileEntry lastEntry = null;
            Constructor<FileEntry> c =
                    FileEntry.class.getDeclaredConstructor(FileEntry.class, String.class, int.class,
                            boolean.class);
            c.setAccessible(true);
            for (String part : path.split("/")) {
                lastEntry = c.newInstance(lastEntry, part, TYPE_DIRECTORY, lastEntry == null);
            }
            return lastEntry;
        } catch (NoSuchMethodException ignored) {
        } catch (InvocationTargetException ignored) {
        } catch (InstantiationException ignored) {
        } catch (IllegalAccessException ignored) {
        }
        return null;
    }

}
