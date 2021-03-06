package com.example.agentzengyu.zy2048.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.example.agentzengyu.zy2048.R;
import com.example.agentzengyu.zy2048.adapter.AboutAdapter;

/**
 * 关于应用
 */
public class AboutActivity extends AppCompatActivity {
    private LinearLayoutManager manager;
    private AboutAdapter adapter;
    private Handler handler;
    private Runnable runnable;
    private RecyclerView recyclerView;
    private int index = 7;
    private boolean run = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initVariable();
        initView();
        startAutoShow();
    }

    @Override
    protected void onDestroy() {
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
        super.onDestroy();
        run = false;
        handler.removeCallbacks(runnable);
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new AboutAdapter(this);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (run) {
                    recyclerView.scrollToPosition(index);
                    index++;
                }
                startAutoShow();
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rvAbout);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(4);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        run = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        run = true;
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * recyclerView自动播放
     */
    private void startAutoShow() {
        handler.postDelayed(runnable, 1500);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,R.anim.activity_out);
    }
}
