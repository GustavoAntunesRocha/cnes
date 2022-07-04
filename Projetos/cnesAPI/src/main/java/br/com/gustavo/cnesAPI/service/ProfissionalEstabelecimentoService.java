package br.com.gustavo.cnesAPI.service;

import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesAPI.model.Profissional;
import br.com.gustavo.cnesAPI.model.ProfissionalEstabelecimento;
import br.com.gustavo.cnesAPI.repository.ProfissionalEstabelecimentoRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;

@Service
public class ProfissionalEstabelecimentoService {

	@Autowired
	ProfissionalEstabelecimentoRepository profissionalEstabelecimentoRepository;

	public List<ProfissionalEstabelecimento> buscaPorNomeECnes(String nomeProfissional, long codigoCnes) {
		return profissionalEstabelecimentoRepository.buscaPorNomeECnes(nomeProfissional, codigoCnes);
	}

	public ProfissionalEstabelecimento buscaPorCnsECnes(String cns, long codigoCnes) {
		return profissionalEstabelecimentoRepository.buscaPorCnsECnes(cns, codigoCnes);
	}
	
	public List<ProfissionalEstabelecimento> buscaPorCns(String cns){
		return profissionalEstabelecimentoRepository.buscaPorCns(cns);
	}

	public String toJson(ProfissionalEstabelecimento profissionalEstabelecimento) {
		
		Profissional profissional = profissionalEstabelecimento.getProfissional();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		JsonObject jsonObj;
		for(ProfissionalEstabelecimento estabelecimento : profissional.getEstabelecimentos()) {
			arrayBuilder.add(Json.createObjectBuilder().add("nome fantasia", estabelecimento.getEstabelecimento().getFantasia())
					.add("razão social", estabelecimento.getEstabelecimento().getRazaoSocial()).add("cnes", estabelecimento.getEstabelecimento().getCodigoCnes())
					.add("cnpj", estabelecimento.getEstabelecimento().getCnpj()).add("codigo unidade", estabelecimento.getEstabelecimento().getCodigoUnidade())
			.add("ocupação", estabelecimento.getCbo().getDescricao()).add("cbo", estabelecimento.getCbo().getCodigo()).build());
		}
		JsonArray arr = arrayBuilder.build();
		jsonObj = Json.createObjectBuilder().add("nome", profissional.getNome()).add("cns", profissional.getCns())
				.addAll(Json.createObjectBuilder().add("estabelecimentos", arr))
				.build();
		StringWriter stringWriter = new StringWriter();
		JsonWriter writer = Json.createWriter(stringWriter);
		writer.writeObject(jsonObj);
		writer.close();
		return stringWriter.getBuffer().toString();
	}
}
