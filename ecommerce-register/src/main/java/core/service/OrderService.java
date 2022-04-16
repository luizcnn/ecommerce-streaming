package core.service;

import dataprovider.dao.OrderDao;
import dataprovider.models.OrderEntity;

public class OrderService {

  private final OrderDao orderDao;

  public OrderService(OrderDao orderDao) {
    this.orderDao = orderDao;
  }

  public void saveOrder(OrderEntity order) {
    orderDao.saveOrder(order);
  }

}
