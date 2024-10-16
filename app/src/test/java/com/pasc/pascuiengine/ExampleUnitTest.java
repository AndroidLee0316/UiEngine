package com.pasc.pascuiengine;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEvent() {
        // String eventUrl = "event://HolenGin?name=john&age=18";
        // String eventUrl = "event://GoToWaterFees_ccb?name=john&age=18";
        String eventUrl = "event://GoToWaterFees_ccb";
        Pattern pattern = Pattern.compile("^event://(\\w*)(\\?.*)?$");
        Matcher matcher = pattern.matcher(eventUrl);
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println("group=" + group);

            String group1 = matcher.group(1);
            System.out.println("group1=" + group1);

            String group2 = matcher.group(2);
            System.out.println("group2=" + group2);
        }
    }
}