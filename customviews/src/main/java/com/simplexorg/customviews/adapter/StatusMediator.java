package com.simplexorg.customviews.adapter;

public interface StatusMediator {
    interface Observer {
        int CLEAR_STATUS = 1;
        void update(int event);
    }

    /**
     * Asks the mediator if object should track the status.
     * @param obj the object asking.
     * @return true if object should track status, false otherwise.
     */
    boolean shouldTrackStatus(Observer obj);

    /**
     * Send request to mediator to signal that current object is
     * going to track status.
     *
     * @param obj the object requesting.
     */
    void requestToTrackStatus(Observer obj);
}
