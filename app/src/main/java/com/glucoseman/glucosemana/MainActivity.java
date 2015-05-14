package com.glucoseman.glucosemana;

import com.common.library.activity.BaseActivity;
import com.common.library.util.LogUtil;

/**
 * Created by xing on 2015/5/13.
 */
public class MainActivity extends BaseActivity {

    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initDatas() {
        LogUtil.outLogDetail("test git status again");
        LogUtil.outLogDetail("test git branch version1.0 test");
    }

    @Override
    public void initBusiness() {

    }
}
