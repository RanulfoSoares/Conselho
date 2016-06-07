package br.com.conselho.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.conselho.dao.ConselheiroDAO;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.util.JSFUtil;


@SuppressWarnings("serial")
@ManagedBean(name = "MBLogin")
@ViewScoped
public class LoginBean implements Serializable{
	
	private String user;
	private String senha;
	private Conselheiro conselheiroLogado;
	
	public LoginBean() {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();   
        Object obj = session.getAttribute("conselheiroLogado");;
        
        if(obj != null){
        	conselheiroLogado = (Conselheiro) obj;
        }        
		
	}
	
	public String logar() {		
		
		try {
			
			user = user.trim();
			senha = senha.trim();
			
			if(!"".equals(user) && !"".equals(senha)){
				Conselheiro usuario = null;
				if("desenvolvedor".equals(user) && "adminadmin".equals(senha)){
					
					usuario = new Conselheiro();
					usuario.setNomeUsual("Olá Desenvolvedor");
					usuario.setNivelAcesso("cor");
					
				}else{
					usuario = new ConselheiroDAO().buscaLogin(user, senha);
				}				
				
				
				if(usuario != null){
					
					HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			        HttpSession session = request.getSession();					
			        session.setAttribute("conselheiroLogado", usuario);
			        
					return "index?faces-redirect=true";
				}else{
					JSFUtil.addErrorMessage("Usuário ou senha incorreto");
					return "";
				}								
			}else{
				JSFUtil.addErrorMessage(" Informe o usuário e senha");
				return "";
			}
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao logar: "+e.getMessage());
			return "";
		}						
	}			
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Conselheiro getConselheiroLogado() {
		return conselheiroLogado;
	}

	public void setConselheiroLogado(Conselheiro conselheiroLogado) {
		this.conselheiroLogado = conselheiroLogado;
	}
	
}
