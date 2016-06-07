package br.com.conselho.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;

import br.com.conselho.dao.BairroDAO;
import br.com.conselho.dao.CidadeDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.dao.LogradouroDAO;
import br.com.conselho.dao.TipoInstituicaoDAO;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.domain.TipoInstituicao;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.CnpjValidator;
import br.com.conselho.util.CpfValidator;
import br.com.conselho.util.EmailValidator;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBInstituicaoCadastro")
@ViewScoped
public class InstituicaoCadastroBean implements Serializable {		
	
	private String template;
	private List<TipoInstituicao> listaTipoInstituicao;
	private List<Estado> listaEstado;
	private String nomeRazao;
	private String nomeFantasia;
	private String cnpj;
	private TipoInstituicao tipoInstituicaoSelecionado;
	private String foneI;
	private String foneII;
	private String email;
	private String contato;
	private String foneContatoI;
	private String foneContatoII;
	private List<Cidade> listaCidade;
	private List<Bairro> listaBairro;
	private List<Logradouro> listaLogradouro = new ArrayList<Logradouro>();
	private Estado estadoSelecionado;
	private Cidade cidadeSelecionado;
	private Bairro bairroSelecionado;
	private Logradouro logradouroSelecionado;
	private String numero;
	private String complemento;
	private String cep;
	private String obs;
	private Long idInstituicaoEdita = null;
	private String cie;
	
	private Conselheiro conselheiroLogado;
	
	public InstituicaoCadastroBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");
	}
	
	
	@PostConstruct
	public void ini(){
		listaTipoInstituicao = new ArrayList<TipoInstituicao>();
		try {
			listaTipoInstituicao = new TipoInstituicaoDAO().lista();
			listaEstado = new EstadoDAO().listaEstados();
			verificaEditar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	public void verificaEditar(){
		
		String idInstituicao = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("i");	
		
		try {
			if(idInstituicao != null){
				
				Instituicao instEd = new InstituicaoDAO().buscaInstituicao(Long.parseLong(idInstituicao));
				
				System.out.println("ID da instituição selecionado foi o: "+idInstituicao);
				
				nomeFantasia = instEd.getFantasia();
				nomeRazao = instEd.getNomeRazao();
				cnpj = instEd.getCnpj();
				tipoInstituicaoSelecionado = instEd.getTipoInstituicao();
				foneI = instEd.getFoneI();
				foneII = instEd.getFoneII();
				contato = instEd.getContato();
				foneContatoI = instEd.getFoneContatoI();
				foneContatoII = instEd.getFoneContatoII();
				email = instEd.getEmail();				
				numero = instEd.getNumero();
				complemento = instEd.getComplemento();
				obs = instEd.getObs();															
				logradouroSelecionado = instEd.getLogradouro();
				estadoSelecionado = instEd.getLogradouro().getBairro().getCidade().getEstado();
				cidadeSelecionado = instEd.getLogradouro().getBairro().getCidade();
				bairroSelecionado = instEd.getLogradouro().getBairro();
				cep = instEd.getLogradouro().getCep().toString();
				idInstituicaoEdita = instEd.getId();
				cie = instEd.getCie();
				
				buscaCidade();
				buscaBairro();
				buscaLogradouro();
				
								
							
			}else{
				
				nomeFantasia = "";
				nomeRazao = "";
				cnpj = "";
				tipoInstituicaoSelecionado = null;
				foneI = "";
				foneII = "";
				contato = "";
				foneContatoI = "";
				foneContatoII = "";
				email = "";
				logradouroSelecionado = null;
				numero = "";
				complemento = "";
				obs = "";
				estadoSelecionado = null;
				cidadeSelecionado = null;
				bairroSelecionado = null;
				cep = "";
				cie = null;
				
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
			
			if("".equals(cie) && "".equals(cnpj)){
				JSFUtil.addErrorMessage("ERRO! ", "Informe o CIE ou o CNPJ");
				return;
			}else{
				if(!"".equals(cnpj)){
					if(CnpjValidator.validate(cnpj)){
						JSFUtil.addErrorMessage("ERRO! ", "CNPJ INVÁLIDO");
						return;
					}else{						
						List<Instituicao> list = new InstituicaoDAO().listaCNPJ(cnpj.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\/", "").replaceAll("\\_", ""));
						if(!list.isEmpty()){
							if(list.size() > 1){
								JSFUtil.addErrorMessage("Atenção! ", "CNPJ duplicado");
							}else{
								Instituicao instituicao = list.get(0);
								if(!instituicao.getId().equals(idInstituicaoEdita)){
									JSFUtil.addErrorMessage("ERRO! ", "CNPJ já cadastrado");
									return;
								}
							}							
						}
					}
				}else if(!"".equals(cie)){
					List<Instituicao> list = new InstituicaoDAO().listaCIE(cie);
					if(!list.isEmpty()){
						if(list.size() > 1){
							JSFUtil.addErrorMessage("Atenção!","CIE duplicado");
						}else{
							Instituicao inst = list.get(0);
							if(!inst.getId().equals(idInstituicaoEdita)){
								JSFUtil.addErrorMessage("ERRO! ", "CIE já cadastrado");
								return;
							}
						}											
					}
				}
			}
			
			if(!"".equals(email)){				
				if(EmailValidator.validarEmail(email)){
					JSFUtil.addErrorMessage("ERRO! ", "EMAIL INVÁLIDO");
					return;
				}				
			}
						
			
			Instituicao instituicao = new Instituicao();
			
			if(idInstituicaoEdita != null){
				instituicao.setId(idInstituicaoEdita);
			}					
			
			instituicao.setFantasia(nomeFantasia);
			instituicao.setNomeRazao(nomeRazao);
			instituicao.setCnpj(cnpj.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\/", "").replaceAll("\\_", ""));
			instituicao.setTipoInstituicao(tipoInstituicaoSelecionado);
			instituicao.setFoneI(foneI.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
			instituicao.setFoneII(foneII.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
			instituicao.setContato(contato);
			instituicao.setFoneContatoI(foneContatoI.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
			instituicao.setFoneContatoII(foneContatoII.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", ""));
			instituicao.setEmail(email);
			instituicao.setLogradouro(logradouroSelecionado);
			instituicao.setNumero(numero);
			instituicao.setComplemento(complemento);
			instituicao.setObs(obs);
			instituicao.setCie(cie);
			instituicao.setConselheiroRegistro(conselheiroLogado);
			
			
			new InstituicaoDAO().salvar(instituicao);
			
			JSFUtil.addInfoMessage("Salvo com sucesso.");
			
			nomeFantasia = "";
			nomeRazao = "";
			cnpj = "";
			tipoInstituicaoSelecionado = null;
			foneI = "";
			foneII = "";
			contato = "";
			foneContatoI = "";
			foneContatoII = "";
			email = "";
			logradouroSelecionado = null;
			numero = "";
			complemento = "";
			obs = "";
			estadoSelecionado = null;
			cidadeSelecionado = null;
			bairroSelecionado = null;
			cep = "";
			cie = "";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFUtil.addErrorMessage("ERRO! ", "Problemas ao salvar: "+e.getMessage());
		}			
	}		
	
	public void validaCNPJ(){		
		
		try {
			if(CnpjValidator.validate(cnpj)){
				JSFUtil.addErrorMessage("ERRO! ", "CNPJ INVÁLIDO");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void validaEmail(){
		
		System.out.println("Entrou no metodo valida email.....");
		
		if(!"".equals(email)){
			try {
				if(EmailValidator.validarEmail(email)){
					JSFUtil.addErrorMessage("ERRO! ", "EMAIL INVÁLIDO");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
		
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

	public List<TipoInstituicao> getListaTipoInstituicao() {
		return listaTipoInstituicao;
	}


	public void setListaTipoInstituicao(List<TipoInstituicao> listaTipoInstituicao) {
		this.listaTipoInstituicao = listaTipoInstituicao;
	}


	public List<Estado> getListaEstado() {
		return listaEstado;
	}


	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}


	public String getNomeRazao() {
		return nomeRazao;
	}


	public void setNomeRazao(String nomeRazao) {
		this.nomeRazao = nomeRazao;
	}


	public String getNomeFantasia() {
		return nomeFantasia;
	}


	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}


	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


	public TipoInstituicao getTipoInstituicaoSelecionado() {
		return tipoInstituicaoSelecionado;
	}


	public void setTipoInstituicaoSelecionado(
			TipoInstituicao tipoInstituicaoSelecionado) {
		this.tipoInstituicaoSelecionado = tipoInstituicaoSelecionado;
	}


	public String getFoneI() {
		return foneI;
	}


	public void setFoneI(String foneI) {
		this.foneI = foneI;
	}


	public String getFoneII() {
		return foneII;
	}


	public void setFoneII(String foneII) {
		this.foneII = foneII;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getContato() {
		return contato;
	}


	public void setContato(String contato) {
		this.contato = contato;
	}


	public String getFoneContatoI() {
		return foneContatoI;
	}


	public void setFoneContatoI(String foneContatoI) {
		this.foneContatoI = foneContatoI;
	}


	public String getFoneContatoII() {
		return foneContatoII;
	}


	public void setFoneContatoII(String foneContatoII) {
		this.foneContatoII = foneContatoII;
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


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getObs() {
		return obs;
	}


	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getCie() {
		return cie;
	}

	public void setCie(String cie) {
		this.cie = cie;
	}


	public String getTemplate() {
		return template;
	}


	public void setTemplate(String template) {
		this.template = template;
	}
	
}
