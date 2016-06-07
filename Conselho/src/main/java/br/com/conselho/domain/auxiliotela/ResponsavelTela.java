package br.com.conselho.domain.auxiliotela;

import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.GrauParentesco;
import br.com.conselho.domain.GrauResponsabilidade;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Parente;
import br.com.conselho.domain.Pessoa;

public class ResponsavelTela {
	
	private Long id;
	private CriancaAdolescente crianca;
	private GrauResponsabilidade grauResponsabilidade;
	
	private Boolean editavel;
	private Parente parente;
	private Pessoa pessoa;
	private Instituicao instituicao;
	public CriancaAdolescente getCrianca() {
		return crianca;
	}
	public void setCrianca(CriancaAdolescente crianca) {
		this.crianca = crianca;
	}
	public GrauResponsabilidade getGrauResponsabilidade() {
		return grauResponsabilidade;
	}
	public void setGrauResponsabilidade(GrauResponsabilidade grauResponsabilidade) {
		this.grauResponsabilidade = grauResponsabilidade;
	}
	public Boolean getEditavel() {
		return editavel;
	}
	public void setEditavel(Boolean editavel) {
		this.editavel = editavel;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}		
	
	public Parente getParente() {
		return parente;
	}
	public void setParente(Parente parente) {
		this.parente = parente;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crianca == null) ? 0 : crianca.hashCode());
		result = prime * result
				+ ((editavel == null) ? 0 : editavel.hashCode());
		result = prime
				* result
				+ ((grauResponsabilidade == null) ? 0 : grauResponsabilidade
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((instituicao == null) ? 0 : instituicao.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponsavelTela other = (ResponsavelTela) obj;
		if (crianca == null) {
			if (other.crianca != null)
				return false;
		} else if (!crianca.equals(other.crianca))
			return false;
		if (editavel == null) {
			if (other.editavel != null)
				return false;
		} else if (!editavel.equals(other.editavel))
			return false;
		if (grauResponsabilidade == null) {
			if (other.grauResponsabilidade != null)
				return false;
		} else if (!grauResponsabilidade.equals(other.grauResponsabilidade))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instituicao == null) {
			if (other.instituicao != null)
				return false;
		} else if (!instituicao.equals(other.instituicao))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		return true;
	}	
}
