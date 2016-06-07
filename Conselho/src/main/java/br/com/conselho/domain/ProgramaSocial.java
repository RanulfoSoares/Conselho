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
@SequenceGenerator(name = "programa_social_seq", sequenceName = "programa_social_cod_seq", allocationSize =1)
public class ProgramaSocial {
	
	@Id
	@GeneratedValue(generator = "programa_social_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Beneficio Beneficio;
	
	@Column(length = 60, nullable = false)
	private String esfera;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Setor setor;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date inicioBeneficio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date terminoBeneficio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private CriancaAdolescente criancaAdolescente;	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Beneficio getBeneficio() {
		return Beneficio;
	}
	public void setBeneficio(Beneficio beneficio) {
		Beneficio = beneficio;
	}
	public String getEsfera() {
		return esfera;
	}
	public void setEsfera(String esfera) {
		this.esfera = esfera;
	}	
	public Date getInicioBeneficio() {
		return inicioBeneficio;
	}
	public void setInicioBeneficio(Date inicioBeneficio) {
		this.inicioBeneficio = inicioBeneficio;
	}
	public Date getTerminoBeneficio() {
		return terminoBeneficio;
	}
	public void setTerminoBeneficio(Date terminoBeneficio) {
		this.terminoBeneficio = terminoBeneficio;
	}
	
	public CriancaAdolescente getCriancaAdolescente() {
		return criancaAdolescente;
	}
	public void setCriancaAdolescente(CriancaAdolescente criancaAdolescente) {
		this.criancaAdolescente = criancaAdolescente;
	}
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
}
