package br.com.conselho.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.conselho.util.AutenticaUsuario;

@SuppressWarnings("serial")
@ManagedBean(name="MBAtribuicoesBusca")
@ViewScoped
public class AtribuicoesBean implements Serializable {
	
	private String template;
	
	public AtribuicoesBean(){
		template = AutenticaUsuario.verificaTemplate();
	}
	
	
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}		
	
}
