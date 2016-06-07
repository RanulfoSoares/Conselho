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
@SequenceGenerator(name = "parente_seq", sequenceName = "parente_cod_seq", allocationSize =1)
public class Parente {
	
	@Id
	@GeneratedValue(generator = "parente_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Pessoa parente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private GrauParentesco grauParentesco;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private CriancaAdolescente criancaAdolescente;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Pessoa getParente() {
		return parente;
	}
	public void setParente(Pessoa parente) {
		this.parente = parente;
	}
	public GrauParentesco getGrauParentesco() {
		return grauParentesco;
	}
	public void setGrauParentesco(GrauParentesco grauParentesco) {
		this.grauParentesco = grauParentesco;
	}
	public CriancaAdolescente getCriancaAdolescente() {
		return criancaAdolescente;
	}
	public void setCriancaAdolescente(CriancaAdolescente criancaAdolescente) {
		this.criancaAdolescente = criancaAdolescente;
	}
	
	

}
