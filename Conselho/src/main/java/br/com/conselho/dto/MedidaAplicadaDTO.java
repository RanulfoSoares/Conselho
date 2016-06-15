package br.com.conselho.dto;

public class MedidaAplicadaDTO {
	
	private String medidaAplicada;
	private String medidaRazao;
	private String violador;
	private String obs;
	
	private String conselheiro;
	private String dataInc;
	
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
	public String getConselheiro() {
		return conselheiro;
	}
	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}
	public String getDataInc() {
		return dataInc;
	}
	public void setDataInc(String dataInc) {
		this.dataInc = dataInc;
	}	
}
