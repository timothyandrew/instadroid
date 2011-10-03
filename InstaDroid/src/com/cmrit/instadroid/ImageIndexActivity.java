package com.cmrit.instadroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class ImageIndexActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_index);
        
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id){
        		Toast toast = Toast.makeText(ImageIndexActivity.this, "You clicked " + position, Toast.LENGTH_SHORT);
        		toast.show();        		
        	}
		});
        
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ImageIndexActivity.this, LoginRegisterActivity.class);
				intent.putExtra("currentState", "login");
				startActivity(intent);
			}
		});
        
        Button registerButton = (Button) findViewById(R.id.register_button);
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