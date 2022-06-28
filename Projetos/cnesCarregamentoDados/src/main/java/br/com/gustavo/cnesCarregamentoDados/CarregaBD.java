package br.com.gustavo.cnesCarregamentoDados;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesCarregamentoDados.model.Cbo;
import br.com.gustavo.cnesCarregamentoDados.model.Endereco;
import br.com.gustavo.cnesCarregamentoDados.model.Estabelecimento;
import br.com.gustavo.cnesCarregamentoDados.model.Estado;
import br.com.gustavo.cnesCarregamentoDados.model.Municipio;
import br.com.gustavo.cnesCarregamentoDados.model.Profissional;
import br.com.gustavo.cnesCarregamentoDados.model.ProfissionalEstabelecimentoDTO;
import br.com.gustavo.cnesCarregamentoDados.repository.CboRepository;
import br.com.gustavo.cnesCarregamentoDados.repository.EnderecoRepository;
import br.com.gustavo.cnesCarregamentoDados.repository.EstabelecimentoRepository;
import br.com.gustavo.cnesCarregamentoDados.repository.EstadoRepository;
import br.com.gustavo.cnesCarregamentoDados.repository.MunicipioRepository;
import br.com.gustavo.cnesCarregamentoDados.repository.ProfissionalRepository;


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
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Autowired
	private CboRepository cboRepository;
	
	private Set<Estado> estados = new HashSet<Estado>();
	private Set<Municipio> municipios = new HashSet<Municipio>();
	private Set<Endereco> enderecos = new HashSet<Endereco>();
	private Set<Estabelecimento> estabelecimentos = new HashSet<Estabelecimento>();
	private List<Profissional> profissionais = new ArrayList<Profissional>();
	private List<ProfissionalEstabelecimentoDTO> ProfissionaisEstabelecimentosDTO = new ArrayList<ProfissionalEstabelecimentoDTO>();
	private List<Cbo> cboList = new ArrayList<Cbo>();
	
	public void grava() {
		estadoRepository.saveAll(estados);
		municipioRepository.saveAll(municipios);
		enderecoRepository.saveAll(enderecos);
		estabelecimentoRepository.saveAll(estabelecimentos);
		
		this.estados.clear();
		this.municipios.clear();
		this.enderecos.clear();
		this.estabelecimentos.clear();
	}
	
	
	public void carregaRelacaoProfissionalEstabelecimento(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"), 10 * 1048576);
			linha = br.readLine();
			
			while ((linha = br.readLine()) != null) {

				String[] aux = linha.split(csvDivisor);
				
				ProfissionalEstabelecimentoDTO objeto = new ProfissionalEstabelecimentoDTO(aux[3], aux[4], aux[5]);
				this.ProfissionaisEstabelecimentosDTO.add(objeto);
				
				if(this.ProfissionaisEstabelecimentosDTO.size() >= 5000) {
					profissionalRepository.gravaTodosRelacao(ProfissionaisEstabelecimentosDTO);
					this.ProfissionaisEstabelecimentosDTO.clear();
				}
				

			}
			profissionalRepository.gravaTodosRelacao(ProfissionaisEstabelecimentosDTO);
			this.ProfissionaisEstabelecimentosDTO.clear();

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
	
	public void carregaProfissionais(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"), 10 * 1048576);
			linha = br.readLine();
			
			while ((linha = br.readLine()) != null) {

				
				String[] aux = linha.split(csvDivisor);
				
				Profissional profissional = new Profissional();
				profissional.setCodigoProfissional(aux[0]);
				profissional.setNome(aux[2]);
				profissional.setCns(aux[3]);
				
				
				this.profissionais.add(profissional);
				
				if(this.profissionais.size() >= 5000) {
					profissionalRepository.gravaTodos(profissionais);
					this.profissionais.clear();
				}

			}
			profissionalRepository.gravaTodos(profissionais);
			this.profissionais.clear();


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
	
	public void carregaCbos(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"), 10 * 1048576);
			linha = br.readLine();
			
			while ((linha = br.readLine()) != null) {

				
				String[] aux = linha.split(csvDivisor);
				
				Cbo cbo = new Cbo();
				cbo.setCodigo(aux[0].replaceAll("\"", ""));
				cbo.setDescricao(aux[1].replaceAll("\"", ""));
				if(aux[3].equals("Y")) {
					cbo.setTipoSaude(true);
				} else {
					cbo.setTipoSaude(false);
				}
				if(aux[4].equals("Y")) {
					cbo.setRegulamentado(true);
				} else {
					cbo.setRegulamentado(false);
				}
				
				this.cboList.add(cbo);

			}
			this.cboRepository.saveAll(cboList);
			this.cboList.clear();


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

	public void carregaEstabelecimentos(String path) {
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ";";
		try {

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"), 1048576);
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {

				linha = removeSinais(linha);
				String[] aux = linha.split(csvDivisor);
				Set<Endereco> enderecos = new HashSet<Endereco>();
				Endereco endereco = new Endereco();
				endereco.setCodigoEstabelecimento(aux[0]);
				endereco.setNomeLogradouro(aux[7]);
				endereco.setNumero(aux[8]);
				endereco.setComplemento(aux[9]);
				endereco.setBairro(aux[10]);
				endereco.setCep(aux[11]);
				endereco.setTelefone(aux[16]);
				endereco.setEmail(aux[18]);
				for(Municipio municipio : this.municipios) {
					if(municipio.getCodigo() == Integer.parseInt(aux[31])) {
						endereco.setMunicipio(municipio);
						break;
					}
				}
				enderecos.add(endereco);
				Estabelecimento estabelecimento = new Estabelecimento(Long.parseLong(aux[1]), aux[0], aux[2], aux[5], aux[6],
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
				Municipio municipio = new Municipio();
				municipio.setCodigo(Integer.parseInt(aux[0]));
				municipio.setNome(aux[1]);
				for(Estado estado : estados) {
					if(estado.getSigla() == aux[2]) {
						municipio.setEstado(estado);
						break;
					}
				}
				
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

			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"), 1048576);
			linha = br.readLine();
			while ((linha = br.readLine()) != null) {
				linha = removeSinais(linha);
				String[] aux = linha.split(csvDivisor);
				Municipio municipio2 = new Municipio();
				for(Municipio municipio : this.municipios) {
					if(municipio.getCodigo() == Integer.parseInt(aux[9])) {
						municipio2 = municipio;
						break;
					}
				}
				Endereco endereco = new Endereco(aux[0], aux[2], Integer.parseInt(aux[3]),
						aux[4], aux[5], aux[6], aux[7], aux[8], municipio2,
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
