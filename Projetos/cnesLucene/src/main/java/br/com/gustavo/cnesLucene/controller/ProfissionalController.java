package br.com.gustavo.cnesLucene.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<Map<String, Object>> getProfissionalNome(
			@PathVariable String nomeProfissional,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "score") String sort) {
		try {
			List<Document> results = profissionalService.buscaPorNome(nomeProfissional, page, pageSize, sort);

			Map<String, Object> response = new HashMap<>();
			response.put("results", DocumentUtils.mapMultipleDocuments(results));
			response.put("page", page);
			response.put("pageSize", pageSize);
			response.put("sort", sort);

			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}


	@GetMapping("/scroll/nome/{nomeProfissional}")
	public ResponseEntity<List<String>> getProfissionalNomeScroll(
			@PathVariable String nomeProfissional,
			@RequestParam(required = false) Integer lastDocId,
			@RequestParam(defaultValue = "10") int pageSize) {
		try {
			ScoreDoc lastDoc = (lastDocId != null) ? new ScoreDoc(lastDocId, 0f) : null;
			List<Document> results = profissionalService.buscaPorNomeScroll(nomeProfissional, lastDoc, pageSize);
			List<String> jsonList = DocumentUtils.mapMultipleDocuments(results);
			return ResponseEntity.ok(jsonList);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}


}
