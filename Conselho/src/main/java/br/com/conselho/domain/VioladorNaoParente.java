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
@SequenceGenerator(name = "violador_nao_parente_seq", sequenceName = "violador_nao_parente_cod_seq", allocationSize =1)
public class VioladorNaoParente {
	
	@Id
	@GeneratedValue(generator = "violador_nao_parente_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pessoa pessoa;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private TipoAgenteViolador tipoAgenteViolador;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Instituicao instituicao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Atendimento atendimento;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public TipoAgenteViolador getTipoAgenteViolador() {
		return tipoAgenteViolador;
	}
	public void setTipoAgenteViolador(TipoAgenteViolador tipoAgenteViolador) {
		this.tipoAgenteViolador = tipoAgenteViolador;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Atendimento getAtendimento() {
		return atendimento;
	}
	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}
	
	

}
