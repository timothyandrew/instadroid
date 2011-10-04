package com.cmrit.instadroid.activity;

import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cmrit.instadroid.R;
import com.cmrit.instadroid.adapter.ImageIndexAdapter;

public class ImageIndexActivity extends Activity {
    private String currentState = "logged-out"; //Can be "logged-in" or "logged-out"
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_index);
        
        if(!checkSDCard()){
        	Toast.makeText(this, "Can't open app. SD Card required.", Toast.LENGTH_SHORT).show();
        	finish();
        }
        
        Bundle b = getIntent().getExtras();
        if(b != null) currentState = b.getString("currentState");

        GridView gridView = (GridView) findViewById(R.id.gridview);
        ImageIndexAdapter gridAdapter = new ImageIndexAdapter(this);
        gridView.setAdapter(gridAdapter);
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id){
        		Intent intent = new Intent(ImageIndexActivity.this, FullScreenImageActivity.class);
        		
        		//Get bitmap from the image view
        		ImageView iv = (ImageView) v;
        		BitmapDrawable bd = (BitmapDrawable) iv.getDrawable();
        		Bitmap bm = bd.getBitmap();
        		
        		//Save it to a temp location that FullScreenImageActivity can retrieve from
        		try{
        			FileOutputStream out = new FileOutputStream(getExternalFilesDir(null).getPath() + "/tmpFile.jpg");                			
        			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
        		} catch(Exception e){
        			e.printStackTrace();
        		}
        		
        		startActivity(intent);
        	}
		});
        
		
        Button loginButton = (Button) findViewById(R.id.login_button);
		Button registerButton = (Button) findViewById(R.id.register_button);
		Button loggedInAs = (Button) findViewById(R.id.logged_in);
		if(currentState.equals("logged-in")){
			loginButton.setVisibility(View.GONE);
			registerButton.setVisibility(View.GONE);
			loggedInAs.setVisibility(View.VISIBLE);		
		} else {
			loginButton.setVisibility(View.VISIBLE);
			registerButton.setVisibility(View.VISIBLE);
			loggedInAs.setVisibility(View.GONE);	
			
			loginButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ImageIndexActivity.this, LoginRegisterActivity.class);
					intent.putExtra("currentState", "login");
					startActivity(intent);
				}
			});
        
			registerButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ImageIndexActivity.this, LoginRegisterActivity.class);
					intent.putExtra("currentState", "register");
					startActivity(intent);
				}
			});
		}
    }
    
    private boolean checkSDCard(){
    	String state = Environment.getExternalStorageState();

    	if (Environment.MEDIA_MOUNTED.equals(state)) {
    	    // We can read and write the media
    	    return true;
    	} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
    	    // We can only read the media
    	    return false;
    	} else {
    	    // Something else is wrong. It may be one of many other states, but all we need
    	    //  to know is we can neither read nor write
    	    return false;
    	}
    }
}