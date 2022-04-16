package dataprovider.dao.impl;

import dataprovider.dao.OrderDao;
import dataprovider.models.OrderEntity;
import utils.JPAUtils;

import javax.persistence.EntityManager;
import java.util.UUID;

public class OrderDaoPostgres implements OrderDao {

  private final EntityManager em = JPAUtils.getEntityManager();

  @Override
  public void saveOrder(OrderEntity orderEntity) {
    em.getTransaction().begin();
    em.persist(orderEntity);
    em.getTransaction().commit();
  }

  @Override
  public Boolean existsById(UUID orderId) {
    final String sql = "SELECT count(1) FROM ORDERS o WHERE o.order_id = :orderId";

    final var count = (Number) em.createNativeQuery(sql)
            .setParameter("orderId", orderId)
            .getSingleResult();

    return count.intValue() > 0;
  }
}
