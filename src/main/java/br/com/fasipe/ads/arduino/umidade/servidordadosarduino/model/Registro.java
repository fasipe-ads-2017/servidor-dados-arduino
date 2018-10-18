package br.com.fasipe.ads.arduino.umidade.servidordadosarduino.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;

@Entity
@Table(name = "REGISTRO")
public class Registro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	private Calendar data;

	@Column(name = "UMIDADE", precision = 0, length = 8)
	private BigDecimal umidade;

	@Column(name = "TEMPERATURA", precision = 0, length = 8)
	private BigDecimal temperatura;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getData() {
		return this.data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public BigDecimal getUmidade() {
		return this.umidade;
	}

	public void setUmidade(BigDecimal umidade) {
		this.umidade = umidade;
	}

	public BigDecimal getTemperatura() {
		return this.temperatura;
	}

	public void setTemperatura(BigDecimal temperatura) {
		this.temperatura = temperatura;
	}

	@JsonGetter("data")
	public String getDataFormatada() {
		if (this.data != null) {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.data.getTime());
		}
		return null;
	}
}
