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
@SequenceGenerator(name = "medida_judicial_aplicada_seq", sequenceName = "medida_judicial_aplicada_cod_seq", allocationSize =1)
public class MedidaJudicialAplicada {
	
	@Id
	@GeneratedValue(generator = "medida_judicial_aplicada_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private MedidaJudicial MedidaJudicial;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private DeterminacaoJudicial determinacaoJudicial;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MedidaJudicial getMedidaJudicial() {
		return MedidaJudicial;
	}
	public void setMedidaJudicial(MedidaJudicial medidaJudicial) {
		MedidaJudicial = medidaJudicial;
	}
	public DeterminacaoJudicial getDeterminacaoJudicial() {
		return determinacaoJudicial;
	}
	public void setDeterminacaoJudicial(DeterminacaoJudicial determinacaoJudicial) {
		this.determinacaoJudicial = determinacaoJudicial;
	}
		

}
