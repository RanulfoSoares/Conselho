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
@SequenceGenerator(name = "crianca_adolescente_violado_seq", sequenceName = "crianca_adolescente_violado_cod_seq", allocationSize =1)
public class CriancaAdolescenteViolado {
	
	@Id
	@GeneratedValue(generator = "crianca_adolescente_violado_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Atendimento atendimento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private CriancaAdolescente criancaAdolescente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Atendimento getAtendimento() {
		return atendimento;
	}
	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}
	public CriancaAdolescente getCriancaAdolescente() {
		return criancaAdolescente;
	}
	public void setCriancaAdolescente(CriancaAdolescente criancaAdolescente) {
		this.criancaAdolescente = criancaAdolescente;
	}
	
	

}
