package dev.lpa;

import java.util.StringJoiner;

public class Employee {
  private final int id;
  private double salary;
  private String firstName;
  private String lastName;
  
  public Employee(int id, double salary, String firstName, String lastName) {
    this.id = id;
    this.salary = salary;
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  public int getId() {
    return id;
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
             .add("\"id\":" + id)
             .add("\"salary\":" + "%.2f".formatted(salary))
             .add("\"firstName\":\"" + firstName + "\"")
             .add("\"lastName\":\"" + lastName + "\"")
             .toString();
  }
}
