package com.example.administrator.kcapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.fragment.FragmentGz;
import com.example.administrator.kcapp.fragment.FragmentNiuren;
import com.example.administrator.kcapp.fragment.FragmentThree;
import com.example.administrator.kcapp.fragment.FragmentSy;
import com.example.administrator.kcapp.fragment.FragmentWd;
import com.example.administrator.kcapp.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /**
     * 底部菜单首页按钮
     */
    @BindView(R.id.menu_sy)
    ImageButton ibSy;
    /**
     * 底部菜单牛人牛事按钮
     */
    @BindView(R.id.menu_fl)
    ImageButton ibNiu;
    /**
     * 底部菜单关注按钮
     */
    @BindView(R.id.menu_fx)
    ImageButton ibGz;
    /**
     * 底部菜单我的按钮
     */
    @BindView(R.id.menu_wd)
    ImageButton ibWd;

    @BindView(R.id.menu1)
    RelativeLayout rl1;
    @BindView(R.id.menu2)
    RelativeLayout rl2;
    @BindView(R.id.menu3)
    RelativeLayout rl3;
    @BindView(R.id.menu4)
    RelativeLayout rl4;

    private List<Fragment> fragmentList = new ArrayList<>();
    private MenuOnClickListener listener = new MenuOnClickListener();

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApp.getInstance().addActivity(this);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBar(MainActivity.this, Color.parseColor("#f6f6f6"));

        init();

    }

    /**
     * 初始化各个组件
     */
    private void init() {

        ibSy.setOnClickListener(listener);
        ibNiu.setOnClickListener(listener);
        ibGz.setOnClickListener(listener);
        ibWd.setOnClickListener(listener);

        rl1.setOnClickListener(listener);
        rl2.setOnClickListener(listener);
        rl3.setOnClickListener(listener);
        rl4.setOnClickListener(listener);
        Fragment fragmentSy = new FragmentSy();
        Fragment fragmentNiu = new FragmentNiuren();
        Fragment fragmentGz = new FragmentGz();
        Fragment fragmentWd = new FragmentWd();

        fragmentList.add(fragmentSy);
        fragmentList.add(fragmentNiu);
        fragmentList.add(fragmentGz);
        fragmentList.add(fragmentWd);

        fragmentTransaction.add(R.id.fl_container, fragmentSy);
        fragmentTransaction.add(R.id.fl_container, fragmentNiu);
        fragmentTransaction.add(R.id.fl_container, fragmentGz);
        fragmentTransaction.add(R.id.fl_container, fragmentWd);

        fragmentTransaction.show(fragmentSy).hide(fragmentNiu).hide(fragmentGz).hide(fragmentWd);
        fragmentTransaction.commit();

        selectButton(ibSy);

    }

    private class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_sy:
                    selectButton(ibSy);
                    switchFragment(0);
                    break;
                case R.id.menu_fl:
                    selectButton(ibNiu);
                    switchFragment(1);
                    break;
                case R.id.menu_fx:
                    selectButton(ibGz);
                    switchFragment(2);
                    break;
                case R.id.menu_wd:
                    selectButton(ibWd);
                    switchFragment(3);
                    break;
                case R.id.menu1:
                    selectButton(ibSy);
                    switchFragment(0);
                    break;
                case R.id.menu2:
                    selectButton(ibNiu);
                    switchFragment(1);
                    break;
                case R.id.menu3:
                    selectButton(ibGz);
                    switchFragment(2);
                    break;
                case R.id.menu4:
                    selectButton(ibWd);
                    switchFragment(3);
                    break;
            }

        }
    }

    /**
     * 选择隐藏与显示的Fragment
     *
     * @param index 显示的Frgament的角标
     */
    private void switchFragment(int index) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < fragmentList.size(); i++) {
            if (index == i) {
                fragmentTransaction.show(fragmentList.get(index));
            } else {
                fragmentTransaction.hide(fragmentList.get(i));
            }
        }
        fragmentTransaction.commit();
    }

    /**
     * 控制底部菜单按钮的选中
     *
     * @param v
     */
    public void selectButton(View v) {
        ibSy.setSelected(false);
        ibNiu.setSelected(false);
        ibGz.setSelected(false);
        ibWd.setSelected(false);
        v.setSelected(true);
    }

    public void selectNiu(){
        selectButton(ibNiu);
        switchFragment(1);
    }

    @Override
    public void onBackPressed() {
        // TODO 自动生成的方法存根
        backtrack();
    }

    /**
     * 退出销毁所有activity
     */
    private void backtrack() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            MyApp.getInstance().exit();
            exitTime = 0;
        }
    }

}
