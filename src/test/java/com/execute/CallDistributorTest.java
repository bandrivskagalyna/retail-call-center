package com.execute;

import com.entity.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;


public class CallDistributorTest {

    private List<Employee> employees;
    private BlockingQueue<Call> incomingCallsQueue;

    @Before
    public void init() throws InterruptedException {
        employees = Arrays.asList(new Staff(1), new Staff(2), new Supervisor(3), new Manager(4));
        incomingCallsQueue = DataGenerator.createCallObject(8, 3);
    }

    @Test
    public void testFindCallHandlerStaff() throws InterruptedException {
        CallDistributor testDistributor = new CallDistributor(employees, incomingCallsQueue);
        Employee testEmployee = testDistributor.findCallHandler();

        Assert.assertNotNull(testEmployee);
        Assert.assertEquals(Role.STAFF, testEmployee.getRole());
    }

    @Test
    public void testFindCallHandlerSupervisor() {
        List<Employee> employeesBusyStaff = buildBusyEmployeesList(true, false, false);
        CallDistributor testDistributor = new CallDistributor(employeesBusyStaff, incomingCallsQueue);
        Employee testEmployee = testDistributor.findCallHandler();

        Assert.assertNotNull(testEmployee);
        Assert.assertEquals(Role.SUPERVISOR, testEmployee.getRole());
    }

    @Test
    public void testFindCallHandlerManager() {
        List<Employee> employeesBusyStaff = buildBusyEmployeesList(true, true, false);
        CallDistributor testDistributor = new CallDistributor(employeesBusyStaff, incomingCallsQueue);
        Employee testEmployee = testDistributor.findCallHandler();

        Assert.assertNotNull(testEmployee);
        Assert.assertEquals(Role.MANAGER, testEmployee.getRole());
    }

    @Test
    public void testFindCallHandlerAllBusy() {
        List<Employee> employeesBusyStaff = buildBusyEmployeesList(true, true, true);
        CallDistributor testDistributor = new CallDistributor(employeesBusyStaff, incomingCallsQueue);
        Employee testEmployee = testDistributor.findCallHandler();

        Assert.assertNull(testEmployee);
    }

    private List<Employee> buildBusyEmployeesList(boolean isStaffBusy, boolean isSupervisorBusy, boolean isManagerBusy) {
        Employee e1 = new Staff(1);
        Employee e2 = new Staff(2);
        Employee e3 = new Staff(3);
        Employee e4 = new Supervisor(4);
        Employee e5 = new Manager(5);

        if (isStaffBusy) {
            e1.setOnCall(true);
            e2.setOnCall(true);
            e3.setOnCall(true);
        }
        if (isSupervisorBusy) {
            e4.setOnCall(true);
        }
        if (isManagerBusy) {
            e5.setOnCall(true);
        }

        return Arrays.asList(e1, e2, e3, e4, e5);
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyData() {
        CallDistributor testDistributor = new CallDistributor(null, null);
        testDistributor.findCallHandler();
    }

}
