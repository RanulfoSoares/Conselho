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

@Entity
@SequenceGenerator(name = "determinacao_judicial_seq", sequenceName = "determinacao_judicial_cod_seq", allocationSize =1)
public class DeterminacaoJudicial {

	@Id
	@GeneratedValue(generator = "determinacao_judicial_seq", strategy = GenerationType.SEQUENCE)
	private Long id;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date inicio;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date termino;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
    private Date dataAtual;
	
	@ManyToOne
    private Setor setor;
	
	@Column(length = 300)
    private String obs;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private CriancaAdolescente criancaAdolescente;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "determinacaoJudicial", cascade = CascadeType.ALL)
    private List<ResponsavelAutuado> listaResponsavelAutuado;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "determinacaoJudicial", cascade = CascadeType.ALL)
    private List<MedidaJudicialAplicada> listaMedidaJudicialAplicada;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CriancaAdolescente getCriancaAdolescente() {
		return criancaAdolescente;
	}
	public void setCriancaAdolescente(CriancaAdolescente criancaAdolescente) {
		this.criancaAdolescente = criancaAdolescente;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getTermino() {
		return termino;
	}
	public void setTermino(Date termino) {
		this.termino = termino;
	}
	public Date getDataAtual() {
		return dataAtual;
	}
	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public List<ResponsavelAutuado> getListaResponsavelAutuado() {
		return listaResponsavelAutuado;
	}
	public void setListaResponsavelAutuado(
			List<ResponsavelAutuado> listaResponsavelAutuado) {
		this.listaResponsavelAutuado = listaResponsavelAutuado;
	}
}
