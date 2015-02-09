package com.sarmaru.mihai.shakeapp;

import android.graphics.Color;
import android.test.InstrumentationTestCase;

/**
 * Created by Mihai Sarmaru on 04.02.2015.
 */
public class CircleColorViewTest extends InstrumentationTestCase {

    public void testCircleColorView () {
        int circleColor = Color.parseColor("#ffffff");
        int labelColor = Color.parseColor("#000000");
        String labelText = "2.7";

        CircleColorView circle = new CircleColorView(getInstrumentation().getTargetContext());
        circle.setCircleColor(circleColor);
        circle.setLabelColor(labelColor);
        circle.setCircleText(labelText);

        assertEquals(circleColor, circle.getCircleColor());
        assertEquals(labelColor, circle.getLabelColor());
        assertEquals(labelText, circle.getCircleText());
    }
}
