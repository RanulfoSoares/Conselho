package br.com.conselho.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.com.conselho.domain.Conselheiro;

@SuppressWarnings("serial")
public class AutenticaSessao implements PhaseListener {
	
	private static final String homepage = "login.xhtml";
	
	@Override
	public void afterPhase(PhaseEvent event) {

		FacesContext facesContext = event.getFacesContext();
		
		if (facesContext == null || facesContext.getViewRoot() == null
				|| facesContext.getViewRoot().getViewId() == null) {
			try {
				ExternalContext externalContext = facesContext.getExternalContext();
				String contextPath = externalContext.getRequestContextPath();
				externalContext.redirect(contextPath + homepage);
				System.out.println("[AutenticaSessao][afterPhase] Redirecionando: "+contextPath + homepage);
				facesContext.responseComplete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String currentPage = facesContext.getViewRoot().getViewId();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(Boolean.TRUE);

		Boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1);
		
		Boolean isConselheiroBuscaPage = (currentPage.lastIndexOf("conselheiro_busca.xhtml") > -1);
		Boolean isConselheiroCadastroPage = (currentPage.lastIndexOf("conselheiro_cadastro.xhtml") > -1);
		Boolean isRelatorioPage = (currentPage.lastIndexOf("relatorios.xhtml") > -1);
		Boolean isIndexPage = (currentPage.lastIndexOf("index.xhtml") > -1);				
		
		Object usuarioLogado = session.getAttribute("conselheiroLogado");
		
		Conselheiro conselheiroLogado = (Conselheiro) usuarioLogado;

		if (!isLoginPage && usuarioLogado == null) {
			String errorPageLocation = "/paginas/"+ homepage;
			facesContext.setViewRoot(facesContext.getApplication().getViewHandler().createView(facesContext, errorPageLocation));
			facesContext.getPartialViewContext().setRenderAll(Boolean.TRUE);
			facesContext.renderResponse();
		
		}else{
			
			if(usuarioLogado != null){
				if(conselheiroLogado.getNivelAcesso().equals("rel")){
					if(isRelatorioPage || isIndexPage){						
					}else{
						String errorPageLocation = "/paginas/"+ homepage;
						facesContext.setViewRoot(facesContext.getApplication().getViewHandler().createView(facesContext, errorPageLocation));
						facesContext.getPartialViewContext().setRenderAll(Boolean.TRUE);
						facesContext.renderResponse();
					}
				}
				
//				if((conselheiroLogado.getNivelAcesso().equals("rel") && !isRelatorioPage) &&
//				   (conselheiroLogado.getNivelAcesso().equals("rel") && !isIndexPage)){
//					
//					
//				}				
				
				if((conselheiroLogado.getNivelAcesso().equals("con") && isConselheiroBuscaPage) ||
			       (conselheiroLogado.getNivelAcesso().equals("con") && isConselheiroCadastroPage) ||
			       (conselheiroLogado.getNivelAcesso().equals("rel") && isConselheiroBuscaPage) ||
			       (conselheiroLogado.getNivelAcesso().equals("rel") && isConselheiroCadastroPage)){
							
						String errorPageLocation = "/paginas/"+ homepage;
						facesContext.setViewRoot(facesContext.getApplication().getViewHandler().createView(facesContext, errorPageLocation));
						facesContext.getPartialViewContext().setRenderAll(Boolean.TRUE);
						facesContext.renderResponse();
						
					}
			}			
			
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {

		// TODO Auto-generated method stub

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
