package se.callista.microservices.workshop.composite;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CompositeApplication {
 	@Value("${connectionTimeToLiveSecs:1}")
	int connectionTimeToLiveSecs;

	@Bean
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory factory =
				new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create()
						.setConnectionManager(new PoolingHttpClientConnectionManager(connectionTimeToLiveSecs, TimeUnit.SECONDS))
						.build());

		return new RestTemplate(factory);
	}
	public static void main(String[] args) {
		SpringApplication.run(CompositeApplication.class, args);
	}
}
