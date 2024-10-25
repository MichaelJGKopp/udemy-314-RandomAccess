package dev.lpa;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Challenge {
  
  private static int recordCount;
  private static final Map<Integer, Long> indexMap = new TreeMap<>();
  
  public static void main(String[] args) {
    
    try (RandomAccessFile ra = new RandomAccessFile("employees.dat", "rw")) {
      
      // set file pointer to start
      ra.seek(0);
      
      // read record count
      recordCount = ra.readInt();
      
      // Load index to memory
      for (int i = 0; i < recordCount - 1; i++) {
        indexMap.put(ra.readInt(), ra.readLong());
      }
      
      // List employee IDs
      System.out.println("Employee Records(" + recordCount + ")");
      System.out.println("----------------------------------");
      indexMap.forEach((id, position) -> System.out.printf(
        "%s %-20d %s %-20d%n",
        "id =", id, "position =", position));
      
      // retrieve record from file using id
      int id = 21;
      Employee ralph = readRecordFromFile(ra,id);
      
      // print record
      // Set the locale to US
      Locale.setDefault(Locale.US);
      System.out.println(ralph);
      
      // update salary in file
      ralph.setSalary(ralph.getSalary() + 0.01);
      writeRecordToFile(ra, ralph);
      
      // read again from file and print
      ralph = readRecordFromFile(ra, ralph.getEmployeeId());
      System.out.println(ralph);
      
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static Employee readRecordFromFile(RandomAccessFile ra, int id) throws IOException {
  
    long filePointer = indexMap.get(id);
    ra.seek(filePointer);
    int idRetrieved = ra.readInt();
    double salaryRetrieved = ra.readDouble();
    String firstName = ra.readUTF();
    String lastName = ra.readUTF();
    
    return new Employee(idRetrieved, salaryRetrieved, firstName, lastName);
  }
  
  public static void writeRecordToFile(RandomAccessFile ra, Employee em) throws IOException {
    
    // retrieve record position in file by id
    long filePointer = indexMap.get(em.getEmployeeId());
    ra.seek(filePointer);
    
    // write record into file
    ra.writeInt(em.getEmployeeId());
    ra.writeDouble(em.getSalary());
    ra.writeUTF(em.getFirstName());
    ra.writeUTF(em.getLastName());
  }
}
