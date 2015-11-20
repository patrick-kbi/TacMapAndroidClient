package com.kbi.obb.aneely3;

import com.kbi.obb.runtime.Component;
import android.content.Context;
import android.view.View;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/*! \brief Brief description
 *
 * Long description
<h3>Input Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr><tr><td>0</td><td>sendLocation</td><td>java.lang.Boolean</td></tr>
<tr><td>1</td><td>enable</td><td>java.lang.Boolean</td></tr>
</table>
<h3>Output Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr>
<tr><td>0</td><td>locationMonitor</td><td>android.location.Location</td></tr>
<tr><td>1</td><td>currentLocation</td><td>android.location.Location</td></tr>
</table>
<h3>Properties</h3>
<table><tr><td>Name</td><td>Type</td><td>Default Value</td></tr>
</table>
*/

public class gpsBestLocation extends Component {
	/// @cond SHOW_ALL
	public int MIN_TIME = 1000; // minimum time between updates ms
	public int MAX_TIME = 1000 * 15;    // maximum time between updates ms
	public int MIN_DIST = 0;   // minimum time between updates meters
	public int PROVIDER_CHECK_RATE_MS = 6 * 1000; // time between checking best provider

	private static final int SENDLOCATION_IN = 0;
	private static final int ENABLE_IN = 1;

	private static final int LOCATIONMONITOR_OUT = 0;
	private static final int CURRENTLOCATION_OUT = 1;

	private Context c;
	private LocationManager lm;
	private Criteria criteria;
	private LocationListener llnet, llgps;
	private boolean enbFlg;
	private String TAG = gpsBestLocation.class.getSimpleName();
	private String provider;
	private Location curLoc, prevLoc, locNET, locGPS;

	public gpsBestLocation(Context context) {
		super(context);
	}

	@Override
	public void onCreate(Context context) {
		super.onCreate(context);
		c = context;
		lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		criteria = new Criteria();  // use default settings
		enbFlg = false;
		provider = null;
		curLoc = null;
		prevLoc = null;


		llnet = new LocationListener() {
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			public void onProviderEnabled(String provider) {

				Log.d(TAG, "llnet.onProviderEnabled:called");
			}

			public void onProviderDisabled(String provider) {
				Log.d(TAG, "llnet.onProviderDisabled:called");
			}

			public void onLocationChanged(Location location) {
				if (enbFlg) {
					if (location != null) {
						if (!isBetterLocation(location, curLoc)) {
							Log.d(TAG, "llnet.onLocationChanged:new location not better - no update");
							return;
						}
						curLoc = location;  // update current location to new location
						triggerOutput(LOCATIONMONITOR_OUT, location);
						Log.d(TAG, "llnet.onLocationChanged:location monitor updated");
					} else {
						Log.d(TAG, "llnet.onLocationChanged:null location");
					}
				} else {
					Log.d(TAG, "llnet:onLocationChanged:location updated - currently disabled enbFlag=" + enbFlg);
				}
			}
		};

		llgps = new LocationListener() {
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			public void onProviderEnabled(String provider) {

				Log.d(TAG, "llgps.onProviderEnabled:called");
			}

			public void onProviderDisabled(String provider) {
				Log.d(TAG, "llgps.onProviderDisabled:called");
			}

			public void onLocationChanged(Location location) {
				if (enbFlg) {
					if (location != null) {
						if (!isBetterLocation(location, curLoc)) {
							Log.d(TAG, "llgps.onLocationChanged:new location not better - no update");
							return;
						}
						curLoc = location;  // update current location to new location
						triggerOutput(LOCATIONMONITOR_OUT, location);
						Log.d(TAG, "llgps.onLocationChanged:location monitor updated");
					} else {
						Log.d(TAG, "llgps.onLocationChanged:null location");
					}
				} else {
					Log.d(TAG, "llgps:onLocationChanged:location updated - currently disabled enbFlag=" + enbFlg);
				}
			}
		};
		
		lm.requestLocationUpdates(lm.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, llnet);
		lm.requestLocationUpdates(lm.GPS_PROVIDER, MIN_TIME, MIN_DIST, llgps);
	}

	@Override
	public void setView(View v) {
		super.setView(v);
	}

	@Override
	public void onDestroy() {
		lm.removeUpdates(llnet);
		lm.removeUpdates(llgps);
	}

	@Override
	public void receive(int portIndex, Object input) {
		switch (portIndex) {
			case SENDLOCATION_IN:
				if (((Boolean) input).booleanValue()) {
					locNET = null;
					locGPS = null;

					if (lm.isProviderEnabled(lm.NETWORK_PROVIDER)) {
						locNET = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);
					}

					if (lm.isProviderEnabled(lm.GPS_PROVIDER)) {
						locGPS = lm.getLastKnownLocation(lm.GPS_PROVIDER);
					}

					if (isBetterLocation(locNET, locGPS)) {
						Log.d(TAG, "receive:SNDLOC_IN:NETWORK location sent curLoc");
						triggerOutput(CURRENTLOCATION_OUT, locNET);
					} else {
						Log.d(TAG, "receive:SNDLOC_IN:GPS location sent curLoc");
						triggerOutput(CURRENTLOCATION_OUT, locGPS);
					}
				}
				break;
			case ENABLE_IN:
				Log.d(TAG, "receive:ENABLE_IN: input-" + ((Boolean) input).booleanValue());
				if (((Boolean) input).booleanValue()) {
					enbFlg = true;
				} else {
					enbFlg = false;
				}
				break;
		}
	}

	/**
	 * Determines whether one Location reading is better than the current Location fix
	 *
	 * @param location            The new Location that you want to evaluate
	 * @param currentBestLocation The current Location fix, to which you want to compare the new one
	 */
	protected boolean isBetterLocation(Location location, Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > MAX_TIME;
		boolean isSignificantlyOlder = timeDelta < -MAX_TIME;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether two providers are the same
	 */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}