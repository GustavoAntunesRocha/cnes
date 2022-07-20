package br.com.gustavo.cnesCarregamentoDados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.com.gustavo.cnesCarregamentoDados.model.Cbo;
import br.com.gustavo.cnesCarregamentoDados.model.Endereco;
import br.com.gustavo.cnesCarregamentoDados.model.Estabelecimento;
import br.com.gustavo.cnesCarregamentoDados.model.Estado;
import br.com.gustavo.cnesCarregamentoDados.model.Municipio;
import br.com.gustavo.cnesCarregamentoDados.model.Profissional;
import br.com.gustavo.cnesCarregamentoDados.model.ProfissionalEstabelecimentoDTO;

@Service
public class GeraArquivos {
	
	private Set<Estado> estados = new HashSet<Estado>();
	private Set<Municipio> municipios = new HashSet<Municipio>();
	private Set<Endereco> enderecos = new HashSet<Endereco>();
	private Set<Estabelecimento> estabelecimentos = new HashSet<Estabelecimento>();
	private Set<Profissional> profissionais = new HashSet<Profissional>();
	private Set<ProfissionalEstabelecimentoDTO> ProfissionaisEstabelecimentosDTO = new HashSet<ProfissionalEstabelecimentoDTO>();
	private Set<Cbo> cboList = new HashSet<Cbo>();

	public void gravaTudo(String path) {
		carregaEstados(path);
		carregaMunicipios(path);
		carregaEnderecos(path);
		carregaEstabelecimentos(path);
	}

	public void carregaRelacaoProfissionalEstabelecimento(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"), 10 * 1048576);
			linha = br.readLine();
			File file = new File("./data/profissionaisEstabelecimento.txt");
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);

				ProfissionalEstabelecimentoDTO objeto = new ProfissionalEstabelecimentoDTO(aux[3].replaceAll("\"", ""),
						aux[4].replaceAll("\"", ""), aux[5].replaceAll("\"", ""));
				this.ProfissionaisEstabelecimentosDTO.add(objeto);

			}
			writeObjectToFile(this.ProfissionaisEstabelecimentosDTO, file);
			this.ProfissionaisEstabelecimentosDTO.clear();

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ path +" não encontrado");
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

	public void carregaProfissionais(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"), 10 * 1048576);
			linha = br.readLine();
			File file = new File("./data/profissionais.txt");
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);

				Profissional profissional = new Profissional();
				profissional.setCodigoProfissional(aux[0].replaceAll("\"", ""));
				profissional.setNome(aux[2].replaceAll("\"", ""));
				profissional.setCns(aux[3].replaceAll("\"", ""));

				this.profissionais.add(profissional);

			}
			writeObjectToFile(this.profissionais, file);
			this.profissionais.clear();

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ path +" não encontrado");
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

	public void carregaCbos(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			File file = new File("./data/cbo.txt");
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);

				Cbo cbo = new Cbo();
				cbo.setCodigo(aux[0].replaceAll("\"", ""));
				cbo.setDescricao(aux[1].replaceAll("\"", ""));
				if (aux[3].equals("Y")) {
					cbo.setTipoSaude(true);
				} else {
					cbo.setTipoSaude(false);
				}
				if (aux[4].equals("Y")) {
					cbo.setRegulamentado(true);
				} else {
					cbo.setRegulamentado(false);
				}

				this.cboList.add(cbo);

			}
			writeObjectToFile(this.cboList, file);

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ path +" não encontrado");
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

	public void carregaEstados(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			File file = new File("./data/estados.txt");
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);
				Estado estado = new Estado(Integer.parseInt(aux[0].replaceAll("\"", "")), aux[1].replaceAll("\"", ""),
						aux[2].replaceAll("\"", ""));
				this.estados.add(estado);

				writeObjectToFile(this.estados, file);

			}

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ path +" não encontrado");
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

	public void carregaEstabelecimentos(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			File file = new File("./data/estabelecimentos.txt");
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);
				Set<Endereco> enderecos = new HashSet<Endereco>();
				Endereco endereco = new Endereco();
				endereco.setCodigoEstabelecimento(aux[0].replaceAll("\"", ""));
				endereco.setNomeLogradouro(aux[7].replaceAll("\"", ""));
				endereco.setNumero(aux[8].replaceAll("\"", ""));
				endereco.setComplemento(aux[9].replaceAll("\"", ""));
				endereco.setBairro(aux[10].replaceAll("\"", ""));
				endereco.setCep(aux[11].replaceAll("\"", ""));
				endereco.setTelefone(aux[16].replaceAll("\"", ""));
				endereco.setEmail(aux[18].replaceAll("\"", ""));
				for (Municipio municipio : this.municipios) {
					if (municipio.getCodigo() == Integer.parseInt(aux[31].replaceAll("\"", ""))) {
						endereco.setMunicipio(municipio);
						break;
					}
				}
				enderecos.add(endereco);
				Estabelecimento estabelecimento = new Estabelecimento(Long.parseLong(aux[1].replaceAll("\"", "")),
						aux[0].replaceAll("\"", ""), aux[2].replaceAll("\"", ""), aux[5].replaceAll("\"", ""),
						aux[6].replaceAll("\"", ""), enderecos);
				this.enderecos.add(endereco);
				this.estabelecimentos.add(estabelecimento);


			}
			writeObjectToFile(this.estabelecimentos, file);

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ path +" não encontrado");
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

	public void carregaEnderecos(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			File file = new File("./data/enderecos.txt");
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);
				Municipio municipio2 = new Municipio();
				for (Municipio municipio : this.municipios) {
					if (municipio.getCodigo() == Integer.parseInt(aux[9].replaceAll("\"", ""))) {
						municipio2 = municipio;
						break;
					}
				}
				Endereco endereco = new Endereco(aux[0].replaceAll("\"", ""), aux[2].replaceAll("\"", ""),
						Integer.parseInt(aux[3].replaceAll("\"", "")), aux[4].replaceAll("\"", ""),
						aux[5].replaceAll("\"", ""), aux[6].replaceAll("\"", ""), aux[7].replaceAll("\"", ""),
						aux[8].replaceAll("\"", ""), municipio2, aux[10].replaceAll("\"", ""),
						aux[11].replaceAll("\"", ""), aux[12].replaceAll("\"", ""), aux[13].replaceAll("\"", ""),
						aux[14].replaceAll("\"", ""), aux[15].replaceAll("\"", ""));
				this.enderecos.add(endereco);

				writeObjectToFile(this.enderecos, file);

			}

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ path +" não encontrado");
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

	public void carregaMunicipios(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			File file = new File("./data/municipios.txt");
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);
				Municipio municipio = new Municipio();
				municipio.setCodigo(Integer.parseInt(aux[0].replaceAll("\"", "")));
				municipio.setNome(aux[1].replaceAll("\"", ""));
				for (Estado estado : estados) {
					if (estado.getSigla() == aux[2]) {
						municipio.setEstado(estado);
						break;
					}
				}

				this.municipios.add(municipio);
				writeObjectToFile(this.municipios, file);

			}

		} catch (FileNotFoundException e) {
			System.out.println("Arquivo "+ path +" não encontrado");
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

	// Serialização
	// Salva objeto em arquivo.
	public static void writeObjectToFile(Object obj, File file) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(obj);
			oos.flush();
		}
	}

}
