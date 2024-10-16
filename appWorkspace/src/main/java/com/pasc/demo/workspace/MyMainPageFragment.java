package com.pasc.demo.workspace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.pasc.business.workspace.MainPageFragment;
import com.pasc.lib.widget.tangram.BasePascCell;
import com.pasc.lib.widget.tangram.CardHeaderCell2;
import com.pasc.lib.widget.tangram.PersonalInfoCell;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.structure.BaseCell;

public class MyMainPageFragment extends MainPageFragment {

  @Override
  protected boolean isPullToRefreshEnable() {
    return true;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    /*configurableRecyclerView.postDelayed(new Runnable() {
      @Override public void run() {
        TangramEngine engine = getEngine();
        if (engine != null) {
          BaseCell testVisibleTrue = engine.findCellById("testVisibleTrue");
          if (testVisibleTrue != null) {
            if (testVisibleTrue instanceof BasePascCell) {
              ((BasePascCell) testVisibleTrue).setVisible(true);
              engine.update(testVisibleTrue);
            }
          }

          BaseCell testVisibleFalse = engine.findCellById("testVisibleFalse");
          if (testVisibleFalse != null) {
            if (testVisibleFalse instanceof BasePascCell) {
              ((BasePascCell) testVisibleFalse).setVisible(false);
              engine.update(testVisibleFalse);
            }
          }
        }
      }
    }, 5000);*/
  }

  @Override public String obtainSearchHint() {
    return null;
  }
}
