package com.dharmesh.bizzbullindiaproject.model.adaptermodel

import com.dharmesh.bizzbullindiaproject.model.adaptermodel.Employee
import java.util.ArrayList

class ManageLeads(var location: String, employees: List<Employee>) {
    var employees: List<Employee> = ArrayList()

    init {
        this.employees = employees
    }
}