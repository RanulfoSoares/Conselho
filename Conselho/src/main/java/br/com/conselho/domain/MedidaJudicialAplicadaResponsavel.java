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
@SequenceGenerator(name = "medida_judicial_aplicada_responsavel_seq", sequenceName = "medida_judicial_aplicada_responsavel_cod_seq", allocationSize =1)
public class MedidaJudicialAplicadaResponsavel {
	
	@Id
	@GeneratedValue(generator = "medida_judicial_aplicada_responsavel_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private MedidaJudicial medidaJudicial;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private ResponsavelAutuado responsavelAutuado;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MedidaJudicial getMedidaJudicial() {
		return medidaJudicial;
	}
	public void setMedidaJudicial(MedidaJudicial medidaJudicial) {
		this.medidaJudicial = medidaJudicial;
	}
	public ResponsavelAutuado getResponsavelAutuado() {
		return responsavelAutuado;
	}
	public void setResponsavelAutuado(ResponsavelAutuado responsavelAutuado) {
		this.responsavelAutuado = responsavelAutuado;
	}
	
	

}
