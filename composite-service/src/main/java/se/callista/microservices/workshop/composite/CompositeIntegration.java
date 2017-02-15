package se.callista.microservices.workshop.composite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
/**
 * Created by you today.
 */
@Component
public class CompositeIntegration {
    private static final Logger LOG = LoggerFactory.getLogger(CompositeIntegration.class);

    @Value("${host1}")
    String host1;

    @Value("${port1}")
    int port1;

    @Value("${host2}")
    String host2;

    @Value("${port2}")
    int port2;

    @Value("${maxRetries:2}")
    int maxRetries;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> callService1(String id) {
        return callService(getServiceUrl(host1, port1) + "/resource-1/" + id);
    }

    public ResponseEntity<String> callService2(String id) {
        return callService(getServiceUrl(host2, port2) + "/resource-2/" + id);
    }

    private String getServiceUrl(String host, int port) {
        return "http://" + host + ":" + port;
    }

    private ResponseEntity<String> callService(String url) {
        LOG.info("Calling url={}", url);
        ResponseEntity<String> response = null;
        Exception lastError = null;

        int retries = 0;
        while (response == null && retries < maxRetries) {
            try {
                LOG.debug("Attempt #: " + retries);
                response = restTemplate.getForEntity(url, String.class);

            } catch (Exception e) {
                LOG.warn("Failed to get response: " + e.getMessage());
                retries++;
            }
        }
        if (response == null) {
            throw new RuntimeException(lastError);
        }

        LOG.debug("Response={}", response.getBody());
        return response;
    }
}