package io.apptik.tarator;

import com.android.builder.testing.ConnectedDevice;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.CollectingOutputReceiver;
import com.android.ddmlib.FileListingService;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.NullOutputReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.android.utils.ILogger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.android.ddmlib.SyncService.getNullProgressMonitor;


public class TaratorDevice extends ConnectedDevice {

    private final IDevice iDevice;
    private final long timeout;
    private final TimeUnit timeUnit;

    public TaratorDevice(IDevice iDevice, ILogger logger, long timeout, TimeUnit timeUnit) {
        super(iDevice, logger, timeout, timeUnit);
        this.iDevice = iDevice;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public void pull(FileListingService.FileEntry[] entries, String localPath)
            throws AdbCommandRejectedException, IOException, TimeoutException, SyncException {
        new File(localPath).mkdirs();
        iDevice.getSyncService().pull(entries,localPath, getNullProgressMonitor());
    }

    public String getExternalStoragePath() throws Exception {
        CollectingOutputReceiver pathNameOutputReceiver = new CollectingOutputReceiver();
        iDevice.executeShellCommand("echo $EXTERNAL_STORAGE", pathNameOutputReceiver);
        return pathNameOutputReceiver.getOutput().trim();
    }

    public void execCmd(String cmd) throws TimeoutException, AdbCommandRejectedException,
            ShellCommandUnresponsiveException, IOException {
        executeShellCommand(cmd, new NullOutputReceiver(), timeout, timeUnit);
    }

}
