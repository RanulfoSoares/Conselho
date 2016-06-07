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
@SequenceGenerator(name = "determinacao_aplicada_seq", sequenceName = "determinacao_aplicada_cod_seq", allocationSize =1)
public class DeterminacaoAplicada {
	
	@Id
	@GeneratedValue(generator = "determinacao_aplicada_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Determinacao determinacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Atribuicao atribuicao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Determinacao getDeterminacao() {
		return determinacao;
	}

	public void setDeterminacao(Determinacao determinacao) {
		this.determinacao = determinacao;
	}

	public Atribuicao getAtribuicao() {
		return atribuicao;
	}

	public void setAtribuicao(Atribuicao atribuicao) {
		this.atribuicao = atribuicao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atribuicao == null) ? 0 : atribuicao.hashCode());
		result = prime * result
				+ ((determinacao == null) ? 0 : determinacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DeterminacaoAplicada other = (DeterminacaoAplicada) obj;
		if (atribuicao == null) {
			if (other.atribuicao != null)
				return false;
		} else if (!atribuicao.equals(other.atribuicao))
			return false;
		if (determinacao == null) {
			if (other.determinacao != null)
				return false;
		} else if (!determinacao.equals(other.determinacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}		

}
