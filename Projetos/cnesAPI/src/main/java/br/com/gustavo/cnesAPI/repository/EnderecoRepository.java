package br.com.gustavo.cnesAPI.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavo.cnesAPI.model.Endereco;
import br.com.gustavo.cnesAPI.model.Municipio;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{

	Set<Endereco> findByCodigoEstabelecimento(String codigoEstabelecimento);
	
	Endereco findByCodigo(int codigo);
	
	Set<Endereco> findByNomeContainingIgnoreCase(String nome);
	
	Set<Endereco> findByBairroContainingIgnoreCase(String bairro);
	
	Set<Endereco> findByCepContainingIgnoreCase(String cep);
	
	Set<Endereco> findByMunicipio(Municipio municipio);
	
}
