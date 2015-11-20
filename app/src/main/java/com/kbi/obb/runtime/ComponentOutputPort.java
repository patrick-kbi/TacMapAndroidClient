package com.kbi.obb.runtime;

import java.util.ArrayList;

public class ComponentOutputPort<T> {
	public int index;

	protected ArrayList<ComponentLink> links;

	public ComponentOutputPort(int i) {
		links = new ArrayList<ComponentLink>();
		index = i;
	}

	public void addLink(ComponentLink link) {
		links.add(link);
	}

	public void broadcast(T obj) {
		for(ComponentLink link : links) {
			link.send(obj);
		}
	}
}
