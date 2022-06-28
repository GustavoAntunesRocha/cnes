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

	public static final String BASE_PATH = "C:\\Users\\gustavo.antunes\\Desktop\\BASE_DE_DADOS_CNES_202205";
	public static final String IN_DIR_ESTADOS = BASE_PATH + "\\tbEstado202205.csv";
	public static final String IN_DIR_MUNICIPIOS = BASE_PATH + "\\tbMunicipio202205.csv";
	public static final String IN_DIR_ENDERECOS = BASE_PATH + "\\rlEstabEndCompl202205.csv";
	public static final String IN_DIR_ESTABELECIMENTOS = BASE_PATH + "\\tbEstabelecimento202205.csv";
	public static final String IN_DIR_CBO = BASE_PATH + "\\tbAtividadeProfissional202205.csv";
	public static final String IN_DIR_PROFISSIONAIS = BASE_PATH + "\\tbDadosProfissionalSus202205.csv";
	public static final String IN_DIR_RL_PROFISSIONAIS_ESTABELECIMENTO = BASE_PATH + "\\rlEstabEquipeProf202205.csv";
	
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
		System.out.println("Gravando dados...");
		carregaBD.grava();
		System.out.println("Terminou de gravar");
		System.out.println("Processando CBO...");
		carregaBD.carregaCbos(IN_DIR_CBO);
		System.out.println("Tabela CBO finalizada");
		System.out.println("Processando profissionais...");
		carregaBD.carregaProfissionais(IN_DIR_PROFISSIONAIS);
		System.out.println("Terminou gravar todos os Profissionais");
		System.out.println("Processando relacao de profissionais com estabelecimentos...");
		carregaBD.carregaRelacaoProfissionalEstabelecimento(IN_DIR_RL_PROFISSIONAIS_ESTABELECIMENTO);
		System.out.println("Terminou gravar relacao Profissionais e Estabelecimentos");
		
		
	}
}
