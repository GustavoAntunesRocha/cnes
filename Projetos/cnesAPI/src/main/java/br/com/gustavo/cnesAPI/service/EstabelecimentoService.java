package br.com.gustavo.cnesAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesAPI.model.Endereco;
import br.com.gustavo.cnesAPI.model.Estabelecimento;
import br.com.gustavo.cnesAPI.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {

	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;

	@Autowired
	private EnderecoService enderecoService;

	public Estabelecimento buscaPorCodigoCNES(int codigoCNES) {
		return estabelecimentoRepository.findByCodigoCnes(codigoCNES);
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
	
	public List<Estabelecimento> buscaPorNomeEstado(String nomeEstado){
		List<Estabelecimento> estabelecimentos = new ArrayList<>();
		estabelecimentos = estabelecimentoRepository.findByEnderecoMunicipioEstadoDescricao(nomeEstado);
		return estabelecimentos;
	}
}
