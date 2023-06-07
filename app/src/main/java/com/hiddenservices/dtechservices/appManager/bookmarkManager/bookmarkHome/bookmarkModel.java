package com.hiddenservices.dtechservices.appManager.bookmarkManager.bookmarkHome;

import com.hiddenservices.dtechservices.dataManager.models.bookmarkRowModel;

import java.util.ArrayList;

class bookmarkModel {
    /*Private Variables*/

    private ArrayList<bookmarkRowModel> mModelList = new ArrayList<>();

    /*Helper Methods*/

    void setList(ArrayList<bookmarkRowModel> model) {
        mModelList = model;
    }

    private void removeFromList(int index) {
        mModelList.remove(index);
    }

    public void onManualClear(int index) {
        removeFromList(index);
    }

    public void clearList() {
        mModelList.clear();
    }

    ArrayList<bookmarkRowModel> getList() {
        return mModelList;
    }

}