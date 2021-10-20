package br.com.gustavo.cnesAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesAPI.model.Estado;
import br.com.gustavo.cnesAPI.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> buscaTodos(){
		return estadoRepository.findAll();
	}
	
	public Estado buscaSigla(String sigla) {
		return estadoRepository.findBySigla(sigla);
	}
	
	public Estado buscaNome(String nomeEstado) {
		return estadoRepository.findByDescricao(nomeEstado);
	}
}
