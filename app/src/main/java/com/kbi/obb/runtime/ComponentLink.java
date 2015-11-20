package com.kbi.obb.runtime;

public class ComponentLink {
	ComponentOutputPort from;
	ComponentInputPort to;

	public ComponentLink(ComponentInputPort t, ComponentOutputPort f) {
		to = t;
		from = f;

		from.addLink(this);
	}
	
	public void send(Object obj) {
		to.receive(obj);
	}
}
