package br.com.conselho.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.conselho.dao.BairroDAO;
import br.com.conselho.dao.CidadeDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.dao.GrauParentescoDAO;
import br.com.conselho.dao.GrauResponsabilidadeDAO;
import br.com.conselho.dao.LogradouroDAO;
import br.com.conselho.dao.MembroDao;
import br.com.conselho.dao.ParentescoDAO;
import br.com.conselho.dao.ResponsavelNovoDAO;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.GrauParentesco;
import br.com.conselho.domain.GrauResponsabilidade;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.MembroTela;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Parentesco;
import br.com.conselho.domain.ResponsavelNovo;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBFamiliaMembro")
@ViewScoped
public class FamiliaMembroBean implements Serializable{
	
	private List<Estado> listaEstado;
	private List<Cidade> listaCidade;
	private List<Bairro> listaBairro = new ArrayList<Bairro>();
	private List<Logradouro> listaLogradouro = new ArrayList<Logradouro>();
	private List<MembroTela> listaMembros = new ArrayList<MembroTela>();
	
	private List<GrauResponsabilidade> listaResponsabilidade = new ArrayList<GrauResponsabilidade>();
	private List<GrauParentesco> listaParentesco = new ArrayList<GrauParentesco>();
	
	
	private Estado estadoSelecionado;
	private Familia familia = new Familia();
	private Cidade cidadeSelecionado;
	private Bairro bairroSelecionado;
	private Logradouro logradouroSelecionado;

	private List<Orgao> listaOrgaoBusca = new ArrayList<Orgao>();
	private String dataNascimento;
	private String idade;
	private String template;
	private Conselheiro conselheiroLogado;
	
	private Membro membroSelecionado;		
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	public FamiliaMembroBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");
	}
	
	@PostConstruct
	public void ini(){

		try {
			listaEstado = new EstadoDAO().listaEstados();
			listaResponsabilidade = new GrauResponsabilidadeDAO().listaGrauResponsabilidade();
			listaParentesco = new GrauParentescoDAO().listaGrauParentescos();
			
			verificaEditar();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	private void verificaEditar(){
		
		try {
			
			String idMembro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
			
			if(idMembro != null){
				MembroTela mt;				
				membroSelecionado = new MembroDao().buscaCodigo(Long.parseLong(idMembro));
				familia = membroSelecionado.getFamilia();
				estadoSelecionado =  familia.getLogradouro().getBairro().getCidade().getEstado();
				cidadeSelecionado = familia.getLogradouro().getBairro().getCidade();
				bairroSelecionado = familia.getLogradouro().getBairro();
				logradouroSelecionado = familia.getLogradouro();
				
				ResponsavelNovoDAO responsavelDao = new ResponsavelNovoDAO();
				ParentescoDAO parentescoDao = new ParentescoDAO();
				
				ResponsavelNovo responsavel = null;
				Parentesco parentesco = null;
				
				buscaCidade();
				buscaBairro();
				buscaLogradouro();
				
				for (Membro membro : familia.getListaMembros()) {
					
					if(!membro.getId().equals(membroSelecionado.getId())){
						if(membro.getDataDesvinculo() == null){
							mt = new MembroTela();
							mt.setIdMembro(membro.getId());
							mt.setNome(membro.getPessoa().getNomeCompleto());
							mt.setIdade(Helper.executaCalculoIdade(membro.getPessoa().getDataNascimento()).toString());
							mt.setPessoa(membro.getPessoa());
							
							responsavel = responsavelDao.buscaResponsavelNovoPorMembros(membroSelecionado, membro);
							
							if(responsavel != null){
								mt.setGrauResponsabilidade(responsavel.getGrauResponsabilidade());
							}
							
							parentesco = parentescoDao.buscaParentesco(membroSelecionado, membro);
							
							if(parentesco != null){
								mt.setGrauParentesco(parentesco.getGrauParentesco());
							}
							
							listaMembros.add(mt);
						}
					}
					
				}
								
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}			
	}
	
	public void salvar(){
		
		try {
			
			if(conselheiroLogado.getNivelAcesso().equals("col")){
				JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido salvar.");
				return;
			}
			
			Parentesco parentesco;
			
			Membro membroPrincipal = membroSelecionado;
			ResponsavelNovo responsavel;
			Membro membroSec;
			ParentescoDAO parentescoDao = new ParentescoDAO();
			MembroDao membroDao = new MembroDao();
			ResponsavelNovoDAO responsavelDao = new ResponsavelNovoDAO();
			
			for (MembroTela m : listaMembros) {
							
				parentesco = new Parentesco();
				membroSec = membroDao.buscaCodigo(m.getIdMembro());											
				
				Parentesco parentescoEdita = parentescoDao.buscaParentesco(membroPrincipal, membroSec);
				
				if(parentescoEdita != null){
					parentesco.setId(parentescoEdita.getId());
				}
				
				parentesco.setParentePrincipal(membroPrincipal);
				parentesco.setParente(membroSec);
				parentesco.setGrauParentesco(m.getGrauParentesco());
				
				parentescoDao.salvar(parentesco);
				
				responsavel = new ResponsavelNovo();
				
				ResponsavelNovo responsavelEdita = responsavelDao.buscaResponsavelNovoPorMembros(membroPrincipal, membroSec);
				
				if(responsavelEdita != null){
					responsavel.setId(responsavelEdita.getId());
				}
				
				responsavel.setMembroPrincipal(membroPrincipal);
				responsavel.setMembro(membroSec);
				responsavel.setGrauResponsabilidade(m.getGrauResponsabilidade());
				
				responsavelDao.salvar(responsavel);
				
				System.out.println("Membro selecionado: "+m.getNome());
				System.out.println("Grau Parentesco: "+m.getGrauParentesco().getNome());
				System.out.println("Membro Responsabilidade: "+m.getGrauResponsabilidade().getNome());			
			}
			
			JSFUtil.addInfoMessage("Salvo com sucesso!");
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao fazer as vinculaçoes de responsabilidade e parentesco: "+e.getMessage());
		}
									
	}
	
	public String preparaMostraDados(){		
		try {
			
			System.out.println("ID SELECIONADO PARA EDITAR TELA DE BUSCA: "+membroSelecionado.getFamilia().getId());
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}		
		return "familia_cadastro?faces-redirect=true&c="+membroSelecionado.getFamilia().getId();		
	}
	
	public void buscaCidade(){
		
		try {
			listaCidade = new CidadeDAO().buscaCidade(estadoSelecionado);
			
			if(!listaBairro.isEmpty()){
				listaBairro = new ArrayList<Bairro>();
			}
			
			if(!listaLogradouro.isEmpty()){
				listaLogradouro = new ArrayList<Logradouro>();
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void buscaBairro(){									
		
		try {
			listaBairro = new BairroDAO().buscaBairro(cidadeSelecionado);
			
			if(!listaLogradouro.isEmpty()){
				listaLogradouro = new ArrayList<Logradouro>();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public void buscaLogradouro(){
		
		System.out.println("Busca bairro!!!!... bairro selecionado "+bairroSelecionado.getNome());
		try {
									
			listaLogradouro = new LogradouroDAO().buscaLogradouro(bairroSelecionado);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}				
	}					
	
	
	public Familia getFamilia() {
		return familia;
	}
	

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Bairro> getListaBairro() {
		return listaBairro;
	}

	public void setListaBairro(List<Bairro> listaBairro) {
		this.listaBairro = listaBairro;
	}

	public List<Logradouro> getListaLogradouro() {
		return listaLogradouro;
	}

	public void setListaLogradouro(List<Logradouro> listaLogradouro) {
		this.listaLogradouro = listaLogradouro;
	}

	public Cidade getCidadeSelecionado() {
		return cidadeSelecionado;
	}

	public void setCidadeSelecionado(Cidade cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
	}

	public Bairro getBairroSelecionado() {
		return bairroSelecionado;
	}

	public void setBairroSelecionado(Bairro bairroSelecionado) {
		this.bairroSelecionado = bairroSelecionado;
	}

	public Logradouro getLogradouroSelecionado() {
		return logradouroSelecionado;
	}

	public void setLogradouroSelecionado(Logradouro logradouroSelecionado) {
		this.logradouroSelecionado = logradouroSelecionado;
	}

	public List<MembroTela> getListaMembros() {
		return listaMembros;
	}

	public void setListaMembros(List<MembroTela> listaMembros) {
		this.listaMembros = listaMembros;
	}

	public List<Orgao> getListaOrgaoBusca() {
		return listaOrgaoBusca;
	}

	public void setListaOrgaoBusca(List<Orgao> listaOrgaoBusca) {
		this.listaOrgaoBusca = listaOrgaoBusca;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public Membro getMembroSelecionado() {
		return membroSelecionado;
	}

	public void setMembroSelecionado(Membro membroSelecionado) {
		this.membroSelecionado = membroSelecionado;
	}

	public List<GrauResponsabilidade> getListaResponsabilidade() {
		return listaResponsabilidade;
	}

	public void setListaResponsabilidade(
			List<GrauResponsabilidade> listaResponsabilidade) {
		this.listaResponsabilidade = listaResponsabilidade;
	}

	public List<GrauParentesco> getListaParentesco() {
		return listaParentesco;
	}

	public void setListaParentesco(List<GrauParentesco> listaParentesco) {
		this.listaParentesco = listaParentesco;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
}
