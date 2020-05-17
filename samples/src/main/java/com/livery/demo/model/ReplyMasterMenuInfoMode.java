package com.livery.demo.model;

import android.os.Parcel;

import java.util.List;

public class ReplyMasterMenuInfoMode extends TBaseResponseMode {

    protected ReplyMasterMenuInfoMode(Parcel in) {
        super(in);
    }

    private List<MasterMenuInfoMode> masterMenuInfo;

    public void setMasterMenuInfo(List<MasterMenuInfoMode> masterMenuInfo) {
        this.masterMenuInfo = masterMenuInfo;
    }

    public List<MasterMenuInfoMode> getMasterMenuInfo() {
        return masterMenuInfo;
    }

    public static class MasterMenuInfoMode {
        private int collectionId;
        private int position;
        private String menuName;

        public void setCollectionId(int collectionId) {
            this.collectionId = collectionId;
        }

        public int getCollectionId() {
            return collectionId;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public String getMenuName() {
            return menuName;
        }

        @Override
        public String toString() {
            return "MasterMenuInfoMode{" +
                    "collectionId=" + collectionId +
                    ", position=" + position +
                    ", menuName='" + menuName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ReplyMasterMenuInfoMode{" +
                "masterMenuInfo=" + masterMenuInfo +
                '}';
    }
}