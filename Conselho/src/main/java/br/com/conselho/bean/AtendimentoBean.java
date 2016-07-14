package br.com.conselho.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.conselho.dao.AdendoDAO;
import br.com.conselho.dao.AgenteVioladorClasseDAO;
import br.com.conselho.dao.AgenteVioladorSubClasseDAO;
import br.com.conselho.dao.AtendimentoDAO;
import br.com.conselho.dao.AtribuicaoDAO;
import br.com.conselho.dao.BairroDAO;
import br.com.conselho.dao.CaracterizacaoViolacaoDireitoDAO;
import br.com.conselho.dao.CaracterizarViolacaoDireitoDAO;
import br.com.conselho.dao.CidadeDAO;
import br.com.conselho.dao.DeterminacaoAplicadaDAO;
import br.com.conselho.dao.DeterminacoesDAO;
import br.com.conselho.dao.DireitoFundamentalDAO;
import br.com.conselho.dao.DireitoVioladoDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.dao.FamiliaDAO;
import br.com.conselho.dao.GrupoDeDireitoDAO;
import br.com.conselho.dao.InstituicaoDAO;
import br.com.conselho.dao.LogradouroDAO;
import br.com.conselho.dao.MedidaAplicadaDAO;
import br.com.conselho.dao.MedidaEmRazaoDAO;
import br.com.conselho.dao.MembroDao;
import br.com.conselho.dao.OrgaoDAO;
import br.com.conselho.dao.OrigemAtendimentoDAO;
import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.dao.RegistroDireitoVioladoDAO;
import br.com.conselho.dao.RegistroMedidaAplicadaDAO;
import br.com.conselho.dao.TipoAtendimentoDAO;
import br.com.conselho.dao.TipoLocalFatoDAO;
import br.com.conselho.dao.ViaSolicitacaoDAO;
import br.com.conselho.dao.VioladorDAO;
import br.com.conselho.dao.VioladorRegistroMedidaAplicadaDAO;
import br.com.conselho.dao.VitimaDAO;
import br.com.conselho.domain.Adendo;
import br.com.conselho.domain.AgenteVioladorClasse;
import br.com.conselho.domain.AgenteVioladorSubClasse;
import br.com.conselho.domain.Atendimento;
import br.com.conselho.domain.Atribuicao;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.CaracterizacaoViolacaoDireito;
import br.com.conselho.domain.CaracterizarDireitoViolado;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Conselheiro;
import br.com.conselho.domain.Determinacao;
import br.com.conselho.domain.DeterminacaoAplicada;
import br.com.conselho.domain.DireitoFundamental;
import br.com.conselho.domain.DireitoViolado;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.GrupoDeDireito;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.domain.MedidaAplicada;
import br.com.conselho.domain.MedidaEmRazao;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.OrigemAtendimento;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.domain.RegistroDireitoViolado;
import br.com.conselho.domain.RegistroMedidaAplicada;
import br.com.conselho.domain.TipoAtendimento;
import br.com.conselho.domain.TipoLocalFato;
import br.com.conselho.domain.ViaSolicitacao;
import br.com.conselho.domain.Violador;
import br.com.conselho.domain.VioladorRegistroMedidaAplicada;
import br.com.conselho.domain.Vitima;
import br.com.conselho.util.AutenticaUsuario;
import br.com.conselho.util.CnpjValidator;
import br.com.conselho.util.CpfValidator;
import br.com.conselho.util.FacesUtil;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBAtendimento")
@ViewScoped
public class AtendimentoBean implements Serializable {
	
	private String template;	
	private String tipoBusca = null;	
	private String documentoBusca = null;
	private Boolean desativaOrgao;	
	private Orgao orgaoBuscaSelecionado = null;
	private List<Orgao> listaOrgaoBusca = new ArrayList<Orgao>();
	private Pessoa pessoaSelecionada = new Pessoa();
	private Boolean rendCadastro;	
	private String dataNascimento;
	private String cor;
	private String sitMatrimonial;
	private String idade;
	private String dataCadastro;
	private String numeroFamilia;
	
	private String tipoBuscaViolador = null;
	private Boolean desativaOrgaoViolador;
	private String documentoBuscaViolador = null;
	private Orgao orgaoBuscaSelecionadoViolador = null;
	private Boolean rendCadastroViolador;
	private Pessoa violadorSelecionadoPessoa;
	private Instituicao violadorSelecionadoInstituicao;
	
	private Membro membroSelecionado;	
	private List<Vitima> listaVitimas;
	private List<Violador> listaViolador;
	
	private List<AgenteVioladorClasse> listaAgenteVioladorClasse;
	private List<AgenteVioladorSubClasse> listaAgenteVioladorSubClasses;
	
	private AgenteVioladorClasse agenteVioladorClasseSelecionado;
	private AgenteVioladorSubClasse agenteVioladorSubClasseSelecionado;
	private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	private List<TipoLocalFato> listaTipoLocalFato = new ArrayList<TipoLocalFato>();
	private List<TipoAtendimento> listaTipoAtendimento = new ArrayList<TipoAtendimento>();
	private List<ViaSolicitacao> listaViaSolicitacao = new ArrayList<ViaSolicitacao>();
	private List<OrigemAtendimento> listaOrigemAtendimento = new ArrayList<OrigemAtendimento>();
	
	private TipoLocalFato tipoLocalFatoSelecionado;
	private TipoAtendimento tipoAtendimentoSelecionado;
	private ViaSolicitacao viaSolicitacaoSelecionado;
	private OrigemAtendimento origemAtendimentoSelecionado;
	
	private List<Estado> listaEstado;
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
	private String pontoReferencia;
	
	private String nomeDenunciante;
	private String foneDenunciante = "";
	
	private Date dataFato;
	private String descricaoAtendimento;
	private String procedenciaDenuncia;
	private Atendimento atendimento;
	private Boolean desabilitaBotoesEdita;
	
	private String descricaoAdendo;
	private List<Adendo> listaAdendos;
	private Boolean visualizar;
	
	private Adendo adendoSelecionado;
	
	private List<DireitoFundamental> listaDireitoFundamental;
	private DireitoFundamental direitoFundamentalSelecionado;
	private List<CaracterizacaoViolacaoDireito> listaCaracterizacaoViolacaoDireito;
	private CaracterizacaoViolacaoDireito caracterizacaoViolacaoDireitoSelecionado;
	private List<CaracterizarDireitoViolado> listaCaracterizarViolacaoDireito;
	private CaracterizarDireitoViolado caracterizarDireitoVioladoSelecionado;
	private Boolean rendTblAdendoDireitoViolado;
	private List<RegistroDireitoViolado> listaRegistroDireitoVioladoAtendimento;	
	private RegistroDireitoViolado registroDireitoVioladoAplicandoMedida;
	private List<MedidaAplicada> listaMedidaAplicadasEmVitima;
	private List<MedidaAplicada> listaMedidaAplicadasEmViolador;
	private List<MedidaEmRazao> listaMedidaEmRazao;
	
	private List<MedidaAplicada> listaMedidaAplicadaEmViolador;
	private List<MedidaAplicada> listaMedidaAplicadaEmVitima;

	private MedidaEmRazao medidaEmRazaoSelecionada;
	private String descricaoMedida;
	private Vitima vitimaSelecionada;
	private boolean btnIncluiDireitoViolado;
	private MedidaAplicada selecaoMedidaAplicada;
	private String obsDireitoViolado;
	
	private List<Vitima> listaVitimasSelecionadas;
	private List<Violador> listaVioladoresSelecionadas;
	private List<DireitoViolado> listaDireitoVioladoAplicado;
	private Conselheiro conselheiroLogado;
	
	private String dataResgistro;
	private String nomeLogado;
	private String idFamilia = null;
	
	private Boolean rendPessoa;
	private Boolean rendInstituicao;
	
	private String nomeRazaoVioladorSelecionado;
	private String cnpjCpfVioladorSelecinado;
	private String cieRgVioladorSelecionado;
	private String tipoOrgaoVioladorSelecionado;
	private String enderecoDataNascVioladorSelecionado;
	private String foneIdadeVioladorSelecionado;
	
	private Boolean incluiAdendoPessoa = Boolean.FALSE;	
	private Vitima vitimaExcluir = null;
	private Violador violadorExcluir = null;
	
	private String acao = "";	
	private final String INCLUIR_VITIMA = "incluirVitima";
	private final String INCLUIR_VIOLADOR = "incluirViolador";
	private final String EXCLUIR_VITIMA = "excluirVitima";
	private final String EXCLUIR_VIOLADOR = "excluirViolador";
	
	private final String INCLUIR_VITIMA_LISTA = "incluirVitimaLista";
	private final String INCLUIR_VIOLADOR_LISTA = "incluirVioladorLista";
	
	private List<Membro> listaMembroFamilia = new ArrayList<Membro>();
	private List<Membro> listaMembroFamiliaSelecionadosVitima = new ArrayList<Membro>();
	
	private List<Membro> listaMembroFamiliaVioladores = new ArrayList<Membro>();	
	
	private List<CaracterizarDireitoViolado> listaCaracterizarDireitoVioladosNoAtendimento;
	private CaracterizarDireitoViolado caracterizarDireitoVioladoSelecionadoAplicaMedida;
	private String numeroFamiliaBusca;
	private List<Vitima> listaVitimaAplicaMedidaEmGrupo = new ArrayList<Vitima>();
	private List<Vitima> listaVitimaAplicaMedidaEmGrupoSelecionados = new ArrayList<Vitima>();	
	private Boolean violadoNaoIdentificado;
	private Membro violadorMembroSelecionado;
	
	private List<Determinacao> listaDeterminacoes = new ArrayList<Determinacao>();
	private List<Determinacao> determinacoesSelecionadas = new ArrayList<Determinacao>();
	private AgenteVioladorSubClasse descumpridor;
	private String descricaoAtribuicao;
	private List<Atribuicao> listaAtribuicoes = new ArrayList<Atribuicao>();
	
	
	public AtendimentoBean() {
		template = AutenticaUsuario.verificaTemplate();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();        
        conselheiroLogado = (Conselheiro) session.getAttribute("conselheiroLogado");        
	}
	
	@PostConstruct
	public void ini(){

		try {
			desativaOrgao = Boolean.TRUE;
			desativaOrgaoViolador = Boolean.TRUE;
			listaOrgaoBusca =  new OrgaoDAO().busca();
			rendCadastro = Boolean.FALSE;
			dataCadastro = formato.format(new Date());
			membroSelecionado = null;
			listaVitimas = new ArrayList<Vitima>();
			listaViolador = new ArrayList<Violador>();
			listaAgenteVioladorClasse = new AgenteVioladorClasseDAO().busca();
			listaTipoLocalFato = new TipoLocalFatoDAO().lista();
			listaTipoAtendimento = new TipoAtendimentoDAO().busca();
			listaViaSolicitacao = new ViaSolicitacaoDAO().busca();
			listaOrigemAtendimento = new OrigemAtendimentoDAO().busca();
			listaEstado = new EstadoDAO().listaEstados();
			desabilitaBotoesEdita = Boolean.FALSE;
			visualizar = Boolean.FALSE;
			rendTblAdendoDireitoViolado = Boolean.FALSE;
			listaDireitoFundamental = new DireitoFundamentalDAO().lista(); 
			listaRegistroDireitoVioladoAtendimento = new ArrayList<RegistroDireitoViolado>();
			btnIncluiDireitoViolado = true;
			listaCaracterizarDireitoVioladosNoAtendimento = new ArrayList<CaracterizarDireitoViolado>();
			
			dataResgistro = Helper.formatDate().format(new Date());
			nomeLogado = conselheiroLogado.getNomeUsual();
			
			rendInstituicao = Boolean.FALSE;
			rendPessoa = Boolean.FALSE;
			violadoNaoIdentificado = Boolean.FALSE;
			listaDeterminacoes = new DeterminacoesDAO().lista();
			veriricavisualizacao();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	private void veriricavisualizacao(){
		try {
			
			String idAtendimento = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("c");
			
			idFamilia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("f");
			
			if(idAtendimento != null){
				
				desabilitaBotoesEdita = Boolean.TRUE;
				atendimento = new AtendimentoDAO().buscaPorCodigo(Long.parseLong(idAtendimento));
				listaVitimas = atendimento.getListaVitimas();
				listaViolador = atendimento.getListaAgenteViolador();
				
				viaSolicitacaoSelecionado = atendimento.getViaSolitacao();
				tipoAtendimentoSelecionado = atendimento.getTipoAtendimento();
				origemAtendimentoSelecionado = atendimento.getOrigemAtendiemnto();
				nomeDenunciante = atendimento.getNomeDenunciante();
				foneDenunciante = atendimento.getFoneIDenunciante();
				dataFato = atendimento.getDataFato();
				tipoLocalFatoSelecionado = atendimento.getTipoLocalFato();
				logradouroSelecionado = atendimento.getLogradouroFato();
				bairroSelecionado = logradouroSelecionado.getBairro();
				cidadeSelecionado = bairroSelecionado.getCidade();
				estadoSelecionado = cidadeSelecionado.getEstado();
				numero = atendimento.getNumeroLocalFato();
				complemento = atendimento.getComplementoLocalFato();
				cep = logradouroSelecionado.getCep().toString();
				pontoReferencia = atendimento.getPontoReferencia();
				descricaoAtendimento = atendimento.getDescricaoAtendimento();
				procedenciaDenuncia = atendimento.getFundamentoDenuncia();
				listaAdendos = new AdendoDAO().buscaAdendo(atendimento);
				listaRegistroDireitoVioladoAtendimento = new RegistroDireitoVioladoDAO().busca(atendimento);								
				violadoNaoIdentificado = atendimento.getVioladorNaoIdentificado() == null ? Boolean.FALSE : atendimento.getVioladorNaoIdentificado();
				listaAtribuicoes = atendimento.getListaAtribuicoes();
				distinctDireitoVioladoAtendimento();
				
				if(!listaAdendos.isEmpty()){
					rendTblAdendoDireitoViolado = Boolean.TRUE;
				}
				
				buscaCidade();
				buscaBairro();
				buscaLogradouro();
				
			}
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao carregar atendimento: "+e.getMessage());
		}				
	}
	
	public void distinctDireitoVioladoAtendimento(){
		
		try {
			List<RegistroDireitoViolado> resgistrosViolados = new RegistroDireitoVioladoDAO().busca(atendimento);
			listaCaracterizarDireitoVioladosNoAtendimento = new ArrayList<CaracterizarDireitoViolado>();
			
			for (RegistroDireitoViolado registroDireitoViolado : resgistrosViolados) {
				if(!listaCaracterizarDireitoVioladosNoAtendimento.contains(registroDireitoViolado.getCaracterizarDireitoViolado())){
					listaCaracterizarDireitoVioladosNoAtendimento.add(registroDireitoViolado.getCaracterizarDireitoViolado());
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void buscaCriancaViolada(){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido buscar.", "");
			return;
		}
		
		if(!"".equals(tipoBusca)){
			if(documentoBusca == null || "".equals(documentoBusca)){
				JSFUtil.addErrorMessage("Informe o numero do documento!");
			}else{
				try {
					if("RG".equals(tipoBusca) && orgaoBuscaSelecionado == null){
						JSFUtil.addErrorMessage("Informe o Orgão para buscar por RG");
						return;
					}else if("CPF".equals(tipoBusca)){
						if(CpfValidator.validate(documentoBusca)){
							JSFUtil.addErrorMessage("CPF informado inválido!!!");
							return;
						}
						documentoBusca = documentoBusca.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\_", "");						
					}else if("RG".equals(tipoBusca)){
						if(documentoBusca.length() < 7){
							JSFUtil.addErrorMessage("Informe o RG corretamente!!!");
							return;
						}
					}else if("CrtNasc".equals(tipoBusca)){
						if(documentoBusca.length() < 5){
							JSFUtil.addErrorMessage("Informe o RG corretamente!!!");
							return;
						}
					}
					
					Pessoa pessoa = null;
					
					pessoa = new PessoaDAO().buscaPorDocumento(tipoBusca, documentoBusca, orgaoBuscaSelecionado);
					
																		
					
					if(pessoa != null){
						
						membroSelecionado = new MembroDao().buscaMembro(pessoa);
						
						if(membroSelecionado == null){
							JSFUtil.addErrorMessage("Verifique o cadastro desta criança. Não possui familia vinculada!!!");
							return;
						}
						
						if(pessoa.getDataNascimento() != null){
							if(Helper.executaCalculoIdade(pessoa.getDataNascimento()) >= 18){
								JSFUtil.addErrorMessage("Este documento pertence ao cadastro de um adulto!!!");
								return;
							}
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
						numeroFamilia = membroSelecionado.getFamilia().getNumeroPasta();
						pessoaSelecionada = pessoa;
					}else{
						JSFUtil.addWarnMessage("Não encontrado, busque por outro documento. Ou verifique se já está cadastrado!!!");
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
	
	public void buscaViolador(){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido buscar.", "");
			return;
		}
		
		if(!"".equals(tipoBuscaViolador)){
			if(documentoBuscaViolador == null || "".equals(documentoBuscaViolador)){
				JSFUtil.addErrorMessage("Informe o numero do documento!!!");
			}else{
				try {
					if("RG".equals(tipoBuscaViolador) && orgaoBuscaSelecionadoViolador == null){
						JSFUtil.addErrorMessage("Informe o Orgão para buscar por RG!!!");
						return;
					}else if("CPF".equals(tipoBuscaViolador)){
						if(CpfValidator.validate(documentoBuscaViolador)){
							JSFUtil.addErrorMessage("CPF informado inválido!!!");
							return;
						}
						documentoBuscaViolador = documentoBuscaViolador.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\_", "");						
					}else if("RG".equals(tipoBuscaViolador)){
						if(documentoBuscaViolador.length() < 7){
							JSFUtil.addErrorMessage("Informe o RG corretamente!!!");
							return;
						}
					}else if("CrtNasc".equals(tipoBuscaViolador)){
						if(documentoBuscaViolador.length() < 5){
							JSFUtil.addErrorMessage("Informe o RG corretamente!!!");
							return;
						}
					}else if("CIE".equals(tipoBuscaViolador)){
						if(documentoBuscaViolador.equals("") || documentoBuscaViolador == null){
							JSFUtil.addErrorMessage("Informe o CIE");
							return;
						}
					}else if("CNPJ".equals(tipoBuscaViolador)){
						documentoBuscaViolador = documentoBuscaViolador.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\/", "").replaceAll("\\_", "");
						if(CnpjValidator.validate(documentoBuscaViolador)){
							JSFUtil.addErrorMessage("CNPJ INVÁLIDO");
							return;
						}
					}
					
					Pessoa pessoa = null;
					Instituicao instituicao = null;
					
					if("CIE".equals(tipoBuscaViolador)){
						List<Instituicao> list = new InstituicaoDAO().listaCIE(documentoBuscaViolador);
						if(list.size() > 1){
							JSFUtil.addErrorMessage("ERRO! ", "Instituição duplicada");
							return;
						}else{
							instituicao = list.get(0);
						}
					}else if("CNPJ".equals(tipoBuscaViolador)){
						List<Instituicao> list = new InstituicaoDAO().listaCNPJ(documentoBuscaViolador);
						if(list.size() > 1){
							JSFUtil.addErrorMessage("ERRO! ", "Instituição duplicada");
							return;
						}else{
							instituicao = list.get(0);
						}						
					}else{
						pessoa = new PessoaDAO().buscaPorDocumento(tipoBuscaViolador, documentoBuscaViolador, orgaoBuscaSelecionado);
					}
									 								
					
					if(pessoa != null){
												
						violadorSelecionadoPessoa = pessoa;
						violadorSelecionadoInstituicao = null;
//						if(membroSelecionado == null){
//							JSFUtil.addErrorMessage("Verifique o cadastro desta criança. Não possui familia vinculada.");
//							return;
//						}
//						
						if(pessoa.getDataNascimento() != null){
							if(Helper.executaCalculoIdade(pessoa.getDataNascimento()) <= 12){
								JSFUtil.addErrorMessage("Violador deve ter mais de 12 anos!");
								return;
							}
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
							sitMatrimonial = "Viuva(o)";
							break;

						default:
							sitMatrimonial = "S/C";
							break;
						
						}
						
						nomeRazaoVioladorSelecionado = pessoa.getNomeCompleto();
						cnpjCpfVioladorSelecinado = pessoa.getCpf();
						cieRgVioladorSelecionado = pessoa.getRg();
						tipoOrgaoVioladorSelecionado = (pessoa.getOrgao() != null ? pessoa.getOrgao().getNome() : "" );
						enderecoDataNascVioladorSelecionado = formato.format(pessoa.getDataNascimento());
						foneIdadeVioladorSelecionado = Helper.executaCalculoIdade(pessoa.getDataNascimento()).toString();
						
//						dataNascimento = formato.format(pessoa.getDataNascimento());
//						idade = Helper.executaCalculoIdade(pessoa.getDataNascimento()).toString();
						
						rendCadastroViolador = Boolean.TRUE;																		
						rendPessoa = Boolean.TRUE;
					    rendInstituicao = Boolean.FALSE;
					
					}else if(instituicao != null){
						
						violadorSelecionadoPessoa = null;
						violadorSelecionadoInstituicao = instituicao;
						
						nomeRazaoVioladorSelecionado = instituicao.getNomeRazao();
						cnpjCpfVioladorSelecinado = instituicao.getCnpj();
						cieRgVioladorSelecionado = instituicao.getCie();
						tipoOrgaoVioladorSelecionado = instituicao.getTipoInstituicao().getNome();
						enderecoDataNascVioladorSelecionado = instituicao.getLogradouro().getNome()+", "+instituicao.getNumero();
						foneIdadeVioladorSelecionado = instituicao.getFoneI();
						
//						violadorSelecionado = new Pessoa();						
//						violadorSelecionado.setNomeCompleto(instituicao.getNomeRazao());
//						pessoaSelecionada = new Pessoa();
//						pessoaSelecionada.setNomeCompleto(instituicao.getNomeRazao());
						
						rendCadastroViolador = Boolean.TRUE;						
						rendPessoa = Boolean.FALSE;
					    rendInstituicao = Boolean.TRUE;
						
					}else{		
						
						JSFUtil.addWarnMessage("Não encontrado, busque por outro documento, ou verifique se está cadastrado.");
						rendPessoa = Boolean.FALSE;
					    rendInstituicao = Boolean.FALSE;
					    rendCadastroViolador = Boolean.FALSE;
					    
					    violadorSelecionadoPessoa = null;
					    violadorSelecionadoInstituicao = null;
					}
										
				} catch (Exception e) {
					
					JSFUtil.addErrorMessage(e.getMessage());
					System.out.println("ERROOOOO...: "+e.getMessage());
					
					rendPessoa = Boolean.FALSE;
				    rendInstituicao = Boolean.FALSE;
				    rendCadastroViolador = Boolean.FALSE;
				    
				    violadorSelecionadoPessoa = null;
				    violadorSelecionadoInstituicao = null;
				}															
			}			
		}else{
			JSFUtil.addErrorMessage("Selecione um tipo de busca!!!");
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
	
	public void alteraOrgaoBuscaViolador(){
		
		if("RG".equals(tipoBuscaViolador)){
			desativaOrgaoViolador = Boolean.FALSE;
		}else{
			desativaOrgaoViolador = Boolean.TRUE;
		}
		
	}
	
	public void incluirVitimaLista(){
		
		try {
			
			if(!listaVitimas.isEmpty()){
				for (Vitima vitima : listaVitimas) {
					if(vitima.getMembro().equals(membroSelecionado)){
						JSFUtil.addWarnMessage("Esta criança ou adolescente já está na lista de vitimas!!!!");
						rendCadastro = false;
						membroSelecionado = null;
						documentoBusca = "";
						return;
					}
				}
			}
			
			if(desabilitaBotoesEdita){				
				RequestContext.getCurrentInstance().execute("PF('dlgAviso').show();");
				acao = INCLUIR_VITIMA;				
			}else{
				
				Vitima vitima = new Vitima();
				vitima.setMembro(membroSelecionado);
				vitima.setEscolaridade(membroSelecionado.getPessoa().getEscolaridade() != null ? membroSelecionado.getPessoa().getEscolaridade().getNome() : null);
				vitima.setIdade(Helper.executaCalculoIdade(membroSelecionado.getPessoa().getDataNascimento()));
				vitima.setTrabalha(membroSelecionado.getPessoa().getTrabalha() ? membroSelecionado.getPessoa().getTrabalha() : null);
				
				listaVitimas.add(vitima);
				rendCadastro = false;
				membroSelecionado = null;
				documentoBusca = "";
				
			}
						
		} catch (Exception e) {
			System.out.println("Erro ao incluir vitima a lista: "+e.getLocalizedMessage());
			JSFUtil.addErrorMessage("Erro ao incluir vitima a lista: "+e.getLocalizedMessage());
		}			
	}
		
	
	public void excluirVitimaLista(ActionEvent event){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido excluir.", "");
			return;
		}
		
		vitimaExcluir = (Vitima) event.getComponent().getAttributes().get("vitSelecionada");
		
		try {
						
			if(vitimaExcluir != null){
				
				if(desabilitaBotoesEdita){
					if(listaVitimas.size() == 1){
						JSFUtil.addErrorMessage("Atendimento deve conter no mínimo uma vitima");
						return;
					}else{
						RequestContext.getCurrentInstance().execute("PF('dlgAviso').show();");
						acao = EXCLUIR_VITIMA;
					}					
				}else{
					listaVitimas.remove(vitimaExcluir);			
					rendCadastro = false;
					JSFUtil.addInfoMessage("Vitima Excluido com sucesso");
				}
				
			}					
						
		} catch (Exception e) {
			System.out.println("Erro ao incluir vitima a lista: "+e.getLocalizedMessage());
			JSFUtil.addErrorMessage("Erro ao incluir vitima a lista: "+e.getLocalizedMessage());
		}
				
	}
	
	public void incluirVioladorLista(){
		
		try {
			
			if(agenteVioladorSubClasseSelecionado == null){
				JSFUtil.addErrorMessage("Informe classe e sub-classe do violador");
				return;
			}
			
			if(!listaViolador.isEmpty()){
				
				if(violadorSelecionadoPessoa != null){
					
					for (Violador violador : listaViolador) {
						if(violador.getPessoa() != null){
							if(violador.getPessoa().equals(violadorSelecionadoPessoa)){
								JSFUtil.addWarnMessage("Este cidadão já está na lista de violadores!!!!!");
								rendCadastroViolador = false;
								violadorSelecionadoPessoa = null;
								documentoBuscaViolador = "";
								return;
							}
						}						
					}
					
				}else if(violadorSelecionadoInstituicao != null){
					
					for (Violador violador : listaViolador) {
						if(violador.getInstituicao() != null){
							if(violador.getInstituicao().equals(violadorSelecionadoInstituicao)){
								JSFUtil.addWarnMessage("Este cidadão já está na lista de violadores!!!!!");
								rendCadastroViolador = false;
								violadorSelecionadoInstituicao = null;
								documentoBuscaViolador = "";
								return;
							}
						}
					}
					
				}								
				
			}												
			
			if(desabilitaBotoesEdita){				
				RequestContext.getCurrentInstance().execute("PF('dlgAviso').show();");
				acao = INCLUIR_VIOLADOR;				
			}else{
				
				Violador violador = new Violador();								
				
				if(violadorSelecionadoPessoa != null){
					violador.setPessoa(violadorSelecionadoPessoa);
					violador.setIdade(Helper.executaCalculoIdade(violadorSelecionadoPessoa.getDataNascimento()));
					violador.setEscolaridade(violadorSelecionadoPessoa.getEscolaridade() != null ? violadorSelecionadoPessoa.getEscolaridade().getNome() : null);
					violador.setEstadoCivil(violadorSelecionadoPessoa.getSituacaoMatrimonial());
				}else if(violadorSelecionadoInstituicao != null){
					violador.setInstituicao(violadorSelecionadoInstituicao);
				}
				
				violador.setAgenteVioladorSubClasse(agenteVioladorSubClasseSelecionado);				
				listaViolador.add(violador);
				rendCadastroViolador = false;
				rendInstituicao = Boolean.FALSE;
				rendPessoa = Boolean.FALSE;
				violadorSelecionadoPessoa = null;
				violadorSelecionadoInstituicao = null;						
				documentoBuscaViolador = "";
				agenteVioladorSubClasseSelecionado = new AgenteVioladorSubClasse();
				
			}						
			
		} catch (Exception e) {
			System.out.println("Erro ao incluir violador a lista: "+e.getLocalizedMessage());
			JSFUtil.addErrorMessage("Erro ao incluir violador a lista: "+e.getLocalizedMessage());
		}			
	}
	
	public void excluirVioladorLista(ActionEvent event){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido excluir.", "");
			return;
		}
		
		violadorExcluir = (Violador) event.getComponent().getAttributes().get("violSelecionada");
		
		try {
			
			if(desabilitaBotoesEdita){
				
				if(listaViolador.size() == 1){
					JSFUtil.addErrorMessage("Atendimento deve conter no mínimo um violador");
					return;
				}else{
					RequestContext.getCurrentInstance().execute("PF('dlgAviso').show();");
					acao = EXCLUIR_VIOLADOR;
				}
				
			}else{
				listaViolador.remove(violadorExcluir);			
				rendCadastroViolador = false;			
				JSFUtil.addInfoMessage("Violador Excluido com sucesso");
			}
						
		} catch (Exception e) {
			System.out.println("Erro ao incluir vitima a lista: "+e.getLocalizedMessage());
			JSFUtil.addErrorMessage("Erro ao incluir vitima a lista: "+e.getLocalizedMessage());
		}
				
	}
	
	public void buscaAgenteVioladorSubClasse(){
		try {
			if(agenteVioladorClasseSelecionado != null){
				listaAgenteVioladorSubClasses = new AgenteVioladorSubClasseDAO().busca(agenteVioladorClasseSelecionado);
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar sub-classe violador: "+e.getMessage());
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
	
	public void incluirAtendimento(){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido salvar.", "");
			return;
		}
		
		if(!listaVitimas.isEmpty()){			
			if(!listaViolador.isEmpty() || violadoNaoIdentificado){
				if(viaSolicitacaoSelecionado 	!= null && 
				   tipoAtendimentoSelecionado 	!= null &&
				   origemAtendimentoSelecionado != null &&
				   tipoLocalFatoSelecionado 	!= null &&
				   logradouroSelecionado		!= null	&&
				   (!numero.trim().equals("")) 			&&
				   descricaoAtendimento.length() > 10	&&
				   dataFato						!= null){
					
					try {
						
						if(!desabilitaBotoesEdita){
							
							Atendimento atendimento = new Atendimento();
							
							atendimento.setDataRegistro(new Date());
							atendimento.setViaSolitacao(viaSolicitacaoSelecionado);					
							atendimento.setTipoAtendimento(tipoAtendimentoSelecionado);
							atendimento.setOrigemAtendiemnto(origemAtendimentoSelecionado);
							
							atendimento.setNomeDenunciante(nomeDenunciante);
							atendimento.setFoneIDenunciante(foneDenunciante);
							
							atendimento.setDataFato(dataFato);
							atendimento.setTipoLocalFato(tipoLocalFatoSelecionado);
							atendimento.setLogradouroFato(logradouroSelecionado);
							atendimento.setNumeroLocalFato(numero);
							atendimento.setComplementoLocalFato(complemento);
							atendimento.setPontoReferencia(pontoReferencia);
							atendimento.setDescricaoAtendimento(descricaoAtendimento);
							atendimento.setFundamentoDenuncia(procedenciaDenuncia);
							atendimento.setConselheiroRegistro(conselheiroLogado);
							atendimento.setVioladorNaoIdentificado(violadoNaoIdentificado);
							
							// verificar se as listas estão populadas.
							
							new AtendimentoDAO().salvar(atendimento);
							
							this.atendimento = atendimento;
							VitimaDAO vitimaDAO = new VitimaDAO();
							
							for (Vitima vitima : listaVitimas) {
								vitima.setAtendimento(atendimento);
								vitimaDAO.salvar(vitima);							
							}
							
							VioladorDAO violadorDAO = new VioladorDAO();
							
							for (Violador violador : listaViolador) {
								violador.setAtendimento(atendimento);
								violadorDAO.salvar(violador);							
							}
						}else{
							
							atendimento.setFundamentoDenuncia(procedenciaDenuncia);							
							new AtendimentoDAO().salvar(atendimento);
							
						}
						
						
						JSFUtil.addInfoMessage("Atendimento salvo com sucesso!!!!");
						
					} catch (Exception e) {
						e.getStackTrace();
						System.out.println("Erro ao salvar: "+e.getMessage());
						JSFUtil.addErrorMessage("Erro ao salvar: "+e.getMessage());
				}																																			
					
				}else{
					JSFUtil.addErrorMessage("É necessário preencher Descrição do fato com seus itens obrigatórios.");
				}
			}else{
				JSFUtil.addErrorMessage("Informe no minimo um violador ou informe que o mesmo não é identificado.");				
			}					
		}else{
			JSFUtil.addErrorMessage("Informe no minimo uma criança e ou adolesente como Vitima");
		}
		
	}
	
	public void salvarAdendo(){				
		
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido salvar.", "");
			return;
		}
		
		boolean salvou = true;
		
		try {
			
			Adendo adendo = new Adendo();
			if(incluiAdendoPessoa){
				
				if(descricaoAdendo.length() > 25){					
					adendo.setDataCadastro(new Date());
					adendo.setConselheiroRegistro(conselheiroLogado);
					adendo.setAtendimento(atendimento);
					adendo.setDescricao(descricaoAdendo);																																					
				
					switch (acao) {
					
						case INCLUIR_VITIMA:
							
								new AdendoDAO().salvar(adendo);						
								Vitima vitima = new Vitima();
								vitima.setMembro(membroSelecionado);
								vitima.setEscolaridade(membroSelecionado.getPessoa().getEscolaridade() != null ? membroSelecionado.getPessoa().getEscolaridade().getNome() : null);
								vitima.setIdade(Helper.executaCalculoIdade(membroSelecionado.getPessoa().getDataNascimento()));
								vitima.setTrabalha(membroSelecionado.getPessoa().getTrabalha() ? membroSelecionado.getPessoa().getTrabalha() : null);
								vitima.setAdendoIncluir(adendo);
								vitima.setAtendimento(atendimento);
								new VitimaDAO().salvar(vitima);						
								listaVitimas.add(vitima);
								rendCadastro = false;	
								membroSelecionado = null;
								documentoBusca = "";
								visualizar = Boolean.TRUE;
								RequestContext.getCurrentInstance().update("formAtendimento");	
								JSFUtil.addInfoMessage("Vitima incluida com sucesso");
								
							break;
							
						case INCLUIR_VIOLADOR:
							
							Violador violador = new Violador();
							
							if(violadorSelecionadoPessoa != null){
								violador.setPessoa(violadorSelecionadoPessoa);
								violador.setIdade(Helper.executaCalculoIdade(violadorSelecionadoPessoa.getDataNascimento()));
								violador.setEscolaridade(violadorSelecionadoPessoa.getEscolaridade() != null ? violadorSelecionadoPessoa.getEscolaridade().getNome() : null);
								violador.setEstadoCivil(violadorSelecionadoPessoa.getSituacaoMatrimonial());
							}else if(violadorSelecionadoInstituicao != null){
								violador.setInstituicao(violadorSelecionadoInstituicao);
							}
							violador.setAgenteVioladorSubClasse(agenteVioladorSubClasseSelecionado);
							new AdendoDAO().salvar(adendo);
							violador.setAdendoIncluir(adendo);
							violador.setAtendimento(atendimento);
							new VioladorDAO().salvar(violador);
							listaViolador.add(violador);
							rendCadastroViolador = Boolean.FALSE;
							rendInstituicao = Boolean.FALSE;
							rendPessoa = Boolean.FALSE;
							violadorSelecionadoPessoa = null;
							violadorSelecionadoInstituicao = null;						
							documentoBuscaViolador = "";
							agenteVioladorSubClasseSelecionado = new AgenteVioladorSubClasse();
							visualizar = Boolean.TRUE;
							RequestContext.getCurrentInstance().update("formAtendimento");	
							JSFUtil.addInfoMessage("Violador incluido com sucesso");
							
							break;
						
						case EXCLUIR_VITIMA:
							
							if(vitimaExcluir != null){
								
								adendo.setDescricao("ADENDO PARA EXCLUIR VITIMA "+vitimaExcluir.getMembro().getPessoa().getNomeCompleto()+": \n"+descricaoAdendo);
								new AdendoDAO().salvar(adendo);
																					
								new VitimaDAO().excluir(vitimaExcluir);
								listaVitimas.remove(vitimaExcluir);
								rendCadastro = false;	
								membroSelecionado = null;
								documentoBusca = "";
								visualizar = Boolean.TRUE;
								RequestContext.getCurrentInstance().update("formAtendimento");	
								JSFUtil.addInfoMessage("Vitima excluido com sucesso");
							}else{
								JSFUtil.addErrorMessage("Vitima foi perdida :(");
							}
							
							break;
						
						case EXCLUIR_VIOLADOR:
							
							adendo.setDescricao("ADENDO PARA EXCLUIR VIOLADOR "+violadorExcluir.getNomeViolador()+": \n"+descricaoAdendo);
							new AdendoDAO().salvar(adendo);
							
							new VioladorDAO().excluir(violadorExcluir);
							listaViolador.remove(violadorExcluir);
							visualizar = Boolean.TRUE;
							RequestContext.getCurrentInstance().update("formAtendimento");	
							JSFUtil.addInfoMessage("Violador excluido com sucesso");
							break;
						
						case INCLUIR_VITIMA_LISTA:												
							
							VitimaDAO vitimaDAO = new VitimaDAO();
							
							descricaoAdendo = descricaoAdendo+"\n\n ADENDO PARA INCLUIR VITIMAS: \n ";						
							
							for (Membro membro : listaMembroFamiliaSelecionadosVitima) {
								descricaoAdendo = descricaoAdendo+membro.getPessoa().getNomeCompleto()+" \n";
								
							}
							
							adendo.setDescricao(descricaoAdendo);
							new AdendoDAO().salvar(adendo);
							
							for (Membro membro : listaMembroFamiliaSelecionadosVitima) {
								System.out.println("Membro selecionado: "+membro.getPessoa().getNomeCompleto());
								boolean incluir = true;
								if(!listaVitimas.isEmpty()){
									for (Vitima vitimaVerifica : listaVitimas) {
										if(vitimaVerifica.getMembro().equals(membro)){
											JSFUtil.addWarnMessage("Os menores já incluidos foram ignorados");						
											incluir = false;
										}
									}
								}
								
								if(incluir){															
									Vitima vitimaIncluir = new Vitima();
									vitimaIncluir.setAdendoIncluir(adendo);
									vitimaIncluir.setMembro(membro);
									vitimaIncluir.setEscolaridade(membro.getPessoa().getEscolaridade() != null ? membro.getPessoa().getEscolaridade().getNome() : null);
									vitimaIncluir.setIdade(Helper.executaCalculoIdade(membro.getPessoa().getDataNascimento()));
									vitimaIncluir.setTrabalha(membro.getPessoa().getTrabalha() ? membro.getPessoa().getTrabalha() : null);
									vitimaIncluir.setAtendimento(atendimento);
									vitimaDAO.salvar(vitimaIncluir);
									listaVitimas.add(vitimaIncluir);
								}								
								
							}			
							
							rendCadastro = false;
							membroSelecionado = null;
							documentoBusca = "";
							listaMembroFamiliaSelecionadosVitima = new ArrayList<Membro>();
							listaMembroFamilia = new ArrayList<Membro>();
							numeroFamiliaBusca = "";
							visualizar = Boolean.TRUE;
							RequestContext.getCurrentInstance().update("formAtendimento");	
							JSFUtil.addInfoMessage("Vitima incluida com sucesso");						
							break;
						
						case INCLUIR_VIOLADOR_LISTA:												
							
							VioladorDAO violadorDAO = new VioladorDAO();
							descricaoAdendo = descricaoAdendo+"\n\n ADENDO PARA INCLUIR VIOLADORES: \n ";						
							
							if (violadorMembroSelecionado != null) {
								descricaoAdendo = descricaoAdendo+violadorMembroSelecionado.getPessoa().getNomeCompleto()+" \n";
							}
							
							adendo.setDescricao(descricaoAdendo);
							new AdendoDAO().salvar(adendo);
													
							System.out.println("Membro selecionado: "+violadorMembroSelecionado.getPessoa().getNomeCompleto());																					
																				
							Violador violador2 = new Violador();																																																
							violador2.setPessoa(violadorMembroSelecionado.getPessoa());
							violador2.setIdade(Helper.executaCalculoIdade(violadorMembroSelecionado.getPessoa().getDataNascimento()));
							violador2.setEscolaridade(violadorMembroSelecionado.getPessoa().getEscolaridade() != null ? violadorMembroSelecionado.getPessoa().getEscolaridade().getNome() : null);
							violador2.setEstadoCivil(violadorMembroSelecionado.getPessoa().getSituacaoMatrimonial());
							violador2.setAgenteVioladorSubClasse(agenteVioladorSubClasseSelecionado);
							violador2.setAdendoIncluir(adendo);
							violador2.setAtendimento(atendimento);
							violadorDAO.salvar(violador2);
							listaViolador.add(violador2);
																							
							JSFUtil.addInfoMessage("Violador incluido com sucesso!");
							
							rendCadastro = false;
							membroSelecionado = null;
							documentoBusca = "";
							violadorMembroSelecionado = new Membro();
							listaMembroFamilia = new ArrayList<Membro>();
							numeroFamiliaBusca = "";
							visualizar = Boolean.TRUE;
							RequestContext.getCurrentInstance().update("formAtendimento");
							
							break;
		
						default:
							break;
					}
				
				}else{
					JSFUtil.addErrorMessage("Insira no minimo 25 caracteres na descrição...");
					salvou = false;
				}
				
			}else{				
				
				if(descricaoAdendo.length() > 25){
					adendo.setDataCadastro(new Date());
					adendo.setConselheiroRegistro(conselheiroLogado);
					adendo.setAtendimento(atendimento);
					adendo.setDescricao(descricaoAdendo);
					
					new AdendoDAO().salvar(adendo);
					
					JSFUtil.addInfoMessage("Salvo com sucesso");
				}else{
					JSFUtil.addErrorMessage("Insira no minimo 25 caracteres na descrição...");
					salvou = false;
				}
								
			}
			
			listaAdendos = new AdendoDAO().buscaAdendo(atendimento);

			if(!listaAdendos.isEmpty()){
				rendTblAdendoDireitoViolado = Boolean.TRUE;
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao salvar adendo: "+e.getMessage());
			JSFUtil.addErrorMessage("Erro ao salvar adendo: "+e.getMessage());
		}
		
		FacesUtil.getRequestContext().addCallbackParam("salvou", salvou);
	}
	
	public void limpaBucasMembrosFamilia(){
		
		rendCadastro = false;
		membroSelecionado = null;
		documentoBusca = "";
		numeroFamiliaBusca = "";
		violadorMembroSelecionado = new Membro();
		listaMembroFamilia = new ArrayList<Membro>();
		listaMembroFamiliaSelecionadosVitima = new ArrayList<Membro>();
		listaMembroFamilia = new ArrayList<Membro>();		
		
	}
	
	public void preparaVisualizaAdendo(ActionEvent event){
		
		Adendo adendo = (Adendo) event.getComponent().getAttributes().get("adendoSelecionada");
		
		dataCadastro = adendo.getDataFatoFormatada();
		descricaoAdendo = adendo.getDescricao();
		visualizar = Boolean.TRUE;
		
		
	}
	
	public void limpaCadastroAdendo(){
		
		descricaoAdendo = null;
		dataCadastro = formato.format(new Date());
		visualizar = Boolean.FALSE;
		verificaFechaAdendo();
		limpaBucasMembrosFamilia();
		
	}
	
	public void buscaCaracterizacaoViolacaoDireito(){
		
		try {
			listaCaracterizacaoViolacaoDireito = new CaracterizacaoViolacaoDireitoDAO().lista(direitoFundamentalSelecionado);
			btnIncluiDireitoViolado = true;
			listaCaracterizarViolacaoDireito = new ArrayList<CaracterizarDireitoViolado>();
			
			caracterizacaoViolacaoDireitoSelecionado = new CaracterizacaoViolacaoDireito();
			caracterizarDireitoVioladoSelecionado = new CaracterizarDireitoViolado();
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao carregar lista de direito violados: "+e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public void buscaCaracterizarViolacaoDireito(){
		
		try {
			listaCaracterizarViolacaoDireito = new CaracterizarViolacaoDireitoDAO().lista(caracterizacaoViolacaoDireitoSelecionado);
			btnIncluiDireitoViolado = true;
			caracterizarDireitoVioladoSelecionado = new CaracterizarDireitoViolado();			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao carregar lista de direito violados: "+e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public void incluiDireitoViolado(){				
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido incluir.", "");
			return;
		}
		
		boolean salvou = true;				
		
		try {														
			
			if(this.atendimento != null){
				if(caracterizarDireitoVioladoSelecionado != null){
					if(!listaVitimasSelecionadas.isEmpty()){
						if(obsDireitoViolado.length() > 25){
																			
							RegistroDireitoViolado registroDireitoViolado;
							RegistroDireitoVioladoDAO registroDireitoVioladoDAO = new RegistroDireitoVioladoDAO();
							
							for (Vitima vitima : listaVitimasSelecionadas) {
								
//								RegistroDireitoViolado registroDireitoViolado1 = registroDireitoVioladoDAO.buscaRegistroDireitoViolado(atendimento, direitoVioladoSelecionado, vitima);
//								if(registroDireitoViolado1 != null){
//									JSFUtil.addErrorMessage(vitima.getMembro().getPessoa().getNomeCompleto()+" já possui este Direito Violado");
//									return;
//								}
								
							}
							
							for (Vitima vitima : listaVitimasSelecionadas) {														
								
								registroDireitoViolado = new RegistroDireitoViolado();							
								registroDireitoViolado.setCaracterizarDireitoViolado(caracterizarDireitoVioladoSelecionado);
								registroDireitoViolado.setVitima(vitima);
								registroDireitoViolado.setAtendimento(atendimento);
								registroDireitoViolado.setDataInc(new Date());
								registroDireitoViolado.setConselheiro("Ranulfo");
								registroDireitoViolado.setObs(obsDireitoViolado);
								registroDireitoViolado.setConselheiroRegistro(conselheiroLogado);
								
								registroDireitoVioladoDAO.salvar(registroDireitoViolado);
								listaRegistroDireitoVioladoAtendimento.add(registroDireitoViolado);
							}												
							
												
							caracterizarDireitoVioladoSelecionado = new CaracterizarDireitoViolado();
							direitoFundamentalSelecionado = new DireitoFundamental();
						}else{
							JSFUtil.addErrorMessage("Justifique Direito Violado com no mínimo 25 caracteres na descrição");
							salvou = false;
						}			
					}else{
						JSFUtil.addErrorMessage("Informe no minimo uma vitima");
						salvou = false;
					}
				}else{								
					JSFUtil.addErrorMessage("Informe o Direito Violado");
					salvou = false;
				}
			}else{
				JSFUtil.addErrorMessage("Atendimento ainda não foi concluido. Conclua o atendimento.");
			}
						
						
		} catch (Exception e) {						
			JSFUtil.addErrorMessage("Erro ao Incluir direito violado: "+e.getMessage());
			salvou = false;
		}
		
		if(salvou){
			// ..:: Reseta Diolog Direito violado ::.. //
			vitimaSelecionada = null;
			obsDireitoViolado = null;
			direitoFundamentalSelecionado = null;
			caracterizarDireitoVioladoSelecionado = null;
			btnIncluiDireitoViolado = true;
		}
		
		FacesUtil.getRequestContext().addCallbackParam("salvou", salvou);
	}
	
	public void limpaDlgDireitoViolado(){
		vitimaSelecionada = null;
		obsDireitoViolado = null;
		direitoFundamentalSelecionado = null;
		caracterizacaoViolacaoDireitoSelecionado = null;
		btnIncluiDireitoViolado = true;
	}
	
	public void ativaBotaoIncluiDireitoViolado(){
		
		if(caracterizarDireitoVioladoSelecionado != null){
			btnIncluiDireitoViolado = false;
		}else{
			btnIncluiDireitoViolado = true;
		}
		
	}
	
	public void preparaIncluirMedidaEmViolador(ActionEvent event){
						
		try {
			
			registroDireitoVioladoAplicandoMedida = (RegistroDireitoViolado) event.getComponent().getAttributes().get("direitoVioladoSelecionado");			
			listaMedidaAplicadasEmViolador = new ArrayList<MedidaAplicada>();
			listaMedidaAplicadasEmViolador = new MedidaAplicadaDAO().busca(registroDireitoVioladoAplicandoMedida.getCaracterizarDireitoViolado().getDireitoViolado(), "VIOLADOR");
			listaMedidaEmRazao = new ArrayList<MedidaEmRazao>();
			
			listaMedidaEmRazao = new MedidaEmRazaoDAO().busca();		
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("Erro ao carregar medidas de direito violado");
		}			
	}
	
	public void incluirMedidaEmViolador(){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido incluir.", "");
			return;
		}
		
		boolean salvou = true;
		
		if(listaMedidaAplicadaEmViolador.isEmpty() || medidaEmRazaoSelecionada == null || descricaoMedida.length() < 25   ){
			JSFUtil.addWarnMessage("Insira todas as informações da medida aplicada e descrição no minimo 25 caracteres");
			salvou = false;
		}else{
			
			try {
				
				for (MedidaAplicada medidaAplicadaSelecionada : listaMedidaAplicadaEmViolador) {
													
					RegistroMedidaAplicada registroMedidaAplicada = new RegistroMedidaAplicada();
					
					registroMedidaAplicada.setRegistroDireitoViolado(registroDireitoVioladoAplicandoMedida);
					registroMedidaAplicada.setData(new Date());
					registroMedidaAplicada.setMedidaAplicada(medidaAplicadaSelecionada);
					registroMedidaAplicada.setMedidaEmRazao(medidaEmRazaoSelecionada);
					registroMedidaAplicada.setDescricao(descricaoMedida);
					registroMedidaAplicada.setConselheiroRegistro(conselheiroLogado);
					
					new RegistroMedidaAplicadaDAO().salvar(registroMedidaAplicada);
					
					if(!listaVioladoresSelecionadas.isEmpty()){
						VioladorRegistroMedidaAplicadaDAO violadorRegistroMedidaAplicadaDAO = new VioladorRegistroMedidaAplicadaDAO();
						for (Violador violador : listaVioladoresSelecionadas) {
							
							VioladorRegistroMedidaAplicada violadorRegistroMedidaAplicada = new VioladorRegistroMedidaAplicada();
							violadorRegistroMedidaAplicada.setRegistroMedidaAplicada(registroMedidaAplicada);
							violadorRegistroMedidaAplicada.setViolador(violador);
							
							violadorRegistroMedidaAplicadaDAO.salvar(violadorRegistroMedidaAplicada);
							
						}
						
					}
					
				}
				
				listaRegistroDireitoVioladoAtendimento = new RegistroDireitoVioladoDAO().busca(atendimento);
				limpaDlg3();
				JSFUtil.addInfoMessage("Medida salva com sucesso");
				
			} catch (Exception e) {
				JSFUtil.addErrorMessage("Erro ao salvar: "+e.getMessage());
				salvou = false;
			}
		}
				
		FacesUtil.getRequestContext().addCallbackParam("salvou", salvou);		
	}
	
	public void abreAdendoPessoa(){		
		incluiAdendoPessoa = Boolean.TRUE;
		RequestContext.getCurrentInstance().execute("PF('dlg2').show();");
		System.out.println("Executou metodo....incluiAdendoPessoa");
				
	}
	
	private void verificaFechaAdendo(){
		
		if(incluiAdendoPessoa){
			acao = "";
			incluiAdendoPessoa = Boolean.FALSE;
		}				
	}
	
	public String navegaFamiliaSelecionado(){					
		return "familia_cadastro?faces-redirect=true&c="+idFamilia+"&d=t";		
	}
	
	public void buscaMembroVitima(){
		
		System.out.println("Entrou no metodo de busca...");
		
		try {
			
			if(numeroFamiliaBusca != null || !numeroFamiliaBusca.equals("")){
				
				List<Familia> listaFamilia = new FamiliaDAO().busca(null, numeroFamiliaBusca);				
				if(listaFamilia.size() > 0){
					listaMembroFamilia = new MembroDao().buscaMembro(listaFamilia.get(0));
					
					List<Membro> aux = new ArrayList<Membro>();
					for (Membro membro : listaMembroFamilia) {
						if(Helper.executaCalculoIdade(membro.getPessoa().getDataNascimento()) >= 18){
							aux.add(membro);
						}
					}
					
					listaMembroFamilia.removeAll(aux);					
				}			 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}			
	}
	
	public void buscaMembroViolador(){
		
		System.out.println("Entrou no metodo de busca...");
		
		try {
			
			if(numeroFamiliaBusca != null || !numeroFamiliaBusca.equals("")){
				
				List<Familia> listaFamilia = new FamiliaDAO().busca(null, numeroFamiliaBusca);				
				if(listaFamilia.size() > 0){
					listaMembroFamiliaVioladores = new MembroDao().buscaMembro(listaFamilia.get(0));																		
				}			 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}			
	}
	
	public void incluiMembrosSelecionados(){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido incluir.", "");
			return;
		}
		
		if(desabilitaBotoesEdita){
			if(listaMembroFamiliaSelecionadosVitima.isEmpty()){
				JSFUtil.addErrorMessage("Nenhuma vitima seleionada");
				return;
			}else{
				RequestContext.getCurrentInstance().execute("PF('dlgAviso').show();");
				acao = INCLUIR_VITIMA_LISTA;
			}					
		}else{
						
			for (Membro membro : listaMembroFamiliaSelecionadosVitima) {
				System.out.println("Membro selecionado: "+membro.getPessoa().getNomeCompleto());
				boolean incluir = true;
				if(!listaVitimas.isEmpty()){
					for (Vitima vitima : listaVitimas) {
						if(vitima.getMembro().equals(membro)){
							JSFUtil.addWarnMessage("Os menores já incluidos foram ignorados");						
							incluir = false;
						}
					}
				}
				
				if(incluir){
													
					Vitima vitima = new Vitima();
					vitima.setMembro(membro);
					vitima.setEscolaridade(membro.getPessoa().getEscolaridade() != null ? membro.getPessoa().getEscolaridade().getNome() : null);
					vitima.setIdade(Helper.executaCalculoIdade(membro.getPessoa().getDataNascimento()));
					vitima.setTrabalha(membro.getPessoa().getTrabalha() ? membro.getPessoa().getTrabalha() : null);					
					listaVitimas.add(vitima); 
				}								
				
			}			
			
			rendCadastro = false;
			membroSelecionado = null;
			documentoBusca = "";
			listaMembroFamiliaSelecionadosVitima = new ArrayList<Membro>();
			listaMembroFamilia = new ArrayList<Membro>();
			numeroFamiliaBusca = "";
			
		}			
		
	}
	
	public void recebeVioladorFamiliaSelecionado(ActionEvent event){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido incluir.", "");
			return;
		}
		violadorMembroSelecionado = new Membro();
		violadorMembroSelecionado = (Membro) event.getComponent().getAttributes().get("membroVioladorSelecionada");		
		
		if(!listaViolador.isEmpty()){
			for (Violador violador : listaViolador) {
				if(violador.getPessoa().equals(violadorMembroSelecionado.getPessoa())){
					JSFUtil.addWarnMessage("Violador já incluido selecione outro membro");						
					return;
				}
			}
		}
		
		RequestContext.getCurrentInstance().execute("PF('dlgClassViolador').show();");
		
	}
	
	public void incluiMembroSelecionadoViolador(){	
		
		if(agenteVioladorSubClasseSelecionado == null){
			JSFUtil.addErrorMessage("Selecione classe e sub-classe de violador");
			FacesUtil.getRequestContext().addCallbackParam("salvou", false);
			return;
		}else{
			FacesUtil.getRequestContext().addCallbackParam("salvou", true);
		}
		
		if(desabilitaBotoesEdita){			
				RequestContext.getCurrentInstance().execute("PF('dlgAviso').show();");
				acao = INCLUIR_VIOLADOR_LISTA;							
		}else{																								
			
			Violador violador = new Violador();
								
			violador.setPessoa(violadorMembroSelecionado.getPessoa());
			violador.setIdade(Helper.executaCalculoIdade(violadorMembroSelecionado.getPessoa().getDataNascimento()));
			violador.setEscolaridade(violadorMembroSelecionado.getPessoa().getEscolaridade() != null ? violadorMembroSelecionado.getPessoa().getEscolaridade().getNome() : null);
			violador.setEstadoCivil(violadorMembroSelecionado.getPessoa().getSituacaoMatrimonial());
			violador.setAgenteVioladorSubClasse(agenteVioladorSubClasseSelecionado);
			 								
			listaViolador.add(violador); 
														
			
			rendCadastro = false;
			membroSelecionado = null;
			documentoBusca = "";
			violadorMembroSelecionado = new Membro();
			listaMembroFamiliaVioladores = new ArrayList<Membro>();
			numeroFamiliaBusca = "";
			
		}			
		
	}
	
	public void preparaAplicaMedidaEmVitima(ActionEvent event){
		try {						
			
			caracterizarDireitoVioladoSelecionadoAplicaMedida = (CaracterizarDireitoViolado) event.getComponent().getAttributes().get("registrodireitoVioladoSelecionado");
			
			List<RegistroDireitoViolado> listaRegistroDireitoViolado = new RegistroDireitoVioladoDAO().buscaRegistroDireitoViolado(atendimento, caracterizarDireitoVioladoSelecionadoAplicaMedida);
			
			for (RegistroDireitoViolado registroDireitoViolado : listaRegistroDireitoViolado) {
				listaVitimaAplicaMedidaEmGrupo.add(registroDireitoViolado.getVitima());
			}
			
			listaMedidaAplicadasEmVitima = new ArrayList<MedidaAplicada>();
			listaMedidaAplicadasEmVitima = new MedidaAplicadaDAO().busca(caracterizarDireitoVioladoSelecionadoAplicaMedida.getDireitoViolado(), "VITIMA");
			
			listaMedidaEmRazao = new ArrayList<MedidaEmRazao>();			
			listaMedidaEmRazao = new MedidaEmRazaoDAO().busca();						
			
			System.out.println("DIREITO VIOLADO SELECIONADO: "+caracterizarDireitoVioladoSelecionadoAplicaMedida.getDireitoViolado().getNome());
			
		} catch (Exception e) {
			System.out.println("Erro preparaAplicaMedidaSobreDireitoViolado: "+e.getMessage());
		}		
	}
	
	public void incluirMedidaEmVitima(){
		
		if(conselheiroLogado.getNivelAcesso().equals("col")){
			JSFUtil.addErrorMessage("ERRO! Procedimento Inválido! Nível de acesso não permitido incluir.", "");
			return;
		}
		
		boolean salvou = true;
		
		try {
			
			if(!listaVitimaAplicaMedidaEmGrupoSelecionados.isEmpty()){
				
				if(listaMedidaAplicadaEmVitima.isEmpty() || medidaEmRazaoSelecionada == null || descricaoMedida.length() < 25 ){
					JSFUtil.addWarnMessage("Insira todas as informações da medida aplicada e descrição com no minimo 25 caracteres");
					salvou = false;
				}else{
					
					RegistroDireitoVioladoDAO registroDireitoVioladoDAO = new RegistroDireitoVioladoDAO();
					RegistroMedidaAplicadaDAO registroMedidaAplicadaDAO = new RegistroMedidaAplicadaDAO();  
					for (Vitima vitima : listaVitimaAplicaMedidaEmGrupoSelecionados) {
						
						for (MedidaAplicada medida : listaMedidaAplicadaEmVitima) {
							System.out.println("Vitima: "+vitima.getMembro().getPessoa().getNomeCompleto());
							System.out.println("Medida a aplicar: "+medida.getResumoDescricao());
							
							RegistroDireitoViolado registroDireitoViolado = registroDireitoVioladoDAO.buscaRegistroDireitoViolado(atendimento, caracterizarDireitoVioladoSelecionadoAplicaMedida, vitima);
							
							RegistroMedidaAplicada registroMedidaAplicada = new RegistroMedidaAplicada();
							
							registroMedidaAplicada.setRegistroDireitoViolado(registroDireitoViolado);
							registroMedidaAplicada.setData(new Date());
							registroMedidaAplicada.setMedidaAplicada(medida);
							registroMedidaAplicada.setMedidaEmRazao(medidaEmRazaoSelecionada);
							registroMedidaAplicada.setDescricao(descricaoMedida);
							registroMedidaAplicada.setConselheiroRegistro(conselheiroLogado);
							
							registroMedidaAplicadaDAO.salvar(registroMedidaAplicada);
							
						}
					}
					
					listaRegistroDireitoVioladoAtendimento = new RegistroDireitoVioladoDAO().busca(atendimento);
					JSFUtil.addInfoMessage("Registro Medida Aplicada Salvo com sucesso!");
					limpaDlgMedidaDireitoViolado();
				}
				
			}else{
				JSFUtil.addErrorMessage("Selecione no  mínimo uma vítima!");
				salvou = false;
			}
			
		} catch (Exception e) {
			System.out.println("[ERRO... "+e.getMessage()+" ]");
			JSFUtil.addErrorMessage("ERRO.... "+e.getMessage());
			salvou = false;
		}
		
		FacesUtil.getRequestContext().addCallbackParam("salvou", salvou);
	}
	
	public void incluirAtribuicao(){
		
		boolean salvou = true;
		
		try {
			
			if(atendimento != null){
				
				if(descumpridor != null && descricaoAtribuicao.length() > 25 && determinacoesSelecionadas.size() > 0){
					
					Atribuicao atribuicao = new Atribuicao();								
					atribuicao.setConselheiro(conselheiroLogado);
					atribuicao.setData(new Date());
					atribuicao.setDescumpridor(descumpridor);
					atribuicao.setDescricao(descricaoAtribuicao);
					atribuicao.setAtendimento(atendimento);
					AtribuicaoDAO atribuicaoDAO = new AtribuicaoDAO();
					atribuicao = atribuicaoDAO.salvar(atribuicao);
					DeterminacaoAplicadaDAO determinacaoAplicadaDAO = new DeterminacaoAplicadaDAO();
					
					for (Determinacao determinacao : determinacoesSelecionadas) {
						System.out.println("Determinações: "+determinacao.getNome());
						DeterminacaoAplicada determinacaoAplicada = new DeterminacaoAplicada();
						determinacaoAplicada.setAtribuicao(atribuicao);
						determinacaoAplicada.setDeterminacao(determinacao);					
						determinacaoAplicadaDAO.salvar(determinacaoAplicada);
					}
					
					listaAtribuicoes.add(atribuicao);
					
					descumpridor = null;
					determinacoesSelecionadas = new ArrayList<Determinacao>();
					descricaoAtribuicao = "";
					
					JSFUtil.addInfoMessage("Atribuição salva com sucesso");
					
				}else{
					JSFUtil.addErrorMessage("Nem todos os itens foram informados");
					salvou = false;
				}
				
			}else{
				JSFUtil.addErrorMessage("É necessario savar o antendimento antes de incluir atribuições");
				salvou = false;
			}						
			
			
		} catch (Exception e) {
			e.printStackTrace();
			salvou = false;
		}
		FacesUtil.getRequestContext().addCallbackParam("salvou", salvou);
	}
		
	
	public void limpaDlgMedidaDireitoViolado(){
		
		caracterizarDireitoVioladoSelecionadoAplicaMedida = new CaracterizarDireitoViolado();
		listaVitimaAplicaMedidaEmGrupo = new ArrayList<Vitima>();
		descricaoMedida = "";		
		medidaEmRazaoSelecionada = new MedidaEmRazao();
		listaVitimaAplicaMedidaEmGrupoSelecionados =  new ArrayList<Vitima>();
		listaMedidaAplicadaEmVitima = new ArrayList<MedidaAplicada>();
	}
	
	public void limpaDlg3(){
		
		registroDireitoVioladoAplicandoMedida = new  RegistroDireitoViolado();
		caracterizarDireitoVioladoSelecionadoAplicaMedida = new CaracterizarDireitoViolado();
		listaVitimaAplicaMedidaEmGrupo = new ArrayList<Vitima>();
		descricaoMedida = "";
		selecaoMedidaAplicada = null;
		medidaEmRazaoSelecionada = new MedidaEmRazao();		
		listaVioladoresSelecionadas = new ArrayList<Violador>();
		listaMedidaEmRazao = new ArrayList<MedidaEmRazao>();
		listaMedidaAplicadaEmViolador = new ArrayList<MedidaAplicada>();
	}		
	
	public void limpaListaVioladores(){
		listaViolador = new ArrayList<Violador>();
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

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public SimpleDateFormat getFormato() {
		return formato;
	}

	public void setFormato(SimpleDateFormat formato) {
		this.formato = formato;
	}

	public String getNumeroFamilia() {
		return numeroFamilia;
	}

	public void setNumeroFamilia(String numeroFamilia) {
		this.numeroFamilia = numeroFamilia;
	}

	public List<Vitima> getListaVitimas() {
		return listaVitimas;
	}

	public void setListaVitimas(List<Vitima> listaVitimas) {
		this.listaVitimas = listaVitimas;
	}

	public String getTipoBuscaViolador() {
		return tipoBuscaViolador;
	}

	public void setTipoBuscaViolador(String tipoBuscaViolador) {
		this.tipoBuscaViolador = tipoBuscaViolador;
	}

	public Boolean getDesativaOrgaoViolador() {
		return desativaOrgaoViolador;
	}

	public void setDesativaOrgaoViolador(Boolean desativaOrgaoViolador) {
		this.desativaOrgaoViolador = desativaOrgaoViolador;
	}

	public String getDocumentoBuscaViolador() {
		return documentoBuscaViolador;
	}

	public void setDocumentoBuscaViolador(String documentoBuscaViolador) {
		this.documentoBuscaViolador = documentoBuscaViolador;
	}

	public Orgao getOrgaoBuscaSelecionadoViolador() {
		return orgaoBuscaSelecionadoViolador;
	}

	public void setOrgaoBuscaSelecionadoViolador(Orgao orgaoBuscaSelecionadoViolador) {
		this.orgaoBuscaSelecionadoViolador = orgaoBuscaSelecionadoViolador;
	}

	public Boolean getRendCadastroViolador() {
		return rendCadastroViolador;
	}

	public void setRendCadastroViolador(Boolean rendCadastroViolador) {
		this.rendCadastroViolador = rendCadastroViolador;
	}

	public List<Violador> getListaViolador() {
		return listaViolador;
	}

	public void setListaViolador(List<Violador> listaViolador) {
		this.listaViolador = listaViolador;
	}

	public List<AgenteVioladorClasse> getListaAgenteVioladorClasse() {
		return listaAgenteVioladorClasse;
	}

	public void setListaAgenteVioladorClasse(
			List<AgenteVioladorClasse> listaAgenteVioladorClasse) {
		this.listaAgenteVioladorClasse = listaAgenteVioladorClasse;
	}

	public List<AgenteVioladorSubClasse> getListaAgenteVioladorSubClasses() {
		return listaAgenteVioladorSubClasses;
	}

	public void setListaAgenteVioladorSubClasses(
			List<AgenteVioladorSubClasse> listaAgenteVioladorSubClasses) {
		this.listaAgenteVioladorSubClasses = listaAgenteVioladorSubClasses;
	}

	public AgenteVioladorClasse getAgenteVioladorClasseSelecionado() {
		return agenteVioladorClasseSelecionado;
	}

	public void setAgenteVioladorClasseSelecionado(
			AgenteVioladorClasse agenteVioladorClasseSelecionado) {
		this.agenteVioladorClasseSelecionado = agenteVioladorClasseSelecionado;
	}

	public AgenteVioladorSubClasse getAgenteVioladorSubClasseSelecionado() {
		return agenteVioladorSubClasseSelecionado;
	}

	public void setAgenteVioladorSubClasseSelecionado(
			AgenteVioladorSubClasse agenteVioladorSubClasseSelecionado) {
		this.agenteVioladorSubClasseSelecionado = agenteVioladorSubClasseSelecionado;
	}

	public List<TipoLocalFato> getListaTipoLocalFato() {
		return listaTipoLocalFato;
	}

	public void setListaTipoLocalFato(List<TipoLocalFato> listaTipoLocalFato) {
		this.listaTipoLocalFato = listaTipoLocalFato;
	}

	public List<TipoAtendimento> getListaTipoAtendimento() {
		return listaTipoAtendimento;
	}

	public void setListaTipoAtendimento(List<TipoAtendimento> listaTipoAtendimento) {
		this.listaTipoAtendimento = listaTipoAtendimento;
	}

	public List<ViaSolicitacao> getListaViaSolicitacao() {
		return listaViaSolicitacao;
	}

	public void setListaViaSolicitacao(List<ViaSolicitacao> listaViaSolicitacao) {
		this.listaViaSolicitacao = listaViaSolicitacao;
	}

	public List<OrigemAtendimento> getListaOrigemAtendimento() {
		return listaOrigemAtendimento;
	}

	public void setListaOrigemAtendimento(
			List<OrigemAtendimento> listaOrigemAtendimento) {
		this.listaOrigemAtendimento = listaOrigemAtendimento;
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

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public TipoLocalFato getTipoLocalFatoSelecionado() {
		return tipoLocalFatoSelecionado;
	}

	public void setTipoLocalFatoSelecionado(TipoLocalFato tipoLocalFatoSelecionado) {
		this.tipoLocalFatoSelecionado = tipoLocalFatoSelecionado;
	}

	public TipoAtendimento getTipoAtendimentoSelecionado() {
		return tipoAtendimentoSelecionado;
	}

	public void setTipoAtendimentoSelecionado(
			TipoAtendimento tipoAtendimentoSelecionado) {
		this.tipoAtendimentoSelecionado = tipoAtendimentoSelecionado;
	}

	public ViaSolicitacao getViaSolicitacaoSelecionado() {
		return viaSolicitacaoSelecionado;
	}

	public void setViaSolicitacaoSelecionado(
			ViaSolicitacao viaSolicitacaoSelecionado) {
		this.viaSolicitacaoSelecionado = viaSolicitacaoSelecionado;
	}

	public OrigemAtendimento getOrigemAtendimentoSelecionado() {
		return origemAtendimentoSelecionado;
	}

	public void setOrigemAtendimentoSelecionado(
			OrigemAtendimento origemAtendimentoSelecionado) {
		this.origemAtendimentoSelecionado = origemAtendimentoSelecionado;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public String getNomeDenunciante() {
		return nomeDenunciante;
	}

	public void setNomeDenunciante(String nomeDenunciante) {
		this.nomeDenunciante = nomeDenunciante;
	}

	public String getFoneDenunciante() {
		return foneDenunciante;
	}

	public void setFoneDenunciante(String foneDenunciante) {
		this.foneDenunciante = foneDenunciante;
	}

	public Date getDataFato() {
		return dataFato;
	}

	public void setDataFato(Date dataFato) {
		this.dataFato = dataFato;
	}

	public String getDescricaoAtendimento() {
		return descricaoAtendimento;
	}

	public void setDescricaoAtendimento(String descricaoAtendimento) {
		this.descricaoAtendimento = descricaoAtendimento;
	}

	public String getProcedenciaDenuncia() {
		return procedenciaDenuncia;
	}

	public void setProcedenciaDenuncia(String procedenciaDenuncia) {
		this.procedenciaDenuncia = procedenciaDenuncia;
	}

	public Boolean getDesabilitaBotoesEdita() {
		return desabilitaBotoesEdita;
	}

	public void setDesabilitaBotoesEdita(Boolean desabilitaBotoesEdita) {
		this.desabilitaBotoesEdita = desabilitaBotoesEdita;
	}

	public String getDescricaoAdendo() {
		return descricaoAdendo;
	}

	public void setDescricaoAdendo(String descricaoAdendo) {
		this.descricaoAdendo = descricaoAdendo;
	}

	public List<Adendo> getListaAdendos() {
		return listaAdendos;
	}

	public void setListaAdendos(List<Adendo> listaAdendos) {
		this.listaAdendos = listaAdendos;
	}

	public Boolean getVisualizar() {
		return visualizar;
	}

	public void setVisualizar(Boolean visualizar) {
		this.visualizar = visualizar;
	}

	public Adendo getAdendoSelecionado() {
		return adendoSelecionado;
	}

	public void setAdendoSelecionado(Adendo adendoSelecionado) {
		this.adendoSelecionado = adendoSelecionado;
	}		
	
	public List<DireitoFundamental> getListaDireitoFundamental() {
		return listaDireitoFundamental;
	}

	public void setListaDireitoFundamental(
			List<DireitoFundamental> listaDireitoFundamental) {
		this.listaDireitoFundamental = listaDireitoFundamental;
	}		

	public List<CaracterizacaoViolacaoDireito> getListaCaracterizacaoViolacaoDireito() {
		return listaCaracterizacaoViolacaoDireito;
	}

	public void setListaCaracterizacaoViolacaoDireito(
			List<CaracterizacaoViolacaoDireito> listaCaracterizacaoViolacaoDireito) {
		this.listaCaracterizacaoViolacaoDireito = listaCaracterizacaoViolacaoDireito;
	}	

	public DireitoFundamental getDireitoFundamentalSelecionado() {
		return direitoFundamentalSelecionado;
	}

	public void setDireitoFundamentalSelecionado(
			DireitoFundamental direitoFundamentalSelecionado) {
		this.direitoFundamentalSelecionado = direitoFundamentalSelecionado;
	}		

	public CaracterizacaoViolacaoDireito getCaracterizacaoViolacaoDireitoSelecionado() {
		return caracterizacaoViolacaoDireitoSelecionado;
	}

	public void setCaracterizacaoViolacaoDireitoSelecionado(
			CaracterizacaoViolacaoDireito caracterizacaoViolacaoDireitoSelecionado) {
		this.caracterizacaoViolacaoDireitoSelecionado = caracterizacaoViolacaoDireitoSelecionado;
	}

	public List<RegistroDireitoViolado> getListaRegistroDireitoVioladoAtendimento() {
		return listaRegistroDireitoVioladoAtendimento;
	}

	public void setListaRegistroDireitoVioladoAtendimento(
			List<RegistroDireitoViolado> listaRegistroDireitoVioladoAtendimento) {
		this.listaRegistroDireitoVioladoAtendimento = listaRegistroDireitoVioladoAtendimento;
	}

	public Boolean getRendTblAdendoDireitoViolado() {
		return rendTblAdendoDireitoViolado;
	}

	public void setRendTblAdendoDireitoViolado(Boolean rendTblAdendoDireitoViolado) {
		this.rendTblAdendoDireitoViolado = rendTblAdendoDireitoViolado;
	}		
	
	public RegistroDireitoViolado getRegistroDireitoVioladoAplicandoMedida() {
		return registroDireitoVioladoAplicandoMedida;
	}

	public void setRegistroDireitoVioladoAplicandoMedida(
			RegistroDireitoViolado registroDireitoVioladoAplicandoMedida) {
		this.registroDireitoVioladoAplicandoMedida = registroDireitoVioladoAplicandoMedida;
	}	

	public List<MedidaEmRazao> getListaMedidaEmRazao() {
		return listaMedidaEmRazao;
	}

	public void setListaMedidaEmRazao(List<MedidaEmRazao> listaMedidaEmRazao) {
		this.listaMedidaEmRazao = listaMedidaEmRazao;
	}	

	public MedidaEmRazao getMedidaEmRazaoSelecionada() {
		return medidaEmRazaoSelecionada;
	}

	public void setMedidaEmRazaoSelecionada(MedidaEmRazao medidaEmRazaoSelecionada) {
		this.medidaEmRazaoSelecionada = medidaEmRazaoSelecionada;
	}

	public String getDescricaoMedida() {
		return descricaoMedida;
	}

	public void setDescricaoMedida(String descricaoMedida) {
		this.descricaoMedida = descricaoMedida;
	}

	public boolean isBtnIncluiDireitoViolado() {
		return btnIncluiDireitoViolado;
	}

	public void setBtnIncluiDireitoViolado(boolean btnIncluiDireitoViolado) {
		this.btnIncluiDireitoViolado = btnIncluiDireitoViolado;
	}

	public Vitima getVitimaSelecionada() {
		return vitimaSelecionada;
	}

	public void setVitimaSelecionada(Vitima vitimaSelecionada) {
		this.vitimaSelecionada = vitimaSelecionada;
	}

	public String getObsDireitoViolado() {
		return obsDireitoViolado;
	}

	public void setObsDireitoViolado(String obsDireitoViolado) {
		this.obsDireitoViolado = obsDireitoViolado;
	}

	public List<Vitima> getListaVitimasSelecionadas() {
		return listaVitimasSelecionadas;
	}

	public void setListaVitimasSelecionadas(List<Vitima> listaVitimasSelecionadas) {
		this.listaVitimasSelecionadas = listaVitimasSelecionadas;
	}

	public List<DireitoViolado> getListaDireitoVioladoAplicado() {
		return listaDireitoVioladoAplicado;
	}

	public void setListaDireitoVioladoAplicado(
			List<DireitoViolado> listaDireitoVioladoAplicado) {
		this.listaDireitoVioladoAplicado = listaDireitoVioladoAplicado;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getDataResgistro() {
		return dataResgistro;
	}

	public void setDataResgistro(String dataResgistro) {
		this.dataResgistro = dataResgistro;
	}

	public String getNomeLogado() {
		return nomeLogado;
	}

	public void setNomeLogado(String nomeLogado) {
		this.nomeLogado = nomeLogado;
	}

	public Boolean getRendPessoa() {
		return rendPessoa;
	}

	public void setRendPessoa(Boolean rendPessoa) {
		this.rendPessoa = rendPessoa;
	}

	public Boolean getRendInstituicao() {
		return rendInstituicao;
	}

	public void setRendInstituicao(Boolean rendInstituicao) {
		this.rendInstituicao = rendInstituicao;
	}

	public String getNomeRazaoVioladorSelecionado() {
		return nomeRazaoVioladorSelecionado;
	}

	public void setNomeRazaoVioladorSelecionado(String nomeRazaoVioladorSelecionado) {
		this.nomeRazaoVioladorSelecionado = nomeRazaoVioladorSelecionado;
	}

	public String getCnpjCpfVioladorSelecinado() {
		return cnpjCpfVioladorSelecinado;
	}

	public void setCnpjCpfVioladorSelecinado(String cnpjCpfVioladorSelecinado) {
		this.cnpjCpfVioladorSelecinado = cnpjCpfVioladorSelecinado;
	}

	public String getCieRgVioladorSelecionado() {
		return cieRgVioladorSelecionado;
	}

	public void setCieRgVioladorSelecionado(String cieRgVioladorSelecionado) {
		this.cieRgVioladorSelecionado = cieRgVioladorSelecionado;
	}

	public String getTipoOrgaoVioladorSelecionado() {
		return tipoOrgaoVioladorSelecionado;
	}

	public void setTipoOrgaoVioladorSelecionado(String tipoOrgaoVioladorSelecionado) {
		this.tipoOrgaoVioladorSelecionado = tipoOrgaoVioladorSelecionado;
	}

	public String getEnderecoDataNascVioladorSelecionado() {
		return enderecoDataNascVioladorSelecionado;
	}

	public void setEnderecoDataNascVioladorSelecionado(
			String enderecoDataNascVioladorSelecionado) {
		this.enderecoDataNascVioladorSelecionado = enderecoDataNascVioladorSelecionado;
	}

	public String getFoneIdadeVioladorSelecionado() {
		return foneIdadeVioladorSelecionado;
	}

	public void setFoneIdadeVioladorSelecionado(String foneIdadeVioladorSelecionado) {
		this.foneIdadeVioladorSelecionado = foneIdadeVioladorSelecionado;
	}

	public Boolean getIncluiAdendoPessoa() {
		return incluiAdendoPessoa;
	}

	public void setIncluiAdendoPessoa(Boolean incluiAdendoPessoa) {
		this.incluiAdendoPessoa = incluiAdendoPessoa;
	}

	public List<Membro> getListaMembroFamilia() {
		return listaMembroFamilia;
	}

	public void setListaMembroFamilia(List<Membro> listaMembroFamilia) {
		this.listaMembroFamilia = listaMembroFamilia;
	}

	public List<Membro> getListaMembroFamiliaSelecionadosVitima() {
		return listaMembroFamiliaSelecionadosVitima;
	}

	public void setListaMembroFamiliaSelecionadosVitima(
			List<Membro> listaMembroFamiliaSelecionadosVitima) {
		this.listaMembroFamiliaSelecionadosVitima = listaMembroFamiliaSelecionadosVitima;
	}

	public String getNumeroFamiliaBusca() {
		return numeroFamiliaBusca;
	}

	public void setNumeroFamiliaBusca(String numeroFamiliaBusca) {
		this.numeroFamiliaBusca = numeroFamiliaBusca;
	}

	public List<Membro> getListaMembroFamiliaVioladores() {
		return listaMembroFamiliaVioladores;
	}

	public void setListaMembroFamiliaVioladores(
			List<Membro> listaMembroFamiliaVioladores) {
		this.listaMembroFamiliaVioladores = listaMembroFamiliaVioladores;
	}

	public Conselheiro getConselheiroLogado() {
		return conselheiroLogado;
	}

	public void setConselheiroLogado(Conselheiro conselheiroLogado) {
		this.conselheiroLogado = conselheiroLogado;
	}

	public List<Violador> getListaVioladoresSelecionadas() {
		return listaVioladoresSelecionadas;
	}

	public void setListaVioladoresSelecionadas(
			List<Violador> listaVioladoresSelecionadas) {
		this.listaVioladoresSelecionadas = listaVioladoresSelecionadas;
	}	

	public CaracterizarDireitoViolado getCaracterizarDireitoVioladoSelecionadoAplicaMedida() {
		return caracterizarDireitoVioladoSelecionadoAplicaMedida;
	}

	public void setCaracterizarDireitoVioladoSelecionadoAplicaMedida(
			CaracterizarDireitoViolado caracterizarDireitoVioladoSelecionadoAplicaMedida) {
		this.caracterizarDireitoVioladoSelecionadoAplicaMedida = caracterizarDireitoVioladoSelecionadoAplicaMedida;
	}

	public List<Vitima> getListaVitimaAplicaMedidaEmGrupo() {
		return listaVitimaAplicaMedidaEmGrupo;
	}

	public void setListaVitimaAplicaMedidaEmGrupo(
			List<Vitima> listaVitimaAplicaMedidaEmGrupo) {
		this.listaVitimaAplicaMedidaEmGrupo = listaVitimaAplicaMedidaEmGrupo;
	}

	public List<Vitima> getListaVitimaAplicaMedidaEmGrupoSelecionados() {
		return listaVitimaAplicaMedidaEmGrupoSelecionados;
	}

	public void setListaVitimaAplicaMedidaEmGrupoSelecionados(
			List<Vitima> listaVitimaAplicaMedidaEmGrupoSelecionados) {
		this.listaVitimaAplicaMedidaEmGrupoSelecionados = listaVitimaAplicaMedidaEmGrupoSelecionados;
	}	

	public List<CaracterizarDireitoViolado> getListaCaracterizarDireitoVioladosNoAtendimento() {
		return listaCaracterizarDireitoVioladosNoAtendimento;
	}

	public void setListaCaracterizarDireitoVioladosNoAtendimento(
			List<CaracterizarDireitoViolado> listaCaracterizarDireitoVioladosNoAtendimento) {
		this.listaCaracterizarDireitoVioladosNoAtendimento = listaCaracterizarDireitoVioladosNoAtendimento;
	}

	public Boolean getVioladoNaoIdentificado() {
		return violadoNaoIdentificado;
	}

	public void setVioladoNaoIdentificado(Boolean violadoNaoIdentificado) {
		this.violadoNaoIdentificado = violadoNaoIdentificado;
	}	

	public List<MedidaAplicada> getListaMedidaAplicadaEmVitima() {
		return listaMedidaAplicadaEmVitima;
	}

	public void setListaMedidaAplicadaEmVitima(
			List<MedidaAplicada> listaMedidaAplicadaEmVitima) {
		this.listaMedidaAplicadaEmVitima = listaMedidaAplicadaEmVitima;
	}

	public MedidaAplicada getSelecaoMedidaAplicada() {
		return selecaoMedidaAplicada;
	}

	public void setSelecaoMedidaAplicada(MedidaAplicada selecaoMedidaAplicada) {
		this.selecaoMedidaAplicada = selecaoMedidaAplicada;
	}	

	public List<MedidaAplicada> getListaMedidaAplicadaEmViolador() {
		return listaMedidaAplicadaEmViolador;
	}

	public void setListaMedidaAplicadaEmViolador(
			List<MedidaAplicada> listaMedidaAplicadaEmViolador) {
		this.listaMedidaAplicadaEmViolador = listaMedidaAplicadaEmViolador;
	}

	public List<MedidaAplicada> getListaMedidaAplicadasEmVitima() {
		return listaMedidaAplicadasEmVitima;
	}

	public void setListaMedidaAplicadasEmVitima(
			List<MedidaAplicada> listaMedidaAplicadasEmVitima) {
		this.listaMedidaAplicadasEmVitima = listaMedidaAplicadasEmVitima;
	}

	public List<MedidaAplicada> getListaMedidaAplicadasEmViolador() {
		return listaMedidaAplicadasEmViolador;
	}

	public void setListaMedidaAplicadasEmViolador(
			List<MedidaAplicada> listaMedidaAplicadasEmViolador) {
		this.listaMedidaAplicadasEmViolador = listaMedidaAplicadasEmViolador;
	}

	public List<Determinacao> getListaDeterminacoes() {
		return listaDeterminacoes;
	}

	public void setListaDeterminacoes(List<Determinacao> listaDeterminacoes) {
		this.listaDeterminacoes = listaDeterminacoes;
	}

	public AgenteVioladorSubClasse getDescumpridor() {
		return descumpridor;
	}

	public void setDescumpridor(AgenteVioladorSubClasse descumpridor) {
		this.descumpridor = descumpridor;
	}

	public List<Determinacao> getDeterminacoesSelecionadas() {
		return determinacoesSelecionadas;
	}

	public void setDeterminacoesSelecionadas(
			List<Determinacao> determinacoesSelecionadas) {
		this.determinacoesSelecionadas = determinacoesSelecionadas;
	}

	public String getDescricaoAtribuicao() {
		return descricaoAtribuicao;
	}

	public void setDescricaoAtribuicao(String descricaoAtribuicao) {
		this.descricaoAtribuicao = descricaoAtribuicao;
	}

	public List<Atribuicao> getListaAtribuicoes() {
		return listaAtribuicoes;
	}

	public void setListaAtribuicoes(List<Atribuicao> listaAtribuicoes) {
		this.listaAtribuicoes = listaAtribuicoes;
	}

	public List<CaracterizarDireitoViolado> getListaCaracterizarViolacaoDireito() {
		return listaCaracterizarViolacaoDireito;
	}

	public void setListaCaracterizarViolacaoDireito(
			List<CaracterizarDireitoViolado> listaCaracterizarViolacaoDireito) {
		this.listaCaracterizarViolacaoDireito = listaCaracterizarViolacaoDireito;
	}

	public CaracterizarDireitoViolado getCaracterizarDireitoVioladoSelecionado() {
		return caracterizarDireitoVioladoSelecionado;
	}

	public void setCaracterizarDireitoVioladoSelecionado(
			CaracterizarDireitoViolado caracterizarDireitoVioladoSelecionado) {
		this.caracterizarDireitoVioladoSelecionado = caracterizarDireitoVioladoSelecionado;
	}
	
	
}
