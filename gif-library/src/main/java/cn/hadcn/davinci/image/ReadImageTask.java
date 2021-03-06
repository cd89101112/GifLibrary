package cn.hadcn.davinci.image;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import cn.hadcn.davinci.R;
import cn.hadcn.davinci.image.base.ImageEntity;
import cn.hadcn.davinci.image.base.ImageLoader;
import cn.hadcn.davinci.image.base.Util;
import pl.droidsonroids.gif.GifDrawable;

/**
 * read image from any where
 * Created by 90Chris on 2016/5/5.
 */
class ReadImageTask {
    private final int DEFAULT_IMAGE_LOADING = R.drawable.image_loading;
    private final int DEFAULT_IMAGE_ERROR = R.drawable.image_load_error;

    private WeakReference<ImageView> mImageView;
    private String mImageUrl;
    private int mLoadingImage = DEFAULT_IMAGE_LOADING;
    private int mErrorImage = DEFAULT_IMAGE_ERROR;
    private VinciImageLoader.ImageCache mImageCache;
    private ImageLoader mImageLoader;
    private Context mContext;
    private int mMaxSize;
    private int mKeyMode;

    ReadImageTask(Context context, VinciImageLoader.ImageCache imageCache, ImageLoader imageLoader, String imageUrl) {
        mImageUrl = imageUrl;
        mImageCache = imageCache;
        mImageLoader = imageLoader;
        mContext = context;
    }

    final void execute(String requestBody) {
        if ( mImageUrl == null || mImageUrl.isEmpty() || Util.generateKey(mImageUrl).isEmpty() ) {
            mImageView.get().setImageDrawable(mContext.getResources().getDrawable(mErrorImage));
            return;
        }
        String rawKey = mImageUrl;
        if ( mKeyMode != 0 && mMaxSize != 0 ) rawKey += mMaxSize;
        ImageEntity entity = mImageCache.getBitmap(Util.generateKey(rawKey));

        if ( entity != null ) {

            // if it's gif, show as gif
            if ( entity.isGif() ) {
                try {
                    GifDrawable gifDrawable = new GifDrawable(entity.getBytes());
                    mImageView.get().setImageDrawable(gifDrawable);
                } catch (Throwable e) {
                }
            } else {
                mImageView.get().setImageBitmap(entity.getBitmap());
            }
        } else if ( mImageUrl.startsWith("http") ) {
            VolleyImageListener listener = new VolleyImageListener(mContext, mImageView, mImageCache);
            listener.setDefaultImage(mLoadingImage, mErrorImage);
            listener.setMaxSize(mMaxSize, mKeyMode);
            mImageLoader.get(mImageUrl, requestBody, listener);
        } else {
            mImageView.get().setImageDrawable(mContext.getResources().getDrawable(mErrorImage));
        }
    }

    void setView(WeakReference<ImageView> imageView, int image_loading, int image_error) {
        mImageView = imageView;
        if ( image_loading != 0 ) mLoadingImage = image_loading;
        if ( image_error != 0 ) mErrorImage = image_error;
    }

    protected void setView(WeakReference<ImageView> imageView) {
        mImageView = imageView;
    }

    void setSize(int size, int mode) {
        mMaxSize = size;
        mKeyMode = mode;
    }
}
