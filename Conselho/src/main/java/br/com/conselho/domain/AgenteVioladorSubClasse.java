package br.com.conselho.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "agente_violador_subclasse_seq", sequenceName = "agente_violador_subclasse_cod_seq", allocationSize =1)
public class AgenteVioladorSubClasse {
	
	@Id
	@GeneratedValue(generator = "agente_violador_subclasse_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(length = 100)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private AgenteVioladorClasse agenteVioladorClasse;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public AgenteVioladorClasse getAgenteVioladorClasse() {
		return agenteVioladorClasse;
	}
	public void setAgenteVioladorClasse(AgenteVioladorClasse agenteVioladorClasse) {
		this.agenteVioladorClasse = agenteVioladorClasse;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((agenteVioladorClasse == null) ? 0 : agenteVioladorClasse
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		AgenteVioladorSubClasse other = (AgenteVioladorSubClasse) obj;
		if (agenteVioladorClasse == null) {
			if (other.agenteVioladorClasse != null)
				return false;
		} else if (!agenteVioladorClasse.equals(other.agenteVioladorClasse))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
}
