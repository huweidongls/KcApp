package com.example.administrator.kcapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.kcapp.MainActivity;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.ActivitySearchRvAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.entity.ActivitySearchRvEntity;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.library.flowlayout.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class BookActivity extends AppCompatActivity {

    @BindView(R.id.activity_book_search_view)
    SearchView searchView;
    @BindView(R.id.activity_search_rv)
    RecyclerView recyclerView;

    private ActivitySearchRvAdapter searchRvAdapter;
    private List<ActivitySearchRvEntity> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        StatusBarUtils.setStatusBar(BookActivity.this, Color.parseColor("#f6f6f6"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

        init();
        initRv();

    }

    private void initRv() {

        data = new ArrayList<>();
        data.add(new ActivitySearchRvEntity("教育"));
        data.add(new ActivitySearchRvEntity("科技"));
        data.add(new ActivitySearchRvEntity("NBA"));
        data.add(new ActivitySearchRvEntity("热门课程"));
        data.add(new ActivitySearchRvEntity("牛人牛事"));
        data.add(new ActivitySearchRvEntity("搞笑视频"));
        data.add(new ActivitySearchRvEntity("噶的说法噶地方"));
        data.add(new ActivitySearchRvEntity("割发代首双方都"));
        data.add(new ActivitySearchRvEntity("和国防生的放大师傅"));
        searchRvAdapter = new ActivitySearchRvAdapter(data);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        recyclerView.setLayoutManager(flowLayoutManager);
        recyclerView.setAdapter(searchRvAdapter);

        searchRvAdapter.setOnItemClickListener(new ActivitySearchRvAdapter.ActivitySearchRvListener() {
            @Override
            public void onClick(String text) {
                searchView.setText(text);
            }
        });

    }

    private void init() {

        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了" + string);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BookActivity.this.finish();
    }

}
