package com.livery.demo.module.swipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sunsta.bear.layout.swipe.ExpandableAdapter;
import com.livery.demo.R;
import com.livery.demo.module.swipe.expanded.entity.Group;
import com.livery.demo.module.swipe.expanded.entity.GroupMember;

import java.util.List;

public class ExpandedAdapter extends ExpandableAdapter<ExpandableAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Group> mGroupList;

    public ExpandedAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setGroupList(List<Group> groupList) {
        this.mGroupList = groupList;
    }

    @Override
    public int parentItemCount() {
        return mGroupList == null ? 0 : mGroupList.size();
    }

    @Override
    public int childItemCount(int parentPosition) {
        List<GroupMember> memberList = mGroupList.get(parentPosition).getMemberList();
        return memberList == null ? 0 : memberList.size();
    }

    @Override
    public ViewHolder createParentHolder(@NonNull ViewGroup root, int viewType) {
        View view = mInflater.inflate(R.layout.item_expand_parent, root, false);
        return new ParentHolder(view, this);
    }

    @Override
    public ViewHolder createChildHolder(@NonNull ViewGroup root, int viewType) {
        View view = mInflater.inflate(R.layout.item_expand_child, root, false);
        return new ChildHolder(view, this);
    }

    @Override
    public void bindParentHolder(@NonNull ViewHolder holder, int position) {
        ((ParentHolder) holder).setData(mGroupList.get(position));
    }

    @Override
    public void bindChildHolder(@NonNull ViewHolder holder, int parentPosition, int position) {
        ((ChildHolder) holder).setData(mGroupList.get(parentPosition).getMemberList().get(position));
    }

    static class ParentHolder extends ViewHolder {

        TextView mTvTitle;
        ImageView mIvStatus;

        public ParentHolder(@NonNull View itemView, ExpandableAdapter adapter) {
            super(itemView, adapter);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mIvStatus = itemView.findViewById(R.id.iv_status);
        }

        public void setData(Group data) {
            mTvTitle.setText(data.getName());
            mIvStatus.setSelected(data.isExpanded());
        }
    }

    static class ChildHolder extends ViewHolder {

        TextView mTvTitle;

        public ChildHolder(@NonNull View itemView, ExpandableAdapter adapter) {
            super(itemView, adapter);
            mTvTitle = itemView.findViewById(R.id.tv_title);
        }

        public void setData(GroupMember data) {
            mTvTitle.setText(data.getName());
        }
    }
}