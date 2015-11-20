package com.kbi.obb.nlailari;

import com.kbi.obb.runtime.Component;
import android.content.Context;
import android.view.View;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONException;

/*! \brief Brief description
 *
 * Long description
<h3>Input Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr><tr><td>0</td><td>key</td><td>java.lang.String</td></tr>
<tr><td>1</td><td>value</td><td>java.lang.String</td></tr>
</table>
<h3>Output Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr>
<tr><td>0</td><td>JSON</td><td>org.json.JSONObject</td></tr>
</table>
<h3>Properties</h3>
<table><tr><td>Name</td><td>Type</td><td>Default Value</td></tr>
</table>
*/
public class StringKeyValueToJSON extends Component {
/// @cond SHOW_ALL
	private static final int KEY_IN = 0;
	private static final int VALUE_IN = 1;

	private static final int JSON_OUT = 0;

	private static final String TAG = "StringKeyValueToJson";

	public String key = "defaultkey";


	public StringKeyValueToJSON(Context context) {
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
		switch(portIndex)
		{
			case (KEY_IN):
				key = (String)input;
			break;
			case (VALUE_IN):
			{
				try{
					JSONObject obj = new JSONObject();
					obj.put(key,(String)input);
					triggerOutput(JSON_OUT, obj);
				}
				catch(JSONException e){
					e.printStackTrace();
				}
			}
		}
	}
/// @endcond
}
