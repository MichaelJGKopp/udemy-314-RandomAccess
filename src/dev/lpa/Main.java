package dev.lpa;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
  
  public static final Map<Long, Long> indexedIds = new LinkedHashMap<>();
  private static int recordsInFile = 0;
  
  public static void main(String[] args) {
    
    String datFileName = "studentsRA";
    BuildStudentData.build(datFileName);
    readIndexIntoMap(datFileName);
    String data = readRecords(datFileName, 50, 51, 52);
    System.out.print(data);
  }
  
  public static void readIndexIntoMap(String datFileName) {
  
    try (RandomAccessFile ra = new RandomAccessFile(datFileName + ".dat","r")) {
      ra.seek(0);
      recordsInFile = ra.readInt();
      for (int i = 0; i < recordsInFile - 1; i++) {
        long id = ra.readLong();
        long pointer = ra.readLong();
        indexedIds.put(id, pointer);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public static String readRecords(String datFileName, long... ids) {
  
    StringBuilder data = new StringBuilder();
    
    try (RandomAccessFile ra = new RandomAccessFile(datFileName + ".dat","r")) {
      for (long id : ids) {
        long filePointer = indexedIds.get(id);
        ra.seek(filePointer);
        data.append(ra.readLine()).append(System.lineSeparator());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return data.toString();
  }
}
