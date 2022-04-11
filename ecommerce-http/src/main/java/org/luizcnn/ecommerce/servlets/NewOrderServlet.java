package org.luizcnn.ecommerce.servlets;

import org.luizcnn.ecommerce.dispatcher.KafkaDispatcher;
import org.luizcnn.ecommerce.dispatcher.impl.KafkaDispatcherImpl;
import org.luizcnn.ecommerce.models.Order;
import org.luizcnn.ecommerce.service.NewOrderService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class NewOrderServlet extends HttpServlet {

  private final KafkaDispatcher<String, byte[]> orderDispatcher = new KafkaDispatcherImpl<>();

  @Override
  public void destroy() {
    super.destroy();
    orderDispatcher.close();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    final var order = getOrder(req);
    process(order);

    System.out.println("New order sent successfully.");
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().println("New order " + order.getOrderId() +" sent successfully.");
  }

  private Order getOrder(HttpServletRequest req) {
    final var email = req.getParameter("email");
    final var amount = req.getParameter("amount");
    final var orderId = UUID.randomUUID().toString();

    return new Order(email, orderId, new BigDecimal(amount));
  }

  private void process(Order order) {
    final var newOrderService = new NewOrderService(orderDispatcher);
    newOrderService.sendToFraudAnalisys(order);
  }
}
