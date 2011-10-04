package com.cmrit.instadroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RemoteViews.ActionException;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class ImageIndexActivity extends Activity {
    private String currentState = "logged-out"; //Can be "logged-in" or "logged-out"
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_index);
        
        Bundle b = getIntent().getExtras();
        if(b != null) currentState = b.getString("currentState");

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id){
        		Toast toast = Toast.makeText(ImageIndexActivity.this, "You clicked " + position, Toast.LENGTH_SHORT);
        		toast.show();        		
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
					finish();
				}
			});
        
			registerButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ImageIndexActivity.this, LoginRegisterActivity.class);
					intent.putExtra("currentState", "register");
					startActivity(intent);
					finish();
				}
			});
		}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	Bundle b = data.getExtras();
    	currentState = b.getString("currentState");
    }
}