package com.cmrit.instadroid.activity;

import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmrit.instadroid.R;
import com.cmrit.instadroid.adapter.EffectsGalleryAdapter;
import com.cmrit.instadroid.util.FilePathtoPositionMap;


public class FullScreenImageActivity extends Activity {
	private boolean effectsVisible = false;
	private Bitmap bm;
	private Gallery g;
	private ImageView iv;
	private int currentImagePosition;
	private static Bitmap currentBitmap;
	
	@Override
	protected void onCreate(Bundle state){
		super.onCreate(state);
		setContentView(R.layout.full_screen_image);
		
		currentImagePosition = getIntent().getExtras().getInt("currentImagePosition");

		iv = (ImageView) findViewById(R.id.fullscreen_image_view);
		
		try {
			bm = BitmapFactory.decodeFile(FilePathtoPositionMap.getItem(currentImagePosition));
	    	iv.setImageBitmap(bm);
	    	currentBitmap = bm;
		} catch (Exception e){
			e.printStackTrace();	
		}
		
		iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
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
	        	
	        	ImageView tmpiv = (ImageView) g.getSelectedView().findViewById(R.id.image);
	        	BitmapDrawable tmpBd = (BitmapDrawable) tmpiv.getDrawable();
	        	Bitmap tmpB = tmpBd.getBitmap();	        	
	        	iv.setImageBitmap(tmpB);
	        	FullScreenImageActivity.currentBitmap = tmpB;
	        	
	        	TextView currentEffect = (TextView) g.getSelectedView().findViewById(R.id.image_view_caption);	        	
	        	
	            Toast.makeText(FullScreenImageActivity.this, currentEffect.getText() + " applied.", Toast.LENGTH_SHORT).show();
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
    		
    		try {
    			//Save modified image
    			FileOutputStream out = new FileOutputStream(FilePathtoPositionMap.getItem(currentImagePosition));
    			currentBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		super.onBackPressed();
    	}
    }
}
