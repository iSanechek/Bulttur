package com.tbuonomo.viewpagerdotsindicator;

import android.content.Context;
import android.util.TypedValue;
import com.isanechek.balttur.R;

public class UiUtils {
    public static int getThemePrimaryColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }
}