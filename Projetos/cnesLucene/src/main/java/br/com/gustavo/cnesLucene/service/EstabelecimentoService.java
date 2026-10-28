package br.com.gustavo.cnesLucene.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoService {

	public boolean existePorCnes(String codigoCnes) {
		return !buscaPorCodigoCnes(codigoCnes).isEmpty();
	}

	public List<Document> buscaPorCodigoCnes(String codigoCnes) {
		return buscaPorCampo("cnes-Estabelecimento", codigoCnes);
	}

	public List<Document> buscaPorCodigoUnidade(String codigoUnidade) {
		return buscaPorCampo("codigoUnidade-Estabelecimento", codigoUnidade);
	}

	public List<Document> buscaPorCnpj(String cnpj) {
		return buscaPorCampo("cnpj-Estabelecimento", cnpj);
	}

	public List<Document> buscaPorRazaoSocial(String razaoSocial) {
		return buscaPorCampo("razaoSocial-Estabelecimento", razaoSocial);
	}

	public List<Document> buscaPorFantasia(String fantasia) {
		return buscaPorCampo("fantasia-Estabelecimento", fantasia);
	}

	public List<Document> buscaPorEndereco(String endereco) {
		String texto = QueryParser.escape(validarValor(endereco));
		return Search.searchMultipleQueries(
				List.of(texto),
				Arrays.asList(
						"logradouro-Estabelecimento",
						"numero-Estabelecimento",
						"complemento-Estabelecimento",
						"bairro-Estabelecimento",
						"cep-Estabelecimento",
						"telefone-Estabelecimento",
						"email-Estabelecimento",
						"codigo-municipio-Estabelecimento"),
				Indexer.getIndexlocationEstabelecimentos());
	}

	public List<Document> buscaPorEndereco(Map<String, String> criterios) {
		Map<String, String> campos = new LinkedHashMap<>();
		adicionarCampo(campos, criterios, "razaoSocial", "razaoSocial-Estabelecimento");
		adicionarCampo(campos, criterios, "fantasia", "fantasia-Estabelecimento");
		adicionarCampo(campos, criterios, "logradouro", "logradouro-Estabelecimento");
		adicionarCampo(campos, criterios, "numero", "numero-Estabelecimento");
		adicionarCampo(campos, criterios, "complemento", "complemento-Estabelecimento");
		adicionarCampo(campos, criterios, "bairro", "bairro-Estabelecimento");
		adicionarCampo(campos, criterios, "cep", "cep-Estabelecimento");
		adicionarCampo(campos, criterios, "telefone", "telefone-Estabelecimento");
		adicionarCampo(campos, criterios, "email", "email-Estabelecimento");

		String municipio = criterios.get("municipio");
		String estado = criterios.get("estado");
		if (municipio != null || estado != null) {
			String codigoMunicipios = buscarCodigosMunicipios(municipio, estado);
			if (codigoMunicipios.isEmpty()) {
				return List.of();
			}
			campos.put("codigo-municipio-Estabelecimento", codigoMunicipios);
		}
		if (campos.isEmpty()) {
			throw new IllegalArgumentException("Informe ao menos uma propriedade do endereço");
		}
		return Search.searchByFields(campos, Indexer.getIndexlocationEstabelecimentos());
	}

	public List<Document> buscaCombinada(Map<String, String> criterios) {
		return buscaPorEndereco(criterios);
	}
	
	public List<Document> buscaPorCodigoProfissional(String codigoProfissional) {
		String query = "codigo-relacao-Profissional:" + QueryParser.escape(validarValor(codigoProfissional));
		List<Document> documents = Search.searchFiles(query, "codigo-relacao-Profissional", Indexer.getIndexlocationRelacaoprofissionalestabelecimento());
		if (documents.isEmpty()) {
			return List.of();
		}
		query = "codigo-Estabelecimento:" + documents.get(0).get("codigo-relacao-Estabelecimento");
		documents = Search.searchFiles(query, "codigo-Estabelecimento", Indexer.getIndexlocationEstabelecimentos());
		return documents;
	}

	public List<Document> buscaPorProfissionais(String codigoProfissional) {
		return buscaPorCodigoProfissional(codigoProfissional);
	}

	private List<Document> buscaPorCampo(String campo, String valor) {
		String query = campo + ":" + QueryParser.escape(validarValor(valor));
		return Search.searchFiles(query, campo, Indexer.getIndexlocationEstabelecimentos());
	}

	private String validarValor(String valor) {
		if (valor == null || valor.isBlank()) {
			throw new IllegalArgumentException("O valor da busca deve ser informado");
		}
		return valor.trim();
	}

	private void adicionarCampo(Map<String, String> campos, Map<String, String> criterios,
			String criterio, String campo) {
		String valor = criterios.get(criterio);
		if (valor != null && !valor.isBlank()) {
			campos.put(campo, QueryParser.escape(validarValor(valor)));
		}
	}

	private String buscarCodigosMunicipios(String municipio, String estado) {
		Map<String, String> criterios = new LinkedHashMap<>();
		if (municipio != null && !municipio.isBlank()) {
			criterios.put("nome-Municipio", QueryParser.escape(validarValor(municipio)));
		}
		if (estado != null && !estado.isBlank()) {
			criterios.put("sigla-estado-Municipio", QueryParser.escape(validarValor(estado)));
		}
		List<Document> municipios = Search.searchByFields(criterios, Indexer.getIndexlocationMunicipios());
		return municipios.stream()
				.map(document -> QueryParser.escape(document.get("codigo-Municipio")))
				.map(codigo -> "\"" + codigo + "\"")
				.collect(Collectors.joining(" OR "));
	}
}
