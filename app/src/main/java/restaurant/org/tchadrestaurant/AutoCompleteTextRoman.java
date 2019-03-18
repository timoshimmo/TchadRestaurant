package restaurant.org.tchadrestaurant;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by tokmang on 6/2/2016.
 */
public class AutoCompleteTextRoman extends AutoCompleteTextView {
    public AutoCompleteTextRoman(Context context) {
        super(context);
        setFont();
    }

    public AutoCompleteTextRoman(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public AutoCompleteTextRoman(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/HelveticaNeueLTStd-Roman.otf");
        setTypeface(font);
    }
}
