package com.application.sample.selectcardviewprototype.app.singleton;

/**
 * Created by davide on 11/09/15.
 */
public class StatusSingleton {
    private static StatusSingleton mInstance;
    public enum StatusEnum { SELECTED, IDLE, NOT_SET };
    private StatusEnum mStatus = StatusEnum.NOT_SET;

    private StatusSingleton() {
    }

    /**
     *
     * @return
     */
    public static StatusSingleton getInstance() {
        return mInstance == null ? mInstance = new StatusSingleton() : mInstance;
    }

    /**
     *
     * @return
     */
    public StatusEnum getStatus() {
        return mStatus;
    }

    /**
     *
     * @param status
     */
    public void setStatus(StatusEnum status) {
        this.mStatus = status;
    }

}
