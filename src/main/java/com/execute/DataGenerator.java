package com.execute;

import com.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DataGenerator {

    private static List<Employee> employees = new ArrayList<>();
    private static BlockingQueue<Call> incomingCallsQueue = new ArrayBlockingQueue<>(50);


    public static List<Employee> generateEmployeeList(int numberOfEmployeeStaff) {
        Employee supervisor = new Supervisor(numberOfEmployeeStaff + 1);
        Employee manager = new Manager(numberOfEmployeeStaff + 2);

        addEmployee(supervisor);
        addEmployee(manager);

//TODO check more than 1 employee staff
        for (int i = 1; i <= numberOfEmployeeStaff; i++) {
            Employee employee = new Staff(i);
            addEmployee(employee);
        }
        return employees;
    }

    private static void addEmployee(Employee employee) {
        employees.add(employee);
    }


    public static BlockingQueue<Call> createCallObject(int amountOfCall, int callDuration) throws InterruptedException {
        if (amountOfCall != 0) {
            for (int i = 1; i <= amountOfCall; i++) {
                Call call = new Call(i, CallStatus.NEW, callDuration);
                putCallInQueue(call);
            }
        }
        return incomingCallsQueue;
    }

    private static void putCallInQueue(Call call) throws InterruptedException {
        incomingCallsQueue.put(call);
    }
}
