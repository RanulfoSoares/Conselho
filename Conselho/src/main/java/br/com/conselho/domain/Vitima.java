package br.com.conselho.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "vitima_seq", sequenceName = "vitima_cod_seq", allocationSize =1)
public class Vitima implements Serializable {
	
	@Id
	@GeneratedValue(generator = "vitima_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Membro membro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Atendimento atendimento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Adendo adendoIncluir;
	
	@Column(length = 3)
	private Integer idade;
	
	private String escolaridade;
	
	private Boolean trabalha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}	

	public Adendo getAdendoIncluir() {
		return adendoIncluir;
	}

	public void setAdendoIncluir(Adendo adendo) {
		this.adendoIncluir = adendo;
	}		

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public Boolean getTrabalha() {
		return trabalha;
	}

	public void setTrabalha(Boolean trabalha) {
		this.trabalha = trabalha;
	}		
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adendoIncluir == null) ? 0 : adendoIncluir.hashCode());
		result = prime * result
				+ ((atendimento == null) ? 0 : atendimento.hashCode());
		result = prime * result
				+ ((escolaridade == null) ? 0 : escolaridade.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idade == null) ? 0 : idade.hashCode());
		result = prime * result + ((membro == null) ? 0 : membro.hashCode());
		result = prime * result
				+ ((trabalha == null) ? 0 : trabalha.hashCode());
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
		Vitima other = (Vitima) obj;
		if (adendoIncluir == null) {
			if (other.adendoIncluir != null)
				return false;
		} else if (!adendoIncluir.equals(other.adendoIncluir))
			return false;
		if (atendimento == null) {
			if (other.atendimento != null)
				return false;
		} else if (!atendimento.equals(other.atendimento))
			return false;
		if (escolaridade == null) {
			if (other.escolaridade != null)
				return false;
		} else if (!escolaridade.equals(other.escolaridade))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idade == null) {
			if (other.idade != null)
				return false;
		} else if (!idade.equals(other.idade))
			return false;
		if (membro == null) {
			if (other.membro != null)
				return false;
		} else if (!membro.equals(other.membro))
			return false;
		if (trabalha == null) {
			if (other.trabalha != null)
				return false;
		} else if (!trabalha.equals(other.trabalha))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return membro.getPessoa().getNomeCompleto();
	}
	
	
}
