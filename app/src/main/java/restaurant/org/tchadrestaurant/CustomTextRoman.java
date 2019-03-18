package restaurant.org.tchadrestaurant;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by tokmang on 5/17/2016.
 */
public class CustomTextRoman  extends TextView {

    public CustomTextRoman(Context context) {
        super(context);
        setFont();
    }
    public CustomTextRoman(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public CustomTextRoman(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/HelveticaNeueLTStd-Roman.otf");
        setTypeface(font);
    }
}
