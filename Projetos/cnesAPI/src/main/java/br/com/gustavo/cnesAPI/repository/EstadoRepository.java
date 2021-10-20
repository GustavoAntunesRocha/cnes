package br.com.gustavo.cnesAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavo.cnesAPI.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer>{
		
	Estado findBySigla(String sigla);

	Estado findByDescricao(String descricao);
}
