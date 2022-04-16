package dataprovider.dao.impl;

import dataprovider.dao.OrderDao;
import dataprovider.models.OrderEntity;
import utils.JPAUtils;

import javax.persistence.EntityManager;

public class OrderDaoPostgres implements OrderDao {

  private final EntityManager em = JPAUtils.getEntityManager();

  @Override
  public void saveOrder(OrderEntity orderEntity) {
    em.getTransaction().begin();
    em.persist(orderEntity);
    em.getTransaction().commit();
  }
}
