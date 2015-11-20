package com.kbi.obb.jpsmith;

import com.kbi.obb.runtime.Component;
import android.content.Context;

/*! \brief Brief description
 *
 * Long description
<h3>Input Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr></table>
<h3>Output Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr>
<tr><td>0</td><td>ConnectionInfo</td><td>java.lang.String</td></tr>
</table>
<h3>Properties</h3>
<table><tr><td>Name</td><td>Type</td><td>Default Value</td></tr>
</table>
*/
public class SocketIOConnectionInfo extends Component {
/// @cond SHOW_ALL
	private static final int CONNECTIONINFO_OUT = 0;
	public String serverURL = "";

	public SocketIOConnectionInfo(Context context) {super(context);}

	@Override
	public void onCreate(Context context) {
	super.onCreate(context);
		triggerOutput(CONNECTIONINFO_OUT, serverURL);
	}

	@Override
	public void receive(int portIndex, Object input) {
	}
/// @endcond
}
