package br.com.gustavo.cnesAPI.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesAPI.model.Endereco;
import br.com.gustavo.cnesAPI.model.Municipio;
import br.com.gustavo.cnesAPI.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private MunicipioService municipioService;
	
	public Optional<Endereco> buscaEndereco(Endereco endereco) {
		return enderecoRepository.findById(endereco.getCodigo());
	}
	
	public Endereco buscaEnderecoCodigo(int codigo){
		return enderecoRepository.findByCodigo(codigo);
	}
	
	public Set<Endereco> buscaEnderecoNome(String nome){
		return enderecoRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	public Set<Endereco> buscaEnderecoMunicipio(Municipio municipio){
		return enderecoRepository.findByMunicipio(municipio);
	}
	
	public Set<Endereco> buscaEnderecoMunicipio(String nomeMunicipio){
		Municipio municipio = municipioService.buscaNome(nomeMunicipio);
		return buscaEnderecoMunicipio(municipio);
	}
}
