package br.com.gustavo.cnesCarregamentoDados.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ProfissionalEstabelecimento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "profissional_estabelecimento_seq", sequenceName = "profissional_estabelecimento_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profissional_estabelecimento_seq")
	private long id;
	
	@ManyToOne
	private Profissional profissional;
	
	@ManyToOne
	private Estabelecimento estabelecimento;
	
	@ManyToOne
	private Cbo cbo;
	
	private ProfissionalEstabelecimento() {
		
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
