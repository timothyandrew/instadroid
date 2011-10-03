package com.cmrit.instadroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginRegisterActivity extends Activity {
	private String currentState;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Bundle b = getIntent().getExtras();
		currentState = b.getString("currentState");
		
		Button ok = (Button) findViewById(R.id.ok);
		if(currentState.equals("login")){
			ok.setText("Login");
		} else {
			ok.setText("Register");
		} 
	}
}
