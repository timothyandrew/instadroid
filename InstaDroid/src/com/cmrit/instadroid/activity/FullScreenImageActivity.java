package com.cmrit.instadroid.activity;

import com.cmrit.instadroid.R;
import com.cmrit.instadroid.R.array;
import com.cmrit.instadroid.R.id;
import com.cmrit.instadroid.R.layout;
import com.cmrit.instadroid.R.menu;
import com.cmrit.instadroid.adapter.EffectsGalleryAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class FullScreenImageActivity extends Activity {
	private boolean effectsVisible = false;
	private Bitmap bm;
	private Gallery g;
	
	@Override
	protected void onCreate(Bundle state){
		super.onCreate(state);
		setContentView(R.layout.full_screen_image);

		ImageView iv = (ImageView) findViewById(R.id.fullscreen_image_view);
		
		try {
			bm = BitmapFactory.decodeFile(getExternalFilesDir(null) + "/tmpFile.jpg");
	    	iv.setImageBitmap(bm);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		preCreateEffects();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.full_screen_image_menu, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()) {
		case R.id.effects:			
			showEffects();
			return true;
		case R.id.share:
	    	new AlertDialog.Builder(this).setTitle("Share this image").setItems(R.array.sharing_sites_list, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(FullScreenImageActivity.this, "Menu clicked at: " + which, Toast.LENGTH_SHORT).show();
				}
			}).show();			
			return true;
		case R.id.settings:
			
			return true;
		}
    	return false;
    }
    
    private void showEffects(){
	    g.setVisibility(View.VISIBLE);
	    effectsVisible = true;
    }
    
    private void hideEffects(){
    	g.setVisibility(View.GONE);
    	effectsVisible = false;
    }
    
    private void preCreateEffects(){
	    g = (Gallery) findViewById(R.id.effects_image_gallery);
	    g.setAdapter(new EffectsGalleryAdapter(this));

	    g.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(FullScreenImageActivity.this, "Gallery clicked at: " + position, Toast.LENGTH_SHORT).show();
	        }
	    });
    }
    
    public Bitmap getBitmap(){
    	return bm;
    }
    
    @Override
    public void onBackPressed(){
    	if(effectsVisible){
    		hideEffects();
    	} else {
    		super.onBackPressed();
    	}
    }
}
