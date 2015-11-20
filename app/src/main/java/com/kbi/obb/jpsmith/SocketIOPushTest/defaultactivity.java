package com.kbi.obb.jpsmith.SocketIOPushTest;

import android.os.Bundle;

import android.widget.LinearLayout;

import com.kbi.obb.runtime.ObbActivity;
import com.kbi.obb.runtime.ComponentLink;

public class defaultactivity extends ObbActivity { 
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.defaultactivity);
serviceClass = DemoService.class;
}
}