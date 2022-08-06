package com.main;

import com.entity.Call;
import com.entity.Employee;
import com.execute.CallDistributor;
import com.execute.DataGenerator;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallMain {
    private static final int STAFF_AMOUNT_DEFAULT = 3;
    private static final int AMOUNT_OF_CALLS_DEFAULT = 9;
    private static final int CALL_DURATION_DEFAULT = 3;

    private static int staffAmount;
    private static int amountOfCalls;
    private static int callDuration;


    public static void main(String[] args) throws InterruptedException {
        parseArguments(args);

        List<Employee> employees = DataGenerator.generateEmployeeList(staffAmount);
        BlockingQueue<Call> incomingCallsQueue = DataGenerator.createCallObject(amountOfCalls, callDuration);

        CallDistributor callDistributor = new CallDistributor(employees, incomingCallsQueue);
        callDistributor.start();

        Thread.sleep(1000);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(callDistributor);
        Thread.sleep(1000);
    }

    private static void parseArguments(String[] args) {
        String errorMsg = null;
        if (args.length != 0) {
            try {
                String staffAmountValue = args[0].substring("staffAmount=".length());
                if (!isPositiveNumber(staffAmountValue)) {
                    errorMsg = "'staffAmount' value should be positive numeric value";
                    throw new Exception();
                }
                staffAmount = Integer.parseInt(staffAmountValue);

                String amountOfCallsValue = args[1].substring("amountOfCalls=".length());
                if (!isPositiveNumber(amountOfCallsValue)) {
                    errorMsg = "'amountOfCalls' should be positive numeric value and not zero";
                    throw new Exception();
                }
                amountOfCalls = Integer.parseInt(amountOfCallsValue);

                String callDurationValue = args[2].substring("callDuration=".length());
                if (!isPositiveNumber(callDurationValue)) {
                    errorMsg = "'callDuration' should be positive numeric value and not zero";
                    throw new Exception();
                }
                callDuration = Integer.parseInt(callDurationValue);

            } catch (Exception e) {
                System.out.println(errorMsg);
                System.out.println("Please add Program Arguments in Edit Configurations section like '\"staffAmount=4 amountOfCalls=15 callDuration=2\"");
                System.exit(0);
            }
        } else {
            staffAmount = STAFF_AMOUNT_DEFAULT;
            amountOfCalls = AMOUNT_OF_CALLS_DEFAULT;
            callDuration = CALL_DURATION_DEFAULT;
        }
    }


    private static boolean isPositiveNumber(String str) {
        return str.matches("^[1-9]\\d*$");
    }
}
