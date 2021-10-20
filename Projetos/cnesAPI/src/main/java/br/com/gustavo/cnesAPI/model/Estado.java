package br.com.gustavo.cnesAPI.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Estado {

	@Id
	private int codigo;
	
	private String sigla;
	
	private String descricao;
	
	public Estado() {}

	public Estado(int codigo, String sigla, String descricao) {
		super();
		this.codigo = codigo;
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
