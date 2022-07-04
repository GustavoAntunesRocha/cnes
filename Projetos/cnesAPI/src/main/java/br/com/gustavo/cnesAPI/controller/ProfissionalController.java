package br.com.gustavo.cnesAPI.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gustavo.cnesAPI.exceptions.ProfissionalNotFoundException;
import br.com.gustavo.cnesAPI.model.Profissional;
import br.com.gustavo.cnesAPI.model.ProfissionalEstabelecimento;
import br.com.gustavo.cnesAPI.service.ProfissionalEstabelecimentoService;
import br.com.gustavo.cnesAPI.service.ProfissionalService;

@RestController
@RequestMapping("/profissional")
public class ProfissionalController {
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@Autowired
	private ProfissionalEstabelecimentoService profissionalEstabelecimentoService;
	
	@GetMapping("/busca/nome/{nomeProfissional}/cnes/{codigoCnes}")
	public String getProfissionalCodigoCNES(@PathVariable String nomeProfissional, @PathVariable String codigoCnes) {
		List<ProfissionalEstabelecimento> profissionalEstabelecimentoList = profissionalService.buscaPorNomeECnes(nomeProfissional, Long.parseLong(codigoCnes));
		List<Profissional> profissionais = new ArrayList<>();
		for(ProfissionalEstabelecimento obj : profissionalEstabelecimentoList) {
			profissionais.add(obj.getProfissional());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(profissionais);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/busca/cns/{codigoCns}/cnes/{codigoCnes}")
	public String getProfissionalCodigoCNS(@PathVariable String codigoCns, @PathVariable String codigoCnes) {
		ProfissionalEstabelecimento profissionalEstabelecimento;
		try {
			profissionalEstabelecimento = profissionalService.buscaPorCnsECnes(codigoCns, Long.parseLong(codigoCnes));
			return profissionalEstabelecimentoService.toJson(profissionalEstabelecimento);
		} catch (ProfissionalNotFoundException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}

}
