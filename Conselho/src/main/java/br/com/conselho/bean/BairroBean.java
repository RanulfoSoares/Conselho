package br.com.conselho.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.conselho.dao.BairroDAO;
import br.com.conselho.dao.CidadeDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Estado;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name="MBBairro")
@ViewScoped
public class BairroBean implements Serializable {
	
	private String template;
	private List<Cidade> listaCidade;
	private Cidade cidadeSelecionado;
	private String nomeBairro;
	private String nomeBusca = "";
	private List<Bairro> listaBairro= new ArrayList<Bairro>();
	private Cidade cidadeSelecionadoBusca;
	private Bairro bairroEdita;
	private BairroDAO bairroDAO = new BairroDAO();
		
	public BairroBean() {		
		template = AutenticaUsuario.verificaTemplate();
	}
	
	@PostConstruct
	public void ini(){
		listaCidade = new ArrayList<Cidade>();
		try {
			listaCidade = new CidadeDAO().buscaCidade("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buscar(){		
		
		try {
			
			if(cidadeSelecionadoBusca == null){
				JSFUtil.addErrorMessage("Selecione no minimo uma cidade");
			}else{
				listaBairro = bairroDAO.busca(nomeBusca, cidadeSelecionadoBusca);
			}
								
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Ocorreu um erro ao consultar bairro: "+e.getMessage());
			System.out.println("Erro ao efetuar a busca:"+e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	public void salvar(){				
		
		try {
			
			if("".equals(nomeBairro.trim()) || cidadeSelecionado == null || nomeBairro.length() <= 3){
				JSFUtil.addErrorMessage("Informe a cidade e o estado.");
				return;
			}
			
			Bairro bairro = new Bairro();
			
			if(bairroEdita == null){
				bairro.setCidade(cidadeSelecionado);
				bairro.setNome(nomeBairro);
			}else{
				bairro = bairroEdita;
				bairro.setCidade(cidadeSelecionado);
				bairro.setNome(nomeBairro);
			}
			
			List<Bairro> bairroExiste = new ArrayList<Bairro>();
			
			if(bairroEdita == null){
				bairroExiste = bairroDAO.buscaBairro(nomeBairro, cidadeSelecionado);
			}
			
			if(bairroExiste.isEmpty()){
				bairroDAO.salvar(bairro);
				listaBairro = new ArrayList<Bairro>();
				JSFUtil.addInfoMessage("Bairro salvo com Sucesso!!!");
			}else{
				JSFUtil.addErrorMessage("Nome de bairro já cadastrado.");
			}
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao salvar: "+e.getMessage());
		}			
	}
		
	public void excluir(ActionEvent event){
		
		try {
			Bairro bairroExcluir = (Bairro) event.getComponent().getAttributes().get("bairroSelecionado");
			bairroDAO.excluir(bairroExcluir);			
			buscar();
			JSFUtil.addInfoMessage("Bairro excluido com sucesso!!!");			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao excluir: "+e.getMessage());
		}
		
	}
	
	public void preparaEdita(ActionEvent event){
		System.out.println("Entrou no metodo prepara edita...");
		try {
			bairroEdita = (Bairro) event.getComponent().getAttributes().get("bairroSelecionado");
			nomeBairro = bairroEdita.getNome();
			cidadeSelecionado = bairroEdita.getCidade();
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao selecionar cidade: "+e.getMessage());
		}
		
	}
	
	public void limpaCampos(){
		System.out.println("Entrou no metodo limpa campos");
		nomeBairro = "";
		cidadeSelecionado = new Cidade();
		bairroEdita = null;
	}	

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Cidade getCidadeSelecionado() {
		return cidadeSelecionado;
	}

	public void setCidadeSelecionado(Cidade cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
	}

	public String getNomeBairro() {
		return nomeBairro;
	}

	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}

	public List<Bairro> getListaBairro() {
		return listaBairro;
	}

	public void setListaBairro(List<Bairro> listaBairro) {
		this.listaBairro = listaBairro;
	}

	public Cidade getCidadeSelecionadoBusca() {
		return cidadeSelecionadoBusca;
	}

	public void setCidadeSelecionadoBusca(Cidade cidadeSelecionadoBusca) {
		this.cidadeSelecionadoBusca = cidadeSelecionadoBusca;
	}		
}
