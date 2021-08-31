package br.com.gustavo.cnesCarregamentoDados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavo.cnesCarregamentoDados.model.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer>{
	
	Municipio findById(int id);

}
