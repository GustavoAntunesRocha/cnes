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
import br.com.gustavo.cnesLucene.service.ProfissionalService;

@RestController()
@RequestMapping("/profissional")
public class ProfissionalController {

	@Autowired
	private ProfissionalService profissionalService;

	//ToDo
	@GetMapping("/busca/cns/{codigoCns}/cnes/{codigoCnes}")
	public String getProfissionalCodigoCNS(@PathVariable String codigoCns, @PathVariable String codigoCnes) {
		return null;
	}

	@GetMapping("/nome/{nomeProfissional}/cnes/{codigoCnes}")
	public ResponseEntity<List<String>> getProfissionalNomeCnes(@PathVariable String nomeProfissional,
			@PathVariable String codigoCnes) {
		try {
			List<Document> results = profissionalService.buscaPorNomeECnes(nomeProfissional, codigoCnes);
			List<String> jsonList = DocumentUtils.mapMultipleDocuments(results);
			return ResponseEntity.ok(jsonList);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/nome/{nomeProfissional}")
	public ResponseEntity<List<String>> getProfissionalNome(@PathVariable String nomeProfissional) {
		try {
			List<Document> results = profissionalService.buscaPorNome(nomeProfissional);
			List<String> jsonList = DocumentUtils.mapMultipleDocuments(results);
			return ResponseEntity.ok(jsonList);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
