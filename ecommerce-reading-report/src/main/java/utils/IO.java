package utils;

import exceptions.GerenateReportException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public final class IO {

  public static void copyTo(Path source, File target) {
    target.getParentFile().mkdirs();

    try {
      Files.copy(source, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new GerenateReportException(e.getMessage(), e);
    }
  }

  public static void append(File target, String content) {
    try {
      Files.write(target.toPath(), content.getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new GerenateReportException(e.getMessage(), e);
    }
  }
}
