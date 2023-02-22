package br.com.gustavo.cnesLucene.model;

import java.io.Serializable;

public class Estado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int codigo;

	private String sigla;

	private String Descricao;

	public Estado() {
	}

	public Estado(int codigo, String sigla, String descricao) {
		super();
		this.codigo = codigo;
		this.sigla = sigla;
		Descricao = descricao;
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
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	@Override
	public String toString() {
		return codigo + "," + sigla + "," + Descricao;
	}
}
