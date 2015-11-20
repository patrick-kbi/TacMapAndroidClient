package com.kbi.obb.zlailari;

import com.kbi.obb.runtime.Component;
import android.content.Context;
import android.view.View;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONException;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/*! \brief Brief description
 *
 * Long description
<h3>Input Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr><tr><td>0</td><td>GetValue</td><td>java.lang.Object</td></tr>
<tr><td>1</td><td>Reset</td><td>java.lang.Object</td></tr>
</table>
<h3>Output Ports</h3>
<table><tr><td>Index</td><td>Name</td><td>Data Type</td></tr>
<tr><td>0</td><td>State</td><td>java.lang.Boolean</td></tr>
<tr><td>1</td><td>JsonProperties</td><td>org.json.JSONObject</td></tr>
</table>
<h3>Properties</h3>
<table><tr><td>Name</td><td>Type</td><td>Default Value</td></tr>
</table>
*/
public class CheckBoxText extends Component //View.OnClickListener
{
/// @cond SHOW_ALL
	private static final String TAG = "CheckBoxText";
	private static final int GETVALUE_IN = 0;
	private static final int RESET_IN = 1;

	private static final int STATE_OUT = 0;
	private static final int JSONPROPERTIES_OUT = 1;
	private static final int STRINGPROPERTIES_OUT = 2;

	public String label = "default";
	public boolean selected = false;

	private boolean checked;
	private JSONObject properties;

	public CheckBoxText(Context context) {
		super(context);
	}

	@Override
	public void onCreate(Context context) {
		super.onCreate(context);
        checked = selected;
	}

	@Override
	public void setView(View v) {
		super.setView(v);
		// v.setOnClickListener(this);
		((android.widget.CheckBox)v).setText(label);
		((android.widget.CheckBox)v).setChecked(checked);
		// chkPizza.setOnCheckedChangeListener(this);
		((android.widget.CheckBox)v).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				sendProperties(isChecked);
			}
		});
	}

	// public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//  checkTax.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//    if(isChecked)
//    {
//     checkTax.setText("Check Box is checked");
//    }
//    else
//    {
//     checkTax.setText("not checked");
//    }

// });

	@Override
	public void receive(int portIndex, Object input) {
		switch (portIndex) {
			case GETVALUE_IN:
				if (view != null) {
					sendProperties(((android.widget.CheckBox)view).isChecked());
				}
				break;
			case RESET_IN:
				if (view != null) {
					((android.widget.CheckBox)view).setChecked(selected);
                    checked = selected;
				}
				break;
		}
	}

	// @Override
	// public void onClick(View v) {
	// 	checked = ((android.widget.CheckBox)v).isChecked();
	// }

	private void sendProperties(boolean isChecked) {
		try {
            checked = isChecked;
			properties = new JSONObject().put(label, isChecked);
			triggerOutput(JSONPROPERTIES_OUT, properties);
		} catch (JSONException ex) {
			ex.printStackTrace();
			Log.d(TAG, "Crashed - CheckBox when putting in JSON");
		}
		triggerOutput(STATE_OUT, isChecked);
		triggerOutput(STRINGPROPERTIES_OUT, (label+" "+isChecked));

	}

/// @endcond
}
