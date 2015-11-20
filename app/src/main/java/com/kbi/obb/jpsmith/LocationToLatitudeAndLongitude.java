package com.kbi.obb.jpsmith;

import com.kbi.obb.runtime.Component;
import android.content.Context;
import android.location.Location;
import android.view.View;

/*! \brief Brief description
 *
 * Long description
<h3>Input Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr><tr><td>0</td><td>LocationInput</td><td>android.location.Location</td></tr>
</table>
<h3>Output Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr>
<tr><td>0</td><td>LongitudeOut</td><td>java.lang.String</td></tr>
<tr><td>1</td><td>LatitudeOut</td><td>java.lang.String</td></tr>
</table>
<h3>Properties</h3>
<table><tr><td>Name</td><td>Type</td><td>Default Value</td></tr>
</table>
*/
public class LocationToLatitudeAndLongitude extends Component {
/// @cond SHOW_ALL
	private static final int LOCATIONINPUT_IN = 0;

	private static final int LONGITUDEOUT_OUT = 0;
	private static final int LATITUDEOUT_OUT = 1;

	public LocationToLatitudeAndLongitude(Context context) {
		super(context);
	}
	@Override
	public void onCreate(Context context) {
		super.onCreate(context);
	}

	@Override
	public void setView(View v) {
		super.setView(v);
	}

	@Override
	public void receive(int portIndex, Object input) {
		Location loc = (Location)input;
		Double latty = loc.getLatitude();
		Double longy = loc.getLongitude();
		String latitude = String.format("%.6f", latty);
		String longitude = String.format("%.6f", longy);

		triggerOutput(LATITUDEOUT_OUT, latitude);
		triggerOutput(LONGITUDEOUT_OUT, longitude);
	}
/// @endcond
}
