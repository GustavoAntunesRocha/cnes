package br.com.gustavo.cnesCarregamentoDados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gustavo.cnesCarregamentoDados.model.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, String>, ProfissionalRepositoryCustom{
	
	Profissional findByCodigoProfissional(String codigoProfissional);

}
