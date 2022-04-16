package entrypoint.consumers;

import core.service.OrderService;
import core.service.UserService;
import dataprovider.dao.impl.OrderDaoPostgres;
import dataprovider.dao.impl.UserDaoPostgres;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.luizcnn.ecommerce.consumer.DefaultConsumer;
import org.luizcnn.ecommerce.consumer.ServiceRunner;
import org.luizcnn.ecommerce.utils.JsonUtils;
import vo.OrderVO;

import java.util.List;

import static org.luizcnn.ecommerce.kafka.TopicEnum.ECOMMERCE_ORDER_APPROVED;

public class OrderApprovedConsumer extends DefaultConsumer {

  private final UserService userService;
  private final OrderService orderService;

  public OrderApprovedConsumer(UserService userService, OrderService orderService) {
    this.userService = userService;
    this.orderService = orderService;
  }

  public static void main(String[] args) {
    final var userDao = new UserDaoPostgres();
    final var orderDao = new OrderDaoPostgres();
    final var userService = new UserService(userDao);
    final var orderService = new OrderService(orderDao);
    new ServiceRunner(
            () -> new OrderApprovedConsumer(userService, orderService)
    ).start(1);
  }

  @Override
  public void consume(ConsumerRecord<String, byte[]> record) {
    final var orderVO = JsonUtils.readValue(record.value(), OrderVO.class);
    final var userEntity = userService.findByEmail(orderVO.getEmail());

    orderService.saveOrder(orderVO.toOrderEntity(userEntity, false));
  }

  @Override
  public List<String> getTopics() {
    return List.of(ECOMMERCE_ORDER_APPROVED.getTopic());
  }

  @Override
  public List<String> getDLQ() {
    return List.of(ECOMMERCE_ORDER_APPROVED.getDLQTopic());
  }
}
