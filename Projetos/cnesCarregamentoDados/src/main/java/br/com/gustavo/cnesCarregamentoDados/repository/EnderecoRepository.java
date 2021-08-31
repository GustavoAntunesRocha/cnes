package br.com.gustavo.cnesCarregamentoDados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavo.cnesCarregamentoDados.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{

	List<Endereco> findByCodigoEstabelecimento(String codigoEstabelecimento);
}
