package br.com.gustavo.cnesLucene.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Document;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalService {

	public List<Document> buscaPorNomeECnes(String nomeProfissional, String cnes) {
		String query;

		query = "cnes-Estabelecimento:" + cnes;
		List<Document> estabelecimentos = Search.searchFiles(query, "cnes-Estabelecimento",
				Indexer.getIndexlocationEstabelecimentos());

		query = "codigo-relacao-Estabelecimento:" + estabelecimentos.get(0).get("codigo-Estabelecimento");
		List<Document> relacaoEstabelecimentos = Search.searchFiles(query, "codigo-relacao-Estabelecimento",
				Indexer.getIndexlocationRelacaoprofissionalestabelecimento());

		List<Document> profissionais = new ArrayList<>();

		for (Document document : relacaoEstabelecimentos) {
			profissionais
					.addAll(Search.searchFiles("codigo-Profissional:" + document.get("codigo-relacao-Profissional"),
							"codigo-Profissional", Indexer.getIndexlocationProfissionais()));
		}

		Iterator<Document> iterator = profissionais.iterator();
		while (iterator.hasNext()) {
			Document document = iterator.next();
			if (!document.get("nome-Profissional").contains(nomeProfissional.toUpperCase())) {
				iterator.remove();
			}
		}
		return profissionais;
	}

	public List<Document> buscaPorNome(String nomeProfissional) {
		String query = "nome-Profissional:" + nomeProfissional;
		List<Document> profissionais = Search.searchFiles(query, "nome-Profissional",
				Indexer.getIndexlocationProfissionais());
		return profissionais;
	}

}
