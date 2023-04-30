package com.example.georgi_petkov_employees;

import java.util.Date;

public class Employees implements Comparable<Employees> {
    private int EmpID;
    private int ProjectID;
    private Date dateFrom;
    private Date dateTo;

    public int getEmpID() {
        return EmpID;
    }

    public void setEmpID(int empID) {
        EmpID = empID;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }


    @Override
    public int compareTo(Employees o) {
        return this.getDateFrom().compareTo(o.getDateFrom());
    }
}
