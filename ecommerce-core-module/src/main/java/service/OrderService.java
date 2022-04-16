package service;

import dataprovider.dao.OrderDao;
import dataprovider.models.OrderEntity;

import java.util.UUID;

public class OrderService {

  private final OrderDao orderDao;

  public OrderService(OrderDao orderDao) {
    this.orderDao = orderDao;
  }

  public void saveOrder(OrderEntity order) {
    orderDao.saveOrder(order);
  }

  public Boolean existsById(UUID orderId) {
    return orderDao.existsById(orderId);
  }

}
