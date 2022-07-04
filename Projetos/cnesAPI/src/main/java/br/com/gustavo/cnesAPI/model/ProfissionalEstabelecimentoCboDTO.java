package br.com.gustavo.cnesAPI.model;

public class ProfissionalEstabelecimentoCboDTO {
	
	private String profissionaisCodigo;
	
	private String estabelecimentosCodigo;
	
	private String cbo;

	public ProfissionalEstabelecimentoCboDTO(String profissionaisCodigo, String estabelecimentosCodigo, String cbo) {
		super();
		this.profissionaisCodigo = profissionaisCodigo;
		this.estabelecimentosCodigo = estabelecimentosCodigo;
		this.cbo = cbo;
	}

	public String getProfissionaisCodigo() {
		return profissionaisCodigo;
	}

	public void setProfissionaisCodigo(String profissionaisCodigo) {
		this.profissionaisCodigo = profissionaisCodigo;
	}

	public String getEstabelecimentosCodigo() {
		return estabelecimentosCodigo;
	}

	public void setEstabelecimentosCodigo(String estabelecimentosCodigo) {
		this.estabelecimentosCodigo = estabelecimentosCodigo;
	}

	public String getCbo() {
		return cbo;
	}

	public void setCbo(String cbo) {
		this.cbo = cbo;
	}

}
