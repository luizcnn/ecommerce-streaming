import consumers.BatchSendReportConsumer;
import consumers.CreateUsersConsumer;
import consumers.OrderApprovedConsumer;
import consumers.OrderRejectedConsumer;

public class CoreApplication {

  public static void main(String[] args) {
    CreateUsersConsumer.main(args);
    BatchSendReportConsumer.main(args);
    OrderApprovedConsumer.main(args);
    OrderRejectedConsumer.main(args);
  }

}
