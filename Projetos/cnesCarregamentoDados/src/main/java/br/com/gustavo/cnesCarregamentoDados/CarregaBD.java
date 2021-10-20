package br.com.gustavo.cnesCarregamentoDados;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesCarregamentoDados.model.Endereco;
import br.com.gustavo.cnesCarregamentoDados.model.Estabelecimento;
import br.com.gustavo.cnesCarregamentoDados.model.Estado;
import br.com.gustavo.cnesCarregamentoDados.model.Municipio;
import br.com.gustavo.cnesCarregamentoDados.repository.EnderecoRepository;
import br.com.gustavo.cnesCarregamentoDados.repository.EstabelecimentoRepository;
import br.com.gustavo.cnesCarregamentoDados.repository.EstadoRepository;
import br.com.gustavo.cnesCarregamentoDados.repository.MunicipioRepository;

@Service
public class CarregaBD {

	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private MunicipioRepository municipioRepository;
	
	private Set<Estado> estados = new HashSet<Estado>();
	private Set<Municipio> municipios = new HashSet<Municipio>();
	private Set<Endereco> enderecos = new HashSet<Endereco>();
	private Set<Estabelecimento> estabelecimentos = new HashSet<Estabelecimento>();
	
	public void grava() {
		estadoRepository.saveAll(estados);
		municipioRepository.saveAll(municipios);
		enderecoRepository.saveAll(enderecos);
		estabelecimentoRepository.saveAll(estabelecimentos);
	}

	public void carregaEstabelecimentos(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"));
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {

				linha = removeSinais(linha);
				String[] aux = linha.split(csvDivisor);
				Set<Endereco> enderecos = new HashSet<Endereco>();
				//enderecos = enderecoRepository.findByCodigoEstabelecimento(aux[0]);
				Endereco endereco = new Endereco();
				endereco.setCodigoEstabelecimento(aux[0]);
				endereco.setNomeLogradouro(aux[7]);
				endereco.setNumero(aux[8]);
				endereco.setComplemento(aux[9]);
				endereco.setBairro(aux[10]);
				endereco.setCep(aux[11]);
				endereco.setTelefone(aux[16]);
				endereco.setEmail(aux[18]);
				//endereco.setMunicipio(municipioRepository.findById(Integer.parseInt(aux[31])));
				for(Municipio municipio : this.municipios) {
					if(municipio.getCodigo() == Integer.parseInt(aux[31])) {
						endereco.setMunicipio(municipio);
						break;
					}
				}
				enderecos.add(endereco);
				Estabelecimento estabelecimento = new Estabelecimento(Integer.parseInt(aux[1]), aux[0], aux[2], aux[5], aux[6],
						enderecos);
				this.enderecos.add(endereco);
				this.estabelecimentos.add(estabelecimento);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
			while ((linha = br.readLine()) != null) {

				linha = removeSinais(linha);
				String[] aux = linha.split(csvDivisor);
				Estado estado = new Estado(Integer.parseInt(aux[0]), aux[1], aux[2]);
				this.estados.add(estado);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
			while ((linha = br.readLine()) != null) {

				linha = removeSinais(linha);
				String[] aux = linha.split(csvDivisor);
				Municipio municipio = new Municipio(Integer.parseInt(aux[0]), aux[1],
						estadoRepository.findBySigla(aux[2]));
				this.municipios.add(municipio);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
			while ((linha = br.readLine()) != null) {
				linha = removeSinais(linha);
				String[] aux = linha.split(csvDivisor);
				
				//Estabelecimento estabelecimento = estabelecimentoRepository.findByCodigoUnidade(aux[0]);
				Endereco endereco = new Endereco(aux[0], aux[2], Integer.parseInt(aux[3]),
						aux[4], aux[5], aux[6], aux[7], aux[8], municipioRepository.findById(Integer.parseInt(aux[9])),
						aux[10], aux[11], aux[12], aux[13], aux[14], aux[15]);
				this.enderecos.add(endereco);
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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

	private static String removeSinais(String entrada) {
		String sa = Normalizer.normalize(entrada, Normalizer.Form.NFD);

		sa = sa.replaceAll("\"", "");
		return sa.replaceAll("\\p{M}", "");
	}
}
