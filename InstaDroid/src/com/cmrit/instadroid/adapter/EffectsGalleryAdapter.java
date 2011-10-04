package com.cmrit.instadroid.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmrit.instadroid.R;
import com.cmrit.instadroid.activity.FullScreenImageActivity;
import com.cmrit.instadroid.activity.lib.BitmapEffects;

public class EffectsGalleryAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;
    private String[] effectList = {	"B&W", "Sepia", "Soften", "Vignette", "Infrared", "Vintage", "Invert", "Original"};

    public EffectsGalleryAdapter(Context c) {
        mContext = c;
        TypedArray a = c.obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.HelloGallery_android_galleryItemBackground, 0);
        a.recycle();
    }

    public int getCount() {
        return effectList.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        
        if(convertView == null){
        	FullScreenImageActivity a = (FullScreenImageActivity) mContext;
        	view = a.getLayoutInflater().inflate(R.layout.image_view_with_caption, parent, false);
        	
        	ImageView iv = (ImageView) view.findViewById(R.id.image);
        	TextView tv = (TextView) view.findViewById(R.id.image_view_caption);
        			
        	view.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT, Gallery.LayoutParams.WRAP_CONTENT));
        	
        	Bitmap bm = a.getBitmap();
        	iv.setImageBitmap(applyEffect(bm, effectList[position]));
        	iv.setAdjustViewBounds(true);
        	iv.setMaxHeight(300);
        	iv.setMaxWidth(300);
        	
        	iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        	iv.setBackgroundResource(mGalleryItemBackground);
        	
        	tv.setText(effectList[position]);
        	
        	return view;
        } else {
        	return convertView;
        }
    }
    
    private Bitmap applyEffect(Bitmap bm, String effectType){
  
		if(effectType.equals("B&W"))
			return BitmapEffects.grayScale(bm);
		else if(effectType.equals("Sepia")) {
			//Return a sepia bitmap
			return bm;
		} else if(effectType.equals("Original")){
			return bm;
		}
		
		return bm;
	
    }
}