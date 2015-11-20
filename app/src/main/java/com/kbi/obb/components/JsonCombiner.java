package com.kbi.obb.components;

import com.kbi.obb.runtime.Component;
import com.kbi.obb.runtime.ComponentInputPort;
import android.content.Context;
import android.view.View;
import android.util.Log;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONException;

public class JsonCombiner extends Component {
	private static final String TAG = "JsonCombiner";
	private int linkCount = 0;
	private int inCount = 0;
	private JSONObject json = new JSONObject();

	private final int json_in = 0;
	private final int reset = 1;

	public JsonCombiner(Context context) {
		super(context);
	}

	@Override
	public void onCreate(Context context) {
	}

	@Override
	public void setView(View v) {
		super.setView(v);
	}

	@Override
	public ComponentInputPort getInput(int portIndex) {
		if (portIndex == json_in) {
			linkCount += 1;
		}

		return super.getInput(portIndex);
	}

	@Override
	public void receive(int portIndex, Object input) {
		switch(portIndex) {
			case json_in:
				inCount += 1;

				try {
					Iterator i = ((JSONObject) input).keys();
					while (i.hasNext()) {
						String nextKey = (String) i.next();
						// We check if the value is empty and don't add it if so
						String stringValue = ((JSONObject)input).getString(nextKey);
						if(!stringValue.equals("")) {
							Object nextValue = ((JSONObject)input).get(nextKey);
							//String nextValue = (String) ((JSONObject) input).get(nextKey);
							json.put(nextKey, nextValue);
						}
					}

					if (inCount == linkCount) {
						Log.d(TAG, json.toString());
						triggerOutput(0,json);
						reset();
					}
				} catch (JSONException e) {
					Log.e(TAG, "JSONException");
				}

				break;
			case reset:
				json = new JSONObject();
				inCount = 0;
				break;
		}
	}

	public void reset() {
		inCount = 0;
		json = new JSONObject();
	}
}
