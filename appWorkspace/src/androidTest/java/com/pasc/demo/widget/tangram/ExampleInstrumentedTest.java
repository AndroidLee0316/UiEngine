package com.pasc.demo.widget.tangram;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.pasc.demo.workspace.R;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    assertEquals("com.pasc.demo.widget.tangram", appContext.getPackageName());
  }

  @Test
  public void testResource() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    Resources resources = appContext.getResources();
    String resourceEntryName = resources.getResourceEntryName(R.drawable.bg_grid);
    String resourceName = resources.getResourceName(R.drawable.bg_grid);
    String resourceTypeName = resources.getResourceTypeName(R.drawable.bg_grid);

    assertEquals("bg_grid", resourceEntryName);
    assertEquals("com.pasc.demo.workspace.base:drawable/bg_grid", resourceName);
    assertEquals("drawable", resourceTypeName);

    String resourceTypeNameLauncher = resources.getResourceTypeName(R.mipmap.ic_launcher);
    assertEquals("mipmap", resourceTypeNameLauncher);
  }
}
