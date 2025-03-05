package br.com.imaginer.resqueuevaccine.infra;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.imaginer.resqueuevaccine.models.Batch;
import br.com.imaginer.resqueuevaccine.models.BatchNotification;
import br.com.imaginer.resqueuevaccine.ms.ClientPublisherService;
import br.com.imaginer.resqueuevaccine.services.BatchServiceImpl;

@Component
public class BatchesAvailabilityCheckService {
  private static final String OUT_OF_STOCK_MESSAGE = "Batch is out of stock.";
  private static final String ABOUT_TO_EXPIRE_MESSAGE = "Batch is about to expire.";

  private final ClientPublisherService clientPublisherService;
  private final BatchServiceImpl batchService;

  private final ApplicationContext applicationContext;

  public BatchesAvailabilityCheckService(ClientPublisherService clientPublisherService,
                                         BatchServiceImpl batchService,
                                         ApplicationContext applicationContext) {
    this.clientPublisherService = clientPublisherService;
    this.batchService = batchService;
    this.applicationContext = applicationContext;
  }

  @Scheduled(fixedRate = 300000)
  public void checkBatchesAvailability() {
    List<Batch> batches = batchService.findAllWithNullNotifyReason();

    for (Batch batch : batches) {
      getSelf().processBatch(batch);
    }
  }

  @Async
  public void processBatch(Batch batch) {
    if (isOutOfStock(batch)) {
      notifyClient(batch, OUT_OF_STOCK_MESSAGE);
    } else if (isAboutToExpire(batch)) {
      notifyClient(batch, ABOUT_TO_EXPIRE_MESSAGE);
    }
  }

  private boolean isOutOfStock(Batch batch) {
    return batch.getAvailableQuantity() == 0;
  }

  private boolean isAboutToExpire(Batch batch) {
    return batch.getExpiryDate().toInstant()
        .atZone(ZoneId.systemDefault()).toLocalDate()
        .isBefore(LocalDate.now().plusDays(6));
  }

  private void notifyClient(Batch batch, String message) {
    batchService.updateBatchNotifyReason(batch, message);
    clientPublisherService.publishNewClientEvent(new BatchNotification(batch, message));
  }

  private BatchesAvailabilityCheckService getSelf() {
    return applicationContext.getBean(BatchesAvailabilityCheckService.class);
  }
}
