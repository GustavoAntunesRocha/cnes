package br.com.gustavo.cnesCarregamentoDados.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.gustavo.cnesCarregamentoDados.model.Profissional;
import br.com.gustavo.cnesCarregamentoDados.model.ProfissionalEstabelecimentoDTO;

public class ProfissionalRepositoryImpl implements ProfissionalRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void gravaTodos(List<Profissional> profissionais) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO profissional (codigo_profissional, cns, nome) VALUES ");
		boolean juststarted = true;
		for (Profissional profissional : profissionais) {
			if (!juststarted) {
				sb.append(", ");
			} else {
				juststarted = false;
			}
			sb.append("(");
			sb.append(profissional.getCodigoProfissional().replaceAll("\"", "'"));
			sb.append(", ");
			sb.append(profissional.getCns().replaceAll("\"", "'"));
			sb.append(", ");
			sb.append(profissional.getNome().replaceAll("\"", "'"));
			sb.append(")");

		}
		
		Query query = entityManager.createNativeQuery(sb.toString());
	    query.executeUpdate();

	}

	@Override
	@Transactional
	public void gravaTodosRelacao(List<ProfissionalEstabelecimentoDTO> objetos) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO profissional_estabelecimento (id, profissional_codigo_profissional, estabelecimento_codigo_unidade, cbo_codigo) VALUES ");
		boolean juststarted = true;
		for (ProfissionalEstabelecimentoDTO objeto : objetos) {
			if (!juststarted) {
				sb.append(", ");
			} else {
				juststarted = false;
			}
			sb.append("(");
			sb.append("nextval('profissional_estabelecimento_seq'), ");
			sb.append(objeto.getProfissionaisCodigo().replaceAll("\"", "'"));
			sb.append(", ");
			sb.append(objeto.getEstabelecimentosCodigo().replaceAll("\"", ""));
			sb.append(", ");
			sb.append(objeto.getCbo().replaceAll("\"", "'"));
			sb.append(")");

		}
		
		Query query = entityManager.createNativeQuery(sb.toString());
	    query.executeUpdate();
		
	}

}
