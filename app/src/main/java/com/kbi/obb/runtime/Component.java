package com.kbi.obb.runtime;

import android.app.Activity;
import java.util.ArrayList;
import android.view.View;
import android.content.Context;
import android.content.Intent;

public abstract class Component {
	ArrayList<ComponentInputPort> inputPorts;
	ArrayList<ComponentOutputPort> outputPorts;
	
	protected ObbService service;
	protected Activity activity;

    public int viewId;
	protected View view;

	public Component(Context context) {
		inputPorts = new ArrayList<ComponentInputPort>();
		outputPorts = new ArrayList<ComponentOutputPort>();
	}

    // Called when the component is added to the services component list
    // This is the place to do any intialization that relies on properties
    public void onCreate(Context context) {
		service = (ObbService)context;
	}

    public void enable() { }

    public void disable() { }

	public void addInputPort(ComponentInputPort input) {
		inputPorts.add(input);
		input.setComponent(this);
	}

	public void addOutputPort(ComponentOutputPort output) {
		outputPorts.add(output);
	}

	public void triggerOutput(int portIndex, Object output) {
		for(ComponentOutputPort port : outputPorts) {
			if(port.index == portIndex) {
				port.broadcast(output);
			}
		}
	}

	public void setActivity(Activity currentActivity) {
		activity = currentActivity;
	}

	public void removeActivity() {
		activity = null;
	}

	public void setView(View v) {
		view = v;
	}

    public void removeView() {
        view = null;
    }

	public ComponentInputPort getInput(int portIndex) {
		for(ComponentInputPort port : inputPorts) {
			if(port.index == portIndex) {
				return port;
			}
		}

		return null;
	}

	public ComponentOutputPort getOutput(int portIndex) {
		for(ComponentOutputPort port : outputPorts) {
			if(port.index == portIndex) {
				return port;
			}
		}

		return null;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) { }
	public void onDialogResult(int result) { }
	public abstract void receive(int portIndex, Object input); 
	public void onDestroy() { }
}
