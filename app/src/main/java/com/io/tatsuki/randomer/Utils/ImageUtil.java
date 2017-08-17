package com.io.tatsuki.randomer.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * 画像関係のUtil
 */

public class ImageUtil {

    /**
     * リソースIDからURLに変換
     * @param context
     * @param drawableResId
     * @return String
     */
    public static String convertUrlFromDrawableResId(@NonNull Context context, int drawableResId) {
        StringBuilder sb = new StringBuilder();
        sb.append(ContentResolver.SCHEME_ANDROID_RESOURCE);
        sb.append("://");
        sb.append(context.getResources().getResourcePackageName(drawableResId));
        sb.append("/");
        sb.append(context.getResources().getResourceTypeName(drawableResId));
        sb.append("/");
        sb.append(context.getResources().getResourceEntryName(drawableResId));
        return sb.toString();
        // Uriに変換したい時は Uri.parse(sb.toString())
    }

    /**
     * 画像のセット
     * @param context
     * @param resourceUrl
     * @param imageView
     */
    public static void setImage(@NonNull Context context, String resourceUrl, ImageView imageView) {
        Picasso.with(context).load(resourceUrl).fit().into(imageView);
    }
}
