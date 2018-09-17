package com.simplexorg.customviews.adapter;

public interface StatusMediator {
    interface Observer {
        int CLEAR_STATUS = 1;
        void update(int event);
    }

    /**
     * Asks the mediator if object should track the status.
     * @param observer the object asking.
     * @return true if object should track status, false otherwise.
     */
    boolean shouldTrackStatus(Observer observer);

    /**
     * Send request to mediator to signal that current object is
     * going to track status.
     *
     * @param observer the object requesting.
     */
    void requestToTrackStatus(Observer observer);
}
