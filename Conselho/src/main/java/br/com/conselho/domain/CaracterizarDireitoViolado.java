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
@SequenceGenerator(name = "caracterizar_direito_violado_seq", sequenceName = "caracterizar_direito_violado_cod_seq", allocationSize =1)
public class CaracterizarDireitoViolado {

	@Id
	@GeneratedValue(generator = "caracterizar_direito_violado_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private CaracterizacaoViolacaoDireito caracterizacaoViolacaoDireito;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private DireitoViolado direitoViolado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CaracterizacaoViolacaoDireito getCaracterizacaoViolacaoDireito() {
		return caracterizacaoViolacaoDireito;
	}

	public void setCaracterizacaoViolacaoDireito(
			CaracterizacaoViolacaoDireito caracterizacaoViolacaoDireito) {
		this.caracterizacaoViolacaoDireito = caracterizacaoViolacaoDireito;
	}

	public DireitoViolado getDireitoViolado() {
		return direitoViolado;
	}

	public void setDireitoViolado(DireitoViolado direitoViolado) {
		this.direitoViolado = direitoViolado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((caracterizacaoViolacaoDireito == null) ? 0
						: caracterizacaoViolacaoDireito.hashCode());
		result = prime * result
				+ ((direitoViolado == null) ? 0 : direitoViolado.hashCode());
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
		CaracterizarDireitoViolado other = (CaracterizarDireitoViolado) obj;
		if (caracterizacaoViolacaoDireito == null) {
			if (other.caracterizacaoViolacaoDireito != null)
				return false;
		} else if (!caracterizacaoViolacaoDireito
				.equals(other.caracterizacaoViolacaoDireito))
			return false;
		if (direitoViolado == null) {
			if (other.direitoViolado != null)
				return false;
		} else if (!direitoViolado.equals(other.direitoViolado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}		
	
}
