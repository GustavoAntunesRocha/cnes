package br.com.gustavo.cnesCarregamentoDados.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Profissional {

	@Id
	private String codigoProfissional;

	private String nome;

	private String cns;

	@OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL)
	private List<ProfissionalEstabelecimento> estabelecimentos = new ArrayList<>();

	public Profissional() {
	}

	public Profissional(String codigoProfissional, String nome, String cns,
			List<ProfissionalEstabelecimento> estabelecimentos) {
		super();
		this.codigoProfissional = codigoProfissional;
		this.nome = nome;
		this.cns = cns;
		this.estabelecimentos = estabelecimentos;
	}

	public String getCodigoProfissional() {
		return codigoProfissional;
	}

	public void setCodigoProfissional(String codigoProfissional) {
		this.codigoProfissional = codigoProfissional;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCns() {
		return cns;
	}

	public void setCns(String cns) {
		this.cns = cns;
	}


	public List<ProfissionalEstabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(List<ProfissionalEstabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cns, codigoProfissional, estabelecimentos, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profissional other = (Profissional) obj;
		return Objects.equals(cns, other.cns) && Objects.equals(codigoProfissional, other.codigoProfissional)
				&& Objects.equals(estabelecimentos, other.estabelecimentos) && Objects.equals(nome, other.nome);
	}

}
