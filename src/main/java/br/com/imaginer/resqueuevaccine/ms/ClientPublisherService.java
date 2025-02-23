package br.com.imaginer.resqueuevaccine.ms;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ClientPublisherService {

  private final StreamBridge streamBridge;

  public void publishNewClientEvent(Object message) {

    Map<String, Object> json = new HashMap<>();

    json.put("message", message);

    boolean sent = streamBridge.send("notifications", json);
    if (sent) {
      log.info("Message sent to 'notifications-exchange' with routing key: notifications");
    } else {
      log.error("Failed to send message to 'notifications-exchange'");
    }
  }

}
