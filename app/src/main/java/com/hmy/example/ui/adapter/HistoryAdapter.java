package com.hmy.example.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hmy.example.R;
import com.hmy.example.bean.ApiInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<ApiInfo, BaseViewHolder> {

    public HistoryAdapter(@Nullable List<ApiInfo> data) {
        super(R.layout.item_history, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ApiInfo data) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        helper.setText(R.id.time, format.format(new Date(data.getTime())));
        helper.setText(R.id.content, data.toString());
    }
}
