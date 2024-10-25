package dev.lpa;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

// already have my owm Employee class, so call the record EmployeeS
record EmployeeS(int employeeId, String firstName, String lastName, double salary) { };

public class ChallengeSolution {
  
  private static final Map<Integer, Long> indexedIds = new HashMap<>();
  
  static {
    int recordsInFile = 0;
    try (RandomAccessFile ra = new RandomAccessFile("employees.dat", "r")) {
      
      recordsInFile = ra.readInt();
      System.out.println(recordsInFile + " records in file");
      for (int i = 0; i < recordsInFile; i++) {
        indexedIds.put(ra.readInt(), ra.readLong());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
  
    try (RandomAccessFile ra = new RandomAccessFile("employees.dat", "rw")) {
      Scanner scanner = new Scanner(System.in);
      List<Integer> ids = new ArrayList<>(indexedIds.keySet());
      Collections.sort(ids);
      
      while (true) {
        System.out.println(ids);
        System.out.println("Enter an Employee Id or 0 to quit");
        if (!scanner.hasNext()) break;
        int employeeId = Integer.parseInt(scanner.nextLine());
        if (employeeId < 1) {
          break;
        }
        if (!ids.contains(employeeId)) {
          continue;
        }
        EmployeeS e = readRecord(ra, employeeId);
        System.out.println("Enter new salary, nothing if no change:");
        try {
          double salary = Double.parseDouble(scanner.nextLine());
          ra.seek(indexedIds.get(employeeId) + 4);
          ra.writeDouble(salary);
          readRecord(ra, employeeId);
        } catch (NumberFormatException ex) {
          System.out.println("Invalid double format for input.");
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private static EmployeeS readRecord(RandomAccessFile ra, int employeeId) throws IOException {
    ra.seek(indexedIds.get(employeeId));
    int id = ra.readInt();
    double salary = ra.readDouble();
    String first = ra.readUTF();
    String last = ra.readUTF();
    
    EmployeeS e = new EmployeeS(id, first, last, salary);
    System.out.println(e);
    return e;
  }
}
