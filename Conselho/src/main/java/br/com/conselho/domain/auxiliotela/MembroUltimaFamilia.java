package br.com.conselho.domain.auxiliotela;

import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Membro;

public class MembroUltimaFamilia {
	
	private Membro membro;
	private  Familia ultimaFamilia;	
	
	public Membro getMembro() {
		return membro;
	}
	public void setMembro(Membro membro) {
		this.membro = membro;
	}
	public Familia getUltimaFamilia() {
		return ultimaFamilia;
	}
	public void setUltimaFamilia(Familia ultimaFamilia) {
		this.ultimaFamilia = ultimaFamilia;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((membro == null) ? 0 : membro.hashCode());
		result = prime * result
				+ ((ultimaFamilia == null) ? 0 : ultimaFamilia.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MembroUltimaFamilia other = (MembroUltimaFamilia) obj;
		if (membro == null) {
			if (other.membro != null)
				return false;
		} else if (!membro.equals(other.membro))
			return false;
		if (ultimaFamilia == null) {
			if (other.ultimaFamilia != null)
				return false;
		} else if (!ultimaFamilia.equals(other.ultimaFamilia))
			return false;
		return true;
	}
	
	

}
