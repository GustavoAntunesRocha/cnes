package br.com.gustavo.cnesLucene.controller;

import java.util.List;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavo.cnesLucene.DocumentUtils;
import br.com.gustavo.cnesLucene.service.EstabelecimentoService;

@RestController
@RequestMapping("/estabelecimento")
public class EstabelecimentoController {

	@Autowired
	private EstabelecimentoService estabelecimentoService;

	@GetMapping(value = "/existe/{cnes}")
	public boolean existe(@PathVariable String cnes) {
		return estabelecimentoService.existePorCnes(cnes);
	}

	@GetMapping(value = "/busca/codigoProfissional/{codigo}")
	public ResponseEntity<List<String>> buscaPorCodigoProfissional(@PathVariable String codigo) {
		try {
			List<Document> results = estabelecimentoService.buscaPorCodigoProfissional(codigo);
			List<String> jsonList = DocumentUtils.mapMultipleDocuments(results);
			return ResponseEntity.ok(jsonList);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
