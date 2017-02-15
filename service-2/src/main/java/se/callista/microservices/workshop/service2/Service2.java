package se.callista.microservices.workshop.service2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by you today.
 */
@RestController
public class Service2 {

    private static final Logger LOG = LoggerFactory.getLogger(Service2.class);

    private String host = findMyHostname();

    @RequestMapping("/resource-2/{id}")
    public ResponseEntity<String> getResource(@PathVariable String id) {
        LOG.info("Service2 called with id={}", id);
        String response = "Service2@" + host + ":" + id;
        LOG.debug("Service2 response={}", response);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    private String findMyHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown host name";
        }
    }
}