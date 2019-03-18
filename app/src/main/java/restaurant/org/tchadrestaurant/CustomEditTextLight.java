package restaurant.org.tchadrestaurant;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by tokmang on 5/17/2016.
 */
public class CustomEditTextLight extends EditText {

    public CustomEditTextLight(Context context) {
        super(context);
        setFont();
    }
    public CustomEditTextLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public CustomEditTextLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/HelveticaNeueLTStd-Lt.otf");
        setTypeface(font);
    }
}
