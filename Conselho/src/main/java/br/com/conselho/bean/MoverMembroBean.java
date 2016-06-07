package br.com.conselho.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.conselho.dao.FamiliaDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.dao.MembroDao;
import br.com.conselho.dao.MoverMembroDAO;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.MoverMembro;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.CnpjValidator;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBMoveMembro")
@ViewScoped
public class MoverMembroBean implements Serializable {
		
	private Membro membroSelecionado;	
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	private String dataAtual;
	private String tipoBusca;
	private MembroDao membroDao = new MembroDao();
	private String documentoBusca;
	private String nomeInstituicaoRespNucleo;	
	private Familia familiaDestino;
	private String motivoSelecionado;
	private String obs;
	private Instituicao instituicaoDestino;
	private Boolean flgBtSalvar = false;
	private String template;
	private Conselheiro conselheiroLogado;
	private String nucleoCnpj;
	private String conseRespInstituicao;
	
	public MoverMembroBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");
	}
	
	@PostConstruct
	private void ini(){
		
		try {
			tipoBusca = "N";
			buscaMembroSelecionado();			
		} catch (Exception e) {
			// TODO: handle exception
		}
					
	}
	
	public void buscaMembroSelecionado(){
		
		try {
			
			String idMembro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
			
			if(idMembro != null){
				
				membroSelecionado = membroDao.buscaCodigo(Long.parseLong(idMembro));
				
				if(membroSelecionado.getFamilia() != null){
					nucleoCnpj = membroSelecionado.getFamilia().getNumeroPasta();
					conseRespInstituicao = membroSelecionado.getFamilia().getConselheiroRegistro().getPessoa().getNomeCompleto();
				}else if(membroSelecionado.getInstituicao() != null){
					nucleoCnpj = membroSelecionado.getInstituicao().getCnpj();
					conseRespInstituicao = membroSelecionado.getInstituicao().getNomeRazao();
				}
				
				dataAtual = formato.format(new Date());											
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void buscaDestino(){
		
		try {
			
				if(tipoBusca.equals("N")){
					
					documentoBusca.replaceAll(" ", "").replaceAll("\\.", "").replaceAll("-", "").replaceAll("-", "").replaceAll("\\/", "");
					
					if(!documentoBusca.equals("")){
						familiaDestino = new Familia();						
						familiaDestino = new FamiliaDAO().buscaRegistro(Helper.formataNumeroPasta(documentoBusca));
						
						if(familiaDestino != null){
							nomeInstituicaoRespNucleo = familiaDestino.getConselheiroRegistro().getNomeUsual();
							
							if(familiaDestino.getNumeroPasta().equals(membroSelecionado.getFamilia().getNumeroPasta())){
								JSFUtil.addWarnMessage("", "Este Membro já se encontra neste núcleo!");
								familiaDestino = null;
								nomeInstituicaoRespNucleo = "";	
							}
							
						}else{
							JSFUtil.addWarnMessage("", "Núcleo não encontrado!");
							familiaDestino = null;
							instituicaoDestino = null;
							nomeInstituicaoRespNucleo = "";							
						}											
					}else{
						JSFUtil.addWarnMessage("", "Informe o documento para busca!");
						familiaDestino = null;
						instituicaoDestino = null;
						nomeInstituicaoRespNucleo = "";
					}
					
				}else if(tipoBusca.equals("I")){					
					
					documentoBusca = documentoBusca.replaceAll(" ", "").replaceAll("\\.", "").replaceAll("-", "").replaceAll("-", "").replaceAll("\\/", "");
					
					if(!documentoBusca.equals("")){
						
						if(CnpjValidator.validate(documentoBusca)){
							JSFUtil.addErrorMessage("ERRO! ", "CNPJ INVÁLIDO");
							return;
						}else{
							
							instituicaoDestino = new Instituicao();
							List<Instituicao> lista = new InstituicaoDAO().listaCNPJ(documentoBusca);
							
							if(lista.size() > 1){
								
								JSFUtil.addWarnMessage("Instituição duplicada");
								return;
								
							}else if(lista.isEmpty()){
								
								JSFUtil.addWarnMessage("Nenhuma instituição com este CNPJ foi encontrado");
								return;
								
							}else{
								
								instituicaoDestino = lista.get(0);
								nomeInstituicaoRespNucleo = instituicaoDestino.getNomeRazao();
								
								if(instituicaoDestino.equals(membroSelecionado.getInstituicao())){
									JSFUtil.addWarnMessage("", "Este Membro já se encontra nesta Instituição!");
									familiaDestino = null;
									instituicaoDestino = null;
									nomeInstituicaoRespNucleo = "";	
								}
								
							}

						}								
					}else{
						JSFUtil.addWarnMessage("", "Informe o documento para busca!");
						familiaDestino = null;
						instituicaoDestino = null;
						nomeInstituicaoRespNucleo = "";
					}
				}
			
		} catch (Exception e) {
			// TODO: handle exception
		}					
	}
	
	public void mover(){
		
		try {
			
			if(conselheiroLogado.getNivelAcesso().equals("col")){
				JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido salvar.");
				return;
			}
			
			if(familiaDestino != null){
				
				if(!"".equals(motivoSelecionado)){
					
					if(obs.length() > 10){
						
						MoverMembro moverMembro = new MoverMembro();					
						moverMembro.setConselheiroRegistro(conselheiroLogado);
						moverMembro.setDataMovimento(new Date());
						
						if(membroSelecionado.getFamilia() != null){
							moverMembro.setFamilaAtual(membroSelecionado.getFamilia());							
						}else if(membroSelecionado.getInstituicao() != null){
							moverMembro.setInstituicaoAtual(membroSelecionado.getInstituicao());
						}
												
						moverMembro.setFamiliaDestino(familiaDestino);
						moverMembro.setMotivo(motivoSelecionado);
						moverMembro.setTipoMovimento(tipoBusca);
						moverMembro.setObs(obs);
						moverMembro.setMembro(membroSelecionado);
						
						new MoverMembroDAO().salvar(moverMembro);
						
						flgBtSalvar = true;
						JSFUtil.addInfoMessage("Membro tranferido com sucesso!");
					
					}else{
						JSFUtil.addErrorMessage("Informe uma observação da transferência no minimo 10 digitos");
					}
					
				}else{
					JSFUtil.addErrorMessage("Informe o motivo da transferência");
				}
				
				
			}else if(instituicaoDestino != null) {
								
				if(!"".equals(motivoSelecionado)){
					
					if(obs.length() > 10){
						
						
						
						MoverMembro moverMembro = new MoverMembro();					
						moverMembro.setConselheiro("Ranulfo");
						moverMembro.setDataMovimento(new Date());
						
						if(membroSelecionado.getFamilia() != null){
							moverMembro.setFamilaAtual(membroSelecionado.getFamilia());							
						}else if(membroSelecionado.getInstituicao() != null){
							moverMembro.setInstituicaoAtual(membroSelecionado.getInstituicao());
						}
							
						moverMembro.setInstituicaoDestino(instituicaoDestino);
						moverMembro.setMotivo(motivoSelecionado);
						moverMembro.setTipoMovimento(tipoBusca);
						moverMembro.setObs(obs);
						moverMembro.setMembro(membroSelecionado);
						
						new MoverMembroDAO().salvar(moverMembro);
						
						flgBtSalvar = true;
						JSFUtil.addInfoMessage("Membro transferido com sucesso!");
					
					}else{
						JSFUtil.addErrorMessage("Informe uma observação da transferência no minimo 10 digitos");
					}
					
				}else{
					JSFUtil.addErrorMessage("Informe o motivo da transferência");
				}
												
			}else{
				JSFUtil.addErrorMessage("Informe a Familia ou Instituição de destino");
			}
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Ocorreu um erro ao transferir membro: "+e.getMessage());
		}
		
		
	}
	
	public String preparaCadastroFamiliar(){	
		
		if(membroSelecionado.getFamilia() != null){
			return "familia_cadastro?faces-redirect=true&c="+membroSelecionado.getFamilia().getId();
		}else if(membroSelecionado.getInstituicao() != null){
			return "instituicao_busca?faces-redirect=true";
		}else{
			return "";
		}
							
	}

	public Membro getMembroSelecionado() {
		return membroSelecionado;
	}

	public void setMembroSelecionado(Membro membroSelecionado) {
		this.membroSelecionado = membroSelecionado;
	}

	public MembroDao getMembroDao() {
		return membroDao;
	}

	public void setMembroDao(MembroDao membroDao) {
		this.membroDao = membroDao;
	}

	public String getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(String dataAtual) {
		this.dataAtual = dataAtual;
	}

	public String getTipoBusca() {
		return tipoBusca;
	}

	public void setTipoBusca(String tipoBusca) {
		this.tipoBusca = tipoBusca;
	}

	public String getDocumentoBusca() {
		return documentoBusca;
	}

	public void setDocumentoBusca(String documentoBusca) {
		this.documentoBusca = documentoBusca;
	}

	public String getNomeInstituicaoRespNucleo() {
		return nomeInstituicaoRespNucleo;
	}

	public void setNomeInstituicaoRespNucleo(String nomeInstituicaoRespNucleo) {
		this.nomeInstituicaoRespNucleo = nomeInstituicaoRespNucleo;
	}

	public String getMotivoSelecionado() {
		return motivoSelecionado;
	}

	public void setMotivoSelecionado(String motivoSelecionado) {
		this.motivoSelecionado = motivoSelecionado;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Boolean getFlgBtSalvar() {
		return flgBtSalvar;
	}

	public void setFlgBtSalvar(Boolean flgBtSalvar) {
		this.flgBtSalvar = flgBtSalvar;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getNucleoCnpj() {
		return nucleoCnpj;
	}

	public void setNucleoCnpj(String nucleoCnpj) {
		this.nucleoCnpj = nucleoCnpj;
	}

	public String getConseRespInstituicao() {
		return conseRespInstituicao;
	}

	public void setConseRespInstituicao(String conseRespInstituicao) {
		this.conseRespInstituicao = conseRespInstituicao;
	}

	public Conselheiro getConselheiroLogado() {
		return conselheiroLogado;
	}

	public void setConselheiroLogado(Conselheiro conselheiroLogado) {
		this.conselheiroLogado = conselheiroLogado;
	}	
	
}
