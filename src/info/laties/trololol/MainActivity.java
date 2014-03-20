package info.laties.trololol;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;
import java.lang.Math;

public class MainActivity extends Activity implements SensorEventListener {
	private SensorManager sm;
	private Sensor sensor;
	private TextView gyroOutput;
	private String gyroOutputFormat;
	private float timestamp;
	private float NS2S = 1.0f / 1000000000.0f;
	private final float[] deltaRotationVector = new float[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        //grab text view
        gyroOutput = (TextView) findViewById(R.id.GyroOut);
        gyroOutputFormat = getResources().getString(R.string.GyroOut);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    protected void onResume() {
    	super.onResume();
    	sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    protected void onPause() {
    	super.onPause();
    	sm.unregisterListener(this);
    }
    
    @SuppressLint("DefaultLocale")
	public void onSensorChanged(SensorEvent event) {
        String out = String.format("%f azimuth, %f pitch, %f roll", event.values[0], event.values[1], event.values[2]);
    	gyroOutput.setText(out);    
    }
    
    public void onAccuracyChanged(Sensor s, int ac) {}

}
