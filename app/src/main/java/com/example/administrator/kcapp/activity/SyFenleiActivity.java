package com.example.administrator.kcapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.kcapp.MainActivity;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.StartDragListener;
import com.example.administrator.kcapp.adapter.SyFenleiAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.gsonbean.GetClassBean;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyFenleiActivity extends AppCompatActivity implements StartDragListener {

    @BindView(R.id.sy_fenlei_back)
    RelativeLayout imageView;
    @BindView(R.id.sy_fenlei_recyclerview)
    RecyclerView recyclerView;

    private List<GetClassBean.DataBean> data;
    private SyFenleiAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_fenlei);

        StatusBarUtils.setStatusBar(SyFenleiActivity.this, Color.parseColor("#f6f6f6"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

        initData();

    }

    private void initData() {

        ViseHttp.POST("api/Classify/getClass")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        GetClassBean getClassBean = gson.fromJson(data, GetClassBean.class);
                        if(getClassBean.getData().size()>0){
                            init(getClassBean.getData());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    private void init(List<GetClassBean.DataBean> data) {

        this.data = data;

//        EventBus.getDefault().register(SyFenleiActivity.this);

        adapter = new SyFenleiAdapter(data);
        adapter.setDraglistener(this);

        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //1.创建item helper
        itemTouchHelper = new ItemTouchHelper(callback);
        //2.绑定到recyclerview上面去
        itemTouchHelper.attachToRecyclerView(recyclerView);
        //3.在ItemHelper的接口回调中过滤开启长按拖动，拓展其他操作

    }

    @OnClick({R.id.sy_fenlei_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.sy_fenlei_back:
                SyFenleiActivity.this.finish();
                this.overridePendingTransition(0, R.anim.activity_close);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SyFenleiActivity.this.finish();
        this.overridePendingTransition(0, R.anim.activity_close);
    }

    //itemHelper的回调
    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {

        /**
         * 官方文档的说明如下：
         * o control which actions user can take on each view, you should override getMovementFlags(RecyclerView, ViewHolder)
         * and return appropriate set of direction flags. (LEFT, RIGHT, START, END, UP, DOWN).
         * 返回我们要监控的方向，上下左右，我们做的是上下拖动，要返回都是UP和DOWN
         * 关键坑爹的是下面方法返回值只有1个，也就是说只能监控一个方向。
         * 不过点入到源码里面有惊喜。源码标记方向如下：
         *  public static final int UP = 1     0001
         *  public static final int DOWN = 1 << 1; （位运算：值其实就是2）0010
         *  public static final int LEFT = 1 << 2   左 值是3
         *  public static final int RIGHT = 1 << 3  右 值是8
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //也就是说返回值是组合式的
            //makeMovementFlags (int dragFlags, int swipeFlags)，看下面的解释说明
            int swipFlag=0;
            //如果也监控左右方向的话，swipFlag=ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
            int dragflag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //等价于：0001&0010;多点触控标记触屏手指的顺序和个数也是这样标记哦
            return  makeMovementFlags(dragflag,swipFlag);

            /**
             * 备注：由getMovementFlags可以联想到setMovementFlags，不过文档么有这个方法，但是：
             * 有 makeMovementFlags (int dragFlags, int swipeFlags)
             * Convenience method to create movement flags.便捷方法创建moveMentFlag
             * For instance, if you want to let your items be drag & dropped vertically and swiped left to be dismissed,
             * you can call this method with: makeMovementFlags(UP | DOWN, LEFT);
             * 这个recyclerview的文档写的简直完美，示例代码都弄好了！！！
             * 如果你想让item上下拖动和左边滑动删除，应该这样用： makeMovementFlags(UP | DOWN, LEFT)
             */

            //拓展一下：如果只想上下的话：makeMovementFlags（UP | DOWN, 0）,标记方向的最小值1
        }



        /**
         * 官方文档的说明如下
         * If user drags an item, ItemTouchHelper will call onMove(recyclerView, dragged, target). Upon receiving this callback,
         * you should move the item from the old position (dragged.getAdapterPosition()) to new position (target.getAdapterPosition())
         * in your adapter and also call notifyItemMoved(int, int).
         * 拖动某个item的回调，在return前要更新item位置，调用notifyItemMoved（draggedPosition，targetPosition）
         * viewHolde:正在拖动item
         * target：要拖到的目标
         * @return true 表示消费事件
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //直接按照文档来操作啊，这文档写得太给力了,简直完美！
            adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            //注意这里有个坑的，itemView 都移动了，对应的数据也要移动
            Collections.swap(data, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            String s = "";
            for(int i = 0; i<data.size(); i++){
                s = s+data.get(i).getName();
            }
            Log.e("222", ""+s);
            return true;
        }

        /**
         * 谷歌官方文档说明如下：
         * 这个看了一下主要是做左右拖动的回调
         * When a View is swiped, ItemTouchHelper animates it until it goes out of bounds, then calls onSwiped(ViewHolder, int).
         * At this point, you should update your adapter (e.g. remove the item) and call related Adapter#notify event.
         * @param viewHolder
         * @param direction
         */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //暂不处理
        }

        /**
         * 官方文档如下：返回true 当前tiem可以被拖动到目标位置后，直接”落“在target上，其他的上面的tiem跟着“落”，
         * 所以要重写这个方法，不然只是拖动的tiem在动，target tiem不动，静止的
         * Return true if the current ViewHolder can be dropped over the the target ViewHolder.
         * @param recyclerView
         * @param current
         * @param target
         * @return
         */
        @Override
        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
            return true;
        }

        /**
         * 官方文档说明如下：
         * Returns whether ItemTouchHelper should start a drag and drop operation if an item is long pressed.
         * 是否开启长按 拖动
         * @return
         */
        @Override
        public boolean isLongPressDragEnabled() {
            //return true后，可以实现长按拖动排序和拖动动画了
            return true;
        }
    };

    @Override
    public void startDragItem(RecyclerView.ViewHolder holder) {
        //谷歌官方文档如下：
        //开启拖动我们给他的holder，但是默认
        // 是长按拖动，直接代码调用拖动要先禁止长按拖动
        //Starts dragging the provided ViewHolder. By default,
        // ItemTouchHelper starts a drag when a View is long pressed.
        // You can disable that behavior by overriding isLongPressDragEnabled().
        itemTouchHelper.startDrag(holder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().removeAllStickyEvents();
//        EventBus.getDefault().unregister(SyFenleiActivity.class);
    }

}
