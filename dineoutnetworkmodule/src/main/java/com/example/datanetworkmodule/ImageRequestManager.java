package com.example.datanetworkmodule;

import android.content.Context;

import com.dineout.android.volley.RequestQueue;
import com.dineout.android.volley.toolbox.ImageLoader;
import com.dineout.android.volley.toolbox.Volley;


public class ImageRequestManager {

	private static ImageRequestManager instance;

	private ImageLoader imageLoader;
	private RequestQueue queue;

	private ImageRequestManager(Context context) {
		this.queue = Volley.newRequestQueue(context,
				null,
				Volley.DEFAULT_IMAGE_CACHE_DIR, 3, false, true);
		imageLoader = new ImageLoader(queue, new ImageCacheManager());
		this.queue.start();
	}

	public static ImageRequestManager getInstance(Context context) {
		if (instance == null) {
			synchronized (ImageCacheManager.class) {
				if (instance == null) {
					instance = new ImageRequestManager(context);
				}
			}
		}
		return instance;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public RequestQueue getQueue() {
		return queue;
	}

}
