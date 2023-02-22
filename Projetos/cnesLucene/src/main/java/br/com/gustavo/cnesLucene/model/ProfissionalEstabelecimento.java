package br.com.gustavo.cnesLucene.model;

import java.io.Serializable;
import java.util.Objects;

public class ProfissionalEstabelecimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	private Profissional profissional;

	private Estabelecimento estabelecimento;

	private Cbo cbo;

	public ProfissionalEstabelecimento() {

	}

	public ProfissionalEstabelecimento(Profissional profissional, Estabelecimento estabelecimento, Cbo cbo) {
		super();
		this.profissional = profissional;
		this.estabelecimento = estabelecimento;
		this.cbo = cbo;
	}

	public long getId() {
		return id;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Cbo getCbo() {
		return cbo;
	}

	public void setCbo(Cbo cbo) {
		this.cbo = cbo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cbo, estabelecimento, id, profissional);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfissionalEstabelecimento other = (ProfissionalEstabelecimento) obj;
		return Objects.equals(cbo, other.cbo) && Objects.equals(estabelecimento, other.estabelecimento)
				&& id == other.id && Objects.equals(profissional, other.profissional);
	}

}
