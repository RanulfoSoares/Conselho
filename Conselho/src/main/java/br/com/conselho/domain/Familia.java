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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
//@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"numeroPasta"})})
@SequenceGenerator(name = "familia_seq", sequenceName = "familia_cod_seq", allocationSize =1)
@SuppressWarnings("serial")
public class Familia implements Serializable {
	
	@Id
	@GeneratedValue(generator = "familia_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(length = 500)
	private String obs;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Logradouro logradouro;
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false) habilitar quando for possivel gerar o banco novamente.
	private Conselheiro conselheiroRegistro;
	
	@Column(length = 6)
	private String numero;
	
	@Column(length = 6)
	private String numeroPasta;
	
	@Column(length = 30)
	private String complemento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataCadastro;
	
	@Column(length = 30)
	private String conselheiro;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "familia")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Membro> listaMembros; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}

	public String getNumeroPasta() {
		return numeroPasta;
	}

	public void setNumeroPasta(String numeroPasta) {
		this.numeroPasta = numeroPasta;
	}	

	public List<Membro> getListaMembros() {
		return listaMembros;
	}

	public void setListaMembros(List<Membro> listaMembros) {
		this.listaMembros = listaMembros;
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
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result
				+ ((conselheiro == null) ? 0 : conselheiro.hashCode());
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result
				+ ((numeroPasta == null) ? 0 : numeroPasta.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
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
		Familia other = (Familia) obj;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (conselheiro == null) {
			if (other.conselheiro != null)
				return false;
		} else if (!conselheiro.equals(other.conselheiro))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (numeroPasta == null) {
			if (other.numeroPasta != null)
				return false;
		} else if (!numeroPasta.equals(other.numeroPasta))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		return true;
	}
	
	
	
}
