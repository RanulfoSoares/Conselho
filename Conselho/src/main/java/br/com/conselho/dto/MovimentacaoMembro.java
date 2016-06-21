package br.com.conselho.dto;

public class MovimentacaoMembro {
	
	private String nomeMembro;
	private String familiaOrigem;
	private String familiaDestina;
	
	public String getNomeMembro() {
		return nomeMembro;
	}
	public void setNomeMembro(String nomeMembro) {
		this.nomeMembro = nomeMembro;
	}
	public String getFamiliaOrigem() {
		return familiaOrigem;
	}
	public void setFamiliaOrigem(String familiaOrigem) {
		this.familiaOrigem = familiaOrigem;
	}
	public String getFamiliaDestina() {
		return familiaDestina;
	}
	public void setFamiliaDestina(String familiaDestina) {
		this.familiaDestina = familiaDestina;
	}		
}
