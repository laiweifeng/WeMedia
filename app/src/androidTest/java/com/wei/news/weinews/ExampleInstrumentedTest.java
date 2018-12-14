package com.wei.news.weinews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.wei.news.weinews", appContext.getPackageName());

        int[] positions=new int[]{1,9,3};
        String str="53218543154651321846513218535";

        ArrayList<String> strings=new ArrayList<>();
        for(int i=0;i<positions.length;i++){
            int position=positions[i];

            String newStr=str.substring(position,str.length());
            strings.add(newStr);
        }
        //打印
        System.out.print(strings.toString());
    }
}
