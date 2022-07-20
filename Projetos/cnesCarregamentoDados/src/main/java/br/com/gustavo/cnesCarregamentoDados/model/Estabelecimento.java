package br.com.gustavo.cnesCarregamentoDados.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Estabelecimento implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long codigoCnes;

	@Id
	private String codigoUnidade;

	private String cnpj;

	private String razaoSocial;

	private String fantasia;

	@OneToMany
	private Set<Endereco> endereco = new HashSet<Endereco>();

	@OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL)
	private List<ProfissionalEstabelecimento> profissionais = new ArrayList<>();


	public Estabelecimento() {
	}

	public Estabelecimento(long codigoCnes, String codigoUnidade, String cnpj, String razaoSocial, String fantasia,
			Set<Endereco> endereco) {
		super();
		this.codigoCnes = codigoCnes;
		this.codigoUnidade = codigoUnidade;
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.fantasia = fantasia;
		this.endereco = endereco;
	}

	public long getCodigoCnes() {
		return codigoCnes;
	}

	public void setCodigoCnes(long codigoCnes) {
		this.codigoCnes = codigoCnes;
	}

	public String getCodigoUnidade() {
		return codigoUnidade;
	}

	public void setCodigoUnidade(String codigoUnidade) {
		this.codigoUnidade = codigoUnidade;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public Set<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(Set<Endereco> endereco) {
		this.endereco = endereco;
	}

	public List<ProfissionalEstabelecimento> getProfissionais() {
		return profissionais;
	}

	public void setProfissionais(List<ProfissionalEstabelecimento> profissionais) {
		this.profissionais = profissionais;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoCnes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estabelecimento other = (Estabelecimento) obj;
		return codigoCnes == other.codigoCnes;
	}

	@Override
	public String toString() {
		return codigoCnes + "," + codigoUnidade + "," + cnpj
				+ "," + razaoSocial + "," + fantasia;
	}

}
