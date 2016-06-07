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
@SequenceGenerator(name = "violador_registro_medida_aplicada_seq", sequenceName = "violador_registro_medida_aplicada_cod_seq", allocationSize =1)
public class VioladorRegistroMedidaAplicada {
	
	@Id
	@GeneratedValue(generator = "violador_registro_medida_aplicada_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Violador violador;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private RegistroMedidaAplicada registroMedidaAplicada;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Violador getViolador() {
		return violador;
	}

	public void setViolador(Violador violador) {
		this.violador = violador;
	}

	public RegistroMedidaAplicada getRegistroMedidaAplicada() {
		return registroMedidaAplicada;
	}

	public void setRegistroMedidaAplicada(
			RegistroMedidaAplicada registroMedidaAplicada) {
		this.registroMedidaAplicada = registroMedidaAplicada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((registroMedidaAplicada == null) ? 0
						: registroMedidaAplicada.hashCode());
		result = prime * result
				+ ((violador == null) ? 0 : violador.hashCode());
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
		VioladorRegistroMedidaAplicada other = (VioladorRegistroMedidaAplicada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (registroMedidaAplicada == null) {
			if (other.registroMedidaAplicada != null)
				return false;
		} else if (!registroMedidaAplicada.equals(other.registroMedidaAplicada))
			return false;
		if (violador == null) {
			if (other.violador != null)
				return false;
		} else if (!violador.equals(other.violador))
			return false;
		return true;
	}	

}
