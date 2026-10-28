package br.com.gustavo.cnesLucene.controller;

import java.util.List;

import org.apache.lucene.document.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.gustavo.cnesLucene.DocumentUtils;
import br.com.gustavo.cnesLucene.service.EstabelecimentoService;

@RestController
@RequestMapping("/estabelecimento")
public class EstabelecimentoController {

	private final EstabelecimentoService estabelecimentoService;

	public EstabelecimentoController(EstabelecimentoService estabelecimentoService) {
		this.estabelecimentoService = estabelecimentoService;
	}

	@GetMapping(value = "/existe/{cnes}")
	public boolean existe(@PathVariable String cnes) {
		return estabelecimentoService.existePorCnes(cnes);
	}

	@GetMapping(value = "/busca/codigoProfissional/{codigo}")
	public ResponseEntity<List<String>> buscaPorCodigoProfissional(@PathVariable String codigo) {
		return responder(estabelecimentoService.buscaPorCodigoProfissional(codigo));
	}

	@GetMapping(value = "/busca/codigoCnes/{codigo}")
	public ResponseEntity<List<String>> buscaPorCodigoCnes(@PathVariable String codigo) {
		return responder(estabelecimentoService.buscaPorCodigoCnes(codigo));
	}

	@GetMapping(value = "/busca/codigoUnidade/{codigo}")
	public ResponseEntity<List<String>> buscaPorCodigoUnidade(@PathVariable String codigo) {
		return responder(estabelecimentoService.buscaPorCodigoUnidade(codigo));
	}

	@GetMapping(value = "/busca/cnpj/{cnpj}")
	public ResponseEntity<List<String>> buscaPorCnpj(@PathVariable String cnpj) {
		return responder(estabelecimentoService.buscaPorCnpj(cnpj));
	}

	@GetMapping(value = "/busca/razaoSocial/{razaoSocial}")
	public ResponseEntity<List<String>> buscaPorRazaoSocial(@PathVariable String razaoSocial) {
		return responder(estabelecimentoService.buscaPorRazaoSocial(razaoSocial));
	}

	@GetMapping(value = "/busca/fantasia/{fantasia}")
	public ResponseEntity<List<String>> buscaPorFantasia(@PathVariable String fantasia) {
		return responder(estabelecimentoService.buscaPorFantasia(fantasia));
	}

	@GetMapping(value = "/busca/endereco/{endereco}")
	public ResponseEntity<List<String>> buscaPorEndereco(@PathVariable String endereco) {
		return responder(estabelecimentoService.buscaPorEndereco(endereco));
	}

	@GetMapping(value = "/busca/endereco")
	public ResponseEntity<List<String>> buscaPorEndereco(
			@RequestParam(required = false) String logradouro,
			@RequestParam(required = false) String numero,
			@RequestParam(required = false) String complemento,
			@RequestParam(required = false) String bairro,
			@RequestParam(required = false) String cep,
			@RequestParam(required = false) String municipio,
			@RequestParam(required = false) String estado,
			@RequestParam(required = false) String telefone,
			@RequestParam(required = false) String email) {
		return buscaCombinada(null, null, logradouro, numero, complemento, bairro, cep,
				municipio, estado, telefone, email);
	}

	@GetMapping(value = "/busca/combinada")
	public ResponseEntity<List<String>> buscaCombinada(
			@RequestParam(required = false) String razaoSocial,
			@RequestParam(required = false) String fantasia,
			@RequestParam(required = false) String logradouro,
			@RequestParam(required = false) String numero,
			@RequestParam(required = false) String complemento,
			@RequestParam(required = false) String bairro,
			@RequestParam(required = false) String cep,
			@RequestParam(required = false) String municipio,
			@RequestParam(required = false) String estado,
			@RequestParam(required = false) String telefone,
			@RequestParam(required = false) String email) {
		Map<String, String> criterios = new LinkedHashMap<>();
		criterios.put("razaoSocial", razaoSocial);
		criterios.put("fantasia", fantasia);
		criterios.put("logradouro", logradouro);
		criterios.put("numero", numero);
		criterios.put("complemento", complemento);
		criterios.put("bairro", bairro);
		criterios.put("cep", cep);
		criterios.put("municipio", municipio);
		criterios.put("estado", estado);
		criterios.put("telefone", telefone);
		criterios.put("email", email);
		return responder(estabelecimentoService.buscaCombinada(criterios));
	}

	@GetMapping(value = "/busca/profissionais/{codigo}")
	public ResponseEntity<List<String>> buscaPorProfissionais(@PathVariable String codigo) {
		return responder(estabelecimentoService.buscaPorProfissionais(codigo));
	}

	private ResponseEntity<List<String>> responder(List<Document> results) {
		try {
			List<String> jsonList = DocumentUtils.mapMultipleDocuments(results);
			return ResponseEntity.ok(jsonList);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
