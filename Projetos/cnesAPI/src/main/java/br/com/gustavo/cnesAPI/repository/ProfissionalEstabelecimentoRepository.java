package br.com.gustavo.cnesAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gustavo.cnesAPI.model.ProfissionalEstabelecimento;

public interface ProfissionalEstabelecimentoRepository extends JpaRepository<ProfissionalEstabelecimento, Long>{
	
	@Query(value = "SELECT * FROM profissional p LEFT JOIN profissional_estabelecimento pe on "
			+ "p.codigo_profissional = pe.profissional_codigo_profissional "
			+ "LEFT JOIN estabelecimento e on e.codigo_unidade = pe.estabelecimento_codigo_unidade "
			+ "where p.nome LIKE %:nomeProfissional% and e.codigo_cnes = :codigoCnes", nativeQuery = true)
	public List<ProfissionalEstabelecimento> buscaPorNomeECnes(@Param("nomeProfissional") String nomeProfissional, @Param("codigoCnes") long codigoCnes);
	
	@Query(value = "SELECT * FROM profissional p LEFT JOIN profissional_estabelecimento pe on "
			+ "p.codigo_profissional = pe.profissional_codigo_profissional "
			+ "LEFT JOIN estabelecimento e on e.codigo_unidade = pe.estabelecimento_codigo_unidade "
			+ "where p.cns = :cns and e.codigo_cnes = :codigoCnes", nativeQuery = true)
	public ProfissionalEstabelecimento buscaPorCnsECnes(@Param("cns") String cns, @Param("codigoCnes") long codigoCnes);
	
	@Query(value = "SELECT * FROM profissional p LEFT JOIN profissional_estabelecimento pe on "
			+ "p.codigo_profissional = pe.profissional_codigo_profissional "
			+ "LEFT JOIN estabelecimento e on e.codigo_unidade = pe.estabelecimento_codigo_unidade "
			+ "where p.cns = :cns", nativeQuery = true)
	public List<ProfissionalEstabelecimento> buscaPorCns(@Param("cns") String cns);

}
