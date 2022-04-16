import org.luizcnn.ecommerce.consumers.FraudConsumer;
import org.luizcnn.ecommerce.consumers.LogConsumer;

public class CoreApplication {

  public static void main(String[] args) {
    FraudConsumer.main(args);
    LogConsumer.main(args);
  }

}
