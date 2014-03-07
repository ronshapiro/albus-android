package me.ronshapiro.albus.android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainStatsModule implements Module {

    private static final int MODULE_VERSION = 1;
    private Context context;

    public MainStatsModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public String getName() {
        return "albus.main";
    }

    @Override
    public JSONObject record(Thread crashedThread, Throwable throwable) {
        JSONObject data = new JSONObject();
        try {
            data.put("version", MODULE_VERSION);
            data.put("threads", getThreadData(crashedThread));
            data.put("app", getAppData());
            data.put("device", getDeviceData());
            data.put("memory", getMemoryData());
            // TODO get battery level info
        } catch (JSONException e) {
            Log.e(MainStatsModule.class.getSimpleName(), "Error serializing to JSON", e);
        }
        return data;
    }

    private JSONArray getThreadData(Thread crashedThread) throws JSONException {
        JSONArray threads = new JSONArray();
        Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : stackTraces.entrySet()) {
            JSONObject threadData = new JSONObject();
            threadData.put("priority", crashedThread.getPriority());
            threadData.put("name", crashedThread.getName());
            threadData.put("state", crashedThread.getState().toString());
            threadData.put("id", crashedThread.getId());

            if (entry.getKey() == Looper.getMainLooper().getThread()) {
                threadData.put("main_thread", true);
            }
            if (entry.getKey() == crashedThread) {
                threadData.put("crashed_thread", true);
            }
            for (StackTraceElement element : entry.getValue()) {
                JSONObject stackTraceLine = new JSONObject();
                stackTraceLine.put("class", element.getClassName())
                        .put("method_name", element.getMethodName())
                        .put("file_name", element.getMethodName())
                        .put("line_number", element.getLineNumber());
                threadData.accumulate("stack_trace", stackTraceLine);
            }
            threads.put(threadData);
        }
        return threads;
    }

    private JSONObject getAppData() throws JSONException {
        JSONObject appData = new JSONObject();
        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appData.put("version_code", info.versionCode);
            appData.put("version_name", info.versionName);
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e); // this shouldn't happen
        }
        return appData;
    }

    private JSONObject getDeviceData() throws JSONException {
        JSONObject deviceData = new JSONObject();
        deviceData.put("type", "android");
        deviceData.put("version", Build.VERSION.SDK_INT);
        deviceData.put("device", Build.DEVICE);
        deviceData.put("manufacturer", Build.MANUFACTURER); // may be unnecessary
        deviceData.put("rooted", isRooted());
        boolean isLandscape = context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
        deviceData.put("is_landscape", isLandscape);
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        deviceData.put("screen_on", pm.isScreenOn());
        // TODO get device storage data
        return deviceData;
    }

    private JSONObject getMemoryData() throws JSONException {
        JSONObject memoryData = new JSONObject();
        Runtime runtime = Runtime.getRuntime();
        memoryData.put("free", runtime.freeMemory());
        memoryData.put("max", runtime.maxMemory());
        memoryData.put("total", runtime.totalMemory());
        return memoryData;
    }

    private static boolean isRooted() {
        // TODO implement this
        return false;
        /*
        return (Build.TAGS != null && Build.TAGS.contains("test-keys")) ||
               (Runtime.getRuntime().exec("su && ls"));
               */
    }
}
