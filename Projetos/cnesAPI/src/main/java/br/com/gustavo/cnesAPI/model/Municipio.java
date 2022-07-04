package br.com.gustavo.cnesAPI.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Municipio {

	@Id
	private int codigo;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "co_estado")
	private Estado estado;
	
	public Municipio() {}

	public Municipio(int codigo, String nome, Estado estado) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.estado = estado;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
}
