package br.com.conselho.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "registro_direito_violado_seq", sequenceName = "registro_direito_violado_cod_seq", allocationSize =1)
public class RegistroDireitoViolado implements Serializable {
	
	@Id
	@GeneratedValue(generator = "registro_direito_violado_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String conselheiro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataInc;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private DireitoViolado direitoViolado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Vitima vitima;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Atendimento atendimento;
	
	@Column(length = 400)
	private String obs;
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false) habilitar quando for possivel gerar o banco novamente.
	private Conselheiro conselheiroRegistro;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registroDireitoViolado")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RegistroMedidaAplicada> listaRegistroMedidaAplicada;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DireitoViolado getDireitoViolado() {
		return direitoViolado;
	}

	public void setDireitoViolado(DireitoViolado direitoViolado) {
		this.direitoViolado = direitoViolado;
	}

	public Vitima getVitima() {
		return vitima;
	}

	public void setVitima(Vitima vitima) {
		this.vitima = vitima;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}		

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}

	public Date getDataInc() {
		return dataInc;
	}

	public void setDataInc(Date dataInc) {
		this.dataInc = dataInc;
	}

	public List<RegistroMedidaAplicada> getListaRegistroMedidaAplicada() {
		return listaRegistroMedidaAplicada;
	}

	public void setListaRegistroMedidaAplicada(
			List<RegistroMedidaAplicada> listaRegistroMedidaAplicada) {
		this.listaRegistroMedidaAplicada = listaRegistroMedidaAplicada;
	}		

	public Conselheiro getConselheiroRegistro() {
		return conselheiroRegistro;
	}

	public void setConselheiroRegistro(Conselheiro conselheiroRegistro) {
		this.conselheiroRegistro = conselheiroRegistro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atendimento == null) ? 0 : atendimento.hashCode());
		result = prime * result
				+ ((conselheiro == null) ? 0 : conselheiro.hashCode());
		result = prime
				* result
				+ ((conselheiroRegistro == null) ? 0 : conselheiroRegistro
						.hashCode());
		result = prime * result + ((dataInc == null) ? 0 : dataInc.hashCode());
		result = prime * result
				+ ((direitoViolado == null) ? 0 : direitoViolado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((listaRegistroMedidaAplicada == null) ? 0
						: listaRegistroMedidaAplicada.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result + ((vitima == null) ? 0 : vitima.hashCode());
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
		RegistroDireitoViolado other = (RegistroDireitoViolado) obj;
		if (atendimento == null) {
			if (other.atendimento != null)
				return false;
		} else if (!atendimento.equals(other.atendimento))
			return false;
		if (conselheiro == null) {
			if (other.conselheiro != null)
				return false;
		} else if (!conselheiro.equals(other.conselheiro))
			return false;
		if (conselheiroRegistro == null) {
			if (other.conselheiroRegistro != null)
				return false;
		} else if (!conselheiroRegistro.equals(other.conselheiroRegistro))
			return false;
		if (dataInc == null) {
			if (other.dataInc != null)
				return false;
		} else if (!dataInc.equals(other.dataInc))
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
		if (listaRegistroMedidaAplicada == null) {
			if (other.listaRegistroMedidaAplicada != null)
				return false;
		} else if (!listaRegistroMedidaAplicada
				.equals(other.listaRegistroMedidaAplicada))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (vitima == null) {
			if (other.vitima != null)
				return false;
		} else if (!vitima.equals(other.vitima))
			return false;
		return true;
	}	
				
}
