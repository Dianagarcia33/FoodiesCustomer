package com.foodies.customer.Constants;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by qboxus on 10/18/2019.
 */

public class AllConstants {


    public static int width=0;
    public static int height=0;

    public static String verdana = "verdana.ttf";
    public static String arial = "arial.ttf";

    public static final String CALCULATION = "CalculationAndroid";
    public static final String TRACKING = "tracking";

    public static final String PACKAGES = "packages";


    public static SimpleDateFormat df =
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ssZZ", Locale.ENGLISH);

    public static SimpleDateFormat df2 =
            new SimpleDateFormat("dd-MM-yyyy HH:mmZZ", Locale.ENGLISH);


    public final static String tag="foodies_customer";

}
