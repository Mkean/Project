package com.bwie.project.cart.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.project.R;
import com.bwie.project.activity.GoodsActivity;
import com.bwie.project.cart.bean.CartBean;
import com.bwie.project.cart.bean.CountPriceBean;
import com.bwie.project.presenter.DelPresenter;
import com.bwie.project.view.DelView;

import java.util.List;

import bean.DelBean;

/**
 * 作者：王庆
 * 时间：2017/12/16
 */

public class MyExpanAdapter extends BaseExpandableListAdapter implements DelView {

    private List<CartBean.DataBean> listGroup;
    private List<List<CartBean.DataBean.ListBean>> listChilds;
    private Context context;
    private Handler handler;
    private String uid;
    private final DelPresenter delPresenter;


    public MyExpanAdapter(List<CartBean.DataBean> listGroup, List<List<CartBean.DataBean.ListBean>> listChilds, Context context, Handler handler, String uid) {
        this.listGroup = listGroup;
        this.listChilds = listChilds;
        this.context = context;
        this.handler = handler;
        this.uid = uid;
        delPresenter = new DelPresenter(this);
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChilds.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChilds.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        final GroupHolder holder;
        if (view == null) {
            holder = new GroupHolder();
            view = View.inflate(context, R.layout.group_item, null);
            holder.check_group = view.findViewById(R.id.check_group);
            holder.text_group = view.findViewById(R.id.text_group);
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }
        final CartBean.DataBean dataBean = listGroup.get(groupPosition);
        holder.text_group.setText(dataBean.getSellerName());
        holder.check_group.setChecked(dataBean.isGroupChecked());


        holder.check_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("TAG", "ASS");
                dataBean.setGroupChecked(!dataBean.isGroupChecked());
                //根据当前一级列表的状态，改变改组里面二级列表的状态
                changeChildState(groupPosition, dataBean.isGroupChecked());
                //判断一级商家是否全部选中，来决定下面全选是否选中
                changeAllState(isAllGroupChecked());
                //发送数量和价格
                sendPriceAndCount();
                //刷新适配器
                notifyDataSetChanged();
            }
        });

        return view;
    }

    /*
    * 设置二级的状态
    * */
    private void changeChildState(int groupPosition, boolean groupChecked) {
        List<CartBean.DataBean.ListBean> listBeen = listChilds.get(groupPosition);
        for (int i = 0; i < listBeen.size(); i++) {
            listBeen.get(i).setSelected(groupChecked ? "1" : "0");
        }

    }

    /*
    * 设置下面的全选的状态
    * */
    private boolean isAllGroupChecked() {
        for (int i = 0; i < listGroup.size(); i++) {
            if (!listGroup.get(i).isGroupChecked()) {
                return false;
            }
        }
        return true;
    }

    /*
    * 根据一级列表是否全部选中，判断外面是否选中
    * */
    private void changeAllState(boolean allGroupChecked) {
        Message obtain = Message.obtain();
        obtain.obj = allGroupChecked;
        obtain.what = 0;
        handler.sendMessage(obtain);
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ChildHolder holder;
        if (view == null) {
            holder = new ChildHolder();
            view = View.inflate(context, R.layout.child_item, null);
            holder.check_child = view.findViewById(R.id.check_child);
            holder.image_good = view.findViewById(R.id.image_good);
            holder.text_title = view.findViewById(R.id.text_title);
            holder.text_price = view.findViewById(R.id.text_price);
            holder.text_jian = view.findViewById(R.id.text_jian);
            holder.text_num = view.findViewById(R.id.text_num);
            holder.text_add = view.findViewById(R.id.text_add);
            holder.relative = view.findViewById(R.id.relative);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        final CartBean.DataBean.ListBean listBean = listChilds.get(groupPosition).get(childPosition);
        holder.check_child.setChecked(listBean.getSelected().equals("0") ? false : true);
        holder.text_title.setText(listBean.getTitle());
        Glide.with(context).load(listBean.getImages().split("!")[0]).into(holder.image_good);
        holder.text_price.setText(listBean.getPrice() + "");
        holder.text_num.setText(listBean.getNum() + "");
        holder.check_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean.setSelected(listBean.getSelected().equals("0") ? "1" : "0");
                //再次重新发送数量及价格
                sendPriceAndCount();
                //判断子条目是否选中
                if (listBean.getSelected().equals("1")) {
                    //判断二级列表是否全部选中
                    if (isAllChildChecked(groupPosition)) {
                        //根据二级列表是否全选中，设置当前组一级列表
                        changeGroupState(groupPosition, true);
                        //根据二级列表是否全选中，设置全选状态
                        changeAllState(isAllGroupChecked());
                    }
                } else {
                    //如果有一个没选中，就需要改变当前组的状态
                    changeGroupState(groupPosition, false);
                    //设置全选
                    changeAllState(isAllGroupChecked());
                }
                notifyDataSetChanged();
            }
        });
        holder.text_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listBean.getNum() == 1) {
                    return;
                }
                listBean.setNum(listBean.getNum() - 1);

                if (listBean.getSelected().equals("1")) {
                    sendPriceAndCount();
                }
                notifyDataSetChanged();
            }
        });

        holder.text_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listBean.setNum(listBean.getNum() + 1);

                if (listBean.getSelected().equals("1")) {
                    sendPriceAndCount();
                }
                notifyDataSetChanged();
            }
        });
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsActivity.class);
                intent.putExtra("pid", listBean.getPid());
                intent.putExtra("uid", uid);
                context.startActivity(intent);
            }
        });

        return view;
    }

    private void changeGroupState(int groupPosition, boolean b) {
        listGroup.get(groupPosition).setGroupChecked(b);

    }

    private boolean isAllChildChecked(int groupPosition) {
        List<CartBean.DataBean.ListBean> listBeen = listChilds.get(groupPosition);
        for (int i = 0; i < listBeen.size(); i++) {
            CartBean.DataBean.ListBean listBean = listBeen.get(i);
            if (listBean.getSelected().equals("0")) {
                return false;
            }
        }
        return true;
    }

    private onDelListener listener;

    public void setListener(onDelListener listener) {
        this.listener = listener;
    }

    public interface onDelListener {
        void onDelListener();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /*
   * 计算数量及价格
   * */
    public void sendPriceAndCount() {
        double price = 0;
        int count = 0;
        for (int i = 0; i < listGroup.size(); i++) {
            List<CartBean.DataBean.ListBean> list = listGroup.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                CartBean.DataBean.ListBean listBean = list.get(j);
                if (listBean.getSelected().equals("1")) {
                    count += listBean.getNum();
                    price += listBean.getNum() * listBean.getPrice();
                }
            }
        }
//        for (int i = 0; i < listChilds.size(); i++) {
//            List<CartBean.DataBean.ListBean> listBeen = listChilds.get(i);
//            for (int j = 0; j < listBeen.size(); j++) {
//                CartBean.DataBean.ListBean listBean = listBeen.get(j);
//                if (listBean.getSelected() == 1) {
//                    count += listBean.getNum();
//                    price += listBean.getNum() * listBean.getPrice();
//                }
//            }
//        }

        CountPriceBean countPriceBean = new CountPriceBean(price, count);
        Message message = Message.obtain();
        message.what = 1;
        message.obj = countPriceBean;
        handler.sendMessage(message);
    }

    /*
    * 设置是否全选
    * */

    public void setIfCheckAll(boolean b) {
        for (int i = 0; i < listGroup.size(); i++) {
            listGroup.get(i).setGroupChecked(b);
            List<CartBean.DataBean.ListBean> list = listGroup.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                list.get(j).setSelected(b ? "1" : "0");
            }
        }
        sendPriceAndCount();
        notifyDataSetChanged();
    }

    public void delCart() {
        boolean num = false;
        for (int i = 0; i < listGroup.size(); i++) {
            List<CartBean.DataBean.ListBean> list = listGroup.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getSelected().equals("1")) {
                    delPresenter.del(uid, list.get(j).getPid());
                    num = true;
                }
            }
        }
        if (!num) {
            Toast.makeText(context, "你还没有选中商品", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onDelSuccess(DelBean delBean) {
        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
        if (listener != null) {
            listener.onDelListener();
        }
    }

    @Override
    public void onDelFailed(Throwable e) {
        Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
        Log.e("TAG", e.getMessage().toString());
    }

    class GroupHolder {
        CheckBox check_group;
        TextView text_group;
    }

    class ChildHolder {
        CheckBox check_child;
        ImageView image_good;
        TextView text_title;
        TextView text_price;
        TextView text_jian;
        TextView text_num;
        TextView text_add;
        RelativeLayout relative;
    }
}
