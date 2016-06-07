package br.com.conselho.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name = "movermembro_seq", sequenceName = "movermembro_cod_seq", allocationSize =1)
public class MoverMembro {
	
	@Id
	@GeneratedValue(generator = "movermembro_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Familia familaAtual;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Familia familiaDestino;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Instituicao instituicaoAtual;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn()
	private Instituicao instituicaoDestino;
	
	@Column(length = 2)
	private String tipoMovimento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Membro membro;
	
	@Column(length = 2)
	private String motivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataMovimento;
	
	@Column(length = 800)
	private String obs;
	
	@Column(length = 45)
	private String conselheiro;
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false) habilitar quando for possivel gerar o banco novamente.
	private Conselheiro conselheiroRegistro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Familia getFamilaAtual() {
		return familaAtual;
	}

	public void setFamilaAtual(Familia familaAtual) {
		this.familaAtual = familaAtual;
	}

	public Familia getFamiliaDestino() {
		return familiaDestino;
	}

	public void setFamiliaDestino(Familia familiaDestino) {
		this.familiaDestino = familiaDestino;
	}

	public Instituicao getInstituicaoAtual() {
		return instituicaoAtual;
	}

	public void setInstituicaoAtual(Instituicao instituicaoAtual) {
		this.instituicaoAtual = instituicaoAtual;
	}

	public Instituicao getInstituicaoDestino() {
		return instituicaoDestino;
	}

	public void setInstituicaoDestino(Instituicao instituicaoDestino) {
		this.instituicaoDestino = instituicaoDestino;
	}

	public String getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
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

	public Conselheiro getConselheiroRegistro() {
		return conselheiroRegistro;
	}

	public void setConselheiroRegistro(Conselheiro conselheiroRegistro) {
		this.conselheiroRegistro = conselheiroRegistro;
	}	
		
}
