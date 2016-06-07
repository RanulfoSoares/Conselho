package br.com.conselho.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "responsavel_seq", sequenceName = "responsavel_cod_seq", allocationSize =1)
public class Responsavel {
	
	@Id
	@GeneratedValue(generator = "responsavel_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pessoa pessoa;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Instituicao instituicao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)	
	private GrauResponsabilidade grauResponsabilidade;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private CriancaAdolescente criancaAdolescente;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public GrauResponsabilidade getGrauResponsabilidade() {
		return grauResponsabilidade;
	}
	public void setGrauResponsabilidade(GrauResponsabilidade grauResponsabilidade) {
		this.grauResponsabilidade = grauResponsabilidade;
	}
	public CriancaAdolescente getCriancaAdolescente() {
		return criancaAdolescente;
	}
	public void setCriancaAdolescente(CriancaAdolescente criancaAdolescente) {
		this.criancaAdolescente = criancaAdolescente;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((criancaAdolescente == null) ? 0 : criancaAdolescente
						.hashCode());
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
		Responsavel other = (Responsavel) obj;
		if (criancaAdolescente == null) {
			if (other.criancaAdolescente != null)
				return false;
		} else if (!criancaAdolescente.equals(other.criancaAdolescente))
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
