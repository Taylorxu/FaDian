package com.wisesignsoft.OperationManagement.manager;

/**
 * Created by ycs on 2017/1/18.
 */
public class PositionManager {
    private String position;
    private static PositionManager instance;

    public synchronized static PositionManager getInstance() {
        if (instance == null) {
            instance = new PositionManager();
        }
        return instance;
    }

    private PositionManager() {
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
