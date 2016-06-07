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
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.conselho.util.Helper;

@Entity
@SequenceGenerator(name = "atendimento_seq", sequenceName = "atendimento_cod_seq", allocationSize =1)
public class Atendimento {
	
	@Id
	@GeneratedValue(generator = "atendimento_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataFato;
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false) habilitar quando for possivel gerar o banco novamente.
	private Conselheiro conselheiroRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRegistro;
	
	@Column(length = 70, nullable = false)
	private String nomeDenunciante;
	
	@Column(length = 11)
	private String foneIDenunciante;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Logradouro logradouroFato;
	
	@Column(length = 6)
	private String numeroLocalFato;
	
	@Column(length = 30)
	private String complementoLocalFato;
	
	@Column(length = 30)
	private String conselheiro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private TipoAtendimento TipoAtendimento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private OrigemAtendimento OrigemAtendiemnto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private ViaSolicitacao ViaSolitacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private TipoLocalFato tipoLocalFato;
	
	@Column(length = 4000, nullable = false)
	private String descricaoAtendimento;
	
	@Column(length = 150)
	private String pontoReferencia;
	
	@Column(length = 2)
	private String fundamentoDenuncia;
	
	private Boolean violadorNaoIdentificado;	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "atendimento")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Vitima> listaVitimas;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "atendimento")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Violador> listaAgenteViolador;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "atendimento")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Atribuicao> listaAtribuicoes;
	
	@Transient
	private String resumoDescricao;
	
	public String getDataFatoFormatada(){
		return Helper.formatDate().format(dataFato);
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataFato() {
		return dataFato;
	}

	public void setDataFato(Date dataFato) {
		this.dataFato = dataFato;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getNomeDenunciante() {
		return nomeDenunciante;
	}

	public void setNomeDenunciante(String nomeDenunciante) {
		this.nomeDenunciante = nomeDenunciante;
	}	

	public Logradouro getLogradouroFato() {
		return logradouroFato;
	}

	public void setLogradouroFato(Logradouro logradouroFato) {
		this.logradouroFato = logradouroFato;
	}

	public String getNumeroLocalFato() {
		return numeroLocalFato;
	}

	public void setNumeroLocalFato(String numeroLocalFato) {
		this.numeroLocalFato = numeroLocalFato;
	}

	public String getComplementoLocalFato() {
		return complementoLocalFato;
	}

	public void setComplementoLocalFato(String complementoLocalFato) {
		this.complementoLocalFato = complementoLocalFato;
	}

	public String getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}

	public TipoAtendimento getTipoAtendimento() {
		return TipoAtendimento;
	}

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		TipoAtendimento = tipoAtendimento;
	}

	public OrigemAtendimento getOrigemAtendiemnto() {
		return OrigemAtendiemnto;
	}

	public void setOrigemAtendiemnto(OrigemAtendimento origemAtendiemnto) {
		OrigemAtendiemnto = origemAtendiemnto;
	}

	public ViaSolicitacao getViaSolitacao() {
		return ViaSolitacao;
	}

	public void setViaSolitacao(ViaSolicitacao viaSolitacao) {
		ViaSolitacao = viaSolitacao;
	}

	public String getDescricaoAtendimento() {
		return descricaoAtendimento;
	}

	public void setDescricaoAtendimento(String descricaoAtendimento) {
		this.descricaoAtendimento = descricaoAtendimento;
	}

	public TipoLocalFato getTipoLocalFato() {
		return tipoLocalFato;
	}

	public void setTipoLocalFato(TipoLocalFato tipoLocalFato) {
		this.tipoLocalFato = tipoLocalFato;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public String getFundamentoDenuncia() {
		return fundamentoDenuncia;
	}

	public void setFundamentoDenuncia(String fundamentoDenuncia) {
		this.fundamentoDenuncia = fundamentoDenuncia;
	}		

	public List<Vitima> getListaVitimas() {
		return listaVitimas;
	}

	public void setListaVitimas(List<Vitima> listaVitimas) {
		this.listaVitimas = listaVitimas;
	}

	public List<Violador> getListaAgenteViolador() {
		return listaAgenteViolador;
	}

	public void setListaAgenteViolador(List<Violador> listaAgenteViolador) {
		this.listaAgenteViolador = listaAgenteViolador;
	}	

	public String getFoneIDenunciante() {
		return foneIDenunciante;
	}

	public void setFoneIDenunciante(String foneIDenunciante) {
		this.foneIDenunciante = foneIDenunciante;
	}		

	public String getResumoDescricao() {
		
		if(descricaoAtendimento.length() <= 210){
			resumoDescricao = descricaoAtendimento.substring(0, descricaoAtendimento.length());
		}else{
			resumoDescricao = descricaoAtendimento.substring(0, 210);
		}		
		
		return resumoDescricao;
	}

	public void setResumoDescricao(String resumoDescricao) {		
		this.resumoDescricao = resumoDescricao;
	}		

	public Conselheiro getConselheiroRegistro() {
		return conselheiroRegistro;
	}


	public void setConselheiroRegistro(Conselheiro conselheiroRegistro) {
		this.conselheiroRegistro = conselheiroRegistro;
	}		


	public Boolean getVioladorNaoIdentificado() {
		return violadorNaoIdentificado;
	}


	public void setVioladorNaoIdentificado(Boolean violadorNaoIdentificado) {
		this.violadorNaoIdentificado = violadorNaoIdentificado;
	}		

	public List<Atribuicao> getListaAtribuicoes() {
		return listaAtribuicoes;
	}

	public void setListaAtribuicoes(List<Atribuicao> listaAtribuicoes) {
		this.listaAtribuicoes = listaAtribuicoes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((OrigemAtendiemnto == null) ? 0 : OrigemAtendiemnto
						.hashCode());
		result = prime * result
				+ ((TipoAtendimento == null) ? 0 : TipoAtendimento.hashCode());
		result = prime * result
				+ ((ViaSolitacao == null) ? 0 : ViaSolitacao.hashCode());
		result = prime
				* result
				+ ((complementoLocalFato == null) ? 0 : complementoLocalFato
						.hashCode());
		result = prime * result
				+ ((conselheiro == null) ? 0 : conselheiro.hashCode());
		result = prime
				* result
				+ ((conselheiroRegistro == null) ? 0 : conselheiroRegistro
						.hashCode());
		result = prime * result
				+ ((dataFato == null) ? 0 : dataFato.hashCode());
		result = prime * result
				+ ((dataRegistro == null) ? 0 : dataRegistro.hashCode());
		result = prime
				* result
				+ ((descricaoAtendimento == null) ? 0 : descricaoAtendimento
						.hashCode());
		result = prime
				* result
				+ ((foneIDenunciante == null) ? 0 : foneIDenunciante.hashCode());
		result = prime
				* result
				+ ((fundamentoDenuncia == null) ? 0 : fundamentoDenuncia
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((listaAgenteViolador == null) ? 0 : listaAgenteViolador
						.hashCode());
		result = prime * result
				+ ((listaVitimas == null) ? 0 : listaVitimas.hashCode());
		result = prime * result
				+ ((logradouroFato == null) ? 0 : logradouroFato.hashCode());
		result = prime * result
				+ ((nomeDenunciante == null) ? 0 : nomeDenunciante.hashCode());
		result = prime * result
				+ ((numeroLocalFato == null) ? 0 : numeroLocalFato.hashCode());
		result = prime * result
				+ ((pontoReferencia == null) ? 0 : pontoReferencia.hashCode());
		result = prime * result
				+ ((resumoDescricao == null) ? 0 : resumoDescricao.hashCode());
		result = prime * result
				+ ((tipoLocalFato == null) ? 0 : tipoLocalFato.hashCode());
		result = prime
				* result
				+ ((violadorNaoIdentificado == null) ? 0
						: violadorNaoIdentificado.hashCode());
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
		Atendimento other = (Atendimento) obj;
		if (OrigemAtendiemnto == null) {
			if (other.OrigemAtendiemnto != null)
				return false;
		} else if (!OrigemAtendiemnto.equals(other.OrigemAtendiemnto))
			return false;
		if (TipoAtendimento == null) {
			if (other.TipoAtendimento != null)
				return false;
		} else if (!TipoAtendimento.equals(other.TipoAtendimento))
			return false;
		if (ViaSolitacao == null) {
			if (other.ViaSolitacao != null)
				return false;
		} else if (!ViaSolitacao.equals(other.ViaSolitacao))
			return false;
		if (complementoLocalFato == null) {
			if (other.complementoLocalFato != null)
				return false;
		} else if (!complementoLocalFato.equals(other.complementoLocalFato))
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
		if (dataFato == null) {
			if (other.dataFato != null)
				return false;
		} else if (!dataFato.equals(other.dataFato))
			return false;
		if (dataRegistro == null) {
			if (other.dataRegistro != null)
				return false;
		} else if (!dataRegistro.equals(other.dataRegistro))
			return false;
		if (descricaoAtendimento == null) {
			if (other.descricaoAtendimento != null)
				return false;
		} else if (!descricaoAtendimento.equals(other.descricaoAtendimento))
			return false;
		if (foneIDenunciante == null) {
			if (other.foneIDenunciante != null)
				return false;
		} else if (!foneIDenunciante.equals(other.foneIDenunciante))
			return false;
		if (fundamentoDenuncia == null) {
			if (other.fundamentoDenuncia != null)
				return false;
		} else if (!fundamentoDenuncia.equals(other.fundamentoDenuncia))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listaAgenteViolador == null) {
			if (other.listaAgenteViolador != null)
				return false;
		} else if (!listaAgenteViolador.equals(other.listaAgenteViolador))
			return false;
		if (listaVitimas == null) {
			if (other.listaVitimas != null)
				return false;
		} else if (!listaVitimas.equals(other.listaVitimas))
			return false;
		if (logradouroFato == null) {
			if (other.logradouroFato != null)
				return false;
		} else if (!logradouroFato.equals(other.logradouroFato))
			return false;
		if (nomeDenunciante == null) {
			if (other.nomeDenunciante != null)
				return false;
		} else if (!nomeDenunciante.equals(other.nomeDenunciante))
			return false;
		if (numeroLocalFato == null) {
			if (other.numeroLocalFato != null)
				return false;
		} else if (!numeroLocalFato.equals(other.numeroLocalFato))
			return false;
		if (pontoReferencia == null) {
			if (other.pontoReferencia != null)
				return false;
		} else if (!pontoReferencia.equals(other.pontoReferencia))
			return false;
		if (resumoDescricao == null) {
			if (other.resumoDescricao != null)
				return false;
		} else if (!resumoDescricao.equals(other.resumoDescricao))
			return false;
		if (tipoLocalFato == null) {
			if (other.tipoLocalFato != null)
				return false;
		} else if (!tipoLocalFato.equals(other.tipoLocalFato))
			return false;
		if (violadorNaoIdentificado == null) {
			if (other.violadorNaoIdentificado != null)
				return false;
		} else if (!violadorNaoIdentificado
				.equals(other.violadorNaoIdentificado))
			return false;
		return true;
	}	
		
}
