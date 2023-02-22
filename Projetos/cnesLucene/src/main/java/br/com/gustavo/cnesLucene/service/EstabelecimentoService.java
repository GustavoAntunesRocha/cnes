package br.com.gustavo.cnesLucene.service;

import java.util.List;

import org.apache.lucene.document.Document;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoService {

	public boolean existePorCnes(String codigoCnes) {
		String query = "cnes-Estabelecimento:" + codigoCnes;
		List<Document> documents = Search.searchFiles(query, "cnes-Estabelecimento", Indexer.getIndexlocationEstabelecimentos());
		if(documents.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public List<Document> buscaPorCodigoProfissional(String codigoProfissional) {
		String query = "codigo-relacao-Profissional:" + codigoProfissional;
		List<Document> documents = Search.searchFiles(query, "codigo-relacao-Profissional", Indexer.getIndexlocationRelacaoprofissionalestabelecimento());
		query = "codigo-Estabelecimento:" + documents.get(0).get("codigo-relacao-Estabelecimento");
		documents = Search.searchFiles(query, "codigo-Estabelecimento", Indexer.getIndexlocationEstabelecimentos());
		return documents;
	}
}
