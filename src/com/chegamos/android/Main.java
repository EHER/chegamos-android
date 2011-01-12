package com.chegamos.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity implements LocationListener {
	
	private LocationManager lm;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //obtem instancia de locationmanager das activities
        lm = (LocationManager) getSystemService(Activity.LOCATION_SERVICE);
        
        //se o gps estiver desligado, abre prompt para ligá-lo
        activeGPS();
        
        //inicia a caça às posições
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

	@Override
	public void onLocationChanged(Location location) {
		try {
		
			String params = ("lat="+ location.getLatitude() +"&lng=" + location.getLongitude());
			
			Log.d("Eher", "http://chegamos.com/places/checkin/?" + params);
			
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://chegamos.com/places/checkin/?" + params));
			startActivity(browserIntent);

			lm.removeUpdates(this);
			
			finish();
			
		
		} catch (Exception e) {
			
		}
		
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void activeGPS() {
		
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Log.d("Eher", "sem gps");
        	createGpsDisabledAlert();  
        } else {
        	Log.d("Eher", "com gps");
        }
	}

	private void createGpsDisabledAlert() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder
				.setMessage("Ative o GPS para utilizar o aplicativo")
				.setCancelable(false).setPositiveButton("Continuar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								showGpsOptions();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showGpsOptions(){  
		this.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));  
	}
	
}