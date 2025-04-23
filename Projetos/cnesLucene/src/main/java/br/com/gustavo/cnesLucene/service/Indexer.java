package br.com.gustavo.cnesLucene.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesLucene.Diretorios;

@Service
public class Indexer {

	private static StandardAnalyzer analyzer = new StandardAnalyzer();
	private static IndexWriter writer;
	private static final String indexLocation = "./data/index";
	private static final String indexLocation_Profissionais = indexLocation + "/profissionais";
	private static final String indexLocation_Cbo = indexLocation + "/cbo";
	private static final String indexLocation_Estados = indexLocation + "/estados";
	private static final String indexLocation_Municipios = indexLocation + "/municipios";
	private static final String indexLocation_Estabelecimentos = indexLocation + "/estabelecimentos";
	private static final String indexLocation_RelacaoProfissionalEstabelecimento = indexLocation
			+ "/relacaoProfissionalEstabelecimento";
	private static final Path versaoIndexadaPath = Path.of("./data/index/versao_indexada.txt");
	private static String indexedVersion = "";

	public static String getIndexedVersion() {
		return indexedVersion;
	}

	public static void updateIndexedVersion() {
		try {
			indexedVersion = "";
			if (Files.exists(versaoIndexadaPath)) {
				indexedVersion = Files.readString(versaoIndexadaPath);
			} else {
				// Diretório não existe, criar estrutura
				Files.createDirectories(versaoIndexadaPath.getParent());
			}
		} catch (IOException e) {
			System.err.println("Erro ao ler versão indexada: " + e.getMessage());
		}
	}

	public static String getIndexLocation() {
		return indexLocation;
	}

	public static String getIndexlocation() {
		return indexLocation;
	}

	public static String getIndexlocationProfissionais() {
		return indexLocation_Profissionais;
	}

	public static String getIndexlocationCbo() {
		return indexLocation_Cbo;
	}

	public static String getIndexlocationEstados() {
		return indexLocation_Estados;
	}

	public static String getIndexlocationMunicipios() {
		return indexLocation_Municipios;
	}

	public static String getIndexlocationEstabelecimentos() {
		return indexLocation_Estabelecimentos;
	}

	public static String getIndexlocationRelacaoprofissionalestabelecimento() {
		return indexLocation_RelacaoProfissionalEstabelecimento;
	}
	
	public static void create(String versao) {
		try {
			System.out.println("Indexando profissionais");
			carregaProfissionais(Diretorios.getInDirProfissionais() + versao + ".csv");
			System.out.println("Terminou de indexar profissionais");

			System.out.println("Indexando CBOs");
			carregaCbos(Diretorios.getInDirCbo() + versao + ".csv");
			System.out.println("Terminou de indexar CBOs");
	
			System.out.println("Indexando estados");
			carregaEstados(Diretorios.getInDirEstados() + versao + ".csv");
			System.out.println("Terminou de indexar Estados");
	
			System.out.println("Indexando municipios");
			carregaMunicipios(Diretorios.getInDirMunicipios() + versao + ".csv");
			System.out.println("Terminou de indexar Municipios");
	
			System.out.println("Indexando estabelecimentos");
			carregaEstabelecimentos(Diretorios.getInDirEstabelecimentos() + versao + ".csv");
			System.out.println("Terminou de indexar Estabelecimentos");
	
			System.out.println("Indexando relaçoes");
			carregaRelacaoProfissionalEstabelecimento(
					Diretorios.getInDirRlProfissionaisEstabelecimento() + versao + ".csv",
					Diretorios.getInDirTbCargaHorariaSus() + versao + ".csv");
			System.out.println("Terminou de indexar relacao Profissional Estabelecimento");
			
			Files.writeString(versaoIndexadaPath, versao, StandardOpenOption.CREATE);
		
			writer.close();
			FileUtils.cleanDirectory(new File(Diretorios.getBasePath()));
			
			System.out.println("\nTerminou indexamento!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void createAll() {

		try {

			for (String versao : Diretorios.getVersoes()) {

				System.out.println("Indexando profissionais");
				carregaProfissionais(Diretorios.getInDirProfissionais() + versao + ".csv");
				System.out.println("Terminou de indexar profissionais");

				System.out.println("Indexando CBOs");
				carregaCbos(Diretorios.getInDirCbo() + versao + ".csv");
				System.out.println("Terminou de indexar CBOs");

				System.out.println("Indexando estados");
				carregaEstados(Diretorios.getInDirEstados() + versao + ".csv");
				System.out.println("Terminou de indexar Estados");

				System.out.println("Indexando municipios");
				carregaMunicipios(Diretorios.getInDirMunicipios() + versao + ".csv");
				System.out.println("Terminou de indexar Municipios");

				System.out.println("Indexando estabelecimentos");
				carregaEstabelecimentos(Diretorios.getInDirEstabelecimentos() + versao + ".csv");
				System.out.println("Terminou de indexar Estabelecimentos");

				System.out.println("Indexando relaçoes");
				carregaRelacaoProfissionalEstabelecimento(
						Diretorios.getInDirRlProfissionaisEstabelecimento() + versao + ".csv",
						Diretorios.getInDirTbCargaHorariaSus() + versao + ".csv");
				System.out.println("Terminou de indexar relacao Profissional Estabelecimento");
				
				Files.writeString(versaoIndexadaPath, versao, StandardOpenOption.APPEND);

			}

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void carregaProfissionais(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {
			Directory directory;
			directory = FSDirectory.open(new File(indexLocation_Profissionais).toPath());
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, indexWriterConfig);

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"), 10 * 1048576);
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {

				Document document = new Document();
				String[] aux = linha.split(csvDivisor);
				document.add(new TextField("codigo-Profissional", aux[0].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("nome-Profissional", aux[2].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("cns-Profissional", aux[3].replaceAll("\"", ""), Field.Store.YES));
				writer.addDocument(document);

			}
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo " + path + " não encontrado");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void carregaEstados(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {
			Directory directory;
			directory = FSDirectory.open(new File(indexLocation_Estados).toPath());
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, indexWriterConfig);

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {

				Document document = new Document();

				String[] aux = linha.split(csvDivisor);

				document.add(new TextField("codigo-Estado", aux[0].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("sigla-Estado", aux[1].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("descricao-Estado", aux[2].replaceAll("\"", ""), Field.Store.YES));

				writer.addDocument(document);

			}
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo " + path + " não encontrado");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void carregaEstabelecimentos(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {
			Directory directory;
			directory = FSDirectory.open(new File(indexLocation_Estabelecimentos).toPath());
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, indexWriterConfig);

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);

				Document document = new Document();

				document.add(new TextField("codigo-Estabelecimento", aux[0].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("cnes-Estabelecimento", aux[1].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("cnpj-Estabelecimento", aux[2].replaceAll("\"", ""), Field.Store.YES));
				document.add(
						new TextField("razaoSocial-Estabelecimento", aux[5].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("fantasia-Estabelecimento", aux[6].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("logradouro-Estabelecimento", aux[7].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("numero-Estabelecimento", aux[8].replaceAll("\"", ""), Field.Store.YES));
				document.add(
						new TextField("complemento-Estabelecimento", aux[9].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("bairro-Estabelecimento", aux[10].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("cep-Estabelecimento", aux[11].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("telefone-Estabelecimento", aux[16].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("email-Estabelecimento", aux[18].replaceAll("\"", ""), Field.Store.YES));

				document.add(new TextField("codigo-municipio-Estabelecimento", aux[31].replaceAll("\"", ""),
						Field.Store.YES));

				writer.addDocument(document);

			}
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo " + path + " não encontrado");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void carregaMunicipios(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {
			Directory directory;
			directory = FSDirectory.open(new File(indexLocation_Municipios).toPath());
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, indexWriterConfig);

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);

				Document document = new Document();

				document.add(new TextField("codigo-Municipio", aux[0].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("nome-Municipio", aux[1].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("sigla-estado-Municipio", aux[2].replaceAll("\"", ""), Field.Store.YES));

				writer.addDocument(document);

			}
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo " + path + " não encontrado");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void carregaCbos(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {
			Directory directory;
			directory = FSDirectory.open(new File(indexLocation_Cbo).toPath());
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, indexWriterConfig);

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {

				Document document = new Document();

				String[] aux = linha.split(csvDivisor);

				document.add(new TextField("codigo-CBO", aux[0].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("descricao-CBO", aux[1].replaceAll("\"", ""), Field.Store.YES));

				if (aux[3].equals("Y")) {
					document.add(new TextField("tipoSaude-CBO", "true", Field.Store.YES));
				} else {
					document.add(new TextField("tipoSaude-CBO", "false", Field.Store.YES));
				}
				if (aux[4].equals("Y")) {
					document.add(new TextField("regulamentado-CBO", "true", Field.Store.YES));
				} else {
					document.add(new TextField("regulamentado-CBO", "false", Field.Store.YES));
				}
				writer.addDocument(document);

			}
			writer.close();

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo " + path + " não encontrado");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void carregaRelacaoProfissionalEstabelecimento(String path1, String path2) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {
			Directory directory;
			directory = FSDirectory.open(new File(indexLocation_RelacaoProfissionalEstabelecimento).toPath());
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			writer = new IndexWriter(directory, indexWriterConfig);

			//Lendo e indexando o primeiro arquivo
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path1), "ISO-8859-1"), 10 * 1048576);
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {
				String[] aux = linha.split(csvDivisor);

				Document document = new Document();

				document.add(
						new TextField("codigo-relacao-Profissional", aux[3].replaceAll("\"", ""), Field.Store.YES));
				document.add(
						new TextField("codigo-relacao-Estabelecimento", aux[4].replaceAll("\"", ""), Field.Store.YES));
				document.add(new TextField("codigo-relacao-CBO", aux[5].replaceAll("\"", ""), Field.Store.YES));

				writer.addDocument(document);

			}
			br.close();
			
			//Lendo e indexando o segundo arquivo
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path2), "ISO-8859-1"), 10 * 1048576);
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {
				String[] aux = linha.split(csvDivisor);

				Document document2 = new Document();

				document2.add(
						new TextField("codigo-relacao-Profissional", aux[1].replaceAll("\"", ""), Field.Store.YES));
				document2.add(
						new TextField("codigo-relacao-Estabelecimento", aux[0].replaceAll("\"", ""), Field.Store.YES));
				document2.add(new TextField("codigo-relacao-CBO", aux[2].replaceAll("\"", ""), Field.Store.YES));

				writer.addDocument(document2);

			}
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo " + path1 + " não encontrado");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
