package br.com.gustavo.cnesCarregamentoDados.repository;

import java.util.List;

import javax.transaction.Transactional;

import br.com.gustavo.cnesCarregamentoDados.model.Profissional;
import br.com.gustavo.cnesCarregamentoDados.model.ProfissionalEstabelecimentoDTO;

public interface ProfissionalRepositoryCustom {
	
	@Transactional
	void gravaTodos(List<Profissional> profissionais);
	
	@Transactional
	void gravaTodosRelacao(List<ProfissionalEstabelecimentoDTO> objetos);

}
