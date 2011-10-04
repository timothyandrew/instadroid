package com.cmrit.instadroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginRegisterActivity extends Activity {
	private String currentState; //Can be "register" or "login"; this defines the state of the button.
	
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
		
		ok.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginRegisterActivity.this, ImageIndexActivity.class);
				intent.putExtra("currentState", "logged-in");
				startActivity(intent);
				LoginRegisterActivity.this.finish();
			}
		});
	}
}