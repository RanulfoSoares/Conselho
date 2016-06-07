package br.com.conselho.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.conselho.dao.CidadeDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.Vitima;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name="MBCidade")
@ViewScoped
public class CidadeBean implements Serializable {
	
	private String template;
	private List<Estado> listaEstado;
	private Estado estadoSelecionado;
	private String nomeCidade;
	private String nomeBusca = "";
	private List<Cidade> listaCidade = new ArrayList<Cidade>();
	private String codigoCidadeSelecionado;
	private Cidade cidadeEdita;
	private CidadeDAO cidadeDAO = new CidadeDAO();
		
	public CidadeBean() {		
		template = AutenticaUsuario.verificaTemplate();
	}
	
	@PostConstruct
	public void ini(){
		listaEstado = new ArrayList<Estado>();
		try {
			listaEstado = new EstadoDAO().listaEstados();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buscar(){		
		
		try {
			listaCidade = cidadeDAO.buscaCidade(nomeBusca);
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Ocorreu um erro ao consultar cidade: "+e.getMessage());
			System.out.println("Erro ao efetuar a busca:"+e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	public void salvar(){				
		
		try {
		
			if("".equals(nomeCidade.trim()) || estadoSelecionado == null || nomeCidade.length() <= 3){
				JSFUtil.addErrorMessage("Informe a cidade e o estado");
				return;
			}		
			
			Cidade cidade = new Cidade();
			
			if(cidadeEdita == null){
				cidade.setEstado(estadoSelecionado);
				cidade.setNome(nomeCidade);
			}else{
				cidade = cidadeEdita;
				cidade.setEstado(estadoSelecionado);
				cidade.setNome(nomeCidade);
				
			}				
							
			List<Cidade> cidadeExiste = new ArrayList<Cidade>();
			
			if(cidadeEdita == null){
				cidadeExiste = cidadeDAO.buscaCidade(nomeCidade, estadoSelecionado);
			}
			
			if(cidadeExiste.isEmpty()){
				cidadeDAO.salvar(cidade);
				listaCidade = new ArrayList<Cidade>();
				JSFUtil.addInfoMessage("Cidade Salva com Sucesso");
			}else{
				JSFUtil.addErrorMessage("Nome de cidade já cadastrada");
			}
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao salvar: "+e.getMessage());
		}			
	}
		
	public void excluir(ActionEvent event){
		
		try {
			Cidade cidadeExcluir = (Cidade) event.getComponent().getAttributes().get("cidadeSelecionado");
			cidadeDAO.excluir(cidadeExcluir);
			buscar();
			JSFUtil.addInfoMessage("Cidade excluida com sucesso");
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao excluir: "+e.getMessage());
		}
		
	}
	
	public void preparaEdita(ActionEvent event){
		System.out.println("Entrou no metodo prepara edita...");
		try {
			cidadeEdita = (Cidade) event.getComponent().getAttributes().get("cidadeSelecionado");
			nomeCidade = cidadeEdita.getNome();
			estadoSelecionado = cidadeEdita.getEstado();
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao selecionar cidade: "+e.getMessage());
		}
		
	}
	
	public void limpaCampos(){
		System.out.println("Entrou no metodo limpa campos");
		nomeCidade = "";
		estadoSelecionado = new Estado();
		cidadeEdita = null;
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

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
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

	public String getCodigoCidadeSelecionado() {
		return codigoCidadeSelecionado;
	}

	public void setCodigoCidadeSelecionado(String codigoCidadeSelecionado) {
		this.codigoCidadeSelecionado = codigoCidadeSelecionado;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
}
