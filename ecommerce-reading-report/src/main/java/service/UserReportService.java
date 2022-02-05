package service;

import models.User;
import utils.IO;

import java.io.File;
import java.nio.file.Path;

import static java.lang.String.format;

public class UserReportService {

  private static final Path SOURCE = Path.of("src", "main", "resources");

  public void process(User user) {
    final var target = new File(user.getReportPath());
    IO.copyTo(SOURCE, target);
    IO.append(target, format("Created for: %s", user.getId()));

    System.out.println(format("File created: %s", target.getAbsolutePath()));
  }

}
