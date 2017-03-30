package ir.bpadashi.requester.model;

import ir.bpadashi.requester.util.TextUtil;

/**
 * Created by BabakPadashi on 28/03/2017.
 */
public class ResponseString {

    private final Object str;

    public ResponseString(Object str) {
        this.str = str;
    }

    public String toString() {

        if (str != null)
            return (String) str.toString();
        return TextUtil.RESPONSE_IS_NULL;

    }

    public String getResponse() {
        if (str != null)
            return (String) str.toString();
        return TextUtil.RESPONSE_IS_NULL;

    }
}
