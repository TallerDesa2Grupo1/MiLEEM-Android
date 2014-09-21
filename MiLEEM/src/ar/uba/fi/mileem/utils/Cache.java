package ar.uba.fi.mileem.utils;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.Context;
import android.util.Log;
import ar.uba.fi.mileem.MileemApp;

public class Cache {
	
	public static Object getCachedObject(String key){
		String id =  toSHA1(key.getBytes());
			try {
				ObjectInputStream fis = new ObjectInputStream(MileemApp.getContext().openFileInput(id + ".json"));
				return fis.readObject();
			} catch (Exception e) {
				Log.e("Cache", "fallo getCachedObject " + key);
				return null;				
			}
	}

	public static void saveTo(String key, Object o){
		String id =  toSHA1(key.getBytes());
		try{
			MileemApp.getContext().deleteFile(id);
			FileOutputStream fos = 	MileemApp.getContext().openFileOutput(id + ".json", Context.MODE_PRIVATE);
			ObjectOutput out = new ObjectOutputStream(fos);
			out.writeObject( o);
			out.close();
		}catch(Exception e){
			Log.e("Cache", "fallo el saveTo " + key);
			e.printStackTrace();
		}
	}
		
	private static String toSHA1(byte[] convertme) {
	    MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    }
	    catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } 
	    return  byteArrayToHexString(md.digest(convertme));
	}
	
	private static String  byteArrayToHexString(byte[] b) {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result +=
		          Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		  }
		  return result;
		}
}
