package br.com.conselho.dto;

public class RegistroDiretitoVioladoDTO {
	
	private String direitoViolado;
	private String quantidade;
	private Double porcentagem;		
	
	public RegistroDiretitoVioladoDTO() {
		super();
	}		

	public RegistroDiretitoVioladoDTO(String direitoViolado) {
		super();
		this.direitoViolado = direitoViolado;
	}



	public RegistroDiretitoVioladoDTO(String direitoViolado, String quantidade,
			Double porcentagem) {
		super();
		this.direitoViolado = direitoViolado;
		this.quantidade = quantidade;
		this.porcentagem = porcentagem;
	}
	
	public String getDireitoViolado() {
		return direitoViolado;
	}
	public void setDireitoViolado(String direitoViolado) {
		this.direitoViolado = direitoViolado;
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
