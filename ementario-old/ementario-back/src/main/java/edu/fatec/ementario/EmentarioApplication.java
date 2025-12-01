package edu.fatec.ementario;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"edu.fatec.ementario.controller", "edu.fatec.ementario.config"})
@EnableJpaRepositories(basePackages = "edu.fatec.ementario.repository")
@EntityScan(basePackages = "edu.fatec.ementario.model")
public class EmentarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmentarioApplication.class, args);
	}

	@Value("${tomcat.ajp.port}")
	int ajpPort;

	@Value("${tomcat.ajp.remoteauthentication}")
	String remoteAuthentication;

	@Value("${tomcat.ajp.enabled}")
	boolean tomcatAjpEnabled;

	@Bean
	public TomcatServletWebServerFactory servletContainer() {

		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		if (tomcatAjpEnabled)
		{
			Connector ajpConnector = new Connector("AJP/1.3");
			ajpConnector.setPort(ajpPort);
			ajpConnector.setSecure(false);
			ajpConnector.setAllowTrace(false);
			ajpConnector.setScheme("https");
			((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(false);
			tomcat.addAdditionalTomcatConnectors(ajpConnector);
		}

		return tomcat;
	}
}
