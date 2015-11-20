package com.kbi.obb.jpsmith;

import com.kbi.obb.runtime.Component;
import android.content.Context;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/*! \brief Brief description
 *
 * Long description
<h3>Input Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr><tr><td>0</td><td>Input</td><td>org.json.JSONObject</td></tr>
<tr><td>1</td><td>ConnectionInfo</td><td>java.lang.String</td></tr>
</table>
<h3>Output Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr>
</table>
<h3>Properties</h3>
<table><tr><td>Name</td><td>Type</td><td>Default Value</td></tr>
</table>
*/
public class SocketIOPush extends Component {
/// @cond SHOW_ALL
	private static final int INPUT_IN = 0;
	private static final int CONNECTIONINFO_IN = 1;
	private Socket socket;
	private JSONObject message = new JSONObject();
	private static final String TAG = " SocketIOPush";

	public SocketIOPush(Context context) {super(context);}

	@Override
	public void receive(int portIndex, Object input) {
		switch(portIndex){
			case CONNECTIONINFO_IN:
				String serverURL = (String) input;
				enableConnection(serverURL);
				break;
			case INPUT_IN:
				message = (JSONObject) input;
				socket.emit("new message", message);
				break;
		}
	}

	public void enableConnection(String serverURL){
		Log.d(TAG, "server URL: " + serverURL);
		try {
			socket = IO.socket(serverURL);
		} catch (URISyntaxException e) {}
		socket.connect();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		socket.disconnect();
	}

/// @endcond
}
