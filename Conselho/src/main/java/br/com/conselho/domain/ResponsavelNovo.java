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
@SequenceGenerator(name = "responsavel_novo_seq", sequenceName = "responsavel_novo_cod_seq", allocationSize =1)
public class ResponsavelNovo {
	
	@Id
	@GeneratedValue(generator = "responsavel_novo_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Membro membroPrincipal;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Membro membro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private GrauResponsabilidade grauResponsabilidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public Membro getMembroPrincipal() {
		return membroPrincipal;
	}

	public void setMembroPrincipal(Membro membroPrincipal) {
		this.membroPrincipal = membroPrincipal;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public GrauResponsabilidade getGrauResponsabilidade() {
		return grauResponsabilidade;
	}

	public void setGrauResponsabilidade(GrauResponsabilidade grauResponsabilidade) {
		this.grauResponsabilidade = grauResponsabilidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((grauResponsabilidade == null) ? 0 : grauResponsabilidade
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((membro == null) ? 0 : membro.hashCode());
		result = prime * result
				+ ((membroPrincipal == null) ? 0 : membroPrincipal.hashCode());
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
		ResponsavelNovo other = (ResponsavelNovo) obj;
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
		if (membro == null) {
			if (other.membro != null)
				return false;
		} else if (!membro.equals(other.membro))
			return false;
		if (membroPrincipal == null) {
			if (other.membroPrincipal != null)
				return false;
		} else if (!membroPrincipal.equals(other.membroPrincipal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponsavelNovo [id=" + id + ", membroPrincipal="
				+ membroPrincipal + ", membro=" + membro
				+ ", grauResponsabilidade=" + grauResponsabilidade + "]";
	}	
	
}
