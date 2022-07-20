package br.com.gustavo.cnesCarregamentoDados.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cbo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String codigo;
	
	private String descricao;
	
	private boolean tipoSaude;
	
	private boolean regulamentado;
	
	public Cbo() {
	}

	public Cbo(String codigo, String descricao, boolean tipoSaude, boolean regulamentado) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.tipoSaude = tipoSaude;
		this.regulamentado = regulamentado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isTipoSaude() {
		return tipoSaude;
	}

	public void setTipoSaude(boolean tipoSaude) {
		this.tipoSaude = tipoSaude;
	}

	public boolean isRegulamentado() {
		return regulamentado;
	}

	public void setRegulamentado(boolean regulamentado) {
		this.regulamentado = regulamentado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descricao, regulamentado, tipoSaude);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cbo other = (Cbo) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(descricao, other.descricao)
				&& regulamentado == other.regulamentado && tipoSaude == other.tipoSaude;
	}

	@Override
	public String toString() {
		return codigo + "," + descricao + "," + tipoSaude + ","
				+ regulamentado;
	}

}
