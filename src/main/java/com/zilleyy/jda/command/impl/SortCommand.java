package com.zilleyy.jda.command.impl;

import com.zilleyy.jda.command.Command;
import com.zilleyy.jda.command.CommandInformation;
import com.zilleyy.jda.command.ExecutionStatus;

/**
 * Author: Zilleyy
 * <br>
 * Date: 30/03/2021 @ 11:27 am AEST
 */
public class SortCommand extends Command {

    public SortCommand() {
        super("Sorts a list of numbers", "sort");
    }

    @Override
    public ExecutionStatus onCommand(CommandInformation information) {
        if(information.getArgs().length > 0) {
            sort(information.getArgs(int.class));
            System.out.println(information.getArgs(int.class));
            return ExecutionStatus.GENERIC_SUCCESS;
        } else {
            return ExecutionStatus.GENERIC_FAILURE;
        }
    }

    public void sort(Integer[] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j > 0; j--) {
                if(array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                }
            }
        }
    }

}
