package br.com.imaginer.resqueuevaccine.infra;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

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

  public BatchesAvailabilityCheckService(ClientPublisherService clientPublisherService,
                                         BatchServiceImpl batchService) {
    this.clientPublisherService = clientPublisherService;
    this.batchService = batchService;
  }

  @Scheduled(fixedRate = 300000)
  public void checkBatchesAvailability() {
    List<Batch> batches = batchService.findAllWithNullNotifyReason();
    batches.forEach(this::processBatch);
  }

  @Async("taskExecutor")
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
    LocalDate expiryDate = batch.getExpiryDate().toInstant()
        .atZone(ZoneId.systemDefault()).toLocalDate();

    return expiryDate.isBefore(LocalDate.now().plusDays(6));
  }

  private void notifyClient(Batch batch, String message) {
    batchService.updateBatchNotifyReason(batch, message);
    clientPublisherService.publishNewClientEvent(new BatchNotification(batch, message));
  }
}
