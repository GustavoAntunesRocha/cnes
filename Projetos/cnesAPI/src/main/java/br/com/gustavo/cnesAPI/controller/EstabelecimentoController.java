package br.com.gustavo.cnesAPI.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gustavo.cnesAPI.model.Estabelecimento;
import br.com.gustavo.cnesAPI.service.EstabelecimentoService;

@RestController
@RequestMapping("/cnes")
public class EstabelecimentoController {

	@Autowired
	private EstabelecimentoService service;
	
	@GetMapping("/estabelecimento/busca/codigoCnes/{codigoCnes}")
	public String getEstabelecimentoCodigoCNES(@PathVariable String codigoCnes) {
		Estabelecimento estabelecimento = service.buscaPorCodigoCNES(Integer.parseInt(codigoCnes));
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(estabelecimento);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/estabelecimento/busca/razaoSocial/{razaoSocial}")
	public String getEstabelecimentoRazaoSocial(@PathVariable String razaoSocial) {
		Set<Estabelecimento> estabelecimentos = service.buscaPorRazaoSocial(razaoSocial);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(estabelecimentos);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/estabelecimento/busca/nomeFatasia/{nomeFatasia}")
	public String getEstabelecimentoNomeFantasia(@PathVariable String nomeFatasia) {
		Set<Estabelecimento> estabelecimentos = service.buscaPorFantasia(nomeFatasia);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(estabelecimentos);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/estabelecimento/busca/cnpj/{cnpj}")
	public String getEstabelecimentoCnpj(@PathVariable String cnpj) {
		Estabelecimento estabelecimentos = service.buscaPorCNPJ(cnpj);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(estabelecimentos);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/estabelecimento/busca/municipio/nome/{nomeMunicipio}")
	public String getEstabelecimentoMunicipioNome(@PathVariable String nomeMunicipio) {
		List<Estabelecimento> estabelecimentos = service.buscaPorNomeMunicipio(nomeMunicipio);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(estabelecimentos);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
