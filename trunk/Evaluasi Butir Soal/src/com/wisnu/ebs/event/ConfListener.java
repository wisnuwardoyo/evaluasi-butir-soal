package com.wisnu.ebs.event;

import com.wisnu.ebs.model.MainModel;


public interface ConfListener {
    public void onLoad(MainModel model);
    public void newFile();
}
