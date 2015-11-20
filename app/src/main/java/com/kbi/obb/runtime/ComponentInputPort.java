package com.kbi.obb.runtime;

import java.util.ArrayList;

public class ComponentInputPort<T> {
	public int index;

	protected Component component;

	public ComponentInputPort(int i) {
		index = i;
	}

	public void setComponent(Component comp) {
		component = comp;
	}

	public void receive(T obj) {
		component.receive(index, obj);
	}
}
