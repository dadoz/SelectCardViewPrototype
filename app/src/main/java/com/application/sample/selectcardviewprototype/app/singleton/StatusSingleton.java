package com.application.sample.selectcardviewprototype.app.singleton;

/**
 * Created by davide on 11/09/15.
 */
public class StatusSingleton {
    private static StatusSingleton mInstance;

    public enum StatusEnum { SELECTED, IDLE, NOT_SET };
    private StatusEnum mStatus = StatusEnum.NOT_SET;

    StatusSingleton() {
    }

    public static StatusSingleton getInstance() {
        return mInstance == null ? mInstance = new StatusSingleton() : mInstance;
    }

    public StatusEnum getStatus() {
        return mStatus;
    }

    public void setStatus(StatusEnum status) {
        this.mStatus = status;
    }

}
