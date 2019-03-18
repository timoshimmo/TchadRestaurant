package restaurant.org.tchadrestaurant;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by tokmang on 5/17/2016.
 */
public class CustomButtonLight extends Button {

    public CustomButtonLight(Context context) {
        super(context);
        setFont();
    }

    public CustomButtonLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomButtonLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/HelveticaNeueLTStd-Lt.otf");
        setTypeface(font);
    }
}
