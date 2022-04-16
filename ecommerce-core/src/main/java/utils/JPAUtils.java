package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JPAUtils {

  private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("ecommerce");

  public static EntityManager getEntityManager() {
    return FACTORY.createEntityManager();
  }

}
