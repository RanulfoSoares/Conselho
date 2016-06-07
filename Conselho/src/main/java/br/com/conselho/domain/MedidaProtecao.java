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
@SequenceGenerator(name = "medida_protecao_seq", sequenceName = "medida_protecao_cod_seq", allocationSize =1)
public class MedidaProtecao {
	
	@Id
	@GeneratedValue(generator = "medida_protecao_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private RegistroMedidaAplicada registroMedida;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private MedidaAplicada medidaAplicada;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RegistroMedidaAplicada getRegistroMedida() {
		return registroMedida;
	}
	public void setRegistroMedida(RegistroMedidaAplicada registroMedida) {
		this.registroMedida = registroMedida;
	}
	public MedidaAplicada getMedidaAplicada() {
		return medidaAplicada;
	}
	public void setMedidaAplicada(MedidaAplicada medidaAplicada) {
		this.medidaAplicada = medidaAplicada;
	}

}
