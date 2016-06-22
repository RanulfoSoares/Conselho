package br.com.conselho.dto;

public class MovimentacaoMembro {
	
	private String nomeMembro;
	private String origem;
	private String destino;	
	private String motivo;
	private String conselheiroAtuante;
	private String data;
	private String obs;
	
	public String getNomeMembro() {
		return nomeMembro;
	}
	public void setNomeMembro(String nomeMembro) {
		this.nomeMembro = nomeMembro;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}	
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getConselheiroAtuante() {
		return conselheiroAtuante;
	}
	public void setConselheiroAtuante(String conselheiroAtuante) {
		this.conselheiroAtuante = conselheiroAtuante;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}	
}
