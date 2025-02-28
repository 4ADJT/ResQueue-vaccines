package br.com.imaginer.resqueuevaccine.infra;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.imaginer.resqueuevaccine.models.Batch;
import br.com.imaginer.resqueuevaccine.models.BatchNotification;
import br.com.imaginer.resqueuevaccine.ms.ClientPublisherService;
import br.com.imaginer.resqueuevaccine.services.BatchServiceImpl;

@Service
public class BatchesAvailabilityCheckService {

  private static final String OUT_OF_STOCK_MESSAGE = "Batch is out of stock.";
  private static final String ABOUT_TO_EXPIRE_MESSAGE = "Batch is about to expire.";

  @Autowired
  private ClientPublisherService clientPublisherService;

  @Autowired
  private BatchServiceImpl batchService;

  @Scheduled(fixedRate = 300000)
  public void checkBatchesAvailability() {
    List<Batch> batches = batchService.findAllWithNullNotifyReason();
    
    for (Batch batch : batches) {
      if (isOutOfStock(batch)) {
        notifyClient(batch, OUT_OF_STOCK_MESSAGE);
      } else if (isAboutToExpire(batch)) {
        notifyClient(batch, ABOUT_TO_EXPIRE_MESSAGE);
      }
    }
  }

  private boolean isOutOfStock(Batch batch) {
    return batch.getQuantity() == 0;
  }

  private boolean isAboutToExpire(Batch batch) {
    return batch.getExpiryDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(LocalDate.now().plusDays(5));
  }

  private void notifyClient(Batch batch, String message) {
    batch.setNotifyReason(message);
    clientPublisherService.publishNewClientEvent(new BatchNotification(batch, message));
  }
}