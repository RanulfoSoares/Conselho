package br.com.conselho.dto;

import java.util.List;

public class NucleoFamiliarDTO {
	
	private String numeroNucleo;
	private String dataInicial;
	private String dataFinal;
	private String conselheiro;
	private String criancas;
	private String adultos;
	private String endereco;
	private String obs;
	
	private List<MovimentacaoMembro> listaSaidaMembros;
	private List<MovimentacaoMembro> listaEntradaMembros;
		
	
	public String getNumeroNucleo() {
		return numeroNucleo;
	}
	public void setNumeroNucleo(String numeroNucleo) {
		this.numeroNucleo = numeroNucleo;
	}
	public String getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getConselheiro() {
		return conselheiro;
	}
	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}
	public String getCriancas() {
		return criancas;
	}
	public void setCriancas(String criancas) {
		this.criancas = criancas;
	}
	public String getAdultos() {
		return adultos;
	}
	public void setAdultos(String adultos) {
		this.adultos = adultos;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public List<MovimentacaoMembro> getListaSaidaMembros() {
		return listaSaidaMembros;
	}
	public void setListaSaidaMembros(List<MovimentacaoMembro> listaSaidaMembros) {
		this.listaSaidaMembros = listaSaidaMembros;
	}
	public List<MovimentacaoMembro> getListaEntradaMembros() {
		return listaEntradaMembros;
	}
	public void setListaEntradaMembros(List<MovimentacaoMembro> listaEntradaMembros) {
		this.listaEntradaMembros = listaEntradaMembros;
	}	
}
