package br.com.conselho.domain;

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

import br.com.conselho.util.Helper;

@Entity
@SequenceGenerator(name = "registro_medida_seq", sequenceName = "registro_medida_cod_seq", allocationSize =1)
public class RegistroMedidaAplicada {
	
	@Id
	@GeneratedValue(generator = "registro_medida_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JoinColumn(nullable = false)
	private Date data;
	
	private String conselheiro;		
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private RegistroDireitoViolado registroDireitoViolado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private MedidaAplicada medidaAplicada;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private MedidaEmRazao medidaEmRazao;
	
	@Column(length = 4000, nullable = false)
	private String descricao;
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false) habilitar quando for possivel gerar o banco novamente.
	private Conselheiro conselheiroRegistro;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registroMedidaAplicada")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VioladorRegistroMedidaAplicada> listaVioladorRegistroMedidaAplicada;
	
	public String getDataFormatada(){
		return Helper.formatDate().format(data);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}

	public RegistroDireitoViolado getRegistroDireitoViolado() {
		return registroDireitoViolado;
	}

	public void setRegistroDireitoViolado(
			RegistroDireitoViolado registroDireitoViolado) {
		this.registroDireitoViolado = registroDireitoViolado;
	}

	public MedidaAplicada getMedidaAplicada() {
		return medidaAplicada;
	}

	public void setMedidaAplicada(MedidaAplicada medidaAplicada) {
		this.medidaAplicada = medidaAplicada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public MedidaEmRazao getMedidaEmRazao() {
		return medidaEmRazao;
	}

	public void setMedidaEmRazao(MedidaEmRazao medidaEmRazao) {
		this.medidaEmRazao = medidaEmRazao;
	}		

	public Conselheiro getConselheiroRegistro() {
		return conselheiroRegistro;
	}

	public void setConselheiroRegistro(Conselheiro conselheiroRegistro) {
		this.conselheiroRegistro = conselheiroRegistro;
	}		

	public List<VioladorRegistroMedidaAplicada> getListaVioladorRegistroMedidaAplicada() {
		return listaVioladorRegistroMedidaAplicada;
	}

	public void setListaVioladorRegistroMedidaAplicada(
			List<VioladorRegistroMedidaAplicada> listaVioladorRegistroMedidaAplicada) {
		this.listaVioladorRegistroMedidaAplicada = listaVioladorRegistroMedidaAplicada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conselheiro == null) ? 0 : conselheiro.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((listaVioladorRegistroMedidaAplicada == null) ? 0
						: listaVioladorRegistroMedidaAplicada.hashCode());
		result = prime * result
				+ ((medidaAplicada == null) ? 0 : medidaAplicada.hashCode());
		result = prime * result
				+ ((medidaEmRazao == null) ? 0 : medidaEmRazao.hashCode());
		result = prime
				* result
				+ ((registroDireitoViolado == null) ? 0
						: registroDireitoViolado.hashCode());
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
		RegistroMedidaAplicada other = (RegistroMedidaAplicada) obj;
		if (conselheiro == null) {
			if (other.conselheiro != null)
				return false;
		} else if (!conselheiro.equals(other.conselheiro))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listaVioladorRegistroMedidaAplicada == null) {
			if (other.listaVioladorRegistroMedidaAplicada != null)
				return false;
		} else if (!listaVioladorRegistroMedidaAplicada
				.equals(other.listaVioladorRegistroMedidaAplicada))
			return false;
		if (medidaAplicada == null) {
			if (other.medidaAplicada != null)
				return false;
		} else if (!medidaAplicada.equals(other.medidaAplicada))
			return false;
		if (medidaEmRazao == null) {
			if (other.medidaEmRazao != null)
				return false;
		} else if (!medidaEmRazao.equals(other.medidaEmRazao))
			return false;
		if (registroDireitoViolado == null) {
			if (other.registroDireitoViolado != null)
				return false;
		} else if (!registroDireitoViolado.equals(other.registroDireitoViolado))
			return false;
		return true;
	}	
	
}
