package br.com.gustavo.cnesAPI.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesAPI.model.Endereco;
import br.com.gustavo.cnesAPI.model.Estabelecimento;
import br.com.gustavo.cnesAPI.model.ProfissionalEstabelecimento;
import br.com.gustavo.cnesAPI.repository.EstabelecimentoRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;

@Service
public class EstabelecimentoService {

	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private ProfissionalEstabelecimentoService profissionalEstabelecimentoService;

	public Estabelecimento buscaPorCodigoCNES(long codigoCNES) {
		return estabelecimentoRepository.findByCodigoCnes(codigoCNES);
	}

	public boolean existePorCNES(long codigoCnes) {
		return estabelecimentoRepository.existsByCodigoCnes(codigoCnes);
	}

	public Estabelecimento buscaPorCNPJ(String cnpj) {
		return estabelecimentoRepository.findByCnpj(cnpj);
	}

	public Set<Estabelecimento> buscaPorRazaoSocial(String razaoSocial) {
		return estabelecimentoRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial);
	}

	public Set<Estabelecimento> buscaPorFantasia(String fantasia) {
		return estabelecimentoRepository.findByFantasiaContainingIgnoreCase(fantasia);
	}

	public Estabelecimento buscaPorEndereco(Endereco endereco) {

		Endereco endereco2 = enderecoService.buscaEnderecoCodigo(endereco.getCodigo());
		if (endereco2 != null) {
			return estabelecimentoRepository.findByEndereco(endereco2);
		}
		return null;
	}

	public List<Estabelecimento> buscaPorNomeMunicipio(String nomeMunicipio) {
		List<Estabelecimento> estabelecimentos = new ArrayList<>();
		estabelecimentos = estabelecimentoRepository.findByEnderecoMunicipioNome(nomeMunicipio);
		return estabelecimentos;
	}

	public List<Estabelecimento> buscaPorNomeEstado(String nomeEstado) {
		List<Estabelecimento> estabelecimentos = new ArrayList<>();
		estabelecimentos = estabelecimentoRepository.findByEnderecoMunicipioEstadoDescricao(nomeEstado);
		return estabelecimentos;
	}

	public List<Estabelecimento> buscaPorProfissionalCns(String cns) {
		List<ProfissionalEstabelecimento> profissionalEstabelecimentoList = profissionalEstabelecimentoService
				.buscaPorCns(cns);
		List<Estabelecimento> estabelecimentos = new ArrayList<>();
		for (ProfissionalEstabelecimento profissionalEstabelecimento : profissionalEstabelecimentoList) {
			estabelecimentos.add(profissionalEstabelecimento.getEstabelecimento());
		}
		return estabelecimentos;
	}

	public String toJson(List<Estabelecimento> estabelecimentos) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		JsonObject jsonObj;
		for (Estabelecimento estabelecimento : estabelecimentos) {
			arrayBuilder.add(Json.createObjectBuilder().add("nome fantasia", estabelecimento.getFantasia())
					.add("razão social", estabelecimento.getRazaoSocial()).add("cnes", estabelecimento.getCodigoCnes())
					.add("cnpj", estabelecimento.getCnpj()).add("codigo unidade", estabelecimento.getCodigoUnidade())
					.build());
		}
		JsonArray arr = arrayBuilder.build();
		jsonObj = Json.createObjectBuilder().add("estabelecimentos", arr).build();
		StringWriter stringWriter = new StringWriter();
		JsonWriter writer = Json.createWriter(stringWriter);
		writer.writeObject(jsonObj);
		writer.close();
		return stringWriter.getBuffer().toString();
	}
}
