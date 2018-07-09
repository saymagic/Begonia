package cn.saymagic.begonia.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

public class PhotoUtils {

	public static final String IMAGE_PATH = "Begonia" + File.separator + "begonia_image";

	public static final File saveBitmapIntoSystem(Context context, Bitmap bitmap) throws IOException {
		if (context == null || bitmap == null) {
			throw new IllegalStateException("context or bit map is null");
		}
		String state = Environment.getExternalStorageState();
		if (!state.equals(Environment.MEDIA_MOUNTED)) {
			throw new IllegalStateException("sd card is not mounted");
		}
		File file = File.createTempFile("begonia", ".png", getImageSaveFileDirectory());
		FileOutputStream out = new FileOutputStream(file);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		out.flush();
		out.close();
		Uri uri = Uri.fromFile(file);
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
		return file;
	}

	private static File getImageSaveFileDirectory() {
		File path = new File(Environment.getExternalStorageDirectory(), IMAGE_PATH);
		if (!path.exists()) {
			path.mkdirs();
		}
		if (path.isFile()) {
			path.delete();
			path.mkdirs();
		}
		return path;
	}

}