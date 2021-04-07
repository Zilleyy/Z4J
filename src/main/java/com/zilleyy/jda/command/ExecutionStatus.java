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
    NO_PERMISSION(GENERIC_FAILURE);

    private ExecutionStatus baseStatus;

    ExecutionStatus(ExecutionStatus baseStatus) {
        this.baseStatus = baseStatus;
    }

    ExecutionStatus() {
        this.baseStatus = this;
    }

    public ExecutionStatus getBaseStatus() {
        return this.baseStatus;
    }
}
