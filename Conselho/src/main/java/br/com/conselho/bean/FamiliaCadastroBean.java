package br.com.conselho.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.conselho.dao.AdendoDAO;
import br.com.conselho.dao.AtendimentoDAO;
import br.com.conselho.dao.BairroDAO;
import br.com.conselho.dao.CidadeDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.dao.FamiliaDAO;
import br.com.conselho.dao.LogradouroDAO;
import br.com.conselho.dao.MembroDao;
import br.com.conselho.dao.OrgaoDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.dao.RegistroDireitoVioladoDAO;
import br.com.conselho.dao.VioladorRegistroMedidaAplicadaDAO;
import br.com.conselho.domain.Adendo;
import br.com.conselho.domain.Atendimento;
import br.com.conselho.domain.Atribuicao;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Determinacao;
import br.com.conselho.domain.DeterminacaoAplicada;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.MembroTela;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.domain.RegistroDireitoViolado;
import br.com.conselho.domain.RegistroMedidaAplicada;
import br.com.conselho.domain.Status;
import br.com.conselho.domain.Violador;
import br.com.conselho.domain.VioladorRegistroMedidaAplicada;
import br.com.conselho.domain.Vitima;
import br.com.conselho.dto.AdendoDTO;
import br.com.conselho.dto.AtendimentoDTO;
import br.com.conselho.dto.AtribuicaoDTO;
import br.com.conselho.dto.DeterminacaoAplicadaDTO;
import br.com.conselho.dto.DireitoVioladoDTO;
import br.com.conselho.dto.MedidaAplicadaDTO;
import br.com.conselho.dto.NucleoFamiliarDTO;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.CpfValidator;
import br.com.conselho.util.FacesUtil;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;
import br.com.conselho.util.ReportUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBFamiliaCadastro")
@ViewScoped
public class FamiliaCadastroBean implements Serializable{
	
	private String template;
	private List<Estado> listaEstado;
	private List<Cidade> listaCidade;
	private List<Bairro> listaBairro = new ArrayList<Bairro>();
	private List<Logradouro> listaLogradouro = new ArrayList<Logradouro>();
	private List<MembroTela> listaMembros = new ArrayList<MembroTela>();
	
	private String idMembro;
	private Familia familia = new Familia();
	private Estado estadoSelecionado;	
	private Cidade cidadeSelecionado;
	private Bairro bairroSelecionado;
	private Logradouro logradouroSelecionado;
	private String tipoBusca = null;
	private String documentoBusca = null;
	private Boolean desativaOrgao;
	private Orgao orgaoBuscaSelecionado = null;
	private List<Orgao> listaOrgaoBusca = new ArrayList<Orgao>();
	private Pessoa pessoaSelecionada = new Pessoa();
	private Boolean rendCadastro;
	private Familia familiaEdita;
	private String dataNascimento;
	private String cor;
	private String sitMatrimonial;
	private String idade;
	private String dataCadastro;
	private List<Atendimento> listaAtendimentos;
	private String idAtendimentoSelecionado;
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	private Conselheiro conselheiroLogado; 	
	private Boolean dlgAtendimentoAberto;
	private String nomeLogado;
	private Boolean rendBtHistorico;
	private Date dataIniHistorico;
	private Date dataFimHistorico;
	private String numeroNovaPastaFamilia;
	
	public FamiliaCadastroBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");                                        
	}
	
	@PostConstruct
	public void ini(){

		try {
			listaEstado = new EstadoDAO().listaEstados();
			desativaOrgao = Boolean.TRUE;
			listaOrgaoBusca =  new OrgaoDAO().busca();
			rendCadastro = Boolean.FALSE;
			dataCadastro = formato.format(new Date());
			listaAtendimentos = new ArrayList<Atendimento>();
			nomeLogado = conselheiroLogado.getNomeUsual();
			dlgAtendimentoAberto = Boolean.FALSE;
			rendBtHistorico = Boolean.FALSE;
			numeroNovaPastaFamilia = "";
			verificaEditar();
		} catch (Exception e) {	
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void verificaEditar(){
		
		try {
			
			String idFamilia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
			
			String idDlgAtendimento = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("d");
			
			if(idFamilia != null){
				MembroTela mt;
				familiaEdita = new Familia();
				familia = new FamiliaDAO().buscaCodigo(Long.parseLong(idFamilia));
				familiaEdita = familia;
				estadoSelecionado =  familia.getLogradouro().getBairro().getCidade().getEstado();
				cidadeSelecionado = familia.getLogradouro().getBairro().getCidade();
				bairroSelecionado = familia.getLogradouro().getBairro();
				logradouroSelecionado = familia.getLogradouro();
				dataCadastro = formato.format(familia.getDataCadastro());
				listaAtendimentos = new AtendimentoDAO().buscaAtendimentos(familia);
				rendBtHistorico = Boolean.TRUE;
				
				if(familia.getConselheiroRegistro() != null){
					nomeLogado = familia.getConselheiroRegistro().getPessoa().getNomeCompleto(); 
				}
				
				if(idDlgAtendimento != null && idDlgAtendimento.equals("t")){
					RequestContext.getCurrentInstance().execute("PF('dlg1').show();");
					//dlgAtendimentoAberto = Boolean.TRUE;
				}
				
				buscaCidade();
				buscaBairro();
				buscaLogradouro();
				
				for (Membro membro : familia.getListaMembros()) {
					if(membro.getDataDesvinculo() == null){
						mt = new MembroTela();
						mt.setIdMembro(membro.getId());
						mt.setNome(membro.getPessoa().getNomeCompleto());
						mt.setIdade(Helper.executaCalculoIdade(membro.getPessoa().getDataNascimento()).toString());
						mt.setPessoa(membro.getPessoa());
						listaMembros.add(mt);
					}					
				}
				
				Collections.sort(listaMembros);
								
			}
			
		} catch (Exception e) {
			// TODO: handle exception
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
	
	public void alteraOrgaoBusca(){
		
		if("CPF".equals(tipoBusca)){
			desativaOrgao = Boolean.TRUE;
		}else if("CrtNasc".equals(tipoBusca)){
			desativaOrgao = Boolean.TRUE;
		}else if("RG".equals(tipoBusca)){
			desativaOrgao = Boolean.FALSE;
		}
		
	}
	
	public void vinculaPessoa(){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido adicionar.");
			return;
		}
		
		boolean salvou = true;		
		
		try {
			
			MembroTela membroTela = new MembroTela();			
			membroTela.setNome(pessoaSelecionada.getNomeCompleto());
			membroTela.setIdade(Helper.executaCalculoIdade(pessoaSelecionada.getDataNascimento()).toString());
			membroTela.setPessoa(pessoaSelecionada);
			
			for (MembroTela mT : listaMembros) {
				if(mT.getPessoa().equals(pessoaSelecionada)){
					JSFUtil.addErrorMessage("Membro já adicionado, busque por outra pessoa");
					salvou = true;
					rendCadastro = false;
					documentoBusca = "";
					tipoBusca = null;
					return;
				}
			}						
			
			listaMembros.add(membroTela);
			
			Collections.sort(listaMembros);
			
			FacesUtil.getRequestContext().addCallbackParam("salvou", salvou);
			
			rendCadastro = false;
			documentoBusca = "";
			tipoBusca = null;
			salvou = false;
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao vincular membro familiar "+e.getMessage());
			System.out.println("Erro ao vincular membro familiar "+e.getMessage());
			salvou = true;
		}					
	}
	
	public void buscaPessoa(){
			
		if(!"".equals(tipoBusca)){
			if(documentoBusca == null || "".equals(documentoBusca)){
				JSFUtil.addErrorMessage("Informe o numero do documento!");
			}else{
				try {
					if("RG".equals(tipoBusca) && orgaoBuscaSelecionado == null){
						JSFUtil.addErrorMessage("Informe o Orgão para buscar por RG");
						return;
					}else if("CPF".equals(tipoBusca)){
						documentoBusca = documentoBusca.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\_", "");
						if(CpfValidator.validate(documentoBusca)){
							JSFUtil.addErrorMessage("CPF informado inválido!!");
							return;
						}
					}else if("RG".equals(tipoBusca)){
						if(documentoBusca.length() < 7){
							JSFUtil.addErrorMessage("Informe o RG corretamente!");
							return;
						}
					}else if("CrtNasc".equals(tipoBusca)){
						if(documentoBusca.length() < 5){
							JSFUtil.addErrorMessage("Informe o RG corretamente!");
							return;
						}
					}
				
					Pessoa pessoa = new PessoaDAO().buscaPorDocumento(tipoBusca, documentoBusca, orgaoBuscaSelecionado);
					
					
					
					if(pessoa != null){
						
						if(new MembroDao().buscaMembro(pessoa) != null){
							JSFUtil.addErrorMessage("Pessoa já vinculada a um núcleo familiar!..");
							return;
						}
						
						
						switch (pessoa.getCor()) {
						case "AM":
							cor = "Amarela";
							break;
							
						case "BR":
							cor = "Branca";
							break;
							
						case "IN":
							cor = "Indígena";	
							break;
								
						case "PA":
							cor = "Parda";	
							break;
						
						case "PR":
							cor = "Preta";
							break;

						default:
							cor = "S/C";
							break;
						}
						
						switch (pessoa.getSituacaoMatrimonial()) {
						case "AM":
							sitMatrimonial = "Amasiada(o)";
							break;
							
						case "CS":
							sitMatrimonial = "Casada(o)";
							break;
							
						case "DS":
							sitMatrimonial = "Desconhecida(o)";	
							break;
								
						case "DV":
							sitMatrimonial = "Divorciada(o)";	
							break;
						
						case "FL":
							sitMatrimonial = "Falecida(o)";
							break;
						
						case "SP":
							sitMatrimonial = "Separada(o)";
							break;
						
						case "ST":
							sitMatrimonial = "Solteira(o)";
							break;
						
						case "VV":
							sitMatrimonial = "Viuva(o)(o)";
							break;

						default:
							sitMatrimonial = "S/C";
							break;
						
						}
						
						dataNascimento = formato.format(pessoa.getDataNascimento());
						idade = Helper.executaCalculoIdade(pessoa.getDataNascimento()).toString();
						rendCadastro = Boolean.TRUE;
						pessoaSelecionada = pessoa;
					}else{						
						JSFUtil.addWarnMessage("Não encontrado. Busque por outro documento");
					}
										
				} catch (Exception e) {
					JSFUtil.addErrorMessage(e.getMessage());
					System.out.println("ERROOOOO...: "+e.getMessage());
				}															
			}			
		}else{
			JSFUtil.addErrorMessage("Selecione um tipo de busca!!!");
		}
		
	}
	
	public void salvarNucleoFamiliar(){
		
		try {
			
			if(conselheiroLogado.getNivelAcesso().equals("col")){
				JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido salvar.");
				return;
			}
			
			System.out.println("Entrou no metodo de salvar familia...");
		
			FamiliaDAO familiaDao = new FamiliaDAO();
			MembroDao membroDao = new MembroDao();
			
			if(familiaEdita == null){
				familia.setNumeroPasta(Helper.proximoNumeroPasta().toString());
			}						
			
			if(!"".equals(familia.getNumeroPasta().replaceAll(" ", "").trim())){
				
				familia.setNumeroPasta(familia.getNumeroPasta().replaceAll(" ", "").trim());
				
				Familia f = null;
				f = familiaDao.buscaRegistro(familia.getNumeroPasta());
				
				if(familiaEdita != null){
					if(!familiaEdita.getNumeroPasta().equals(f.getNumeroPasta())){
						JSFUtil.addErrorMessage("Este numero de registro já está sendo utilizado!");
						return;
					}
				}else if(f != null){
					JSFUtil.addErrorMessage("Este numero de registro já está sendo utilizado!");
					return;
				}
																
			}
				if(logradouroSelecionado != null ){
					
					if("".equals(familia.getNumero().replaceAll(" ", "").trim())){
						JSFUtil.addErrorMessage("Informe o numero");
						return;
					}else{
						familia.setNumero(familia.getNumero().replaceAll(" ", "").trim());
					}
					
					
					if(!listaMembros.isEmpty()){
						
						Boolean adultoForaLista = Boolean.TRUE;
						Boolean menorForaLista = Boolean.TRUE;
						for (MembroTela membroTela : listaMembros) {
							if(Helper.executaCalculoIdade(membroTela.getPessoa().getDataNascimento()) > 18){
								adultoForaLista = Boolean.FALSE;
							}else{
								menorForaLista = Boolean.FALSE;
							}
						}
						
						if(adultoForaLista || menorForaLista){
							JSFUtil.addErrorMessage("Informe no minimo um adulto e um menor");
							return;
						}
					
						familia.setLogradouro(logradouroSelecionado);
						
						if(familiaEdita != null){
							familia.setDataCadastro(familiaEdita.getDataCadastro());
							familia.setConselheiroRegistro(conselheiroLogado);
						}else{
							familia.setDataCadastro(new Date());
						}
						
						familia.setConselheiroRegistro(conselheiroLogado);
						familia.setNumeroPasta(Helper.formataNumeroPasta(familia.getNumeroPasta()));																		
						
						familia = familiaDao.salvar(familia);
						Status status = new Status();
						status.setId(1L);						
						
						if(familiaEdita != null){
							
							// verifica se tem algum membro novo add
							for (MembroTela mT : listaMembros) {
								boolean salvaMembro = true;
								
								for (Membro membro : familiaEdita.getListaMembros()) {
									if(membro.getPessoa().equals(mT.getPessoa())){
										salvaMembro = false;
									}
								}
								

								if(salvaMembro){
									Membro m = new Membro();
									m.setPessoa(mT.getPessoa());
									m.setFamilia(familia);
									m.setDataCadastro(new Date());
									m.setStatus(status);
									m = membroDao.salvar(m);
									familiaEdita.getListaMembros().add(m);
								}								
							}
							
							
							// verifica se algum membro foi excluido da familia
							for (Membro m : familiaEdita.getListaMembros()) {
								
								boolean excluiMembro = true;
								
								for (MembroTela mT : listaMembros) {
									if(mT.getPessoa().equals(m.getPessoa())){
										excluiMembro = false;										
									}
								}
								

								if(excluiMembro){
																		
									membroDao.excluir(m);
									//familiaEdita.getListaMembros().add(m);
								}
								
							}
						}else{
							for (MembroTela mT : listaMembros) {
								Membro m = new Membro();
								m.setPessoa(mT.getPessoa());
								m.setFamilia(familia);
								m.setDataCadastro(new Date());
								m.setStatus(status);
								m = membroDao.salvar(m);
							}
						}
						if(familiaEdita == null){
							numeroNovaPastaFamilia = familia.getNumeroPasta(); 
							RequestContext.getCurrentInstance().execute("PF('dlgNovaFamilia').show();");
						}else{
							numeroNovaPastaFamilia = "";
						}
						JSFUtil.addInfoMessage("Núcleo Familiar salvo com sucesso!");
						
						familia = new Familia();
						listaCidade = new ArrayList<Cidade>();
						listaBairro = new ArrayList<Bairro>();
						listaLogradouro = new ArrayList<Logradouro>();
						
						estadoSelecionado = new Estado();
						cidadeSelecionado = new Cidade();
						bairroSelecionado = new Bairro();
						logradouroSelecionado = new Logradouro();
						listaMembros = new ArrayList<MembroTela>();
						familiaEdita = null;
						dataCadastro = formato.format(new Date());
						
						
					}else{
						JSFUtil.addErrorMessage("Informe ao menos um membro do núcleo familiar");
					}
					
				}else{
					JSFUtil.addErrorMessage("Informe o endereço do núcleo familiar");
				}
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("ERRO AO SALVAR: "+e.getMessage());
			System.out.println("Erro salvar familia...:"+e.getMessage());
		}
	}
	
	public void excluirMembroLista(ActionEvent event){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! ", "Procedimento Inválido! Nível de acesso não permitido salvar.");
			return;
		}
		
		MembroTela membroTela = (MembroTela) event.getComponent().getAttributes().get("membroSelecionado");
		
		System.out.println("Memebro selecionado para exclusão: "+membroTela.getNome());
		
		listaMembros.remove(membroTela);		
		Collections.sort(listaMembros);		
		JSFUtil.addWarnMessage("Membro editado porém ainda não excluido. Para confirmar exclusão clique em SALVAR");
	}
	
	public String preparaFamiliaMembro(){		
		try {
			
			System.out.println("ID SELECIONADO PARA EDITAR TELA DE BUSCA: "+idMembro);
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}		
		return "familia_membro?faces-redirect=true&c="+idMembro;		
	}
	
	public String preparaEditaPessoa(){		
		try {
			
			System.out.println("ID SELECIONADO PARA EDITAR TELA DE BUSCA: "+idMembro);
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}		
		return "cidadao_cadastro?faces-redirect=true&c="+idMembro;		
	}
	
	public String preparaMoverMembro(){		
		try {
			
			System.out.println("ID SELECIONADO PARA EDITAR TELA DE BUSCA: "+idMembro);
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar: "+e.getMessage());
			JSFUtil.addErrorMessage("ERRO ao Buscar: "+e.getMessage());
		}		
		return "mover_membro?faces-redirect=true&c="+idMembro;		
	}
	
	public String navegaAtendimento(){		
		
		System.out.println("Atendimento Selecionado: "+idAtendimentoSelecionado);
		
		return "atendimento_cadastro?faces-redirect=true&c="+idAtendimentoSelecionado+"&f="+familiaEdita.getId();
		
	}
	
	public void executaRelatorio(){
		
		try {
			Map<String, Object> parametros = new HashMap<>();

			
			NucleoFamiliarDTO nucleoFamiliar = new NucleoFamiliarDTO();
			nucleoFamiliar.setNumeroNucleo(familiaEdita.getNumeroPasta());
			nucleoFamiliar.setDataInicial("23/01/2015");
			nucleoFamiliar.setDataFinal("23/12/2015");
			nucleoFamiliar.setConselheiro(familiaEdita.getConselheiroRegistro().getPessoa().getNomeCompleto());
			
			String criancas = "";
			String adultos = "";
			
			for (Membro membro : familiaEdita.getListaMembros()) {								
				if(Helper.executaCalculoIdade(membro.getPessoa().getDataNascimento()) < 18){
					criancas = criancas+membro.getPessoa().getNomeCompleto()+", ";
				}else{
					adultos = adultos+membro.getPessoa().getNomeCompleto()+", ";
				}
			}
			
			
			
			nucleoFamiliar.setCriancas(criancas);
			nucleoFamiliar.setAdultos(adultos);															
			nucleoFamiliar.setEndereco(familiaEdita.getLogradouro().getNome()+", "+
									   familiaEdita.getNumero()+" - "+
									   familiaEdita.getLogradouro().getBairro().getNome()+", "+
									   familiaEdita.getLogradouro().getBairro().getCidade().getNome()+" - "+
									   familiaEdita.getLogradouro().getBairro().getCidade().getEstado().getSigla());
			
			nucleoFamiliar.setObs(familiaEdita.getObs());
			
			List<NucleoFamiliarDTO> lista = new ArrayList<NucleoFamiliarDTO>();
			lista.add(nucleoFamiliar);
			
			String caminho = "/relatorios/NucleoFamiliar.jasper";

			ReportUtil.executarRelatorio(caminho, parametros, "Consulta-Familia", lista);

		} catch (Exception e) {
			e.printStackTrace();
		}	
	}					
	
	public void executaRelatorioPrincipal(){
		
		try {			
			
			if(dataIniHistorico == null || dataFimHistorico == null){				
				JSFUtil.addErrorMessage("ERRO: Informe as datas de inicio e fim");
				return;
			}
			
			if(dataFimHistorico.after(dataIniHistorico) || dataFimHistorico.equals(dataIniHistorico)){
				
				AtendimentoDTO atendimentoDTO;
				
				List<AtendimentoDTO> listaAtendimentoDTO = new ArrayList<AtendimentoDTO>();
				List<Adendo> listaAdendos;
				RegistroDireitoVioladoDAO registroDireitoVioladoDAO = new RegistroDireitoVioladoDAO();			
				
				NucleoFamiliarDTO nucleoFamiliar = new NucleoFamiliarDTO();
				nucleoFamiliar.setNumeroNucleo(familiaEdita.getNumeroPasta());
				nucleoFamiliar.setDataInicial(Helper.formatDate().format(dataIniHistorico));
				nucleoFamiliar.setDataFinal(Helper.formatDate().format(dataFimHistorico));
				nucleoFamiliar.setConselheiro(familiaEdita.getConselheiroRegistro().getPessoa().getNomeCompleto());
				
				String criancas = "";
				String adultos = "";
				
				for (Membro membro : familiaEdita.getListaMembros()) {											
					if(Helper.executaCalculoIdade(membro.getPessoa().getDataNascimento()) < 18){
						criancas = criancas+membro.getPessoa().getNomeCompleto()+", ";
					}else{
						adultos = adultos+membro.getPessoa().getNomeCompleto()+", ";
					}
				}
				
				
				
				nucleoFamiliar.setCriancas(criancas);
				nucleoFamiliar.setAdultos(adultos);															
				nucleoFamiliar.setEndereco(familiaEdita.getLogradouro().getNome()+", "+
										   familiaEdita.getNumero()+" - "+
										   familiaEdita.getLogradouro().getBairro().getNome()+", "+
										   familiaEdita.getLogradouro().getBairro().getCidade().getNome()+" - "+
										   familiaEdita.getLogradouro().getBairro().getCidade().getEstado().getSigla());
				
				nucleoFamiliar.setObs(familiaEdita.getObs());						
				
				for (Atendimento atendimento : listaAtendimentos) {				
					
					Date dataFato = new SimpleDateFormat("dd/MM/yyyy").parse(atendimento.getDataFatoFormatada());								
					
					if(dataFato.equals(dataIniHistorico) || dataFato.equals(dataFimHistorico) ||
							(dataFato.after(dataIniHistorico) && dataFato.before(dataFimHistorico))){
						
						atendimentoDTO = new AtendimentoDTO();
						
						atendimentoDTO.setData(atendimento.getDataFatoFormatada());
						atendimentoDTO.setConselheiro(atendimento.getConselheiroRegistro().getPessoa().getNomeCompleto());
						atendimentoDTO.setFormaDenuncia(atendimento.getViaSolitacao().getNome());
						atendimentoDTO.setOrigemDenuncia(atendimento.getOrigemAtendiemnto().getNome());
						atendimentoDTO.setLocalViolacao(atendimento.getTipoLocalFato().getNome());
						atendimentoDTO.setTipoSolicitacao(atendimento.getTipoAtendimento().getNome());
						
						String vitimas ="";
						for (Vitima vitima : atendimento.getListaVitimas()) {
							vitimas = vitimas+vitima.getMembro().getPessoa().getNomeCompleto()+", ";
						}				
						atendimentoDTO.setVitimas(vitimas);
						vitimas ="";
						
						String violadores ="";
						
						if(!atendimento.getListaAgenteViolador().isEmpty()){
							for (Violador violador : atendimento.getListaAgenteViolador()) {
								if(violador.getPessoa() != null){
									violadores = violadores+violador.getPessoa().getNomeCompleto()+", ";
								}else{
									violadores = violadores+violador.getInstituicao().getNomeRazao()+", ";
								}
								
							}
						}else{
							if(atendimento.getVioladorNaoIdentificado()){
								violadores = "VIOLADOR NÃO IDENTIFICADO";
							}
						}
															
						atendimentoDTO.setVioladores(violadores);
						violadores ="";
										
						atendimentoDTO.setDescricaoFatos(atendimento.getDescricaoAtendimento());
						
						/*
						 * Adendos
						 */
						listaAdendos = new ArrayList<Adendo>();
						listaAdendos = new AdendoDAO().buscaAdendo(atendimento);
						
						if(!listaAdendos.isEmpty()){
							List<AdendoDTO> lista = new ArrayList<AdendoDTO>();
							AdendoDTO adendtoDTO;
							for (Adendo adendo : listaAdendos) {
								adendtoDTO = new AdendoDTO();
								adendtoDTO.setDataCadastro(adendo.getDataFatoFormatada());
								
								// retirar este if no futuro
								if(adendo.getConselheiroRegistro()!= null){
									adendtoDTO.setConselheiro(adendo.getConselheiroRegistro().getPessoa().getNomeCompleto());
								}
								
								adendtoDTO.setDescricao(adendo.getDescricao());
								lista.add(adendtoDTO);
							}
							atendimentoDTO.setAdendos(lista);
						}
						
						
						List<RegistroDireitoViolado> listaRegistroDireitoVioladoAtendimento;
						List<RegistroMedidaAplicada> listaRegistroMedidaAplicadas;
						
						List<DireitoVioladoDTO> listaDireitoVioladoDTO = new ArrayList<DireitoVioladoDTO>();
						listaRegistroDireitoVioladoAtendimento = registroDireitoVioladoDAO.busca(atendimento);
						DireitoVioladoDTO direitoVioladoDTO;
						List<MedidaAplicadaDTO> listaMedidaAplicadaDTO;
						MedidaAplicadaDTO medidaAplicadaDTO;
						
						for (RegistroDireitoViolado registroDireitoViolado : listaRegistroDireitoVioladoAtendimento) {
							direitoVioladoDTO = new DireitoVioladoDTO();
							direitoVioladoDTO.setDireitoViolado(registroDireitoViolado.getDireitoViolado().getNome());
							direitoVioladoDTO.setGrupoDireito(registroDireitoViolado.getDireitoViolado().getGrupoDeDireito().getNome());
							direitoVioladoDTO.setObs(registroDireitoViolado.getObs());
							direitoVioladoDTO.setVitima(registroDireitoViolado.getVitima().getMembro().getPessoa().getNomeCompleto());
							direitoVioladoDTO.setCaminhoSub(JSFUtil.getRealPath("/WEB-INF/relatorios/"));
							
							listaRegistroMedidaAplicadas = new ArrayList<RegistroMedidaAplicada>();
							listaRegistroMedidaAplicadas = registroDireitoViolado.getListaRegistroMedidaAplicada();					
							listaMedidaAplicadaDTO = new ArrayList<MedidaAplicadaDTO>();
							
							for (RegistroMedidaAplicada registroMedidaAplicada : listaRegistroMedidaAplicadas) {
								medidaAplicadaDTO = new MedidaAplicadaDTO();
								medidaAplicadaDTO.setMedidaAplicada(registroMedidaAplicada.getMedidaAplicada().getNome());
								medidaAplicadaDTO.setMedidaRazao(registroMedidaAplicada.getMedidaEmRazao().getNome());
								medidaAplicadaDTO.setObs(registroMedidaAplicada.getDescricao());
								
								if(!registroMedidaAplicada.getListaVioladorRegistroMedidaAplicada().isEmpty()){
									String violador = "Violador: ";
									for (VioladorRegistroMedidaAplicada violadorRegistroMedidaAplicada : registroMedidaAplicada.getListaVioladorRegistroMedidaAplicada()) {
										violador=violador+violadorRegistroMedidaAplicada.getViolador().getNomeViolador()+", ";
									}
									medidaAplicadaDTO.setViolador(violador);
								}else{
									medidaAplicadaDTO.setViolador("Violado");
								}
								
								listaMedidaAplicadaDTO.add(medidaAplicadaDTO);
							}
							
							direitoVioladoDTO.setMedidasAplicadas(listaMedidaAplicadaDTO);
							listaDireitoVioladoDTO.add(direitoVioladoDTO);
						}
						atendimentoDTO.setDireitosViolados(listaDireitoVioladoDTO);
						
						List<AtribuicaoDTO> listaAtribuicaoDTO = new ArrayList<AtribuicaoDTO>();
						for (Atribuicao atribuicao : atendimento.getListaAtribuicoes()) {
							
							AtribuicaoDTO atribuicaoDTO = new AtribuicaoDTO();
							atribuicaoDTO.setData(Helper.formatDate().format(atribuicao.getData()));
							atribuicaoDTO.setConselheiro(atribuicao.getConselheiro().getNomeUsual());
							atribuicaoDTO.setDescumpridor(atribuicao.getDescumpridor().getAgenteVioladorClasse().getNome()+", "+atribuicao.getDescumpridor().getNome());
							atribuicaoDTO.setDescricao(atribuicao.getDescricao());
							//atribuicaoDTO.setCaminhoSub(JSFUtil.getRealPath("/WEB-INF/relatorios/"));
							atribuicaoDTO.setCaminhoSub("C:\\Users\\Thiago Henrique\\Documents\\GitHub\\Conselho\\Conselho\\src\\main\\webapp\\WEB-INF\\relatorios\\");
							
							List<DeterminacaoAplicadaDTO> listaDeterminacaoAplicadaDTO = new ArrayList<DeterminacaoAplicadaDTO>();
							for (DeterminacaoAplicada determinacao : atribuicao.getListaDeterminacoesAplicadas()) {								
								DeterminacaoAplicadaDTO determinacaoAplicadaDTO = new DeterminacaoAplicadaDTO();
								determinacaoAplicadaDTO.setNomeDeterminacao(determinacao.getDeterminacao().getNome());
								listaDeterminacaoAplicadaDTO.add(determinacaoAplicadaDTO);								
							}
							atribuicaoDTO.setListaDeterminacaoAplicada(listaDeterminacaoAplicadaDTO);							
							listaAtribuicaoDTO.add(atribuicaoDTO);
						}
						atendimentoDTO.setListaAtribuicao(listaAtribuicaoDTO);
						listaAtendimentoDTO.add(atendimentoDTO);					
					}					
				}									                       														
													
				new ReportUtil().executePrincipal(nucleoFamiliar, listaAtendimentoDTO, "Historico Familiar");
				
			}else{
				JSFUtil.addErrorMessage("Data final inferior a data incial, selecione novas datas");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERRO: "+e.getMessage());
		}	
	}
	
	public Boolean getRendBtHistorico() {
		return rendBtHistorico;
	}

	public void setRendBtHistorico(Boolean rendBtHistorico) {
		this.rendBtHistorico = rendBtHistorico;
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

	public Boolean getDesativaOrgao() {
		return desativaOrgao;
	}

	public void setDesativaOrgao(Boolean desativaOrgao) {
		this.desativaOrgao = desativaOrgao;
	}

	public Orgao getOrgaoBuscaSelecionado() {
		return orgaoBuscaSelecionado;
	}

	public void setOrgaoBuscaSelecionado(Orgao orgaoBuscaSelecionado) {
		this.orgaoBuscaSelecionado = orgaoBuscaSelecionado;
	}

	public List<Orgao> getListaOrgaoBusca() {
		return listaOrgaoBusca;
	}

	public void setListaOrgaoBusca(List<Orgao> listaOrgaoBusca) {
		this.listaOrgaoBusca = listaOrgaoBusca;
	}

	public Pessoa getPessoaSelecionada() {
		return pessoaSelecionada;
	}

	public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}

	public Boolean getRendCadastro() {
		return rendCadastro;
	}

	public void setRendCadastro(Boolean rendCadastro) {
		this.rendCadastro = rendCadastro;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getSitMatrimonial() {
		return sitMatrimonial;
	}

	public void setSitMatrimonial(String sitMatrimonial) {
		this.sitMatrimonial = sitMatrimonial;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public String getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(String idMembro) {
		this.idMembro = idMembro;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<Atendimento> getListaAtendimentos() {
		return listaAtendimentos;
	}

	public void setListaAtendimentos(List<Atendimento> listaAtendimentos) {
		this.listaAtendimentos = listaAtendimentos;
	}

	public String getIdAtendimentoSelecionado() {
		return idAtendimentoSelecionado;
	}

	public void setIdAtendimentoSelecionado(String idAtendimentoSelecionado) {
		this.idAtendimentoSelecionado = idAtendimentoSelecionado;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getNomeLogado() {
		return nomeLogado;
	}

	public void setNomeLogado(String nomeLogado) {
		this.nomeLogado = nomeLogado;
	}

	public Boolean getDlgAtendimentoAberto() {
		return dlgAtendimentoAberto;
	}

	public void setDlgAtendimentoAberto(Boolean dlgAtendimentoAberto) {
		this.dlgAtendimentoAberto = dlgAtendimentoAberto;
	}

	public Date getDataIniHistorico() {
		return dataIniHistorico;
	}

	public void setDataIniHistorico(Date dataIniHistorico) {
		this.dataIniHistorico = dataIniHistorico;
	}

	public Date getDataFimHistorico() {
		return dataFimHistorico;
	}

	public void setDataFimHistorico(Date dataFimHistorico) {
		this.dataFimHistorico = dataFimHistorico;
	}

	public String getNumeroNovaPastaFamilia() {
		return numeroNovaPastaFamilia;
	}

	public void setNumeroNovaPastaFamilia(String numeroNovaPastaFamilia) {
		this.numeroNovaPastaFamilia = numeroNovaPastaFamilia;
	}		
}
