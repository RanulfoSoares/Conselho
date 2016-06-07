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
@SequenceGenerator(name = "parentesco_seq", sequenceName = "Parentesco_cod_seq", allocationSize =1)
public class Parentesco {
	
	@Id
	@GeneratedValue(generator = "parentesco_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Membro parentePrincipal;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Membro parente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private GrauParentesco grauParentesco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}		

	public Membro getParentePrincipal() {
		return parentePrincipal;
	}

	public void setParentePrincipal(Membro parentePrincipal) {
		this.parentePrincipal = parentePrincipal;
	}	

	public Membro getParente() {
		return parente;
	}

	public void setParente(Membro parente) {
		this.parente = parente;
	}

	public GrauParentesco getGrauParentesco() {
		return grauParentesco;
	}

	public void setGrauParentesco(GrauParentesco grauParentesco) {
		this.grauParentesco = grauParentesco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((grauParentesco == null) ? 0 : grauParentesco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((parente == null) ? 0 : parente.hashCode());
		result = prime
				* result
				+ ((parentePrincipal == null) ? 0 : parentePrincipal.hashCode());
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
		Parentesco other = (Parentesco) obj;
		if (grauParentesco == null) {
			if (other.grauParentesco != null)
				return false;
		} else if (!grauParentesco.equals(other.grauParentesco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parente == null) {
			if (other.parente != null)
				return false;
		} else if (!parente.equals(other.parente))
			return false;
		if (parentePrincipal == null) {
			if (other.parentePrincipal != null)
				return false;
		} else if (!parentePrincipal.equals(other.parentePrincipal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Parentesco [id=" + id + ", parentePrincipal="
				+ parentePrincipal + ", parente=" + parente
				+ ", grauParentesco=" + grauParentesco + "]";
	}

	
}
