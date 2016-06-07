package br.com.conselho.dto;

public class AdendoDTO {
	
	private String dataCadastro;
	private String descricao;
	private String conselheiro;
	
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getConselheiro() {
		return conselheiro;
	}
	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}
}
