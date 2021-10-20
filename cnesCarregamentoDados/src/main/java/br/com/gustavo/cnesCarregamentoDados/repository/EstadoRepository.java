package br.com.gustavo.cnesCarregamentoDados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavo.cnesCarregamentoDados.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	
	Estado findBySigla(String sigla);

}
