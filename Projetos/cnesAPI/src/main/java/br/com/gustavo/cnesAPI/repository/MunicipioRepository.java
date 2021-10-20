package br.com.gustavo.cnesAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavo.cnesAPI.model.Estado;
import br.com.gustavo.cnesAPI.model.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer>{
	
	Municipio findById(int id);

	Municipio findByNome(String nome);
	
	List<Municipio> findByEstado(Estado estado);
}
