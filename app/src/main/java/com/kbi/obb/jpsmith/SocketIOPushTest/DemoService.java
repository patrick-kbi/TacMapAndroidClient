package com.kbi.obb.jpsmith.SocketIOPushTest;

import com.kbi.obb.runtime.ComponentLink;

import com.kbi.obb.runtime.ObbService;
import com.kbi.obb.runtime.ComponentInputPort;
import com.kbi.obb.runtime.ComponentOutputPort;
public class DemoService extends ObbService { 
@Override
public void init() {
com.kbi.obb.zlailari.CheckBoxText component83a44e5632af43428a7c1e8ccd20dd52 = new com.kbi.obb.zlailari.CheckBoxText(this);
component83a44e5632af43428a7c1e8ccd20dd52.addInputPort(new ComponentInputPort<java.lang.Object>(0));
component83a44e5632af43428a7c1e8ccd20dd52.addInputPort(new ComponentInputPort<java.lang.Object>(1));
component83a44e5632af43428a7c1e8ccd20dd52.addOutputPort(new ComponentOutputPort<java.lang.Boolean>(0));
component83a44e5632af43428a7c1e8ccd20dd52.addOutputPort(new ComponentOutputPort<org.json.JSONObject>(1));
component83a44e5632af43428a7c1e8ccd20dd52.addOutputPort(new ComponentOutputPort<java.lang.String>(2));
component83a44e5632af43428a7c1e8ccd20dd52.label = "Enable GPS";
component83a44e5632af43428a7c1e8ccd20dd52.selected = false;
component83a44e5632af43428a7c1e8ccd20dd52.viewId = R.id.component83a44e5632af43428a7c1e8ccd20dd52;
addComponent(component83a44e5632af43428a7c1e8ccd20dd52);

com.kbi.obb.components.JsonCombiner component03ae5e54351f44df8aef01cae3a9cd07 = new com.kbi.obb.components.JsonCombiner(this);
component03ae5e54351f44df8aef01cae3a9cd07.addInputPort(new ComponentInputPort<org.json.JSONObject>(0));
component03ae5e54351f44df8aef01cae3a9cd07.addOutputPort(new ComponentOutputPort<org.json.JSONObject>(0));
addComponent(component03ae5e54351f44df8aef01cae3a9cd07);

com.kbi.obb.nlailari.StringKeyValueToJSON componenta1f0a0636317402ab3d359fa0873eda5 = new com.kbi.obb.nlailari.StringKeyValueToJSON(this);
componenta1f0a0636317402ab3d359fa0873eda5.addInputPort(new ComponentInputPort<java.lang.String>(0));
componenta1f0a0636317402ab3d359fa0873eda5.addInputPort(new ComponentInputPort<java.lang.String>(1));
componenta1f0a0636317402ab3d359fa0873eda5.addOutputPort(new ComponentOutputPort<org.json.JSONObject>(0));
componenta1f0a0636317402ab3d359fa0873eda5.key = "lat";
addComponent(componenta1f0a0636317402ab3d359fa0873eda5);

com.kbi.obb.jpsmith.LocationToLatitudeAndLongitude component3547def9e0e841bc9aab4deeb96379a9 = new com.kbi.obb.jpsmith.LocationToLatitudeAndLongitude(this);
component3547def9e0e841bc9aab4deeb96379a9.addInputPort(new ComponentInputPort<android.location.Location>(0));
component3547def9e0e841bc9aab4deeb96379a9.addOutputPort(new ComponentOutputPort<java.lang.String>(0));
component3547def9e0e841bc9aab4deeb96379a9.addOutputPort(new ComponentOutputPort<java.lang.String>(1));
addComponent(component3547def9e0e841bc9aab4deeb96379a9);

com.kbi.obb.nlailari.StringKeyValueToJSON component5d6aa04c1645409fb48895311094d63d = new com.kbi.obb.nlailari.StringKeyValueToJSON(this);
component5d6aa04c1645409fb48895311094d63d.addInputPort(new ComponentInputPort<java.lang.String>(0));
component5d6aa04c1645409fb48895311094d63d.addInputPort(new ComponentInputPort<java.lang.String>(1));
component5d6aa04c1645409fb48895311094d63d.addOutputPort(new ComponentOutputPort<org.json.JSONObject>(0));
component5d6aa04c1645409fb48895311094d63d.key = "long";
addComponent(component5d6aa04c1645409fb48895311094d63d);

//com.kbi.obb.jpsmith.SocketIOConnectionInfo component1f58c86090fb437a9a84df0cbfb8a407 = new com.kbi.obb.jpsmith.SocketIOConnectionInfo(this);
//component1f58c86090fb437a9a84df0cbfb8a407.addOutputPort(new ComponentOutputPort<java.lang.String>(0));
//component1f58c86090fb437a9a84df0cbfb8a407.serverURL = "http://10.0.0.14:5670";
//addComponent(component1f58c86090fb437a9a84df0cbfb8a407);

com.kbi.obb.jpsmith.SocketIOPush component4732bb2ba22446d696884cc6c93d0640 = new com.kbi.obb.jpsmith.SocketIOPush(this);
component4732bb2ba22446d696884cc6c93d0640.addInputPort(new ComponentInputPort<org.json.JSONObject>(0));
component4732bb2ba22446d696884cc6c93d0640.addInputPort(new ComponentInputPort<java.lang.String>(1));
addComponent(component4732bb2ba22446d696884cc6c93d0640);

com.kbi.obb.aneely3.gpsBestLocation componentbfa70f58bc8c4da3bf683285e235c793 = new com.kbi.obb.aneely3.gpsBestLocation(this);
componentbfa70f58bc8c4da3bf683285e235c793.addInputPort(new ComponentInputPort<java.lang.Boolean>(0));
componentbfa70f58bc8c4da3bf683285e235c793.addInputPort(new ComponentInputPort<java.lang.Boolean>(1));
componentbfa70f58bc8c4da3bf683285e235c793.addOutputPort(new ComponentOutputPort<android.location.Location>(0));
componentbfa70f58bc8c4da3bf683285e235c793.addOutputPort(new ComponentOutputPort<android.location.Location>(1));
addComponent(componentbfa70f58bc8c4da3bf683285e235c793);

com.kbi.obb.components.generators.StringGenerator component1cf0ea5a5ed5403fb945fd7c4b876339 = new com.kbi.obb.components.generators.StringGenerator(this);
component1cf0ea5a5ed5403fb945fd7c4b876339.addInputPort(new ComponentInputPort<java.lang.Object>(0));
component1cf0ea5a5ed5403fb945fd7c4b876339.addOutputPort(new ComponentOutputPort<java.lang.String>(0));
component1cf0ea5a5ed5403fb945fd7c4b876339.string = "Samsung GS3";
addComponent(component1cf0ea5a5ed5403fb945fd7c4b876339);

com.kbi.obb.nlailari.StringKeyValueToJSON componenta768035280914136ba8354542c723d91 = new com.kbi.obb.nlailari.StringKeyValueToJSON(this);
componenta768035280914136ba8354542c723d91.addInputPort(new ComponentInputPort<java.lang.String>(0));
componenta768035280914136ba8354542c723d91.addInputPort(new ComponentInputPort<java.lang.String>(1));
componenta768035280914136ba8354542c723d91.addOutputPort(new ComponentOutputPort<org.json.JSONObject>(0));
componenta768035280914136ba8354542c723d91.key = "clientID";
addComponent(componenta768035280914136ba8354542c723d91);

//ComponentLink link0 = new ComponentLink(component4732bb2ba22446d696884cc6c93d0640.getInput(1), component1f58c86090fb437a9a84df0cbfb8a407.getOutput(0));
ComponentLink link1 = new ComponentLink(component4732bb2ba22446d696884cc6c93d0640.getInput(0), component03ae5e54351f44df8aef01cae3a9cd07.getOutput(0));
ComponentLink link2 = new ComponentLink(component03ae5e54351f44df8aef01cae3a9cd07.getInput(0), componenta1f0a0636317402ab3d359fa0873eda5.getOutput(0));
ComponentLink link3 = new ComponentLink(component03ae5e54351f44df8aef01cae3a9cd07.getInput(0), component5d6aa04c1645409fb48895311094d63d.getOutput(0));
ComponentLink link4 = new ComponentLink(componentbfa70f58bc8c4da3bf683285e235c793.getInput(1), component83a44e5632af43428a7c1e8ccd20dd52.getOutput(0));
ComponentLink link5 = new ComponentLink(component5d6aa04c1645409fb48895311094d63d.getInput(1), component3547def9e0e841bc9aab4deeb96379a9.getOutput(0));
ComponentLink link6 = new ComponentLink(component3547def9e0e841bc9aab4deeb96379a9.getInput(0), componentbfa70f58bc8c4da3bf683285e235c793.getOutput(0));
ComponentLink link7 = new ComponentLink(componenta1f0a0636317402ab3d359fa0873eda5.getInput(1), component3547def9e0e841bc9aab4deeb96379a9.getOutput(1));
ComponentLink link8 = new ComponentLink(componenta768035280914136ba8354542c723d91.getInput(1), component1cf0ea5a5ed5403fb945fd7c4b876339.getOutput(0));
ComponentLink link9 = new ComponentLink(component03ae5e54351f44df8aef01cae3a9cd07.getInput(0), componenta768035280914136ba8354542c723d91.getOutput(0));
ComponentLink link10 = new ComponentLink(component1cf0ea5a5ed5403fb945fd7c4b876339.getInput(0), componentbfa70f58bc8c4da3bf683285e235c793.getOutput(0));
}
}