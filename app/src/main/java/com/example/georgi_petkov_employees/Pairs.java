package com.example.georgi_petkov_employees;

import java.io.Serializable;

public class Pairs implements Serializable, Comparable<Pairs> {
    public int EmpID1;
    public int EmpID2;
    public int ProjID;
    public int DaysWorked;

    public int getEmpID1() {
        return EmpID1;
    }

    public void setEmpID1(int empID1) {
        EmpID1 = empID1;
    }

    public int getEmpID2() {
        return EmpID2;
    }

    public void setEmpID2(int empID2) {
        EmpID2 = empID2;
    }

    public int getProjID() {
        return ProjID;
    }

    public void setProjID(int projID) {
        ProjID = projID;
    }

    public Integer getDaysWorked() {
        return DaysWorked;
    }

    public void setDaysWorked(int daysWorked) {
        DaysWorked = daysWorked;
    }

    public Pairs(int empID1, int empID2, int projID, int daysWorked) {
        EmpID1 = empID1;
        EmpID2 = empID2;
        ProjID = projID;
        DaysWorked = daysWorked;
    }

    @Override
    public int compareTo(Pairs o) {
        return this.getDaysWorked().compareTo(o.getDaysWorked());
    }
}
