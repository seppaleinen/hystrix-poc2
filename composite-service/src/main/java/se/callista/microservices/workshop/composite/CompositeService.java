package se.callista.microservices.workshop.composite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by you today.
 */
@RestController
public class CompositeService {
    private static final Logger LOG = LoggerFactory.getLogger(CompositeService.class);

    private CompositeIntegration integration;

    @Autowired
    public void setIntegration(CompositeIntegration integration) {
        this.integration = integration;
    }

    @RequestMapping("/composite-resource/{id}")
    public ResponseEntity<String> getResource(@PathVariable String id) {
        LOG.info("Composite called with id={}", id);

        ResponseEntity<String> response1 = integration.callService1(id);
        ResponseEntity<String> response2 = integration.callService2(id);

        String responseBody =  response1.getBody() + "," + response2.getBody();
        LOG.debug("Composite response={}", responseBody);
        return new ResponseEntity<String>(responseBody, HttpStatus.OK);
    }
}