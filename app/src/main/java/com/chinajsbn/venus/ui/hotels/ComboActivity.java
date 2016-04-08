package com.chinajsbn.venus.ui.hotels;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinajsbn.venus.R;
import com.chinajsbn.venus.ui.base.ActivityFeature;
import com.chinajsbn.venus.ui.base.MBaseFragmentActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tool.widget.MasterTitleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@ActivityFeature(layout = R.layout.activity_combo)
public class ComboActivity extends MBaseFragmentActivity {

    @ViewInject(R.id.content_container)
    private LinearLayout container;

    @ViewInject(R.id.titleView)
    private MasterTitleView titleView;

    private String combo ;

    @Override
    public void initialize() {
        combo = getIntent().getStringExtra("combo");
        int size = 0;
        try {
            JSONObject obj = new JSONObject(combo);
            JSONArray arr = obj.getJSONArray("dishesList");

            titleView.setTitleText(obj.getString("name"));
            size = arr.length();
            for (int i = 0; i < size ; i++){
                TextView tv = new TextView(context);
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setPadding(10, 10, 10, 10);
                tv.setText(arr.getJSONObject(i).getString("name"));
                container.addView(tv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeydown() {
        animFinish();
        return false;
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view){
        animFinish();
    }
}
