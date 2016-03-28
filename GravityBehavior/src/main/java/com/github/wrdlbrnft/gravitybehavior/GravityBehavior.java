package com.github.wrdlbrnft.gravitybehavior;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 28/03/16
 */
public class GravityBehavior extends CoordinatorLayout.Behavior<View> {

    public static GravityBehavior of(View view) {
        final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        return (GravityBehavior) params.getBehavior();
    }

    private final SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            final float x = event.values[0];
            final float y = event.values[1];

            final float angle = (float) -Math.toDegrees(Math.atan2(y, x) - Math.atan2(1.0, 0.0));
            mView.setRotation(angle);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private final SensorManager mSensorManager;
    private View mView;

    public GravityBehavior(Context context) {
        super();
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public GravityBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        mView = child;
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    public boolean startRotating() {
        final Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if (sensor == null) {
            return false;
        }

        mSensorManager.registerListener(mListener, sensor, SensorManager.SENSOR_DELAY_UI);
        return true;
    }

    public void stopRotating() {
        mSensorManager.unregisterListener(mListener);
    }
}
