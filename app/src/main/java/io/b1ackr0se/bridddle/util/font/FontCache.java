package io.b1ackr0se.bridddle.util.font;


import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(Context context, @Font String fontName) {
        Typeface typeface = fontCache.get(fontName);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            fontCache.put(fontName, typeface);
        }
        return typeface;
    }
}
