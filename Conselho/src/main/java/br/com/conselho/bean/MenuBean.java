package br.com.conselho.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@ManagedBean(name="MBMenu")
@SessionScoped
public class MenuBean {
	
	private Integer index = 0;	
	
	 public String logout() {
	        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	        HttpSession session = request.getSession();
	        session.removeAttribute("conselheiroLogado");
	        
	        return "login?faces-redirect=true";
	    }
	
	public void menu0(){
			index = 0;
	}
	 
	public void menu1(){
		index = 1;
	}	
	public void menu2(){
		index = 2;
	}	
	public void menu3(){
		index = 3;
	}
	public void menu4(){
		index = 4;
	}	
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}					
	
}
