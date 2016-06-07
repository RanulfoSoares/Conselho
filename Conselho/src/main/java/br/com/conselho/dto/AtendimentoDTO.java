package br.com.conselho.dto;

import java.util.List;

public class AtendimentoDTO {
	
	private String data;
	private String conselheiro;
	private String formaDenuncia;
	private String origemDenuncia;
	private String localViolacao;
	private String tipoSolicitacao;
	private String vitimas;
	private String violadores;
	private String descricaoFatos;	
	
	private List<AdendoDTO> adendos;
	private List<DireitoVioladoDTO> direitosViolados; 		
		
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getConselheiro() {
		return conselheiro;
	}
	public void setConselheiro(String conselheiro) {
		this.conselheiro = conselheiro;
	}
	public String getFormaDenuncia() {
		return formaDenuncia;
	}
	public void setFormaDenuncia(String formaDenuncia) {
		this.formaDenuncia = formaDenuncia;
	}
	public String getOrigemDenuncia() {
		return origemDenuncia;
	}
	public void setOrigemDenuncia(String origemDenuncia) {
		this.origemDenuncia = origemDenuncia;
	}
	public String getLocalViolacao() {
		return localViolacao;
	}
	public void setLocalViolacao(String localViolacao) {
		this.localViolacao = localViolacao;
	}
	public String getTipoSolicitacao() {
		return tipoSolicitacao;
	}
	public void setTipoSolicitacao(String tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}
	public String getVitimas() {
		return vitimas;
	}
	public void setVitimas(String vitimas) {
		this.vitimas = vitimas;
	}
	public String getVioladores() {
		return violadores;
	}
	public void setVioladores(String violadores) {
		this.violadores = violadores;
	}
	public String getDescricaoFatos() {
		return descricaoFatos;
	}
	public void setDescricaoFatos(String descricaoFatos) {
		this.descricaoFatos = descricaoFatos;
	}
	
	public List<DireitoVioladoDTO> getDireitosViolados() {						
		return direitosViolados;
	}
	public void setDireitosViolados(List<DireitoVioladoDTO> direitosViolados) {
		this.direitosViolados = direitosViolados;
	}
	public List<AdendoDTO> getAdendos() {
		return adendos;
	}
	public void setAdendos(List<AdendoDTO> adendos) {
		this.adendos = adendos;
	}
		

}
