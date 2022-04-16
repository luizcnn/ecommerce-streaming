package dataprovider.dao.impl;

import dataprovider.dao.UserDao;
import dataprovider.models.UserEntity;
import utils.JPAUtils;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

public class UserDaoPostgres implements UserDao {

  private final EntityManager em = JPAUtils.getEntityManager();

  @Override
  public Boolean existsUserBy(String email) {
    final String sql = "SELECT count(1) FROM USERS u WHERE lower(u.email) = lower(:email)";

    final var count = (BigInteger) em.createNativeQuery(sql)
            .setParameter("email", email)
            .getSingleResult();

    return count.compareTo(BigInteger.ZERO) > 0;
  }

  @Override
  public void save(UserEntity userEntity) {
    em.getTransaction().begin();
    em.persist(userEntity);
    em.getTransaction().commit();
  }

  @Override
  public List<UserEntity> findAll() {
    final String sql = "SELECT * FROM USERS";

    return (List<UserEntity>) em.createNativeQuery(sql, UserEntity.class)
            .getResultList();
  }

  @Override
  public UserEntity findByEmail(String email) {
    final var sql = new StringBuilder();
    sql.append("SELECT * FROM USERS u \n");
    sql.append("WHERE u.email = :email");

    return (UserEntity) em.createNativeQuery(sql.toString(), UserEntity.class)
            .setParameter("email", email)
            .getSingleResult();
  }

}
