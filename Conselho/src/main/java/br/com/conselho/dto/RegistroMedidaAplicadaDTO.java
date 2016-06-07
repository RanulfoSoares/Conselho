package br.com.conselho.dto;

public class RegistroMedidaAplicadaDTO {
	
	private String MedidaAplicada;
	private String quantidade;
	private Double porcentagem;		
	
	public RegistroMedidaAplicadaDTO() {
		super();
	}
	public RegistroMedidaAplicadaDTO(String medidaAplicada) {
		super();
		MedidaAplicada = medidaAplicada;
	}
	public String getMedidaAplicada() {
		return MedidaAplicada;
	}
	public void setMedidaAplicada(String medidaAplicada) {
		MedidaAplicada = medidaAplicada;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public Double getPorcentagem() {
		return porcentagem;
	}
	public void setPorcentagem(Double porcentagem) {
		this.porcentagem = porcentagem;
	}
	
	

}
