package br.com.gustavo.cnesAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesAPI.exceptions.ProfissionalNotFoundException;
import br.com.gustavo.cnesAPI.model.ProfissionalEstabelecimento;
import br.com.gustavo.cnesAPI.repository.ProfissionalRepository;

@Service
public class ProfissionalService {
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Autowired
	private ProfissionalEstabelecimentoService profissionalEstabelecimentoService;
	
	@Autowired 
	private EstabelecimentoService estabelecimentoService;
	
	public List<ProfissionalEstabelecimento> buscaPorNomeECnes(String nomeProfissional, long codigoCnes){
		return profissionalEstabelecimentoService.buscaPorNomeECnes(nomeProfissional, codigoCnes);
	}
	
	public ProfissionalEstabelecimento buscaPorCnsECnes(String cns, long codigoCnes){
		ProfissionalEstabelecimento profissionalEstabelecimento = profissionalEstabelecimentoService.buscaPorCnsECnes(cns, codigoCnes);
		if(profissionalEstabelecimento != null) {
			return profissionalEstabelecimento;
		} else {
			throw new ProfissionalNotFoundException(existePorCnsCnes(cns,codigoCnes));
		}
		 
	}
	
	public boolean existePorCns(String cns) {
		if(profissionalRepository.findByCns(cns).isEmpty()) {
			return false;
		}
		return true;
	}
	
	public String existePorCnsCnes(String cns, long codigoCnes) {
		boolean existePorCnes = estabelecimentoService.existePorCNES(codigoCnes);
		boolean existePorCns = existePorCns(cns);
		if(!existePorCns) {
			return "Não encontrado, CNS inexistente!";
		} else if(!existePorCnes) {
			return "Não encontrado, CNES inexistente!";
		} else {
			return "Não encontrado, CNS não lotado no dado CNES!";
		}
	}

}
