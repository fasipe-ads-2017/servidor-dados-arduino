package br.com.fasipe.ads.arduino.umidade.servidordadosarduino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fasipe.ads.arduino.umidade.servidordadosarduino.model.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {

	List<Registro> findByIdGreaterThanOrderByIdDesc(Integer id);

	List<Registro> findByOrderByIdDesc();

	List<Registro> findByIdGreaterThan(Integer id);

}
