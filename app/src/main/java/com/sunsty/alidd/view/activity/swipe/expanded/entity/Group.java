package com.sunsty.alidd.view.activity.swipe.expanded.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Group implements Parcelable {

    private String name;
    private List<GroupMember> mMemberList;
    private boolean isExpanded;

    public Group() {
    }

    protected Group(Parcel in) {
        name = in.readString();
        mMemberList = in.createTypedArrayList(GroupMember.CREATOR);
        isExpanded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(mMemberList);
        dest.writeByte((byte)(isExpanded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupMember> getMemberList() {
        return mMemberList;
    }

    public void setMemberList(List<GroupMember> memberList) {
        mMemberList = memberList;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}