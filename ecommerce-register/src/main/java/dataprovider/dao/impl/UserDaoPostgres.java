package dataprovider.dao.impl;

import dataprovider.dao.UserDao;
import dataprovider.models.User;
import utils.JPAUtils;

import javax.persistence.EntityManager;
import java.math.BigInteger;

public class UserDaoPostgres implements UserDao {

  private final EntityManager em = JPAUtils.getEntityManager();;

  @Override
  public Boolean existsUserBy(String email) {
    final String sql = "SELECT count(1) FROM USERS u WHERE lower(u.email) = lower(:email)";

    final var count = (BigInteger) em.createNativeQuery(sql)
            .setParameter("email", email)
            .getSingleResult();

    return count.compareTo(BigInteger.ZERO) > 0;
  }

  @Override
  public void save(User user) {
    em.getTransaction().begin();
    em.persist(user);
    em.getTransaction().commit();
  }

}
