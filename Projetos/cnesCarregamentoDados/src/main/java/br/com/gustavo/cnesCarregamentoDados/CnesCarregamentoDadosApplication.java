package br.com.gustavo.cnesCarregamentoDados;

import java.io.File;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class CnesCarregamentoDadosApplication implements CommandLineRunner {

	public static final String BASE_PATH = "./data/arquivos";
	public static String IN_DIR_ESTADOS = BASE_PATH + "/tbEstado";
	public static String IN_DIR_MUNICIPIOS = BASE_PATH + "/tbMunicipio";
	public static String IN_DIR_ENDERECOS = BASE_PATH + "/rlEstabEndCompl";
	public static String IN_DIR_ESTABELECIMENTOS = BASE_PATH + "/tbEstabelecimento";
	public static String IN_DIR_CBO = BASE_PATH + "/tbAtividadeProfissional";
	public static String IN_DIR_PROFISSIONAIS = BASE_PATH + "/tbDadosProfissionalSus";
	public static String IN_DIR_RL_PROFISSIONAIS_ESTABELECIMENTO = BASE_PATH + "/rlEstabEquipeProf";

	@Autowired
	private CarregaBD carregaBD;

	public static void main(String[] args) {
		SpringApplication.run(CnesCarregamentoDadosApplication.class, args);
	}

	private int menu() {
		Scanner menu = new Scanner(System.in);
		menu.reset();
		System.out.println("|-----------------------------");
		System.out.println("| Opção 1 - Baixar e atualizar base de dados");
		System.out.println("| Opção 2 - Atualizar banco da dados");
		System.out.println("| Opção 3 - Somente baixar base de dados");
		System.out.println("| Opção 4 - Sair");
		System.out.println("|-----------------------------");
		System.out.println("Digite uma opção: ");
		int escolha = menu.nextInt();
		return escolha;
	}

	@Override
	public void run(String... args) {
		while (true) {
			int escolha = menu();
			switch (escolha) {
			case 1: {
				Scanner menu = new Scanner(System.in);
				System.out.println("Digite a url base: (Ex.: ftp.datasus.gov.br)");
				String ultimaVersao = FtpClient.atualizarCnes(menu.next().trim().toLowerCase(), "./data/");
				menu.close();
				processa(ultimaVersao);
				return;
			}
			case 2:{
				processa(null);
				return;
			}
			case 3:{
				Scanner menu = new Scanner(System.in);
				System.out.println("Digite a url base: (Ex.: ftp.datasus.gov.br)");
				FtpClient.atualizarCnes(menu.next().trim().toLowerCase(), "./data/");
				menu.close();
				return;
			}
			case 4:{
				return;
			}
			default:
				System.out.println("Opção inválida: " + escolha);
			}
			
		}
	}

	private void processa(String ultimaVersao) {

		if(ultimaVersao == null) {
			File fileAux = new File(BASE_PATH);
			String[] fileList = fileAux.list();
			for(String fileName : fileList) {
				if(fileName.startsWith("tbEstado")) {
					ultimaVersao = fileName.substring(8, 13);
				}
			}
		}
		carregaBD.carregaEstados(IN_DIR_ESTADOS + ultimaVersao + ".csv");
		System.out.println("Terminou ler Estados");
		carregaBD.carregaMunicipios(IN_DIR_MUNICIPIOS + ultimaVersao + ".csv");
		System.out.println("Terminou ler Municipios");
		carregaBD.carregaEstabelecimentos(IN_DIR_ESTABELECIMENTOS + ultimaVersao + ".csv");
		System.out.println("Terminou ler Estabelecimentos");
		carregaBD.carregaEnderecos(IN_DIR_ENDERECOS + ultimaVersao + ".csv");
		System.out.println("Terminou ler Enderecos");
		System.out.println("Gravando dados...");
		carregaBD.grava();
		System.out.println("Terminou de gravar");
		System.out.println("Processando CBO...");
		carregaBD.carregaCbos(IN_DIR_CBO + ultimaVersao + ".csv");
		System.out.println("Tabela CBO finalizada");
		System.out.println("Processando profissionais...");
		carregaBD.carregaProfissionais(IN_DIR_PROFISSIONAIS + ultimaVersao + ".csv");
		System.out.println("Terminou gravar todos os Profissionais");
		System.out.println("Processando relacao de profissionais com estabelecimentos...");
		carregaBD.carregaRelacaoProfissionalEstabelecimento(
				IN_DIR_RL_PROFISSIONAIS_ESTABELECIMENTO + ultimaVersao + ".csv");
		System.out.println("Terminou gravar relacao Profissionais e Estabelecimentos");

	}
}
