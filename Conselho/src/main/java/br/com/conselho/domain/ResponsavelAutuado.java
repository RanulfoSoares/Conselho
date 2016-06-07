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
@SequenceGenerator(name = "responsavel_autuado_seq", sequenceName = "responsavel_autuado_cod_seq", allocationSize =1)
public class ResponsavelAutuado {
	
	@Id
	@GeneratedValue(generator = "responsavel_autuado_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private DeterminacaoJudicial determinacaoJudicial;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Responsavel responsavel;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DeterminacaoJudicial getDeterminacaoJudicial() {
		return determinacaoJudicial;
	}
	public void setDeterminacaoJudicial(DeterminacaoJudicial determinacaoJudicial) {
		this.determinacaoJudicial = determinacaoJudicial;
	}
	public Responsavel getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}
	
	

}
