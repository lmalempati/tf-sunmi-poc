package com.sunmi.msrtests.utilities;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunmi.msrtests.data.entity.FDRCTestCase;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
public class JSONUtilities {

    /**
     * Read file from asset directory
     * @param ctx current context
     * @param resourceName file to read
     * @return content of the file, string format
     */
    private static String readFromAsset(final Context ctx, final int resourceName)
    {
        String jsonTextValue = "";
        try {
            //InputStream is = ctx.getAssets().open(resourceName);
            InputStream is = ctx.getResources().openRawResource(resourceName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonTextValue = new String(buffer, "UTF-8");
        }catch(IOException  e) {
            e.printStackTrace();
        }
        return jsonTextValue;
    }

    public static Set<FDRCTestCase> getListOfJsonObjects(final Context context, final int resourceName) {

        Set<FDRCTestCase> objectList = new HashSet<FDRCTestCase>();
        String jsonTextValue = readFromAsset(context, resourceName);
        Type listType = new TypeToken<HashSet<FDRCTestCase>>() {}.getType();
        // convert json into a list of Users
        try {
            objectList = new Gson().fromJson(jsonTextValue, listType);
        }
        catch (Exception e) {
            // we never know :)
        }
        return objectList;
    }
}
