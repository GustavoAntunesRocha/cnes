package br.com.gustavo.cnesCarregamentoDados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gustavo.cnesCarregamentoDados.model.Estabelecimento;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer>{
	
	Estabelecimento findById(int id);

	Estabelecimento findByCodigoUnidade(String codigoUnidade);
}
