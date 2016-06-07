package br.com.conselho.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.dao.GrauResponsabilidadeDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.dao.ResponsavelDAO;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.GrauResponsabilidade;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Responsavel;
import br.com.conselho.util.CnpjValidator;
import br.com.conselho.util.JSFUtil;


@ManagedBean(name="MBResponsavelInstituicao")
@ViewScoped
public class CadastroResponsavelInstituicaoBean {	
	
	private String documentoBusca;
	private String tipoBusca;
	private CriancaAdolescente criancaSelecionada;
	private String nomeCriancaSelecionado;
	private Instituicao instituicaoSelecionado;
	
	
	private Boolean mostraCadastro = Boolean.FALSE;
	private GrauResponsabilidade grauResponsabilidadeSelecionado;

	
	@PostConstruct
	public void ini(){
		
		String idCrianca = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
		System.out.println("Criança selecionada: "+idCrianca);
		if(idCrianca != null){
			try {
				
				grauResponsabilidadeSelecionado = new GrauResponsabilidadeDAO().listaGrauResponsabilidade("Guardiã(o)");
				System.out.println("grau selecionado..........: "+grauResponsabilidadeSelecionado.getNome());
				criancaSelecionada = new CriancaAdolescenteDAO().buscaCrianca(Long.parseLong(idCrianca));
				nomeCriancaSelecionado = criancaSelecionada.getPessoa().getNomeCompleto();
			} catch (Exception e) {				
				e.printStackTrace();
				System.out.println("ERRO ao carregar a tela..."+e.getMessage());
			}
		}
	}
	
	public String navegaResponsavel(){
		
		System.out.println("entrou no metodo redireciona responsavel... selecionado id: "+criancaSelecionada.getId());
		
		return "responsavel_busca?faces-redirect=true&c="+criancaSelecionada.getId();
		
	}
	
	public void buscaInstituicao(){			
		
		InstituicaoDAO instituicaoDAO = new  InstituicaoDAO();
		ResponsavelDAO responsavelDAO = new ResponsavelDAO();
		try {
			
			if(!"".equals(tipoBusca)){
				if(!"".equals(documentoBusca)){
					if("CNPJ".equals(tipoBusca)){
						if(CnpjValidator.validate(documentoBusca)){
							JSFUtil.addErrorMessage("ERRO! ", "CNPJ INVÁLIDO");
							mostraCadastro= Boolean.FALSE;
						}else{
							List<Instituicao> lista = instituicaoDAO.listaCNPJ(documentoBusca);				
							if(!lista.isEmpty()){
								if(lista.size() < 2){																		
									instituicaoSelecionado = lista.get(0);
									List<Responsavel> listResp = responsavelDAO.buscaResponsavelInstituicaoPorCrianca(criancaSelecionada);									
									if(listResp.isEmpty()){
										listResp = responsavelDAO.buscaResponsavelPessoaPorCrianca(criancaSelecionada);
										if(listResp.isEmpty()){
											mostraCadastro= Boolean.TRUE;
										}else{
											JSFUtil.addErrorMessage("Esta criança já possui um responsável pessoa. ");
											mostraCadastro= Boolean.FALSE;
										}										
									}else{
										JSFUtil.addErrorMessage("Esta criança já possui uma instituição como responsável");
										mostraCadastro= Boolean.FALSE;
									}									
								 }else{
									 JSFUtil.addErrorMessage("Este CNPJ está duplicado.");
									 mostraCadastro= Boolean.FALSE;
								 }
							}else{
								JSFUtil.addWarnMessage("Nenhuma Instituiçao encontrada");
								mostraCadastro= Boolean.FALSE;
							}
						}									
					}else if("CIE".equals(tipoBusca)){
						List<Instituicao> lista = instituicaoDAO.listaCIE(documentoBusca);				
						if(!lista.isEmpty()){
							if(lista.size() < 2){
								instituicaoSelecionado = lista.get(0);
								List<Responsavel> listResp = responsavelDAO.buscaResponsavelInstituicaoPorCrianca(criancaSelecionada);								
								if(listResp.isEmpty()){									
									listResp = responsavelDAO.buscaResponsavelPessoaPorCrianca(criancaSelecionada);
									if(listResp.isEmpty()){
										mostraCadastro= Boolean.TRUE;
									}else{
										JSFUtil.addErrorMessage("Esta criança já possui um guardião pessoa.");
										mostraCadastro= Boolean.FALSE;
									}									
								}else{
									JSFUtil.addErrorMessage("Esta criança já possui uma instituição como responsável");
									mostraCadastro= Boolean.FALSE;
								}
							}else{
								JSFUtil.addErrorMessage("Este CIE está duplicado.");
								mostraCadastro= Boolean.FALSE;
							 }														
						}else{
							JSFUtil.addWarnMessage("Nenhuma Instituiçao encontrada");
							mostraCadastro= Boolean.FALSE;
						}
					}
				}else{
					JSFUtil.addWarnMessage("Informe o documento");
					mostraCadastro= Boolean.FALSE;
				}				
			}else{
				JSFUtil.addWarnMessage("Selecione o tipo de documento");
				mostraCadastro= Boolean.FALSE;
			}
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao buscar instituição");
			mostraCadastro= Boolean.FALSE;
		}						
	}
	
	public void vincularInstituicao(){
						
		try {
			Responsavel responsavel = new Responsavel();			
			responsavel.setCriancaAdolescente(criancaSelecionada);
			responsavel.setGrauResponsabilidade(grauResponsabilidadeSelecionado);
			responsavel.setInstituicao(instituicaoSelecionado);			
			new ResponsavelDAO().salvar(responsavel);
			JSFUtil.addInfoMessage("Salvo com sucesso");
			instituicaoSelecionado = new Instituicao();
			mostraCadastro = Boolean.FALSE;			
		} catch (Exception e) {			
			e.printStackTrace();
			JSFUtil.addErrorMessage("Erro ao vincular instituição a criança. "+e.getMessage());
		}
		
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

	public String getNomeCriancaSelecionado() {
		return nomeCriancaSelecionado;
	}

	public void setNomeCriancaSelecionado(String nomeCriancaSelecionado) {
		this.nomeCriancaSelecionado = nomeCriancaSelecionado;
	}

	public Instituicao getInstituicaoSelecionado() {
		return instituicaoSelecionado;
	}

	public void setInstituicaoSelecionado(Instituicao instituicaoSelecionado) {
		this.instituicaoSelecionado = instituicaoSelecionado;
	}

	public Boolean getMostraCadastro() {
		return mostraCadastro;
	}
	public void setMostraCadastro(Boolean mostraCadastro) {
		this.mostraCadastro = mostraCadastro;
	}

	public GrauResponsabilidade getGrauResponsabilidadeSelecionado() {
		return grauResponsabilidadeSelecionado;
	}

	public void setGrauResponsabilidadeSelecionado(
			GrauResponsabilidade grauResponsabilidadeSelecionado) {
		this.grauResponsabilidadeSelecionado = grauResponsabilidadeSelecionado;
	}	
}
