package br.com.gustavo.cnesCarregamentoDados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class CnesCarregamentoDadosApplication {

	public static final String BASE_PATH = "C:\\Users\\gustavo.antunes\\Desktop\\BASE_DE_DADOS_CNES_202106";
	public static final String IN_DIR_ESTADOS = BASE_PATH + "\\tbEstado202106.csv";
	public static final String IN_DIR_MUNICIPIOS = BASE_PATH + "\\tbMunicipio202106.csv";
	public static final String IN_DIR_ENDERECOS = BASE_PATH + "\\rlEstabEndCompl202106.csv";
	public static final String IN_DIR_ESTABELECIMENTOS = BASE_PATH + "\\tbEstabelecimento202106.csv";
	
	@Autowired
	private CarregaBD carregaBD;
	
	public static void main(String[] args) {
		SpringApplication.run(CnesCarregamentoDadosApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void processa() {
		carregaBD.carregaEstados(IN_DIR_ESTADOS);
		System.out.println("Terminou ler Estados");
		carregaBD.carregaMunicipios(IN_DIR_MUNICIPIOS);
		System.out.println("Terminou ler Municipios");
		carregaBD.carregaEstabelecimentos(IN_DIR_ESTABELECIMENTOS);
		System.out.println("Terminou ler Estabelecimentos");
		carregaBD.carregaEnderecos(IN_DIR_ENDERECOS);
		System.out.println("Terminou ler Enderecos");
		carregaBD.grava();
	}
}
