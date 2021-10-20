package br.com.gustavo.cnesAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavo.cnesAPI.model.Estado;
import br.com.gustavo.cnesAPI.model.Municipio;
import br.com.gustavo.cnesAPI.repository.MunicipioRepository;

@Service
public class MunicipioService {

	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	public Municipio buscaNome(String nomeMunicipio) {
		return municipioRepository.findByNome(nomeMunicipio);
	}
	
	public List<Municipio> buscaTodosPorEstado(Estado estado){
		return municipioRepository.findByEstado(estado);
	}
	
	public List<Municipio> buscaTodosPorEstado(String nomeEstado){
		Estado estado = estadoService.buscaNome(nomeEstado);
		return buscaTodosPorEstado(estado);
	}
}
