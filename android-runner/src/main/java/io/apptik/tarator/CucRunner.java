package io.apptik.tarator;


import android.os.Build;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

import java.io.File;

import cucumber.api.android.CucumberInstrumentationCore;

import static android.content.Context.MODE_WORLD_READABLE;
import static android.os.Environment.getExternalStorageDirectory;


public class CucRunner extends AndroidJUnitRunner {
    private CucumberInstrumentationCore cucumberInstrumentationCore = new
            CucumberInstrumentationCore(this);

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("CUC", String.format("cuc Start: %s ", b2s(bundle)));
//        try {
//            initReportDir();
//        } catch (IllegalAccessException e) {
//           throw new RuntimeException(e);
//        }
        this.cucumberInstrumentationCore.create(bundle);
    }

    private void initReportDir() throws IllegalAccessException {
        File directory;
        if (Build.VERSION.SDK_INT >= 21) {
            // Use external storage.
            directory = new File(getExternalStorageDirectory(),
                    this.getTargetContext().getPackageName() + "/cuc");
        } else {
            // Use internal storage.
            directory = this.getTargetContext().getDir("cuc", MODE_WORLD_READABLE);
        }
        createDirs(directory);

    }

    private static void createDirs(File dir) throws IllegalAccessException {
        Log.d("Cuc", "creating dir: " + dir);
        File parent = dir.getParentFile();
        if (!parent.exists()) {
            createDirs(parent);
        }
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalAccessException("Unable to create dir: " + dir.getAbsolutePath());
        }
        chmodPlusRWX(dir);
    }

    protected static void chmodPlusRWX(File file) {
        file.setReadable(true, false);
        file.setWritable(true, false);
        file.setExecutable(true, false);
    }

    @Override
    public void onStart() {
        waitForIdleSync();
        this.cucumberInstrumentationCore.start();
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        Log.e("CUC", String.format("cuc results:%s :: %s ", resultCode, b2s(results)));
        super.finish(resultCode, results);
    }

    public static String b2s(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }
}