package com.execute;

import com.entity.Call;
import com.entity.Employee;
import com.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CallDistributor implements Runnable {

    private ExecutorService executorService;
    private boolean active;
    private List<Employee> employees;
    public  BlockingQueue<Call> incomingCallsQueue;

    public CallDistributor(List<Employee> employees,  BlockingQueue<Call> incomingCallsQueue ) {
        this.employees = employees;
        this.incomingCallsQueue=incomingCallsQueue;
        this.executorService = Executors.newFixedThreadPool(20);
    }

    private boolean isActive() {
        return active;
    }

    private void setActive(boolean active) {
        this.active = active;
    }

    public synchronized Employee findCallHandler() {
        List<Employee> availableEmployees = employees.stream()
                .filter(e -> e.isOnCall() == false)
                .collect(Collectors.toList());

        Employee callHandlerEmployee = filterEmployees(availableEmployees, Role.STAFF)
                .orElse(filterEmployees(availableEmployees, Role.SUPERVISOR)
                        .orElse(filterEmployees(availableEmployees, Role.MANAGER)
                                .orElse(null)));
        return callHandlerEmployee;

    }

    private Optional<Employee> filterEmployees(List<Employee> availableEmployees, Role role) {
        return availableEmployees.stream()
                .filter(e -> e.getRole() == role)
                .findFirst();
    }

    @Override
    public void run() {
        while (isActive()) {
            if (this.incomingCallsQueue.isEmpty()) {
                setActive(false);
            } else {
                try {
                    Employee employee = findCallHandler();
                    if (employee != null) {
                        Call call = incomingCallsQueue.poll();

                        Thread.sleep(1000);

                        employee.addCallInQueue(call);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.stop();
    }

    public synchronized void start() {
        this.setActive(true);
        for (Employee employee : this.employees) {
            this.executorService.execute(employee);
        }
    }

    public synchronized void stop() {
        try {
            Thread.sleep(10 * 1000);
            executorService.shutdown();
        } catch (Exception e) {
            System.out.println("Exception =" + e);
        }
        System.exit(0);
    }
}
