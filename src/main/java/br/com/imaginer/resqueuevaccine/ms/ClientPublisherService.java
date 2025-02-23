package br.com.imaginer.resqueuevaccine.ms;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ClientPublisherService {

  private final StreamBridge streamBridge;

  public void publishNewClientEvent(Object message) {

    boolean sent = streamBridge.send("notifications", message);
    if (sent) {
      log.info("Message sent to 'notification-exchange' with routing key: notification");
    } else {
      log.error("Failed to send message to 'notification-exchange'");
    }
  }

}
