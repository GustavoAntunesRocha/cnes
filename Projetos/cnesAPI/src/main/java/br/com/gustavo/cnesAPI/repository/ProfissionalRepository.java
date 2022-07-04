package br.com.gustavo.cnesAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.gustavo.cnesAPI.model.Estabelecimento;
import br.com.gustavo.cnesAPI.model.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, String>{
	
	Profissional findByCodigoProfissional(String codigoProfissional);
	
	List<Profissional> findByEstabelecimentosEstabelecimento(Estabelecimento estabelecimentos);
	
	@Query("SELECT p FROM Profissional p LEFT JOIN ProfissionalEstabelecimento pe on "
			+ "p.codigoProfissional = pe.profissional.codigoProfissional "
			+ "LEFT JOIN Estabelecimento e on e.codigoUnidade = pe.estabelecimento.codigoUnidade "
			+ "where e.codigoCnes = :codigoCnes")
	List<String> buscaPorNomeECnes(@Param("codigoCnes") long codigoCnes);
	
	List<Profissional> findByCns(String cns);

}
