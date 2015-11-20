package com.kbi.obb.runtime;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.content.Intent;
import android.content.Context;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import android.util.Log;

public class ObbActivity extends Activity {
	protected static final String TAG = "ObbActivity";
    static final int EXIT_MENU_ITEM = 0;

	// Dialog constants
	private static final int SHOW_MESSAGE = 0;

	// Dialog data constants
	public static final String DIALOG_MESSAGE = "MESSAGE";
	public static final String DIALOG_TITLE = "TITLE";
	public static final String DIALOG_REQUEST_CODE = "DIALOG_REQUEST_CODE";

    // This will be generated in each activity to link to the specific service
    protected Class serviceClass;
    protected ObbService service;
    protected int containerId;

	private ShowDialogReceiver showDialogReceiver;
	private StartActivityReceiver startActivityReceiver;
	private StartActivityForResultReceiver startActivityForResultReceiver;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        containerId = getResources().getIdentifier("id/container", null, getPackageName());
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent i = new Intent(ObbActivity.this, serviceClass); 
        startService(i);
        bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);

		showDialogReceiver = new ShowDialogReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ObbService.SHOW_DIALOG);
		registerReceiver(showDialogReceiver, filter);

		startActivityReceiver = new StartActivityReceiver();
		filter = new IntentFilter();
		filter.addAction(ObbService.START_ACTIVITY);
		registerReceiver(startActivityReceiver, filter);

		startActivityForResultReceiver = new StartActivityForResultReceiver();
		filter = new IntentFilter();
		filter.addAction(ObbService.START_ACTIVITY_FOR_RESULT);
		registerReceiver(startActivityForResultReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();

        unbind();
		unregisterReceiver(showDialogReceiver);
		unregisterReceiver(startActivityReceiver);
		unregisterReceiver(startActivityForResultReceiver);
    }

    @Override
    public void onDestroy() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, EXIT_MENU_ITEM, 0, "Quit");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case EXIT_MENU_ITEM:
                if (service != null) {
                    service.stop();
                }
                finish();
                break;
        }
        return true;
    }

    public void unbind() {
        unregisterViews();
        unbindService(serviceConnection);
    }

    public void registerViews() {
        ViewGroup root = (ViewGroup)findViewById(containerId);
		registerViewsRecursive(root);
		/*
        int children = root.getChildCount();
        for(int i=0;i<children;i++) {
            View child = root.getChildAt(i);
            if(child.getId() != View.NO_ID) {
                service.registerView(child);
            }
        }
		*/
    }

	public void registerViewsRecursive(ViewGroup root) {
		int children = root.getChildCount();
		for(int i=0;i<children;i++) {
			View child = root.getChildAt(i);
			if(child instanceof ViewGroup) {
				registerViewsRecursive((ViewGroup)child);
			} 
			if(child.getId() != View.NO_ID && service != null) {
				service.registerView(child);
			}
		}
	}

    public void unregisterViews() {
        ViewGroup root = (ViewGroup)findViewById(containerId);
		unregisterViewsRecursive(root);
		/*
        int children = root.getChildCount();
        for(int i=0;i<children;i++) {
            View child = root.getChildAt(i);
            if(child.getId() != View.NO_ID) {
                service.unregisterView(child);
            }
        }
		*/
    }

	public void unregisterViewsRecursive(ViewGroup root) {
		int children = root.getChildCount();
		for(int i=0;i<children;i++) {
			View child = root.getChildAt(i);
			if(child instanceof ViewGroup) {
				unregisterViewsRecursive((ViewGroup)child);
			} 
			if(child.getId() != View.NO_ID && service != null) {
				service.unregisterView(child);
			}
		}
	}

	public Dialog onCreateDialog(int id, Bundle args) {
		switch(id) {
			case SHOW_MESSAGE:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(args.getString(DIALOG_TITLE));
				builder.setMessage(args.getString(DIALOG_MESSAGE));
				builder.setPositiveButton("Close", 
					new SimpleDialogCloseListener(args.getInt(DIALOG_REQUEST_CODE)));
				return builder.create();
		}

		return null;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent i = new Intent();
		i.setAction(ObbService.ACTIVITY_RESULT);
		i.putExtra(ObbService.RESULT_INTENT, data);
		i.putExtra(ObbService.ACTIVITY_REQUEST_CODE, requestCode);
		i.putExtra(ObbService.ACTIVITY_RESULT_CODE, resultCode);
		sendBroadcast(i);
	}

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            service = ((ObbService.LocalBinder)binder).getService();
			service.setActivity(ObbActivity.this);
            registerViews();
        }

        public void onServiceDisconnected(ComponentName className) {
			service.removeActivity(ObbActivity.this);
            service = null;
        }
    };

	private class ShowDialogReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Showing dialog");
			Bundle bundle = new Bundle();
			bundle.putString(DIALOG_TITLE, intent.getStringExtra(DIALOG_TITLE));
			bundle.putString(DIALOG_MESSAGE, intent.getStringExtra(DIALOG_MESSAGE));
			bundle.putInt(DIALOG_REQUEST_CODE, intent.getIntExtra(DIALOG_REQUEST_CODE, 0));
			showDialog(SHOW_MESSAGE, bundle);
		}
	}

	private class StartActivityReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Starting activity");
			Intent i = intent.getParcelableExtra(ObbService.ACTIVITY_INTENT);
			startActivity(i);
		}
	}

	private class StartActivityForResultReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "Start activity for result");
			Intent i = intent.getParcelableExtra(ObbService.ACTIVITY_INTENT);
			int requestCode = intent.getIntExtra(ObbService.ACTIVITY_REQUEST_CODE, 0);
			startActivityForResult(i, requestCode);
		}
	}

	private class SimpleDialogCloseListener implements DialogInterface.OnClickListener {
		int requestCode = 0;
		public SimpleDialogCloseListener(int code) {
			requestCode = code;
		}

		public void onClick(DialogInterface dialog, int which) {
			Intent i = new Intent();
			i.setAction(ObbService.DIALOG_RESULT);
			i.putExtra(DIALOG_REQUEST_CODE, requestCode);
			sendBroadcast(i);
		}
	}
}
