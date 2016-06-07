package br.com.conselho.dto;

public class MedidaAplicadaDTO {
	
	private String medidaAplicada;
	private String medidaRazao;
	private String violador;
	private String obs;
	
	public String getMedidaAplicada() {
		return medidaAplicada;
	}
	public void setMedidaAplicada(String medidaAplicada) {
		this.medidaAplicada = medidaAplicada;
	}
	public String getMedidaRazao() {
		return medidaRazao;
	}
	public void setMedidaRazao(String medidaRazao) {
		this.medidaRazao = medidaRazao;
	}
	public String getViolador() {
		return violador;
	}
	public void setViolador(String violador) {
		this.violador = violador;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}		
}
