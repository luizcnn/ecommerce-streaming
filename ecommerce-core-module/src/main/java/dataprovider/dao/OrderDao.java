package dataprovider.dao;

import dataprovider.models.OrderEntity;

import java.util.UUID;

public interface OrderDao {

  void saveOrder(OrderEntity orderEntity);

  Boolean existsById(UUID orderId);
}
