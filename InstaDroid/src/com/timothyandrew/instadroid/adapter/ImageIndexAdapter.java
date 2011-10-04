package com.timothyandrew.instadroid.adapter;

import java.io.File;
import java.io.FileFilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.timothyandrew.instadroid.util.FilePathtoPositionMap;

public class ImageIndexAdapter extends BaseAdapter {
	private Context mContext;
	private File[] imagePaths;

	public ImageIndexAdapter(Context c) {
		mContext = c;
		FilePathtoPositionMap.clear();
		getImagesFromExternalStorage();
	}

	public int getCount() {
		return imagePaths.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(25, 25, 25, 25);
		} else {
			imageView = (ImageView) convertView;
		}
		try {
			Bitmap b = BitmapFactory.decodeFile(imagePaths[position].getPath());
			imageView.setImageBitmap(b);
		} catch (Exception e) {
			e.printStackTrace();
		}

		FilePathtoPositionMap.addItem(position, imagePaths[position].getPath());

		return imageView;
	}

	private void getImagesFromExternalStorage() {
		File dir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		imagePaths = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String extension = getExtension(pathname);
				if (extension != null) {
					if (extension.equals("jpg") || extension.equals("jpeg"))
						return true;
				}
				return false;
			}

			private String getExtension(File f) {
				String ext = null;
				String s = f.getName();

				if (s.charAt(0) == '.')
					return null;

				int i = s.lastIndexOf('.');

				if (i > 0 && i < s.length() - 1) {
					ext = s.substring(i + 1).toLowerCase();
				}
				return ext;
			}
		});
	}
}
