/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.keithpaterson.veneer;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author keith.paterson
 */
public class Employee {
    
    private final SimpleStringProperty name;
    private final SimpleStringProperty department;

    Employee(String name, String department) {
        this.name = new SimpleStringProperty(name);
        this.department = new SimpleStringProperty(department);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String fName) {
        name.set(fName);
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String fName) {
        department.set(fName);
    }
    
}
