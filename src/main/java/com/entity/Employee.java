package com.entity;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Employee implements Runnable {

    private long id;
    private Role role;
    private boolean onCall;
    private BlockingQueue<Call> callQueue;


    public Employee(long id, Role role ) {
        this.id=id;
        this.role = role;
        this.callQueue = new ArrayBlockingQueue<>(50);
    }

    public synchronized long getId() {
        return id;
    }

    public synchronized void setId(long id) {
        this.id = id;
    }

    public synchronized Role getRole() {
        return role;
    }

    public synchronized void setRole(Role role) {
        this.role = role;
    }

    public BlockingQueue<Call> getCallQueue() {
        return callQueue;
    }

    public void setCallQueue(BlockingQueue<Call> callQueue) {
        this.callQueue = callQueue;
    }

    public void addCallInQueue(Call call) {
        this.callQueue.add(call);
    }

    public synchronized boolean isOnCall() {
        return onCall;
    }

    public synchronized void setOnCall(boolean onCall) {
        this.onCall = onCall;
    }

    @Override
    public void run() {
        while (true) {
            if (!this.callQueue.isEmpty()) {
                Call call = null;
                try {
                    this.setOnCall(true);
                    call = this.callQueue.poll();
                    call.setStatus(CallStatus.ACTIVE);
                    System.out.println(role + " " + id + " handling call: " + call);
                    Thread.sleep(call.getDurationInSeconds() * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Error occurred ");
                } finally {
                    this.setOnCall(false);
                    if (call != null) {
                        call.setStatus(CallStatus.FINISHED);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", role=" + role +
                ", onCall=" + onCall +
                ", callQueue=" + callQueue +
                '}';
    }

}
