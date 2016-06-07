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
@SequenceGenerator(name = "violador_parente_seq", sequenceName = "violador_parente_cod_seq", allocationSize =1)
public class VioladorParente {
	
	@Id
	@GeneratedValue(generator = "violador_parente_seq", strategy = GenerationType.SEQUENCE)
	private Long id;		
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Parente parente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private TipoAgenteViolador tipoAgenteViolador;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Atendimento atendimento;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Parente getParente() {
		return parente;
	}
	public void setParente(Parente parente) {
		this.parente = parente;
	}
	public TipoAgenteViolador getTipoAgenteViolador() {
		return tipoAgenteViolador;
	}
	public void setTipoAgenteViolador(TipoAgenteViolador tipoAgenteViolador) {
		this.tipoAgenteViolador = tipoAgenteViolador;
	}
	public Atendimento getAtendimento() {
		return atendimento;
	}
	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}
	

}
