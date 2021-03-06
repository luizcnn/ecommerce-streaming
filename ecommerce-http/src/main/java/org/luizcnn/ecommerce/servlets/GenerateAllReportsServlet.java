package org.luizcnn.ecommerce.servlets;

import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;
import org.luizcnn.ecommerce.service.ReportService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GenerateAllReportsServlet extends HttpServlet {

  private final KafkaDispatcher<String, byte[]> batchDispatcher = new KafkaDispatcherImpl<>();

  @Override
  public void destroy() {
    super.destroy();
    batchDispatcher.close();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    sendReportToAllUsers();
    System.out.println("Sent reports to all users");
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().println("Reports generated and sent.");
  }

  private void sendReportToAllUsers() {
    final var userReportService = new ReportService(batchDispatcher);
    userReportService.sendReport();
  }

}
