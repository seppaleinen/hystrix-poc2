package se.callista.microservises.workshop.oauth;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

// Instruct embedded Tomcat to run on a random free port and skip talking to the Config Server and the Bus
// Regarding settings of java.security.egd, see http://wiki.apache.org/tomcat/HowTo/FasterStartUp#Entropy_Source
@RunWith(SpringRunner.class)
@SpringBootTest(classes=AuthserverApplication.class, webEnvironment=RANDOM_PORT, properties = {
	"spring.cloud.bus.enabled=false",
	"spring.cloud.config.enabled=false",
  "spring.sleuth.stream.enabled=false",
  "java.security.egd=file:/dev/./urandom"
})
public class ApplicationTests {

	@Value("${local.server.port}")
	private int port;

	private RestTemplate template = new TestRestTemplate();

	@Test
	public void testStartup() {

	}

	@Ignore
	@Test
	public void homePageProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/uaa/", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		String auth = response.getHeaders().getFirst("WWW-Authenticate");
		assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""));
	}

	@Ignore
	@Test
	public void userEndpointProtected() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/uaa/user", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		String auth = response.getHeaders().getFirst("WWW-Authenticate");
		assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""));
	}

	@Ignore
	@Test
	public void authorizationRedirects() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/uaa/oauth/authorize", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		String auth = response.getHeaders().getFirst("WWW-Authenticate");
		assertTrue("Wrong header: " + auth, auth.startsWith("Basic realm=\""));
	}

}
