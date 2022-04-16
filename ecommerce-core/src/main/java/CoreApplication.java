import entrypoint.consumers.BatchSendReportConsumer;
import entrypoint.consumers.CreateUsersConsumer;
import entrypoint.consumers.OrderApprovedConsumer;
import entrypoint.consumers.OrderRejectedConsumer;

public class CoreApplication {

  public static void main(String[] args) {
    CreateUsersConsumer.main(args);
    BatchSendReportConsumer.main(args);
    OrderApprovedConsumer.main(args);
    OrderRejectedConsumer.main(args);
  }

}
