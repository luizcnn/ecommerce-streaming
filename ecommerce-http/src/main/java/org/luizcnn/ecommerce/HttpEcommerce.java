package org.luizcnn.ecommerce;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.luizcnn.ecommerce.servlets.GenerateAllReportsServlet;
import org.luizcnn.ecommerce.servlets.NewOrderServlet;

public class HttpEcommerce {

  public static void main(String[] args) throws Exception {
    final var server = new Server(8080);

    final var context = new ServletContextHandler();
    context.setContextPath("/");
    context.addServlet(new ServletHolder(new NewOrderServlet()), "/new");
    context.addServlet(new ServletHolder(new GenerateAllReportsServlet()), "/admin/generate-reports");
    server.setHandler(context);

    server.start();
    server.join();
  }

}
