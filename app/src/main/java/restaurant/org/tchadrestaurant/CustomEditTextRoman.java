package restaurant.org.tchadrestaurant;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by tokmang on 5/17/2016.
 */
public class CustomEditTextRoman extends EditText {

    public CustomEditTextRoman(Context context) {
        super(context);
        setFont();
    }

    public CustomEditTextRoman(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomEditTextRoman(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/HelveticaNeueLTStd-Roman.otf");
        setTypeface(font);
    }
}
