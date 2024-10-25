package dev.lpa;

import java.util.StringJoiner;

public class Employee {
  private final int employeeId;
  private double salary;
  private String firstName;
  private String lastName;
  
  public Employee(int employeeId, double salary, String firstName, String lastName) {
    this.employeeId = employeeId;
    this.salary = salary;
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  public int getEmployeeId() {
    return employeeId;
  }
  
  public double getSalary() {
    return salary;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setSalary(double salary) {
    this.salary = salary;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  @Override
  public String toString() {
    return new StringJoiner(", ", "{", "}")
             .add("\"id\":" + employeeId)
             .add("\"salary\":" + "%.2f".formatted(salary))
             .add("\"firstName\":\"" + firstName + "\"")
             .add("\"lastName\":\"" + lastName + "\"")
             .toString();
  }
}
