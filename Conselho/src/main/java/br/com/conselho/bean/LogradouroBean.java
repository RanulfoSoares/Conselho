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
import br.com.conselho.dao.LogradouroDAO;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name="MBLogradouro")
@ViewScoped
public class LogradouroBean implements Serializable {
	
	private List<Estado> listaEstado;
	private List<Cidade> listaCidade;
	private List<Bairro> listaBairro;
	private List<Logradouro> listaLogradouro;
	
	private Estado estadoSelecionado;
	private Cidade cidadeSelecionado;
	private Bairro bairroSelecionado;
		
	private Bairro bairroSelecionadoCadastro;
	
	private CidadeDAO cidadeDAO = new CidadeDAO();
	private BairroDAO bairroDAO = new BairroDAO();
	private LogradouroDAO logradouroDAO = new LogradouroDAO();
	
	private String nomeBusca;
	
	private String nome;
	private String cep;
	private Logradouro logradouroEdita;
	
	@PostConstruct
	public void ini(){
		try {
			listaEstado = new EstadoDAO().listaEstados();
		} catch (Exception e) {
			System.out.println("Erro ao inicializar Bean: "+e.getMessage());
		}
	}
	
	public void buscaCidade(){
		
		try {
			listaCidade = cidadeDAO.buscaCidade(estadoSelecionado);
		} catch (Exception e) {
			System.out.println("Erro ao popular combo de cidade: "+e.getMessage());
		}		
	}
	
	public void buscaBairro(){
		
		try {
			listaBairro = bairroDAO.buscaBairro(cidadeSelecionado);
		} catch (Exception e) {
			System.out.println("Erro ao popular combo de bairro: "+e.getMessage());
		}		
	}
	
	public void buscaLogradouro(){
		try {
			
			if(bairroSelecionado == null){
				JSFUtil.addErrorMessage("Selecione no minimo o bairro para efetuar a busca.");				
			}else{
				listaLogradouro = logradouroDAO.buscaLogradouro(bairroSelecionado, nomeBusca);
			}					
		} catch (Exception e) {
			System.out.println("Erro ao buscar logradouro: "+e.getMessage());
		}				
	}
	
	public void limpaListas(){
		listaCidade = new ArrayList<Cidade>();
		listaBairro = new ArrayList<Bairro>();
		estadoSelecionado = new Estado();
		cidadeSelecionado = new Cidade();
		bairroSelecionado = new Bairro();
		bairroSelecionadoCadastro = new Bairro();
		
		nome = "";
		cep = "";		
	}
	
	public void salvar(){
		
		try {
			
			if("".equals(nome.trim()) || bairroSelecionadoCadastro == null){
				JSFUtil.addErrorMessage("Informe o logradouro e o bairro para salvar.");
			}else{
				
				if(logradouroEdita == null){
					Logradouro logradouro = new Logradouro();
					logradouro.setNome(nome);
					logradouro.setBairro(bairroSelecionadoCadastro);
					logradouro.setCep(Integer.parseInt(cep.replace("-", "").trim()));
					new LogradouroDAO().salvar(logradouro);					
				}else{
					logradouroEdita.setNome(nome);
					logradouroEdita.setBairro(bairroSelecionadoCadastro);
					logradouroEdita.setCep(Integer.parseInt(cep.replace("-", "").trim()));
					new LogradouroDAO().salvar(logradouroEdita);
				}
				listaLogradouro = new ArrayList<Logradouro>();				
				JSFUtil.addInfoMessage("Logradouro salvo com sucesso!!!");
			}
																	
		} catch (Exception e) {
			System.out.println("Erro ao salvar Logradouro: "+e.getMessage());
			JSFUtil.addErrorMessage("Erro ao salvar Logradouro: "+e.getMessage());
		}
		
	}
	
	public void excluir(ActionEvent event){
		System.out.println("Entrou no metodo prepara edita...");
		try {
			logradouroEdita = (Logradouro) event.getComponent().getAttributes().get("logradouroSelecionado");
			logradouroDAO.excluir(logradouroEdita);
			listaLogradouro = new ArrayList<Logradouro>();
			JSFUtil.addInfoMessage("Logradouro excluido com sucesso!!!");
		} catch (Exception e) {
			JSFUtil.addErrorMessage(e.getMessage());
		}		
	}
	
	public void preparaEdita(ActionEvent event){
		System.out.println("Entrou no metodo prepara edita...");
		try {
			logradouroEdita = (Logradouro) event.getComponent().getAttributes().get("logradouroSelecionado");		
			nome = logradouroEdita.getNome();
			estadoSelecionado = logradouroEdita.getBairro().getCidade().getEstado();
			cidadeSelecionado = logradouroEdita.getBairro().getCidade();
			bairroSelecionadoCadastro = logradouroEdita.getBairro();
			cep = logradouroEdita.getCep().toString();
			buscaCidade();
			buscaBairro();
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao selecionar cidade: "+e.getMessage());
		}
		
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

	public Cidade getCidadeSelecionado() {
		return cidadeSelecionado;
	}

	public void setCidadeSelecionado(Cidade cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
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

	public Bairro getBairroSelecionado() {
		return bairroSelecionado;
	}

	public void setBairroSelecionado(Bairro bairroSelecionado) {
		this.bairroSelecionado = bairroSelecionado;
	}

	public List<Logradouro> getListaLogradouro() {
		return listaLogradouro;
	}

	public void setListaLogradouro(List<Logradouro> listaLogradouro) {
		this.listaLogradouro = listaLogradouro;
	}

	public String getNomeBusca() {
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca) {
		this.nomeBusca = nomeBusca;
	}	

	public Bairro getBairroSelecionadoCadastro() {
		return bairroSelecionadoCadastro;
	}

	public void setBairroSelecionadoCadastro(Bairro bairroSelecionadoCadastro) {
		this.bairroSelecionadoCadastro = bairroSelecionadoCadastro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}	
}
