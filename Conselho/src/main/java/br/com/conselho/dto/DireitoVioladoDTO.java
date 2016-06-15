package br.com.conselho.dto;

import java.util.List;

public class DireitoVioladoDTO {
	
	private String direitoViolado;
	private String grupoDireito;
	private String vitima;
	private String obs;
	private String caminhoSub;
	private List<MedidaAplicadaDTO> medidasAplicadas;
	
	private String conselheiro;
	private String dataInc;

	public String getDireitoViolado() {
		return direitoViolado;
	}

	public void setDireitoViolado(String direitoViolado) {
		this.direitoViolado = direitoViolado;
	}

	public List<MedidaAplicadaDTO> getMedidasAplicadas() {
		return medidasAplicadas;
	}

	public void setMedidasAplicadas(List<MedidaAplicadaDTO> medidasAplicadas) {
		this.medidasAplicadas = medidasAplicadas;
	}

	public String getGrupoDireito() {
		return grupoDireito;
	}

	public void setGrupoDireito(String grupoDireito) {
		this.grupoDireito = grupoDireito;
	}

	public String getVitima() {
		return vitima;
	}

	public void setVitima(String vitima) {
		this.vitima = vitima;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getCaminhoSub() {
		return caminhoSub;
	}

	public void setCaminhoSub(String caminhoSub) {
		this.caminhoSub = caminhoSub;
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
