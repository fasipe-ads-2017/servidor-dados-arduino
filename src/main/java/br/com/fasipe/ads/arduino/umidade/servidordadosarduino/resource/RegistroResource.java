package br.com.fasipe.ads.arduino.umidade.servidordadosarduino.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fasipe.ads.arduino.umidade.servidordadosarduino.model.Registro;
import br.com.fasipe.ads.arduino.umidade.servidordadosarduino.service.RegistroService;

@RestController
@RequestMapping("/dados/v1")
public class RegistroResource {

	@Autowired
	private RegistroService service;

	@GetMapping("all")
	public List<Registro> all() {
		return this.service.all();
	}

	@GetMapping("after/{id}")
	public List<Registro> after(@PathVariable("id") Integer id) {
		return this.service.after(id);
	}

	@GetMapping("clear")
	public void clear() {
		this.service.clear();
	}
}
