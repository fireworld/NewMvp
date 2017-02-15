package cc.colorcat.newmvp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by cxx on 2016/3/23.
 * xx.ch@outlook.com
 */
public class VHolder {
    private final SparseArray<View> mViews = new SparseArray<>();
    protected final View mRootView;
    private int mViewType = -1;

    public static VHolder from(@NonNull LayoutInflater inflater, @LayoutRes int layoutResId, @Nullable ViewGroup container) {
        return new VHolder(inflater.inflate(layoutResId, container, false));
    }

    public static VHolder from(@NonNull LayoutInflater inflater, @LayoutRes int layoutResId) {
        return new VHolder(inflater.inflate(layoutResId, null));
    }

    public static VHolder from(@NonNull Context ctx, @LayoutRes int layoutResId) {
        return new VHolder(LayoutInflater.from(ctx).inflate(layoutResId, null));
    }

    public static VHolder from(@NonNull Activity act) {
        return new VHolder(act.getWindow().getDecorView());
    }

    public static VHolder from(@LayoutRes int layoutResId, @NonNull ViewGroup parent) {
        return new VHolder(LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false));
    }

    public static VHolder from(@NonNull View view) {
        return new VHolder(view);
    }

    protected VHolder(@NonNull View view) {
        mRootView = Op.nonNull(view, "view == null");
    }

    public View getRootView() {
        return mRootView;
    }

    public VHolder setType(int viewType) {
        mViewType = viewType;
        return this;
    }

    public int getType() {
        return mViewType;
    }

    /**
     * @throws NullPointerException If can't find the view by specified id.
     */
    @SuppressWarnings(value = "unchecked")
    public <V extends View> V getView(@IdRes int viewResId) {
        View view = mViews.get(viewResId);
        if (view == null) {
            view = mRootView.findViewById(viewResId);
            if (view != null) {
                mViews.put(viewResId, view);
            } else {
                throw new NullPointerException("Can't find view, id = " + viewResId);
            }
        }
        return (V) view;
    }

    public VHolder setText(@IdRes int viewResId, CharSequence text) {
        TextView tv = getView(viewResId);
        tv.setText(text);
        return this;
    }

    public VHolder setText(@IdRes int viewResId, @StringRes int strResId) {
        TextView tv = getView(viewResId);
        tv.setText(strResId);
        return this;
    }

    public VHolder setError(@IdRes int viewResId, @NonNull CharSequence text) {
        TextView tv = getView(viewResId);
        tv.setError(text);
        return this;
    }

    public VHolder setError(@IdRes int viewResId, @NonNull CharSequence text, @NonNull Drawable icon) {
        TextView tv = getView(viewResId);
        tv.setError(text, icon);
        return this;
    }

    /**
     * Sets flags on the Paint being used to display the text and
     * reflows the text if they are different from the old flags.
     *
     * @see Paint#setFlags
     */
    public VHolder setPaintFlags(@IdRes int viewResId, int flags) {
        TextView tv = getView(viewResId);
        tv.setPaintFlags(flags);
        return this;
    }

    public boolean isChecked(@IdRes int viewResId) {
        Checkable c = getView(viewResId);
        return c.isChecked();
    }

    public VHolder setChecked(@IdRes int viewResId, boolean checked) {
        Checkable c = getView(viewResId);
        c.setChecked(checked);
        return this;
    }

    public VHolder toggle(@IdRes int viewResId) {
        Checkable c = getView(viewResId);
        c.toggle();
        return this;
    }

    public VHolder setOnCheckedChangeListener(@IdRes int viewResId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton cb = getView(viewResId);
        cb.setOnCheckedChangeListener(listener);
        return this;
    }

    public VHolder setBackground(@IdRes int viewResId, Drawable drawable) {
        View view = getView(viewResId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        return this;
    }

    public VHolder setBackground(@IdRes int viewResId, @DrawableRes int drawableResId) {
        View view = getView(viewResId);
        view.setBackgroundResource(drawableResId);
        return this;
    }

    public VHolder setBackgroundColor(@IdRes int viewResId, @ColorInt int color) {
        View view = getView(viewResId);
        view.setBackgroundColor(color);
        return this;
    }

    public VHolder setOnClickListener(View.OnClickListener listener, @IdRes int... viewResId) {
        for (int id : viewResId) {
            View view = getView(id);
            view.setOnClickListener(listener);
        }
        return this;
    }

    public VHolder setOnClickListener(@IdRes int viewResId, View.OnClickListener listener) {
        View view = getView(viewResId);
        view.setOnClickListener(listener);
        return this;
    }

    public VHolder setOnLongClickListener(View.OnLongClickListener listener, @IdRes int... viewResId) {
        for (int id : viewResId) {
            View view = getView(id);
            view.setOnLongClickListener(listener);
        }
        return this;
    }

    public VHolder setOnLongClickListener(@IdRes int viewResId, View.OnLongClickListener listener) {
        View view = getView(viewResId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public VHolder setVisibility(@IdRes int viewResId, @Visibility int visibility) {
        View view = getView(viewResId);
        view.setVisibility(visibility);
        return this;
    }

    public VHolder setEnabled(@IdRes int viewResId, boolean enabled) {
        View view = getView(viewResId);
        view.setEnabled(enabled);
        return this;
    }

    public VHolder setImageResource(@IdRes int viewResId, @DrawableRes int drawableResId) {
        ImageView iv = getView(viewResId);
        iv.setImageResource(drawableResId);
        return this;
    }

    public VHolder setImageBitmap(@IdRes int viewResId, Bitmap bitmap) {
        ImageView iv = getView(viewResId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    public VHolder setImageBitmap(@IdRes int viewResId, @NonNull String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return setImageBitmap(viewResId, bitmap);
    }

    public VHolder setImageBitmap(@IdRes int viewResId, @NonNull File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return setImageBitmap(viewResId, bitmap);
    }

    public VHolder setTag(@IdRes int viewResId, final Object tag) {
        View view = getView(viewResId);
        view.setTag(tag);
        return this;
    }

    /**
     * @param key The specified key should be an id declared in the resources of the application to ensure it is unique.
     * @throws IllegalArgumentException If they specified key is not valid
     */
    public VHolder setTag(@IdRes int viewResId, int key, final Object tag) {
        View view = getView(viewResId);
        view.setTag(key, tag);
        return this;
    }

    @SuppressWarnings(value = "unchecked")
    public <E> E getTag(@IdRes int viewResId) {
        View view = getView(viewResId);
        return (E) view.getTag();
    }

    @SuppressWarnings(value = "unchecked")
    public <E> E getTag(@IdRes int viewResId, int key) {
        View view = getView(viewResId);
        return (E) view.getTag(key);
    }

    public String getText(@IdRes int viewResId) {
        TextView view = getView(viewResId);
        return view.getText().toString();
    }

    public String getTrimmedText(@IdRes int viewResId) {
        TextView view = getView(viewResId);
        return view.getText().toString().trim();
    }

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }
}