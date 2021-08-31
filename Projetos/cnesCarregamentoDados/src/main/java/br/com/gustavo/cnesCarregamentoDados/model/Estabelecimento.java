package br.com.gustavo.cnesCarregamentoDados.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Estabelecimento {
	
	@Id
	private int codigoCnes;
	
	private String codigoUnidade;
	
	private String cnpj;
	
	private String razaoSocial;
	
	private String fantasia;
	
	@OneToMany
	(mappedBy = "estabelecimento")
	private List<Endereco> endereco;
	
	public Estabelecimento() {}

	public Estabelecimento(int codigoCnes, String codigoUnidade, String cnpj, String razaoSocial, String fantasia,
			List<Endereco> endereco) {
		super();
		this.codigoCnes = codigoCnes;
		this.codigoUnidade = codigoUnidade;
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.fantasia = fantasia;
		this.endereco = endereco;
	}

	public int getCodigoCnes() {
		return codigoCnes;
	}

	public void setCodigoCnes(int codigoCnes) {
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

	public List<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}
	
	
	
}
