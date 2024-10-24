package dev.lpa;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildStudentData {

  public static void build(String datFileName) {
    
    Path studentJson = Path.of("students.json");
    String dataFile = datFileName + ".dat";
    Map<Long, Long> indexedIds = new LinkedHashMap<>();
    
    try {
      Files.deleteIfExists(Path.of(dataFile));
      String data = Files.readString(studentJson);
      data = data.replaceFirst("^(\\[)", "")
               .replaceFirst("(\\])$", "");
      var records = data.split(System.lineSeparator());
      System.out.println("# of records = " + records.length);
      
      long startingPos = 4 + (16L * records.length); // 1 int + 2 long times #records
      
      Pattern idPattern = Pattern.compile("studentId\":(\\d+)");
      try (RandomAccessFile ra= new RandomAccessFile(dataFile, "rw")) { // read and write
        ra.seek(startingPos);
        for (String record : records) {
          Matcher m = idPattern.matcher(record);
          if (m.find()) {
            long id = Long.parseLong(m.group(1));
            indexedIds.put(id, ra.getFilePointer());
            ra.writeUTF(record);
          }
        }
        writeIndex(ra, indexedIds);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private static void writeIndex(RandomAccessFile ra, Map<Long, Long> indexMap) {
    try {
      ra.seek(0);
      ra.writeInt(indexMap.size());
      for (var entry : indexMap.entrySet()) {
        ra.writeLong(entry.getKey());
        ra.writeLong(entry.getValue());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}