package br.com.conselho.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import br.com.conselho.domain.Conselheiro;


public class AutenticaUsuario {
	
	public static String verificaTemplate(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        Conselheiro currentConselho = (Conselheiro) session.getAttribute("conselheiroLogado");
        
        String templateSelecionado = "";
        
        if(currentConselho != null){
        	
        	switch (currentConselho.getNivelAcesso()) {
			case "cor":
				System.out.println("Usuario CONSELHEIRO COORDENADOR");
        		templateSelecionado = "/WEB-INF/template/template_adm.xhtml";
				break;
			
			case "con":
				System.out.println("Usuario CONSELHEIRO");
        		templateSelecionado = "/WEB-INF/template/template_usu.xhtml";
				break;
			
			case "col":
				System.out.println("Usuario COLABORADOR");
        		templateSelecionado = "/WEB-INF/template/template_adm.xhtml";
				break;
			
			case "rel":
				System.out.println("Usuario RELATORIO");
        		templateSelecionado = "/WEB-INF/template/template_rel.xhtml";
				break;

			default:
				break;
			}
        	        	
        }
        
        return templateSelecionado;
        
	}
}
