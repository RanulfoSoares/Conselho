package br.com.conselho.dto;

import java.util.List;

public class AtribuicaoDTO {
	
	private String data;
	private String conselheiro;
	private String descumpridor;
	private String descricao;	
	private List<DeterminacaoAplicadaDTO> listaDeterminacaoAplicada;
	private String caminhoSub;
	
	
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
	public String getDescumpridor() {
		return descumpridor;
	}
	public void setDescumpridor(String descumpridor) {
		this.descumpridor = descumpridor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<DeterminacaoAplicadaDTO> getListaDeterminacaoAplicada() {
		return listaDeterminacaoAplicada;
	}
	public void setListaDeterminacaoAplicada(
			List<DeterminacaoAplicadaDTO> listaDeterminacaoAplicada) {
		this.listaDeterminacaoAplicada = listaDeterminacaoAplicada;
	}
	public String getCaminhoSub() {
		return caminhoSub;
	}
	public void setCaminhoSub(String caminhoSub) {
		this.caminhoSub = caminhoSub;
	}	
	
}

