package localnews.nordmedia.de.localnews;

import android.view.View;

/**
 * Created by Oderik on 17.07.2014.
 */
public class Views {
    static <T extends View> T find(View view, int id) {
        return (T) view.findViewById(id);
    }
}
