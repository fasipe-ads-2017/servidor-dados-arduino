package br.com.fasipe.ads.arduino.umidade.servidordadosarduino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.fasipe.ads.arduino.umidade.servidordadosarduino.service.RegistroService;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ServidorDadosArduinoApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext run = SpringApplication.run(ServidorDadosArduinoApplication.class, args);

		run.getBean(RegistroService.class).loop();
	}
}
