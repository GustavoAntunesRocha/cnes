package br.com.gustavo.cnesLucene.model;

import java.io.Serializable;

public class Municipio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int codigo;

	private String nome;

	private Estado estado;

	public Municipio() {
	}

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

	@Override
	public String toString() {
		return codigo + "," + nome + "," + estado;
	}

}
