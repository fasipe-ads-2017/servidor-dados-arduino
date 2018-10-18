package br.com.fasipe.ads.arduino.umidade.servidordadosarduino.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import br.com.fasipe.ads.arduino.umidade.servidordadosarduino.model.Registro;
import br.com.fasipe.ads.arduino.umidade.servidordadosarduino.repository.RegistroRepository;

@Service
public class RegistroService {

	private static final String UMIDADE_PREFIX = "UMIDADE:";

	@Value("${arduino.port}")
	private String arduinoPort;

	@Autowired
	private RegistroRepository repository;

	public void salvar(Registro registro) {
		this.repository.save(registro);
	}

	public void registrar(String dado) {

		System.out.println(dado);

		if (dado.indexOf(UMIDADE_PREFIX) >= 0) {
			final int posicaoFinal = dado.indexOf(";") < 0 ? dado.length() - 1 : dado.indexOf(";");
			dado = dado.substring(dado.indexOf(UMIDADE_PREFIX) + UMIDADE_PREFIX.length(), posicaoFinal);
		}

		if (!StringUtils.isNumeric(dado)) {
			System.out.print("Dado invalido: [");
			System.out.print(dado);
			System.out.println("]");
			return;
		}

		final Registro registro = new Registro();

		registro.setUmidade(new BigDecimal(dado));
		registro.setData(Calendar.getInstance());

		this.salvar(registro);
	}

	public void loopX() {

		Arrays.stream(SerialPort.getCommPorts()).forEach(p -> System.out.println(p.getSystemPortName()));

		final SerialPort comPort = SerialPort.getCommPort(this.arduinoPort);
		comPort.openPort();

		final SerialPortDataListener listener = new SerialPortDataListener() {

			@Override
			public void serialEvent(SerialPortEvent event) {

				System.out.println("Dados recebidos");

				final String dado = new String(event.getReceivedData());

				Arrays.stream(dado.split(";")).forEach(d -> RegistroService.this.registrar(dado));
			}

			@Override
			public int getListeningEvents() {
				return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
			}
		};
		comPort.addDataListener(listener);

	}

	public void loop() {

		while (true) {

			Arrays.stream(SerialPort.getCommPorts()).forEach(p -> System.out.println(p.getSystemPortName()));

			final SerialPort comPort = SerialPort.getCommPort(this.arduinoPort);
			comPort.openPort();

			try {
				while (true) {
					while (comPort.bytesAvailable() <= 0) {
						Thread.sleep(1000);
					}

					final byte[] readBuffer = new byte[comPort.bytesAvailable()];
					final int numRead = comPort.readBytes(readBuffer, readBuffer.length);

					if (numRead > 0) {
						final String dado = new String(readBuffer);

						Arrays.stream(dado.split(";")).forEach(d -> this.registrar(dado));
					}
				}
			} catch (final Throwable e) {
				e.printStackTrace();
			}
			comPort.closePort();
		}
	}

	public List<Registro> all() {
		return this.repository.findByOrderByIdDesc();
	}

	public List<Registro> after(Integer id) {
		return this.repository.findByIdGreaterThanOrderByIdDesc(id);
	}

	public void clear() {
		this.repository.deleteAll();
	}
}
