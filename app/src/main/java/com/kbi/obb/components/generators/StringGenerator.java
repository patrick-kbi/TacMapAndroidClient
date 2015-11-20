package com.kbi.obb.components.generators;

import com.kbi.obb.runtime.Component;
import android.content.Context;

public class StringGenerator extends Component {
    public String string;

    public StringGenerator(Context context) {
        super(context);
    }

    @Override
    public void receive(int portIndex, Object input) {
        triggerOutput(0, string);
    }
}
