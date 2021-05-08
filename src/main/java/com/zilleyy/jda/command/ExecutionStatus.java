package com.zilleyy.jda.command;

/**
 * Author: Zilleyy
 * <br>
 * Date: 26/03/2021 @ 10:59 am AEST
 */
public enum ExecutionStatus {

    GENERIC_FAILURE,
    GENERIC_SUCCESS,
    INCORRECT_ARGUMENTS(GENERIC_FAILURE),
    INVALID_USER(INCORRECT_ARGUMENTS),
    NO_PERMISSION(GENERIC_FAILURE);

    private boolean isBaseStatus;
    private ExecutionStatus parentStatus;

    ExecutionStatus(ExecutionStatus parentStatus) {
        this.isBaseStatus = false;
        this.parentStatus = parentStatus;
    }

    ExecutionStatus() {
        this.isBaseStatus = true;
        this.parentStatus = this;
    }

    public ExecutionStatus getParentStatus() {
        return this.parentStatus;
    }

    public ExecutionStatus getBaseStatus() {
        ExecutionStatus base = this;
        while(!base.isBaseStatus) {
            base = base.getParentStatus();
        }
        return base;
    }
}
