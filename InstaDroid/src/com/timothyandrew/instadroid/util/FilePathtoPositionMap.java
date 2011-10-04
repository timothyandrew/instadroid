package com.timothyandrew.instadroid.util;

import java.util.HashMap;

public class FilePathtoPositionMap {
	//Indexes are positions, elements are file paths
	private static HashMap<Integer, String> map = new HashMap<Integer, String>();
	
	static public void addItem(int key, String value){
		map.put(key, value);
	}
	
	static public void clear(){
		map.clear();
	}
	
	static public String getItem(int key){
		return map.get(key);
	}
	
	static public HashMap<Integer, String> getMap(){
		return map;
	}
}
