package br.com.conselho.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

@Entity
@SequenceGenerator(name = "crianca_adolescente_seq", sequenceName = "crianca_adolescente_cod_seq", allocationSize =1)
public class CriancaAdolescente {
	
	@Id
	@GeneratedValue(generator = "crianca_adolescente_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Pessoa pessoa;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Instituicao escola;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "criancaAdolescente", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	private List<Parente> listaParente;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "criancaAdolescente", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	private List<Responsavel> listaResponsavel;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "criancaAdolescente", cascade = CascadeType.ALL)	
	private List<DeterminacaoJudicial> listaDeterminacaoJudicial;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "criancaAdolescente", cascade = CascadeType.ALL)	
	private List<ProgramaSocial> listaProgramaSocial;
	
	
	public Long getId() {
		return id;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Responsavel> getListaResponsavel() {
		return listaResponsavel;
	}
	public void setListaResponsavel(List<Responsavel> listaResponsavel) {
		this.listaResponsavel = listaResponsavel;
	}
	public List<DeterminacaoJudicial> getListaDeterminacaoJudicial() {
		return listaDeterminacaoJudicial;
	}
	public void setListaDeterminacaoJudicial(
			List<DeterminacaoJudicial> listaDeterminacaoJudicial) {
		this.listaDeterminacaoJudicial = listaDeterminacaoJudicial;
	}
	public List<ProgramaSocial> getListaProgramaSocial() {
		return listaProgramaSocial;
	}
	public void setListaProgramaSocial(List<ProgramaSocial> listaProgramaSocial) {
		this.listaProgramaSocial = listaProgramaSocial;
	}
	public List<Parente> getListaParente() {
		return listaParente;
	}
	public void setListaParente(List<Parente> listaParente) {
		this.listaParente = listaParente;
	}
	public Instituicao getEscola() {
		return escola;
	}
	public void setEscola(Instituicao escola) {
		this.escola = escola;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((escola == null) ? 0 : escola.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((listaDeterminacaoJudicial == null) ? 0
						: listaDeterminacaoJudicial.hashCode());
		result = prime * result
				+ ((listaParente == null) ? 0 : listaParente.hashCode());
		result = prime
				* result
				+ ((listaProgramaSocial == null) ? 0 : listaProgramaSocial
						.hashCode());
		result = prime
				* result
				+ ((listaResponsavel == null) ? 0 : listaResponsavel.hashCode());
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
		CriancaAdolescente other = (CriancaAdolescente) obj;
		if (escola == null) {
			if (other.escola != null)
				return false;
		} else if (!escola.equals(other.escola))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listaDeterminacaoJudicial == null) {
			if (other.listaDeterminacaoJudicial != null)
				return false;
		} else if (!listaDeterminacaoJudicial
				.equals(other.listaDeterminacaoJudicial))
			return false;
		if (listaParente == null) {
			if (other.listaParente != null)
				return false;
		} else if (!listaParente.equals(other.listaParente))
			return false;
		if (listaProgramaSocial == null) {
			if (other.listaProgramaSocial != null)
				return false;
		} else if (!listaProgramaSocial.equals(other.listaProgramaSocial))
			return false;
		if (listaResponsavel == null) {
			if (other.listaResponsavel != null)
				return false;
		} else if (!listaResponsavel.equals(other.listaResponsavel))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		return true;
	}	
	
	
}
