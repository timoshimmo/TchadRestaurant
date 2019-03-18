package restaurant.org.tchadrestaurant;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by tokmang on 5/17/2016.
 */
public class CustomTextLight extends TextView {

    public CustomTextLight(Context context) {
        super(context);
        setFont();
    }
    public CustomTextLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public CustomTextLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/HelveticaNeueLTStd-Lt.otf");
        setTypeface(font);
    }
}
