package br.com.gustavo.cnesLucene;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.gustavo.cnesLucene.service.Indexer;

@SpringBootApplication
public class CnesLuceneApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CnesLuceneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length >= 1) {
			Indexer.updateIndexedVersion();
			String versao = FtpClient.atualizarCnes("ftp.datasus.gov.br", "./data/arquivos/");
			if (!versao.equals(Indexer.getIndexedVersion())) {
				Indexer.create(versao);
			} else {
				System.out.println("\nVersão mais atual já está indexada");
			}
		}

	}

}
