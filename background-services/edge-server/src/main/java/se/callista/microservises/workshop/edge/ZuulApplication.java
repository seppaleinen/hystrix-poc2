package se.callista.microservises.workshop.edge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.Filter;

@EnableResourceServer
@SpringBootApplication
@Controller
@EnableZuulProxy
public class ZuulApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ZuulApplication.class);

    @Bean
    public Filter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(5120);
        return filter;
    }

    static {
        // for localhost testing only
        LOG.warn("Will disable hostname check in SSL, only to be used during development!!!");
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZuulApplication.class).web(true).run(args);
    }
}
