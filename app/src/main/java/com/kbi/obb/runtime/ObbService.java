package com.kbi.obb.runtime;

import android.os.Binder;
import android.os.IBinder;
import android.app.Service;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.util.Log;
import android.net.Uri;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

public class ObbService extends Service {
    private static final String TAG = "ObbService";
    private final IBinder binder = new LocalBinder();

	// Intent Actions
	public static final String SHOW_DIALOG = "obb.actions.SHOW_DIALOG";
	public static final String DIALOG_RESULT = "obb.actions.DIALOG_RESULT";
	public static final String START_ACTIVITY = "obb.actions.START_ACTIVITY";
	public static final String START_ACTIVITY_FOR_RESULT = "obb.actions.START_ACTIVITY_FOR_RESULT";
	public static final String ACTIVITY_RESULT = "obb.actions.ACTIVITY_RESULT";

	// Intent data keys
	public static final String ACTIVITY_INTENT = "obb.data.ACTIVITY_INTENT";
	public static final String ACTIVITY_REQUEST_CODE = "obb.data.ACTIVITY_REQUEST_CODE";
	public static final String ACTIVITY_RESULT_CODE = "obb.data.ACTIVITY_RESULT_CODE";
	public static final String RESULT_INTENT = "obb.data.RESULT_INTENT";

    protected ArrayList<Component> components;
    protected ArrayList<ComponentLink> links;

	protected HashMap<Integer, Component> activityCallbacks;

	private ActivityResultReceiver resultReceiver; 
	private DialogResultReceiver dialogResults;

    public class LocalBinder extends Binder {
        ObbService getService() {
            return ObbService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        components = new ArrayList<Component>();
        links = new ArrayList<ComponentLink>();
		activityCallbacks = new HashMap<Integer, Component>();

		resultReceiver = new ActivityResultReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTIVITY_RESULT);
		registerReceiver(resultReceiver, filter);

		dialogResults = new DialogResultReceiver();
		filter = new IntentFilter();
		filter.addAction(DIALOG_RESULT);
		registerReceiver(dialogResults, filter);

        init();
		for(Component comp : components) {
			comp.onCreate(this);
		}
    }

    @Override
    public void onDestroy() {
		unregisterReceiver(resultReceiver);
		unregisterReceiver(dialogResults);
		for(Component comp : components) {
			comp.onDestroy();
		}
        Log.d(TAG, "Obbservice destroyed");
    }

    /* To be overridden by generated code. Creates all components and component links */
    protected void init() {

    }

    protected void addComponent(Component comp) {
        components.add(comp);
		// Moving this to after init so that all components are setup when this is called
        //comp.onCreate(this);
    }

    // Disable all components before shutting down
    public void stop() {
        for(Component comp : components) {
            comp.disable();
        }
        stopSelf();
    }

	public void setActivity(Activity activity) {
		for(Component comp : components) {
			comp.setActivity(activity);
		}
	}

	public void removeActivity(Activity activity) {
		for(Component comp : components) {
			comp.removeActivity();
		}
	}

    public void registerView(View v) {
        for(Component comp : components) {
            if(comp.viewId == v.getId()) {
                comp.setView(v);
            }
        }
    }

    public void unregisterView(View v) {
        for(Component comp : components) {
            if(comp.viewId == v.getId()) {
                comp.removeView();
            }
        }
    }

	public void showSimpleDialog(String title, String message, Component callback) {
		int index = -1;
		for(int i=0;i<components.size();i++) {
			if(callback == components.get(i)) {
				index = i;
			}
		}

		Intent i = new Intent();
		i.setAction(SHOW_DIALOG);
		i.putExtra(ObbActivity.DIALOG_TITLE, title);
		i.putExtra(ObbActivity.DIALOG_MESSAGE, message);
		i.putExtra(ObbActivity.DIALOG_REQUEST_CODE, index);
		sendBroadcast(i);
	}

	public void startActivity(Intent intent) {
		Intent i = new Intent();
		i.setAction(START_ACTIVITY);
		i.putExtra(ACTIVITY_INTENT, intent);
		sendBroadcast(i);
	}

	public void startActivityForResult(Intent intent, Component callback) {
		int index = -1;
		for(int i=0;i<components.size();i++) {
			if(callback == components.get(i)) {
				index = i;
			}
		}

		activityCallbacks.put(Integer.valueOf(index), callback);
		Intent i = new Intent();
		i.setAction(START_ACTIVITY_FOR_RESULT);
		i.putExtra(ACTIVITY_INTENT, intent);
		i.putExtra(ACTIVITY_REQUEST_CODE, index);
		sendBroadcast(i);
	}

    @Override 
    public IBinder onBind(Intent intent) {
        return binder;
    }

	private class ActivityResultReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			int requestCode = intent.getIntExtra(ObbService.ACTIVITY_REQUEST_CODE, 0);
			Log.d(TAG, "requestCode "+requestCode);
			Intent data = intent.getParcelableExtra(RESULT_INTENT);
			int resultCode = intent.getIntExtra(ACTIVITY_RESULT_CODE, 0);
			try {
				components.get(requestCode).onActivityResult(requestCode, resultCode, data);
			} catch (Exception ex) {
				// no such component?
			}
		}
	}

	private class DialogResultReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			int requestCode = intent.getIntExtra(ObbActivity.DIALOG_REQUEST_CODE, 0);
			try {
				components.get(requestCode).onDialogResult(requestCode);
			} catch (Exception ex) {
				// no such component?
			}
		}
	}
}
