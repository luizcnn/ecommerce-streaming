package consumers;

import dataprovider.dao.impl.OrderDaoPostgres;
import dataprovider.dao.impl.UserDaoPostgres;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.consumer.ServiceRunner;
import org.luizcnn.ecommerce.kafka.TopicEnum;
import org.luizcnn.ecommerce.utils.JsonUtils;
import service.OrderService;
import service.UserService;
import vo.OrderVO;

import java.util.List;

public class OrderRejectedConsumer extends DefaultConsumer {

  private final UserService userService;
  private final OrderService orderService;

  public OrderRejectedConsumer(UserService userService, OrderService orderService) {
    this.orderService = orderService;
    this.userService = userService;
  }

  public static void main(String[] args) {
    final var userDao = new UserDaoPostgres();
    final var orderDao = new OrderDaoPostgres();
    final var userService = new UserService(userDao);
    final var orderService = new OrderService(orderDao);
    new ServiceRunner(
            () -> new OrderRejectedConsumer(userService, orderService)
    ).start(1);
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    final var orderVO = JsonUtils.readValue(record.value(), OrderVO.class);
    final var userEntity = userService.findByEmail(orderVO.getEmail());

    orderService.saveOrder(orderVO.toOrderEntity(userEntity, true));
  }

  @Override
  public List<String> getTopics() {
    return List.of(TopicEnum.ECOMMERCE_ORDER_REJECTED.getTopic());
  }

  @Override
  public List<String> getDLQ() {
    return List.of(TopicEnum.ECOMMERCE_ORDER_REJECTED.getDLQTopic());
  }
}
