package br.com.conselho.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.conselho.util.Helper;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "conselheiro_seq", sequenceName = "conselheiro_cod_seq", allocationSize =1)
public class Conselheiro implements Serializable {
	
	@Id
	@GeneratedValue(generator = "conselheiro_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Pessoa pessoa;
	
	@Column(length = 20, nullable = false)
	private String nomeUsual;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;		
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Logradouro logradouro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Conselheiro conselheiroRegistro;
	
	@Column(length = 6, nullable = false)
	private String numero;
	
	@Column(length = 100)
	private String complemento;
	
	@Column(length = 70)
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date primeiroMandatoInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date primeiroMandatoTermino;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date segundoMandatoInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date segundoMandatoTermino;
	
	@Column(length = 300)
	private String obs;
	
	@Column(length = 30)
	private String senha;
	
	@Column(length = 3)
	private String funcao;
	
	@Column(length = 3)
	private String nivelAcesso;
	
	@Column(length = 1)
	private String flgAtivo;
	
	@Transient
	public String getprimeiroMandatoInicioFormatado(){
		if(primeiroMandatoInicio != null){
			return Helper.formatDate().format(primeiroMandatoInicio);
		}else{
			return "";
		}
		
	}
	
	@Transient
	public String getprimeiroMandatoTerminoFormatado(){
		if(primeiroMandatoTermino != null){
			return Helper.formatDate().format(primeiroMandatoTermino);
		}else{
			return "";
		}		
	}
	
	@Transient
	public String getNomeNivelAcesso(){
		switch (nivelAcesso) {
		case "cor":
			return "Conselheiro Coordenador";			

		case "con":
			return "Conselheiro";			
		
		case "col":
			return "Colaborador";			
		
		case "rel":
			return "Relatorio";
		
		default:
			return "Não Especificado";			
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getPrimeiroMandatoInicio() {
		return primeiroMandatoInicio;
	}

	public void setPrimeiroMandatoInicio(Date primeiroMandatoInicio) {
		this.primeiroMandatoInicio = primeiroMandatoInicio;
	}

	public Date getPrimeiroMandatoTermino() {
		return primeiroMandatoTermino;
	}

	public void setPrimeiroMandatoTermino(Date primeiroMandatoTermino) {
		this.primeiroMandatoTermino = primeiroMandatoTermino;
	}

	public Date getSegundoMandatoInicio() {
		return segundoMandatoInicio;
	}

	public void setSegundoMandatoInicio(Date segundoMandatoInicio) {
		this.segundoMandatoInicio = segundoMandatoInicio;
	}

	public Date getSegundoMandatoTermino() {
		return segundoMandatoTermino;
	}

	public void setSegundoMandatoTermino(Date segundoMandatoTermino) {
		this.segundoMandatoTermino = segundoMandatoTermino;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public String getFlgAtivo() {
		return flgAtivo;
	}

	public void setFlgAtivo(String flgAtivo) {
		this.flgAtivo = flgAtivo;
	}

	public String getNomeUsual() {
		return nomeUsual;
	}

	public void setNomeUsual(String nomeUsual) {
		this.nomeUsual = nomeUsual;
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
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((flgAtivo == null) ? 0 : flgAtivo.hashCode());
		result = prime * result + ((funcao == null) ? 0 : funcao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nivelAcesso == null) ? 0 : nivelAcesso.hashCode());
		result = prime * result
				+ ((nomeUsual == null) ? 0 : nomeUsual.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime
				* result
				+ ((primeiroMandatoInicio == null) ? 0 : primeiroMandatoInicio
						.hashCode());
		result = prime
				* result
				+ ((primeiroMandatoTermino == null) ? 0
						: primeiroMandatoTermino.hashCode());
		result = prime
				* result
				+ ((segundoMandatoInicio == null) ? 0 : segundoMandatoInicio
						.hashCode());
		result = prime
				* result
				+ ((segundoMandatoTermino == null) ? 0 : segundoMandatoTermino
						.hashCode());
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
		Conselheiro other = (Conselheiro) obj;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (flgAtivo == null) {
			if (other.flgAtivo != null)
				return false;
		} else if (!flgAtivo.equals(other.flgAtivo))
			return false;
		if (funcao == null) {
			if (other.funcao != null)
				return false;
		} else if (!funcao.equals(other.funcao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nivelAcesso == null) {
			if (other.nivelAcesso != null)
				return false;
		} else if (!nivelAcesso.equals(other.nivelAcesso))
			return false;
		if (nomeUsual == null) {
			if (other.nomeUsual != null)
				return false;
		} else if (!nomeUsual.equals(other.nomeUsual))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (primeiroMandatoInicio == null) {
			if (other.primeiroMandatoInicio != null)
				return false;
		} else if (!primeiroMandatoInicio.equals(other.primeiroMandatoInicio))
			return false;
		if (primeiroMandatoTermino == null) {
			if (other.primeiroMandatoTermino != null)
				return false;
		} else if (!primeiroMandatoTermino.equals(other.primeiroMandatoTermino))
			return false;
		if (segundoMandatoInicio == null) {
			if (other.segundoMandatoInicio != null)
				return false;
		} else if (!segundoMandatoInicio.equals(other.segundoMandatoInicio))
			return false;
		if (segundoMandatoTermino == null) {
			if (other.segundoMandatoTermino != null)
				return false;
		} else if (!segundoMandatoTermino.equals(other.segundoMandatoTermino))
			return false;
		return true;
	}	
	
}
