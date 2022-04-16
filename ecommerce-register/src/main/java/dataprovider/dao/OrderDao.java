package dataprovider.dao;

import dataprovider.models.OrderEntity;

public interface OrderDao {

  void saveOrder(OrderEntity orderEntity);

}
