package br.com.gustavo.cnesAPI.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.gustavo.cnesAPI.model.Endereco;
import br.com.gustavo.cnesAPI.model.Estabelecimento;

@Repository
public interface EstabelecimentoRepository extends CrudRepository<Estabelecimento, Integer>{
	
	Estabelecimento findByCodigoCnes(int codigoCnes);

	Estabelecimento findByCodigoUnidade(String codigoUnidade);
	
	Estabelecimento findByCnpj(String cnpj);
	
	Set<Estabelecimento> findByRazaoSocialContainingIgnoreCase(String razaoSocial);
	
	Set<Estabelecimento> findByFantasiaContainingIgnoreCase(String fantasia);
	
	Estabelecimento findByEndereco(Endereco endereco);
	
	List<Estabelecimento> findByEnderecoMunicipioNome(String nome);
	
	List<Estabelecimento> findByEnderecoMunicipioEstadoDescricao(String nome);
}
