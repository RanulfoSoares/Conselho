package br.com.conselho.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.conselho.dao.AtendimentoDAO;
import br.com.conselho.dao.BairroDAO;
import br.com.conselho.dao.CidadeDAO;
import br.com.conselho.dao.EstadoDAO;
import br.com.conselho.dao.MembroDao;
import br.com.conselho.dao.RegistroDireitoVioladoDAO;
import br.com.conselho.dao.RegistroMedidaAplicadaDAO;
import br.com.conselho.dao.VioladorDAO;
import br.com.conselho.dao.VitimaDAO;
import br.com.conselho.domain.Atendimento;
import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.DireitoViolado;
import br.com.conselho.domain.Estado;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.domain.MedidaAplicada;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.RegistroDireitoViolado;
import br.com.conselho.domain.RegistroMedidaAplicada;
import br.com.conselho.domain.Violador;
import br.com.conselho.domain.Vitima;
import br.com.conselho.dto.AnaliseDTO;
import br.com.conselho.dto.DadosAtendimentoDTO;
import br.com.conselho.dto.RegistroDiretitoVioladoDTO;
import br.com.conselho.dto.RegistroMedidaAplicadaDTO;
import br.com.conselho.dto.SetorPublicoDTO;
import br.com.conselho.dto.VioladoresAtendimentoDTO;
import br.com.conselho.util.Helper;
import br.com.conselho.util.JSFUtil;
import br.com.conselho.util.ReportUtil;


@SuppressWarnings("serial")
@ManagedBean(name = "MBRelatorios")
@ViewScoped
public class RelatoriosBean implements Serializable {
	
	private Date dataIni;
	private Date dataFim;
	
	private List<Estado> listaEstado;
	private List<Cidade> listaCidade;
	private List<Bairro> listaBairro;
	
	private Estado estadoSelecionado;
	private Cidade cidadeSelecionado;
	private Bairro bairroSelecionado;
	
	@PostConstruct
	public void ini(){
		try {
			listaEstado = new EstadoDAO().listaEstados();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dadosVitimasAtendimento(){
		
		try {
			Map<String, Object> parametros = new HashMap<>();
			
			VitimaDAO vitimaDAO = new VitimaDAO();
			System.out.println("entrou no metodo busca numero de crianças Data Inicio:"+dataIni.toString()+" Data Fim:"+dataFim.toString());
			
			if(dataFim.after(dataIni) || dataFim.equals(dataIni)){
				Long criancas = vitimaDAO.totalCrianca(dataIni, dataFim);			
				Long adolescente = vitimaDAO.totalAdolescente(dataIni, dataFim);																							
				Long totalFeminino = vitimaDAO.totalFeminino(dataIni, dataFim);
				Long totalMasculino = vitimaDAO.totalMasculino(dataIni, dataFim);
				Long totalAmarela = vitimaDAO.totalAmarela(dataIni, dataFim);
				Long totalBranco = vitimaDAO.totalBranca(dataIni, dataFim);
				Long totalIndigena = vitimaDAO.totalIndigena(dataIni, dataFim);
				Long totalParda = vitimaDAO.totalParda(dataIni, dataFim);
				Long totalPreta = vitimaDAO.totalPreta(dataIni, dataFim);
				Long totalEnsinoMedio = vitimaDAO.totalEnsinoMedio(dataIni, dataFim);
				Long totalFundamentalPrimeira = vitimaDAO.totalFundamentalPrimeira(dataIni, dataFim);
				Long totalFundamentalSexta = vitimaDAO.totalFundamentalSexta(dataIni, dataFim);
				Long totalTrabalha = vitimaDAO.totalTrabalha(true, dataIni, dataFim);
				Long totalNaoTrabalha = vitimaDAO.totalTrabalha(false, dataIni, dataFim);
				
				AnaliseDTO analiseDTO = new AnaliseDTO();
				
				analiseDTO.setDataInicio(Helper.formatDate().format(dataIni));			
				analiseDTO.setDataFim(Helper.formatDate().format(dataFim));
				analiseDTO.setPorcAdolescente(calculaPorcentagem(criancas+adolescente, adolescente));
				analiseDTO.setPorcCrianca(calculaPorcentagem(criancas+adolescente, criancas));
				analiseDTO.setQtdAdolescente(adolescente.toString());
				analiseDTO.setQtdCrianca(criancas.toString());
				
				analiseDTO.setQuantFeminino(totalFeminino.toString());
				analiseDTO.setPorcFeminino(calculaPorcentagem(totalFeminino+totalMasculino, totalFeminino));
				analiseDTO.setQuantMasculino(totalMasculino.toString());
				analiseDTO.setPorcMasculino(calculaPorcentagem(totalFeminino+totalMasculino, totalMasculino));
				
				analiseDTO.setQuantAmarela(totalAmarela.toString());			
				analiseDTO.setPorcAmarela(calculaPorcentagem(totalAmarela +
															 totalBranco  +
															 totalIndigena+
															 totalParda   +
															 totalPreta, totalAmarela));
				analiseDTO.setQuantBranca(totalBranco.toString());
				analiseDTO.setPorcBranca(calculaPorcentagem(totalAmarela +
															 totalBranco  +
															 totalIndigena+
															 totalParda   +
															 totalPreta, totalBranco));
				analiseDTO.setQuantIndigena(totalIndigena.toString());
				analiseDTO.setPorcIndigena(calculaPorcentagem(totalAmarela +
															 totalBranco  +
															 totalIndigena+
															 totalParda   +
															 totalPreta, totalIndigena));
				analiseDTO.setQuantParda(totalParda.toString());
				analiseDTO.setPorcParda(calculaPorcentagem(totalAmarela +
															 totalBranco  +
															 totalIndigena+
															 totalParda   +
															 totalPreta, totalParda));
				analiseDTO.setQuantPreta(totalPreta.toString());
				analiseDTO.setPorcPreta(calculaPorcentagem(totalAmarela +
															 totalBranco  +
															 totalIndigena+
															 totalParda   +
															 totalPreta, totalPreta));
				
				analiseDTO.setQuantEnsinoMedio(totalEnsinoMedio.toString());
				analiseDTO.setPorcEnsinoMedio(calculaPorcentagem(totalEnsinoMedio+totalFundamentalPrimeira+totalFundamentalSexta, totalEnsinoMedio));
				analiseDTO.setQuantFundamentalPrimeira(totalFundamentalPrimeira.toString());
				analiseDTO.setPorcFundamentalPrimeira(calculaPorcentagem(totalEnsinoMedio+totalFundamentalPrimeira+totalFundamentalSexta, totalFundamentalPrimeira));
				analiseDTO.setQuantFundamentalSexta(totalFundamentalSexta.toString());
				analiseDTO.setPorcFundamentalSexta(calculaPorcentagem(totalEnsinoMedio+totalFundamentalPrimeira+totalFundamentalSexta, totalFundamentalSexta));
				
				analiseDTO.setQuantSim(totalTrabalha.toString());
				analiseDTO.setPorcSim(calculaPorcentagem(totalTrabalha+totalNaoTrabalha, totalTrabalha));
				analiseDTO.setQuantNao(totalNaoTrabalha.toString());
				analiseDTO.setPorcNao(calculaPorcentagem(totalTrabalha+totalNaoTrabalha, totalNaoTrabalha));
				
				List<AnaliseDTO> lista = new ArrayList<AnaliseDTO>();
				lista.add(analiseDTO);
				
				String caminho = "/WEB-INF/relatorios/crianca_adolescente.jasper";

				ReportUtil.executarRelatorio(caminho, parametros, "Consulta-Familia", lista);
				resetaData();
			}else{
				JSFUtil.addErrorMessage("Data final inferior a data incial, selecione novas datas");
			}											

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}		
	
	public void dadosVioladoresAtendimento(){
		
		Map<String, Object> parametros = new HashMap<>();
		List<VioladoresAtendimentoDTO> lista = new ArrayList<VioladoresAtendimentoDTO>();
		VioladoresAtendimentoDTO violadoresAtendimentoDTO = new VioladoresAtendimentoDTO();
		
		try {
			
			if(dataFim.after(dataIni) || dataFim.equals(dataIni)){
				
				VioladorDAO violadorDAO = new VioladorDAO();				
				
				Long totalFeminino = violadorDAO.totalFeminino(dataIni, dataFim);
				Long totalMasculino = violadorDAO.totalMasculino(dataIni, dataFim);
				Long totalMaior = violadorDAO.totalMaior(dataIni, dataFim);
				Long totalMenor = violadorDAO.totalMenor(dataIni, dataFim);
				Long totalInstiuicao = violadorDAO.totalInstituicao (dataIni, dataFim);
				Long totalNaoIdentificado = new AtendimentoDAO().totalAtendimentoVioladorNaoIdentificado(dataIni, dataFim);				
				Long totalAmarela = violadorDAO.totalAmarela(dataIni, dataFim);
				Long totalBranca = violadorDAO.totalBranca(dataIni, dataFim);
				Long totalIndigena = violadorDAO.totalIndigena(dataIni, dataFim);
				Long totalParda = violadorDAO.totalParda(dataIni, dataFim);
				Long totalPreta = violadorDAO.totalPreta(dataIni, dataFim);	
				Long totalAmasiada = violadorDAO.totalAmasiada(dataIni, dataFim);
				Long totalCasada = violadorDAO.totalCasada(dataIni, dataFim);
				Long totalDesconhecido = violadorDAO.totalDesconhecido(dataIni, dataFim);
				Long totalSeparada = violadorDAO.totalSeparada(dataIni, dataFim);
				Long totalSolteira = violadorDAO.totalSolteira(dataIni, dataFim);
				Long totalViuva = violadorDAO.totalViuva(dataIni, dataFim);
				Long totalEnsinoMedio = violadorDAO.totalEnsinoMedio(dataIni, dataFim);
				Long totalFundamentalPrimeira = violadorDAO.totalFundamentalPrimeira(dataIni, dataFim);
				Long totalFundamentalSexta = violadorDAO.totalFundamentalSexta(dataIni, dataFim);
				Long totalSuperior = violadorDAO.totalSuperior(dataIni, dataFim);
				
				verificaParentescoVitimaViolador(dataIni, dataFim);
				
												
				violadoresAtendimentoDTO.setQuantFeminino(totalFeminino.toString());
				violadoresAtendimentoDTO.setPorcFeminino(calculaPorcentagem(totalFeminino+totalMasculino, totalFeminino));
				violadoresAtendimentoDTO.setQuantMasculino(totalMasculino.toString());
				violadoresAtendimentoDTO.setPorcMasculino(calculaPorcentagem(totalFeminino+totalMasculino, totalMasculino));
				
				violadoresAtendimentoDTO.setQuantMaior(totalMaior.toString());
				violadoresAtendimentoDTO.setPorcMaior(calculaPorcentagem(totalMaior +
						                                                 totalInstiuicao +
						                                                 totalNaoIdentificado +
						                                                 totalMenor, totalMaior));
				violadoresAtendimentoDTO.setQuantMenor(totalMenor.toString());
				violadoresAtendimentoDTO.setPorcMenor(calculaPorcentagem(totalMaior  +
						                                                 totalInstiuicao +
						                                                 totalNaoIdentificado +
						                                                 totalMenor, totalMenor));
				
				violadoresAtendimentoDTO.setQuantInstituicao(totalInstiuicao.toString());
				violadoresAtendimentoDTO.setPorcInstituicao(calculaPorcentagem(totalMaior  + 
						                                                       totalInstiuicao +
						                                                       totalNaoIdentificado +
						                                                       totalMenor, totalInstiuicao));
				
				violadoresAtendimentoDTO.setQuantNaoIdentificado(totalNaoIdentificado.toString()); 				
				violadoresAtendimentoDTO.setPorcNaoIdentificado(calculaPorcentagem(totalMaior  + 
						                                                       totalInstiuicao +
						                                                       totalNaoIdentificado +
						                                                       totalMenor, totalNaoIdentificado));
				
				violadoresAtendimentoDTO.setQuantAmarela(totalAmarela.toString());
				violadoresAtendimentoDTO.setPorcAmarela(calculaPorcentagem(totalAmarela  +
																			totalBranca  +
																			totalIndigena+
																			totalParda   +
																			totalPreta    , totalAmarela));	
				
				violadoresAtendimentoDTO.setQuantBranca(totalBranca.toString());
				violadoresAtendimentoDTO.setPorcBranca(calculaPorcentagem(totalAmarela  +
																			totalBranca  +
																			totalIndigena+
																			totalParda   +
																			totalPreta    , totalBranca));
				
				violadoresAtendimentoDTO.setQuantIndigena(totalIndigena.toString());
				violadoresAtendimentoDTO.setPorcIndigena(calculaPorcentagem(totalAmarela  +
																		totalBranca  +
																		totalIndigena+
																		totalParda   +
																		totalPreta    , totalIndigena));
				
				violadoresAtendimentoDTO.setQuantParda(totalParda.toString());
				violadoresAtendimentoDTO.setPorcParda(calculaPorcentagem(totalAmarela  +
																			totalBranca  +
																			totalIndigena+
																			totalParda   +
																			totalPreta    , totalParda));
				
				violadoresAtendimentoDTO.setQuantPreta(totalPreta.toString());
				violadoresAtendimentoDTO.setPorcPreta(calculaPorcentagem(totalAmarela  +
																		  totalBranca  +
																		  totalIndigena+
																		  totalParda   +
																		  totalPreta    , totalPreta));
				
				violadoresAtendimentoDTO.setQuantAmasiada(totalAmasiada.toString());
				violadoresAtendimentoDTO.setPorcAmasiada(calculaPorcentagem(totalAmasiada  +
													                        totalCasada +
													                        totalDesconhecido +
													                        totalSeparada +
													                        totalSolteira +
													                        totalViuva     , totalAmasiada));
				
				violadoresAtendimentoDTO.setQuantCasada(totalCasada.toString());
				violadoresAtendimentoDTO.setPorcCasada(calculaPorcentagem(totalAmasiada  +
												                          totalCasada +
												                          totalDesconhecido +
												                          totalSeparada +
												                          totalSolteira +
												                          totalViuva     , totalCasada));
				
				violadoresAtendimentoDTO.setQuantSeparada(totalSeparada.toString());
				violadoresAtendimentoDTO.setPorcSeparada(calculaPorcentagem(totalAmasiada  +
													                        totalCasada +
													                        totalDesconhecido +
													                        totalSeparada +
													                        totalSolteira +
													                        totalViuva     , totalSeparada));
				
				violadoresAtendimentoDTO.setQuantDesconhecido(totalDesconhecido.toString());
				violadoresAtendimentoDTO.setPorcDesconhecido(calculaPorcentagem(totalAmasiada  +
														                        totalCasada +
														                        totalDesconhecido +
														                        totalSeparada +
														                        totalSolteira +
														                        totalViuva     , totalDesconhecido));
				
				violadoresAtendimentoDTO.setQuantSolteira(totalSolteira.toString());
				violadoresAtendimentoDTO.setPorcSolteira(calculaPorcentagem(totalAmasiada  +
													                        totalCasada +
													                        totalDesconhecido +
													                        totalSeparada +
													                        totalSolteira +
													                        totalViuva     , totalSolteira));
				
				violadoresAtendimentoDTO.setQuantViuva(totalViuva.toString());
				violadoresAtendimentoDTO.setPorcViuva(calculaPorcentagem(totalAmasiada  +
												                         totalCasada +
												                         totalDesconhecido +
												                         totalSeparada +
												                         totalSolteira +
												                         totalViuva     , totalViuva));
				
				
				violadoresAtendimentoDTO.setQuantFundamentalPrimeira(totalFundamentalPrimeira.toString());
				violadoresAtendimentoDTO.setPorcFundamentalPrimeira(calculaPorcentagem(totalFundamentalPrimeira +
																						totalFundamentalSexta   +
																						totalEnsinoMedio        +
																						totalSuperior           , totalFundamentalPrimeira));		
				
				violadoresAtendimentoDTO.setQuantFundamentalSexta(totalFundamentalSexta.toString());
				violadoresAtendimentoDTO.setPorcFundamentalSexta(calculaPorcentagem(totalFundamentalPrimeira +
																						totalFundamentalSexta   +
																						totalEnsinoMedio        +
																						totalSuperior           , totalFundamentalSexta));
				
				violadoresAtendimentoDTO.setQuantEnsinoMedio(totalEnsinoMedio.toString());
				violadoresAtendimentoDTO.setPorcEnsinoMedio(calculaPorcentagem(totalFundamentalPrimeira +
																					 totalFundamentalSexta   +
																					 totalEnsinoMedio        +
																					 totalSuperior           , totalEnsinoMedio));
				
				violadoresAtendimentoDTO.setQuantSuperior(totalSuperior.toString());
				violadoresAtendimentoDTO.setPorcSuperior(calculaPorcentagem(totalFundamentalPrimeira +
																				totalFundamentalSexta   +
																				totalEnsinoMedio        +
																				totalSuperior           , totalSuperior));
				
												
				
				violadoresAtendimentoDTO.setDataInicio(Helper.formatDate().format(dataIni));
				violadoresAtendimentoDTO.setDataFim(Helper.formatDate().format(dataFim));
						
				
				lista.add(violadoresAtendimentoDTO);		
				
				String caminho = "/WEB-INF/relatorios/familia_responsavel.jasper";

				ReportUtil.executarRelatorio(caminho, parametros, "Consulta-Familia", lista);
				resetaData();
				
			}else{
				JSFUtil.addErrorMessage("Data final inferior a data incial, selecione novas datas");
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao gerar boleto: "+e.getMessage());
		}						
	}
	
	public void dadosAtendimentos(){
		
		try {
			
			Map<String, Object> parametros = new HashMap<>();
			List<DadosAtendimentoDTO> lista = new ArrayList<DadosAtendimentoDTO>();
			DadosAtendimentoDTO dadosAtendimentoDTO = new DadosAtendimentoDTO();
			
			if(dataFim.after(dataIni) || dataFim.equals(dataIni)){
				
				AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
				
				// Solicitação via
				Long totalPlantao = atendimentoDAO.totalPlantao(dataIni, dataFim);
				Long totalPorEscrito = atendimentoDAO.totalPorEscrito(dataIni, dataFim);
				Long totalPorEmail = atendimentoDAO.totalPorEmail(dataIni, dataFim);
				Long totalPorSite = atendimentoDAO.totalPorSite(dataIni, dataFim);
				Long totalPorTelefone = atendimentoDAO.totalPorTelefone(dataIni, dataFim);
				Long totalSede = atendimentoDAO.totalSede(dataIni, dataFim);
				
				// Tipo de atendimento
				Long totalAcompanhamento = atendimentoDAO.totalAcompanhamento(dataIni, dataFim);
				Long totalDireito = atendimentoDAO.totalDireito(dataIni, dataFim);
				Long totalEncaminhamento = atendimentoDAO.totalEncaminhamento(dataIni, dataFim);
				Long totalOrientacao = atendimentoDAO.totalOrientacao(dataIni, dataFim);
				Long totalDenuncia = atendimentoDAO.totalDenuncia(dataIni, dataFim);
				
				// Origem do atendimento
				Long totalPropriaCrianca = atendimentoDAO.totalPropriaCrianca(dataIni, dataFim);					
				Long totalAnonima = atendimentoDAO.totalAnonima(dataIni, dataFim);
				Long totalAutoridadeJudiciaria = atendimentoDAO.totalAutoridadeJudiciaria(dataIni, dataFim);
				Long totalAutoridadePolicial = atendimentoDAO.totalAutoridadePolicial(dataIni, dataFim);
				Long totalConselhoTutelar = atendimentoDAO.totalConselhoTutelar(dataIni, dataFim);
				Long totalDireitosHumano = atendimentoDAO.totalDireitosHumano(dataIni, dataFim);
				Long totalEducacao = atendimentoDAO.totalEducacao(dataIni, dataFim);
				Long totalOutroMembroFamilia = atendimentoDAO.totalOutroMembroFamilia(dataIni, dataFim);
				Long totalPaisResponsavel = atendimentoDAO.totalPaisResponsavel(dataIni, dataFim);
				Long totalParente = atendimentoDAO.totalParente(getDataIni(), getDataFim());
				Long totalPromocaoSocial = atendimentoDAO.totalPromocaoSocial(dataIni, dataFim);
				Long totalPromotoria = atendimentoDAO.totalPromotoria(dataIni, dataFim);
				Long totalSaude = atendimentoDAO.totalSaude(dataIni, dataFim);
				Long totalTerceiro = atendimentoDAO.totalTerceiro(dataIni, dataFim);
				Long totalVizinha = atendimentoDAO.totalVizinha(dataIni, dataFim);
				
				
				//Local do fato
				Long totalAldeia = atendimentoDAO.totalAldeia(dataIni, dataFim);
				Long totalAmbienteFamiliar = atendimentoDAO.totalAmbienteFamiliar(dataIni, dataIni);
				Long totalAmbienteShow = atendimentoDAO.totalAmbienteShow(dataIni, dataIni);
				Long totalBarSimilar = atendimentoDAO.totalBarSimilar(dataIni, dataFim);
				Long totalClubeLazer = atendimentoDAO.totalClubeLazer(dataIni, dataFim);
				Long totalCreche = atendimentoDAO.totalCreche(dataIni, dataFim);
				Long totalEmpresa = atendimentoDAO.totalEmpresa(dataIni, dataFim);
				Long totalEscola  = atendimentoDAO.totalEscola (dataIni, dataFim);
				Long totalEstabelecimentoComercial = atendimentoDAO.totalEstabelecimentoComercial(dataIni,dataFim);
				Long totalHospital = atendimentoDAO.totalHospital(dataIni, dataFim);
				Long totalImovelAbandonado = atendimentoDAO.totalImovelAbandonado(dataIni, dataFim);
				Long totalMeioRural = atendimentoDAO.totalMeioRural(dataIni, dataFim);
				Long totalPostoSaude = atendimentoDAO.totalPostoSaude(dataIni, dataFim);
				Long totalPostoPolicial = atendimentoDAO.totalPostoPolicial(dataIni, dataFim);
				Long totalPracaEsporte = atendimentoDAO.totalPracaEsporte(dataIni, dataFim);
				Long totalPracaLazer = atendimentoDAO.totalPracaLazer(dataIni, dataFim);
				Long totalPracaPublica = atendimentoDAO.totalPracaPublica(dataIni, dataFim);
				Long totalProntoSocorro = atendimentoDAO.totalProntoSocorro(dataIni, dataFim);
				Long totalReparticaoPublica = atendimentoDAO.totalReparticaoPublica(dataIni, dataFim);
				Long totalRioAcudeSimilar = atendimentoDAO.totalRioAcudeSimilar(dataIni, dataFim);
				Long totalTrabalho =  atendimentoDAO.totalTrabalho(dataIni, dataFim);
				Long totalViaPublica = atendimentoDAO.totalViaPublica(dataIni, dataFim);
				
				// Denuncia tem fundamento
				Long totalComFundamento = atendimentoDAO.totalComFundamento(dataIni, dataFim);
				Long totalSemFundamento = atendimentoDAO.totalSemFundamento(dataIni, dataFim);
				
				// Classe sub-classe violador
				Long totalPropriaCriancaAdolescente = atendimentoDAO.totalPropriaCrianca(dataIni, dataFim);
//				Long TotalCras = atendimentoDAO.totalCras(dataIni, dataFim);
				
				dadosAtendimentoDTO.setQuantPlantao(totalPlantao.toString());
				dadosAtendimentoDTO.setPorcPlantao(calculaPorcentagem(totalPlantao     +
						                                              totalPorEmail    +
																	  totalPorEscrito  +
																	  totalPorSite     +
																	  totalPorTelefone +
																	  totalSede        , totalPlantao));
				
				dadosAtendimentoDTO.setQuantPorEmail(totalPorEmail.toString());
				dadosAtendimentoDTO.setPorcPorEmail(calculaPorcentagem(totalPlantao     +
							                                           totalPorEmail    +
																       totalPorEscrito  +
																       totalPorSite     +
																       totalPorTelefone +
																       totalSede        , totalPorEmail));
				
				dadosAtendimentoDTO.setQuantPorEscrito(totalPorEscrito.toString());
				dadosAtendimentoDTO.setPorcPorEscrito(calculaPorcentagem(totalPlantao     +
						                                                 totalPorEmail    +
																		 totalPorEscrito  +
																		 totalPorSite     +
																		 totalPorTelefone +
																		 totalSede        , totalPorEscrito));
								
				dadosAtendimentoDTO.setQuantPorSite(totalPorSite.toString());
				dadosAtendimentoDTO.setPorcPorSite(calculaPorcentagem(totalPlantao     +
						                                              totalPorEmail    +
																	  totalPorEscrito  +
																	  totalPorSite     +
																 	  totalPorTelefone +
																 	  totalSede        , totalPorSite));
				
				dadosAtendimentoDTO.setQuantPorTelefone(totalPorTelefone.toString());
				dadosAtendimentoDTO.setPorcPorTelefone(calculaPorcentagem(totalPlantao     +
						                                                  totalPorEmail    +
																		  totalPorEscrito  +
																		  totalPorSite     +
																	 	  totalPorTelefone +
																	 	  totalSede        , totalPorTelefone));
				
				dadosAtendimentoDTO.setQuantSede(totalSede.toString());
				dadosAtendimentoDTO.setPorcSede(calculaPorcentagem(totalPlantao     +
					                                    	       totalPorEmail    +
																   totalPorEscrito  +
																   totalPorSite     +
															 	   totalPorTelefone +
															 	   totalSede        , totalSede));	
				
				dadosAtendimentoDTO.setQuantAcompanhamento(totalAcompanhamento.toString());
				dadosAtendimentoDTO.setPorcAcompanhamento(calculaPorcentagem(totalAcompanhamento +
																			 totalDireito        +
																			 totalEncaminhamento +
																			 totalOrientacao     +
																			 totalDenuncia       , totalAcompanhamento));	
				
				dadosAtendimentoDTO.setQuantDireito(totalDireito.toString());
				dadosAtendimentoDTO.setPorcDireito(calculaPorcentagem(totalAcompanhamento +
																	  totalDireito        +
																	  totalEncaminhamento +
																	  totalOrientacao     +
																	  totalDenuncia       , totalDireito));
				
				dadosAtendimentoDTO.setQuantEncaminhamento(totalEncaminhamento.toString());
				dadosAtendimentoDTO.setPorcEncaminhamento(calculaPorcentagem(totalAcompanhamento +
																			 totalDireito        +
																			 totalEncaminhamento +
																			 totalOrientacao     +
																			 totalDenuncia       , totalEncaminhamento));
				
				dadosAtendimentoDTO.setQuantOrientacao(totalOrientacao.toString());
				dadosAtendimentoDTO.setPorcOrientacao(calculaPorcentagem(totalAcompanhamento +
																		 totalDireito        +
																		 totalEncaminhamento +
																		 totalOrientacao     +
																		 totalDenuncia       , totalOrientacao));
				
				dadosAtendimentoDTO.setQuantDenuncia(totalDenuncia.toString());
				dadosAtendimentoDTO.setPorcDenuncia(calculaPorcentagem(totalAcompanhamento +
																	   totalDireito        +
																	   totalEncaminhamento +
																	   totalOrientacao     +
																	   totalDenuncia       , totalDenuncia));
				
				dadosAtendimentoDTO.setQuantPropriaCrianca(totalPropriaCrianca.toString());
				dadosAtendimentoDTO.setPorcPropriaCrianca(calculaPorcentagem(totalPropriaCrianca            +
					                                                         totalAnonima                   +
					                                                         totalAutoridadeJudiciaria      +
					                                                         totalAutoridadePolicial        +
					                                                         totalConselhoTutelar           +
					                                                         totalDireitosHumano            +
					                                                         totalEducacao                  +
					                                                         totalOutroMembroFamilia        +
					                                                         totalPaisResponsavel           +
					                                                         totalParente                   +
					                                                         totalPromocaoSocial            +
					                                                         totalPromotoria                +
					                                                         totalSaude                     +
					                                                         totalTerceiro                  +
					                                                         totalVizinha                   ,totalPropriaCrianca));
			
				
				dadosAtendimentoDTO.setQuantAnonima(totalAnonima.toString());
				dadosAtendimentoDTO.setPorcAnonima(calculaPorcentagem(totalPropriaCrianca            +
											                          totalAnonima                   +
											                          totalAutoridadeJudiciaria      +
											                          totalAutoridadePolicial        +
											                          totalConselhoTutelar           +
											                          totalDireitosHumano            +
											                          totalEducacao                  +
											                          totalOutroMembroFamilia        +
											                          totalPaisResponsavel           +
											                          totalParente                   +
											                          totalPromocaoSocial            +
											                          totalPromotoria                +
											                          totalSaude                     +
											                          totalTerceiro                  +
											                          totalVizinha                   ,totalAnonima));

				
				dadosAtendimentoDTO.setQuantAutoridadeJudiciaria(totalAutoridadeJudiciaria.toString());
				dadosAtendimentoDTO.setPorcAutoridadeJudiciaria(calculaPorcentagem(totalPropriaCrianca            +
														                           totalAnonima                   +
														                           totalAutoridadeJudiciaria      +
														                           totalAutoridadePolicial        +
														                           totalConselhoTutelar           +
														                           totalDireitosHumano            +
														                           totalEducacao                  +
														                           totalOutroMembroFamilia        +
														                           totalPaisResponsavel           +
														                           totalParente                   +
														                           totalPromocaoSocial            +
														                           totalPromotoria                +
														                           totalSaude                     +
														                           totalTerceiro                  +
														                           totalVizinha                   ,totalAutoridadeJudiciaria));
				
				dadosAtendimentoDTO.setQuantAutoridadePolicial(totalAutoridadePolicial.toString());
				dadosAtendimentoDTO.setPorcAutoridadePolicial(calculaPorcentagem(totalPropriaCrianca            +
														                         totalAnonima                   +
														                         totalAutoridadeJudiciaria      +
														                         totalAutoridadePolicial        +
														                         totalConselhoTutelar           +
														                         totalDireitosHumano            +
														                         totalEducacao                  +
														                         totalOutroMembroFamilia        +
														                         totalPaisResponsavel           +
														                         totalParente                   +
														                         totalPromocaoSocial            +
														                         totalPromotoria                +
														                         totalSaude                     +
														                         totalTerceiro                  +
														                         totalVizinha                   ,totalAutoridadePolicial));

				
				dadosAtendimentoDTO.setQuantConselhoTutelar(totalConselhoTutelar.toString());
				dadosAtendimentoDTO.setPorcConselhoTutelar(calculaPorcentagem(totalPropriaCrianca            +
													                          totalAnonima                   +
													                          totalAutoridadeJudiciaria      +
													                          totalAutoridadePolicial        +
													                          totalConselhoTutelar           +
													                          totalDireitosHumano            +
													                          totalEducacao                  +
													                          totalOutroMembroFamilia        +
													                          totalPaisResponsavel           +
													                          totalParente                   +
													                          totalPromocaoSocial            +
													                          totalPromotoria                +
													                          totalSaude                     +
													                          totalTerceiro                  +
													                          totalVizinha                   ,totalConselhoTutelar));

				
				dadosAtendimentoDTO.setQuantDireitosHumano(totalDireitosHumano.toString());
				dadosAtendimentoDTO.setPorcDireitosHumano(calculaPorcentagem(totalPropriaCrianca            +
													                         totalAnonima                   +
													                         totalAutoridadeJudiciaria      +
													                         totalAutoridadePolicial        +
													                         totalConselhoTutelar           +
													                         totalDireitosHumano            +
													                         totalEducacao                  +
													                         totalOutroMembroFamilia        +
													                         totalPaisResponsavel           +
													                         totalParente                   +
													                         totalPromocaoSocial            +
													                         totalPromotoria                +
													                         totalSaude                     +
													                         totalTerceiro                  +
													                         totalVizinha                   ,totalDireitosHumano));

				
				dadosAtendimentoDTO.setQuantEducacao(totalEducacao.toString());
				dadosAtendimentoDTO.setPorcEducacao(calculaPorcentagem(totalPropriaCrianca            +
											                           totalAnonima                   +
											                           totalAutoridadeJudiciaria      +
											                           totalAutoridadePolicial        +
											                           totalConselhoTutelar           +
											                           totalDireitosHumano            +
											                           totalEducacao                  +
											                           totalOutroMembroFamilia        +
											                           totalPaisResponsavel           +
											                           totalParente                   +
											                           totalPromocaoSocial            +
											                           totalPromotoria                +
											                           totalSaude                     +
											                           totalTerceiro                  +
											                           totalVizinha                   ,totalEducacao));

				
				dadosAtendimentoDTO.setQuantOutroMembroFamilia(totalOutroMembroFamilia.toString());
				dadosAtendimentoDTO.setPorcOutroMembroFamilia(calculaPorcentagem(totalPropriaCrianca            +
														                         totalAnonima                   +
														                         totalAutoridadeJudiciaria      +
														                         totalAutoridadePolicial        +
														                         totalConselhoTutelar           +
														                         totalDireitosHumano            +
														                         totalEducacao                  +
														                         totalOutroMembroFamilia        +
														                         totalPaisResponsavel           +
														                         totalParente                   +
														                         totalPromocaoSocial            +
														                         totalPromotoria                +
														                         totalSaude                     +
														                         totalTerceiro                  +
														                         totalVizinha                   ,totalOutroMembroFamilia));

				
				dadosAtendimentoDTO.setQuantPaisResponsavel(totalPaisResponsavel.toString());
				dadosAtendimentoDTO.setPorcPaisResponsavel(calculaPorcentagem(totalPropriaCrianca            +
													                          totalAnonima                   +
													                          totalAutoridadeJudiciaria      +
													                          totalAutoridadePolicial        +
													                          totalConselhoTutelar           +
													                          totalDireitosHumano            +
													                          totalEducacao                  +
													                          totalOutroMembroFamilia        +
													                          totalPaisResponsavel           +
													                          totalParente                   +
													                          totalPromocaoSocial            +
													                          totalPromotoria                +
													                          totalSaude                     +
													                          totalTerceiro                  +
													                          totalVizinha                   ,totalPaisResponsavel));

				
				dadosAtendimentoDTO.setQuantParente(totalParente.toString());
				dadosAtendimentoDTO.setPorcParente(calculaPorcentagem(totalPropriaCrianca            +
			 								                          totalAnonima                   +
			   								                          totalAutoridadeJudiciaria      +
											                          totalAutoridadePolicial        +
											                          totalConselhoTutelar           +
											                          totalDireitosHumano            +
											                          totalEducacao                  +
											                          totalOutroMembroFamilia        +
											                          totalPaisResponsavel           +
											                          totalParente                   +
											                          totalPromocaoSocial            +
											                          totalPromotoria                +
											                          totalSaude                     +
											                          totalTerceiro                  +
											                          totalVizinha                   ,totalParente));

				
				dadosAtendimentoDTO.setQuantPromocaoSocial(totalPromocaoSocial.toString());
				dadosAtendimentoDTO.setPorcPromocaoSocial(calculaPorcentagem(totalPropriaCrianca                       +
																                        totalAnonima                   +
																	                    totalAutoridadeJudiciaria      +
																                        totalAutoridadePolicial        +
																                        totalConselhoTutelar           +
																                        totalDireitosHumano            +
																                        totalEducacao                  +
																                        totalOutroMembroFamilia        +
																                        totalPaisResponsavel           +
																                        totalParente                   +
																                        totalPromocaoSocial            +
																                        totalPromotoria                +
																                        totalSaude                     +
																                        totalTerceiro                  +
																                        totalVizinha                   ,totalPromocaoSocial));
				
				dadosAtendimentoDTO.setQuantPromotoria(totalPromotoria.toString());
				dadosAtendimentoDTO.setPorcPromotoria(calculaPorcentagem(totalPropriaCrianca            +
												                         totalAnonima                   +
													                     totalAutoridadeJudiciaria      +
												                         totalAutoridadePolicial        +
												                         totalConselhoTutelar           +
												                         totalDireitosHumano            +
												                         totalEducacao                  +
												                         totalOutroMembroFamilia        +
												                         totalPaisResponsavel           +
												                         totalParente                   +
												                         totalPromocaoSocial            +
												                         totalPromotoria                +
												                         totalSaude                     +
												                         totalTerceiro                  +
												                         totalVizinha                   ,totalPromotoria ));

				
				dadosAtendimentoDTO.setQuantSaude(totalSaude.toString());
				dadosAtendimentoDTO.setPorcSaude(calculaPorcentagem(totalPropriaCrianca            +
											                        totalAnonima                   +
												                    totalAutoridadeJudiciaria      +
											                        totalAutoridadePolicial        +
											                        totalConselhoTutelar           +
											                        totalDireitosHumano            +
											                        totalEducacao                  +
											                        totalOutroMembroFamilia        +
											                        totalPaisResponsavel           +
											                        totalParente                   +
											                        totalPromocaoSocial            +
											                        totalPromotoria                +
											                        totalSaude                     +
											                        totalTerceiro                  +
											                        totalVizinha                   ,totalSaude ));

				
				dadosAtendimentoDTO.setQuantTerceiro(totalTerceiro.toString());
				dadosAtendimentoDTO.setPorcTerceiro(calculaPorcentagem(totalPropriaCrianca            +
											                           totalAnonima                   +
												                       totalAutoridadeJudiciaria      +
											                           totalAutoridadePolicial        +
											                           totalConselhoTutelar           +
											                           totalDireitosHumano            +
											                           totalEducacao                  +
											                           totalOutroMembroFamilia        +
											                           totalPaisResponsavel           +
											                           totalParente                   +
											                           totalPromocaoSocial            +
											                           totalPromotoria                +
											                           totalSaude                     +
											                           totalTerceiro                  +
											                           totalVizinha                   ,totalTerceiro ));

				
				dadosAtendimentoDTO.setQuantVizinha(totalVizinha.toString());
				dadosAtendimentoDTO.setPorcVizinha(calculaPorcentagem(totalPropriaCrianca            +
									    	                          totalAnonima                   +
												                      totalAutoridadeJudiciaria      +
											                          totalAutoridadePolicial        +
											                          totalConselhoTutelar           +
											                          totalDireitosHumano            +
											                          totalEducacao                  +
											                          totalOutroMembroFamilia        +
											                          totalPaisResponsavel           +
											                          totalParente                   +
											                          totalPromocaoSocial            +
											                          totalPromotoria                +
											                          totalSaude                     +
											                          totalTerceiro                  +
											                          totalVizinha                   ,totalVizinha  ));
				
				dadosAtendimentoDTO.setQuantAmbienteFamiliar(totalAmbienteFamiliar.toString());
				 dadosAtendimentoDTO.setPorcAmbienteFamiliar(calculaPorcentagem(totalAmbienteFamiliar         +
				                                                                totalBarSimilar               +
																				totalClubeLazer               +
					 															totalCreche                   +
																				totalEmpresa                  +
																				totalEstabelecimentoComercial +
																				totalHospital                 + 
																				totalImovelAbandonado         +
																				totalMeioRural                +
																				totalPostoSaude               +
																				totalPostoPolicial            +
																				totalPracaEsporte             + 
																				totalPracaLazer               +
																				totalPracaPublica             +
																				totalProntoSocorro            +
																				totalReparticaoPublica        +
																				totalRioAcudeSimilar          +
																				totalTrabalho                 +
																				totalViaPublica               +
																				totalAldeia                   +
																				totalAmbienteShow             +
																				totalEscola                   , totalAmbienteFamiliar ));
																				
				 dadosAtendimentoDTO.setQuantBarSimilar(totalBarSimilar.toString());
				 dadosAtendimentoDTO.setPorcBarSimilar(calculaPorcentagem(totalAmbienteFamiliar         +
																		  totalBarSimilar               +
																		  totalClubeLazer               +
																		  totalCreche                   +
																		  totalEmpresa                  +
																		  totalEstabelecimentoComercial +
																		  totalHospital                 + 
																		  totalImovelAbandonado         +
																		  totalMeioRural                +
																		  totalPostoSaude               +
																		  totalPostoPolicial            +
																		  totalPracaEsporte             + 
																		  totalPracaLazer               +
																		  totalPracaPublica             +
																		  totalProntoSocorro            +
																		  totalReparticaoPublica        +
																		  totalRioAcudeSimilar          +
																		  totalTrabalho                 +
																		  totalViaPublica               +
																		  totalAldeia                   +
																		  totalAmbienteShow             +
																		  totalEscola                   , totalBarSimilar ));
																		  
				 dadosAtendimentoDTO.setQuantClubeLazer(totalClubeLazer.toString());
				 dadosAtendimentoDTO.setPorcClubeLazer(calculaPorcentagem(totalAmbienteFamiliar         +
											        					  totalBarSimilar               +
																		  totalClubeLazer               +
																		  totalCreche                   +
																		  totalEmpresa                  +
																		  totalEstabelecimentoComercial +
																		  totalHospital                 + 
																		  totalImovelAbandonado         +
																		  totalMeioRural                +
																		  totalPostoSaude               +
																		  totalPostoPolicial            +
																		  totalPracaEsporte             + 
																		  totalPracaLazer               +
																		  totalPracaPublica             +
																		  totalProntoSocorro            +
																		  totalReparticaoPublica        +
																		  totalRioAcudeSimilar          +
																		  totalTrabalho                 +
																		  totalViaPublica               +
																		  totalAldeia                   +
																		  totalAmbienteShow             +
																		  totalEscola                   , totalClubeLazer ));
																		  
				 dadosAtendimentoDTO.setQuantCreche(totalCreche.toString());
				 dadosAtendimentoDTO.setPorcCreche(calculaPorcentagem(totalAmbienteFamiliar         +
																	  totalBarSimilar               +
																	  totalClubeLazer               +
																	  totalCreche                   +
																	  totalEmpresa                  +
																	  totalEstabelecimentoComercial +
																	  totalHospital                 + 
																	  totalImovelAbandonado         +
																	  totalMeioRural                +
																	  totalPostoSaude               +
																	  totalPostoPolicial            +
																	  totalPracaEsporte             + 
																	  totalPracaLazer               +
																	  totalPracaPublica             +
																	  totalProntoSocorro            +
																	  totalReparticaoPublica        +
																	  totalRioAcudeSimilar          +
																	  totalTrabalho                 +
																	  totalViaPublica               +
																	  totalAldeia                   +
																	  totalAmbienteShow             +
																	  totalEscola                   , totalCreche ));
																	  
				 dadosAtendimentoDTO.setQuantEmpresa(totalEmpresa.toString());
				 dadosAtendimentoDTO.setPorcEmpresa(calculaPorcentagem(totalAmbienteFamiliar         +
																	   totalBarSimilar               +
																	   totalClubeLazer               +
																	   totalCreche                   +
																	   totalEmpresa                  +
																	   totalEstabelecimentoComercial +
																	   totalHospital                 + 
																	   totalImovelAbandonado         +
																	   totalMeioRural                +
																	   totalPostoSaude               +
																	   totalPostoPolicial            +
																       totalPracaEsporte             + 
																	   totalPracaLazer               +
																	   totalPracaPublica             +
																	   totalProntoSocorro            +
																	   totalReparticaoPublica        +
																	   totalRioAcudeSimilar          +
																	   totalTrabalho                 +
																	   totalViaPublica               +
																	   totalAldeia                   +
																	   totalAmbienteShow             +
																	   totalEscola                   , totalEmpresa ));
																	   
				 dadosAtendimentoDTO.setQuantEstabelecimentoComercial(totalEstabelecimentoComercial.toString());
				 dadosAtendimentoDTO.setPorcEstabelecimentoComercial(calculaPorcentagem(totalAmbienteFamiliar         +
																						totalBarSimilar               +
																						totalClubeLazer               +
																						totalCreche                   +
																						totalEmpresa                  +
																						totalEstabelecimentoComercial +
																						totalHospital                 + 
																						totalImovelAbandonado         +
																						totalMeioRural                +
																						totalPostoSaude               +
																						totalPostoPolicial            +
																						totalPracaEsporte             + 
																						totalPracaLazer               +
																						totalPracaPublica             +
																						totalProntoSocorro            +
																						totalReparticaoPublica        +
																						totalRioAcudeSimilar          +
																						totalTrabalho                 +
																						totalViaPublica               +
																						totalAldeia                   +
																						totalAmbienteShow             +
																						totalEscola                   , totalEstabelecimentoComercial ));
																						
				 dadosAtendimentoDTO.setQuantHospital(totalHospital.toString());
				 dadosAtendimentoDTO.setPorcHospital(calculaPorcentagem(totalAmbienteFamiliar         +
																		totalBarSimilar               +
																		totalClubeLazer               +
																		totalCreche                   +
																		totalEmpresa                  +
																		totalEstabelecimentoComercial +
																		totalHospital                 + 
																		totalImovelAbandonado         +
																		totalMeioRural                +
																		totalPostoSaude               +
																		totalPostoPolicial            +
																		totalPracaEsporte             + 
																		totalPracaLazer               +
																		totalPracaPublica             +
																		totalProntoSocorro            +
																		totalReparticaoPublica        +
																		totalRioAcudeSimilar          +
																		totalTrabalho                 +
																		totalViaPublica               +
																		totalAldeia                   +
																		totalAmbienteShow             +
																		totalEscola                   , totalHospital  ));
																		
				 dadosAtendimentoDTO.setQuantImovelAbandonado(totalImovelAbandonado.toString());
				 dadosAtendimentoDTO.setPorcImovelAbandonado(calculaPorcentagem(totalAmbienteFamiliar         +
																				totalBarSimilar               +
																				totalClubeLazer               +
																				totalCreche                   +
																				totalEmpresa                  +
																				totalEstabelecimentoComercial +
																				totalHospital                 + 
																				totalImovelAbandonado         +
																				totalMeioRural                +
																				totalPostoSaude               +
																				totalPostoPolicial            +
																				totalPracaEsporte             + 
																				totalPracaLazer               +
																				totalPracaPublica             +
																				totalProntoSocorro            +
																				totalReparticaoPublica        +
																				totalRioAcudeSimilar          +
																				totalTrabalho                 +
																				totalViaPublica               +
																				totalAldeia                   +
																				totalAmbienteShow             +
																				totalEscola                   , totalImovelAbandonado ));
																				
				 dadosAtendimentoDTO.setQuantMeioRural(totalMeioRural.toString());
				 dadosAtendimentoDTO.setPorcMeioRural(calculaPorcentagem(totalAmbienteFamiliar         +
																		 totalBarSimilar               +
																		 totalClubeLazer               +
																		 totalCreche                   +
																		 totalEmpresa                  +
																		 totalEstabelecimentoComercial +
																		 totalHospital                 + 
																		 totalImovelAbandonado         +
																		 totalMeioRural                +
																		 totalPostoSaude               +
																		 totalPostoPolicial            +
																		 totalPracaEsporte             + 
																		 totalPracaLazer               +
																		 totalPracaPublica             +
																		 totalProntoSocorro            +
																		 totalReparticaoPublica        +
																		 totalRioAcudeSimilar          +
																		 totalTrabalho                 +
																		 totalViaPublica               +
																		 totalAldeia                   +
																		 totalAmbienteShow             +
																		 totalEscola                   , totalMeioRural ));
																						
				 dadosAtendimentoDTO.setQuantPostoSaude(totalPostoSaude.toString());
				 dadosAtendimentoDTO.setPorcPostoSaude(calculaPorcentagem(totalAmbienteFamiliar         +
																		  totalBarSimilar               +
																		  totalClubeLazer               +
																		  totalCreche                   +
																	      totalEmpresa                  +
																		  totalEstabelecimentoComercial +
																		  totalHospital                 + 
																		  totalImovelAbandonado         +
																		  totalMeioRural                +
																		  totalPostoSaude               +
																		  totalPostoPolicial            +
																		  totalPracaEsporte             + 
																		  totalPracaLazer               +
																		  totalPracaPublica             +
																		  totalProntoSocorro            +
																		  totalReparticaoPublica        +
																		  totalRioAcudeSimilar          +
																		  totalTrabalho                 +
																		  totalViaPublica               +
																		  totalAldeia                   +
																		  totalAmbienteShow             +
																		  totalEscola                   , totalPostoSaude ));
																		
				 dadosAtendimentoDTO.setQuantPostoPolicial(totalPostoPolicial.toString());
				 dadosAtendimentoDTO.setPorcPostoPolicial(calculaPorcentagem(totalAmbienteFamiliar         +
																			 totalBarSimilar               +
																			 totalClubeLazer               +
																			 totalCreche                   +
																			 totalEmpresa                  +
																			 totalEstabelecimentoComercial +
																			 totalHospital                 + 
																			 totalImovelAbandonado         +
																			 totalMeioRural                +
																			 totalPostoSaude               +
																			 totalPostoPolicial            +
																			 totalPracaEsporte             + 
																			 totalPracaLazer               +
																			 totalPracaPublica             +
																			 totalProntoSocorro            +
																			 totalReparticaoPublica        +
																			 totalRioAcudeSimilar          +
																			 totalTrabalho                 +
																			 totalViaPublica               +
																			 totalAldeia                   +
																			 totalAmbienteShow             +
																			 totalEscola                   , totalPostoPolicial ));
																						
				 dadosAtendimentoDTO.setQuantPracaEsporte(totalPracaEsporte.toString());
				 dadosAtendimentoDTO.setPorcPracaEsporte(calculaPorcentagem(totalAmbienteFamiliar         +
																			totalBarSimilar               +
																			totalClubeLazer               +
																			totalCreche                   +
																			totalEmpresa                  +
																			totalEstabelecimentoComercial +
																			totalHospital                 + 
																			totalImovelAbandonado         +
																			totalMeioRural                +
																			totalPostoSaude               +
																			totalPostoPolicial            +
																			totalPracaEsporte             + 
																			totalPracaLazer               +
																			totalPracaPublica             +
																			totalProntoSocorro            +
																			totalReparticaoPublica        +
																			totalRioAcudeSimilar          +
																			totalTrabalho                 +
																			totalViaPublica               +
																			totalAldeia                   +
																			totalAmbienteShow             +
																			totalEscola                   , totalPracaEsporte ));
																			
				 dadosAtendimentoDTO.setQuantPracaLazer(totalPracaLazer.toString());
				 dadosAtendimentoDTO.setPorcPracaLazer(calculaPorcentagem(totalAmbienteFamiliar     +
																	  totalBarSimilar               +
																	  totalClubeLazer               +
																	  totalCreche                   +
																	  totalEmpresa                  +
																	  totalEstabelecimentoComercial +
																	  totalHospital                 + 
																	  totalImovelAbandonado         +
																	  totalMeioRural                +
																	  totalPostoSaude               +
																	  totalPostoPolicial            +
																	  totalPracaEsporte             + 
																	  totalPracaLazer               +
																	  totalPracaPublica             +
																	  totalProntoSocorro            +
																	  totalReparticaoPublica        +
																	  totalRioAcudeSimilar          +
																	  totalTrabalho                 +
																	  totalViaPublica               +
																	  totalAldeia                   +
																	  totalAmbienteShow             +
																	  totalEscola                   , totalPracaLazer ));
																	  
				 dadosAtendimentoDTO.setQuantPracaPublica(totalPracaPublica.toString());
				 dadosAtendimentoDTO.setPorcPracaPublica(calculaPorcentagem(totalAmbienteFamiliar                     +
																						totalBarSimilar               +
																						totalClubeLazer               +
																						totalCreche                   +
																						totalEmpresa                  +
																						totalEstabelecimentoComercial +
																						totalHospital                 + 
																						totalImovelAbandonado         +
																						totalMeioRural                +
																						totalPostoSaude               +
																						totalPostoPolicial            +
																						totalPracaEsporte             + 
																						totalPracaLazer               +
																						totalPracaPublica             +
																						totalProntoSocorro            +
																						totalReparticaoPublica        +
																						totalRioAcudeSimilar          +
																						totalTrabalho                 +
																						totalViaPublica               +
																						totalAldeia                   +
																						totalAmbienteShow             +
																						totalEscola                   , totalPracaPublica ));
																						
				 dadosAtendimentoDTO.setQuantProntoSocorro(totalProntoSocorro.toString());
				 dadosAtendimentoDTO.setPorcProntoSocorro(calculaPorcentagem(totalAmbienteFamiliar         +
																			 totalBarSimilar               +
																			 totalClubeLazer               +
																			 totalCreche                   +
																			 totalEmpresa                  +
																			 totalEstabelecimentoComercial +
																			 totalHospital                 + 
																			 totalImovelAbandonado         +
																			 totalMeioRural                +
																			 totalPostoSaude               +
																			 totalPostoPolicial            +
																			 totalPracaEsporte             + 
																			 totalPracaLazer               +
																			 totalPracaPublica             +
																			 totalProntoSocorro            +
																			 totalReparticaoPublica        +
																			 totalRioAcudeSimilar          +
																			 totalTrabalho                 +
																			 totalViaPublica               +
																			 totalAldeia                   +
																			 totalAmbienteShow             +
																			 totalEscola                   , totalProntoSocorro ));
																			 
				 dadosAtendimentoDTO.setQuantReparticaoPublica(totalReparticaoPublica.toString());
				 dadosAtendimentoDTO.setPorcReparticaoPublica(calculaPorcentagem(totalAmbienteFamiliar         +
																				 totalBarSimilar               +
																				 totalClubeLazer               +
																				 totalCreche                   +
																				 totalEmpresa                  +
																				 totalEstabelecimentoComercial +
																				 totalHospital                 + 
																				 totalImovelAbandonado         +
																				 totalMeioRural                +
																				 totalPostoSaude               +
																				 totalPostoPolicial            +
																				 totalPracaEsporte             + 
																				 totalPracaLazer               +
																				 totalPracaPublica             +
																				 totalProntoSocorro            +
																				 totalReparticaoPublica        +
																				 totalRioAcudeSimilar          +
																				 totalTrabalho                 +
																				 totalViaPublica               +
																				 totalAldeia                   +
																				 totalAmbienteShow             +
																				 totalEscola                   , totalReparticaoPublica ));
																				 
				 dadosAtendimentoDTO.setQuantRioAcudeSimilar(totalRioAcudeSimilar.toString());
				 dadosAtendimentoDTO.setPorcRioAcudeSimilar(calculaPorcentagem(totalAmbienteFamiliar                  +
																						totalBarSimilar               +
																						totalClubeLazer               +
																						totalCreche                   +
																						totalEmpresa                  +
																						totalEstabelecimentoComercial +
																						totalHospital                 + 
																						totalImovelAbandonado         +
																						totalMeioRural                +
																						totalPostoSaude               +
																						totalPostoPolicial            +
																						totalPracaEsporte             + 
																						totalPracaLazer               +
																						totalPracaPublica             +
																						totalProntoSocorro            +
																						totalReparticaoPublica        +
																						totalRioAcudeSimilar          +
																						totalTrabalho                 +
																						totalViaPublica               +
																						totalAldeia                   +
																						totalAmbienteShow             +
																						totalEscola                   , totalRioAcudeSimilar ));
																						
				 dadosAtendimentoDTO.setQuantTrabalho(totalTrabalho.toString());
				 dadosAtendimentoDTO.setPorcTrabalho(calculaPorcentagem(totalAmbienteFamiliar         +
																		totalBarSimilar               +
																		totalClubeLazer               +
																		totalCreche                   +
																		totalEmpresa                  +
																		totalEstabelecimentoComercial +
																		totalHospital                 + 
																		totalImovelAbandonado         +
																		totalMeioRural                +
																		totalPostoSaude               +
																		totalPostoPolicial            +
																		totalPracaEsporte             + 
																		totalPracaLazer               +
																		totalPracaPublica             +
																		totalProntoSocorro            +
																		totalReparticaoPublica        +
																		totalRioAcudeSimilar          +
																		totalTrabalho                 +
																		totalViaPublica               +
																		totalAldeia                   +
																		totalAmbienteShow             +
																		totalEscola                   , totalTrabalho ));
																		
				 dadosAtendimentoDTO.setQuantViaPublica(totalViaPublica.toString());
				 dadosAtendimentoDTO.setPorcViaPublica(calculaPorcentagem(totalAmbienteFamiliar         +
																		  totalBarSimilar               +
																		  totalClubeLazer               +
																		  totalCreche                   +
																		  totalEmpresa                  +
																		  totalEstabelecimentoComercial +
																		  totalHospital                 + 
																		  totalImovelAbandonado         +
																		  totalMeioRural                +
																		  totalPostoSaude               +
																		  totalPostoPolicial            +
																		  totalPracaEsporte             + 
																		  totalPracaLazer               +
																		  totalPracaPublica             +
																		  totalProntoSocorro            +
																		  totalReparticaoPublica        +
																		  totalRioAcudeSimilar          +
																		  totalTrabalho                 +
																		  totalViaPublica               +
																		  totalAldeia                   +
																		  totalAmbienteShow             +
																		  totalEscola                   , totalViaPublica ));
																		  
				 dadosAtendimentoDTO.setQuantAldeia(totalAldeia.toString());
				 dadosAtendimentoDTO.setPorcAldeia(calculaPorcentagem(totalAmbienteFamiliar         +
																	  totalBarSimilar               +
																	  totalClubeLazer               +
																	  totalCreche                   +
																	  totalEmpresa                  +
																	  totalEstabelecimentoComercial +
																	  totalHospital                 + 
																	  totalImovelAbandonado         +
																	  totalMeioRural                +
																	  totalPostoSaude               +
																	  totalPostoPolicial            +
																	  totalPracaEsporte             + 
																	  totalPracaLazer               +
																	  totalPracaPublica             +
																	  totalProntoSocorro            +
																	  totalReparticaoPublica        +
																	  totalRioAcudeSimilar          +
																	  totalTrabalho                 +
																	  totalViaPublica               +
																	  totalAldeia                   +
																	  totalAmbienteShow             +
																	  totalEscola                   , totalAldeia ));
																	  
				 dadosAtendimentoDTO.setQuantAmbienteShow(totalAmbienteShow.toString());
				 dadosAtendimentoDTO.setPorcAmbienteShow(calculaPorcentagem(totalAmbienteFamiliar         +
																			totalBarSimilar               +
																			totalClubeLazer               +
																			totalCreche                   +
																			totalEmpresa                  +
																			totalEstabelecimentoComercial +
																			totalHospital                 + 
																			totalImovelAbandonado         +
																			totalMeioRural                +
																			totalPostoSaude               +
																			totalPostoPolicial            +
																			totalPracaEsporte             + 
																			totalPracaLazer               +
																			totalPracaPublica             +
																			totalProntoSocorro            +
																			totalReparticaoPublica        +
																			totalRioAcudeSimilar          +
																			totalTrabalho                 +
																			totalViaPublica               +
																			totalAldeia                   +
																			totalAmbienteShow             +
																			totalEscola                   , totalAmbienteShow ));
																			
				 dadosAtendimentoDTO.setQuantEscola(totalEscola.toString());
				 dadosAtendimentoDTO.setPorcEscola(calculaPorcentagem(totalAmbienteFamiliar         +
																						totalBarSimilar               +
																						totalClubeLazer               +
																						totalCreche                   +
																						totalEmpresa                  +
																						totalEstabelecimentoComercial +
																						totalHospital                 + 
																						totalImovelAbandonado         +
																						totalMeioRural                +
																						totalPostoSaude               +
																						totalPostoPolicial            +
																						totalPracaEsporte             + 
																						totalPracaLazer               +
																						totalPracaPublica             +
																						totalProntoSocorro            +
																						totalReparticaoPublica        +
																						totalRioAcudeSimilar          +
																						totalTrabalho                 +
																						totalViaPublica               +
																						totalAldeia                   +
																						totalAmbienteShow             +
																						totalEscola                   , totalEscola ));	
				
				dadosAtendimentoDTO.setQuantSim(totalComFundamento.toString());
				dadosAtendimentoDTO.setPorcSim(calculaPorcentagem(totalComFundamento +
																  totalSemFundamento ,  totalComFundamento));
				
				dadosAtendimentoDTO.setQuantNao(totalSemFundamento.toString());
				dadosAtendimentoDTO.setPorcNao(calculaPorcentagem(totalComFundamento +
																  totalSemFundamento ,  totalSemFundamento));
											
				
				dadosAtendimentoDTO.setDataInicio(Helper.formatDate().format(dataIni));
				dadosAtendimentoDTO.setDataFim(Helper.formatDate().format(dataFim));
								
				
				lista.add(dadosAtendimentoDTO);		
				
				String caminho = "/WEB-INF/relatorios/dados_atendimento.jasper";

				ReportUtil.executarRelatorio(caminho, parametros, "Consulta-Familia", lista);
				resetaData();
				
			}else{
				JSFUtil.addErrorMessage("Data final inferior a data incial, selecione novas datas");
			}						
			
		} catch (Exception e) {
			System.out.println("Erro...:"+e.getMessage());
		}
		
	}
	
	public void setorPublico(){
		
		try {
			
			Map<String, Object> parametros = new HashMap<>();
			List<SetorPublicoDTO> lista = new ArrayList<SetorPublicoDTO>();
			SetorPublicoDTO setorPublicoDTO = new SetorPublicoDTO();
			
			if(dataFim.after(dataIni) || dataFim.equals(dataIni)){
				
				AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
				
				Long totalJudiciario = atendimentoDAO.totalAutoridadeJudiciaria(dataIni, dataFim);
				Long totalMinisterioPublico = atendimentoDAO.totalPromotoria(dataIni, dataFim);
				Long totalSecretariaEducacao = atendimentoDAO.totalEducacao(dataIni, dataFim);
				Long totalSecretariaSaude = atendimentoDAO.totalSaude(dataIni, dataFim);
				Long totalAssistenciaPromocao = atendimentoDAO.totalPromocaoSocial(dataIni, dataFim);
				Long totalSegurancaPublica = atendimentoDAO.totalAutoridadePolicial(dataIni, dataFim);
				
				setorPublicoDTO.setQuantJudiciario(totalJudiciario.toString());
				setorPublicoDTO.setPorcJudiciario(calculaPorcentagem(totalJudiciario          +
											                         totalMinisterioPublico   +
											                         totalSecretariaEducacao  +
											                         totalSecretariaSaude     +
											                         totalAssistenciaPromocao +
											                         totalSegurancaPublica    , totalJudiciario  ));
				
				setorPublicoDTO.setQuantMinisterioPublico(totalMinisterioPublico.toString());
				setorPublicoDTO.setPorcMinisterioPublico(calculaPorcentagem(totalJudiciario          +
													                        totalMinisterioPublico   +
													                        totalSecretariaEducacao  +
													                        totalSecretariaSaude     +
													                        totalAssistenciaPromocao +
													                        totalSegurancaPublica    , totalMinisterioPublico));
				
				setorPublicoDTO.setQuantSecretariaEducacao(totalSecretariaEducacao.toString());
				setorPublicoDTO.setPorcSecretariaEducacao(calculaPorcentagem(totalJudiciario          +
													                         totalMinisterioPublico   +
													                         totalSecretariaEducacao  +
													                         totalSecretariaSaude     +
													                         totalAssistenciaPromocao +
													                         totalSegurancaPublica    , totalSecretariaEducacao));
				
				setorPublicoDTO.setQuantSecretariaSaude(totalSecretariaSaude.toString());
				setorPublicoDTO.setPorcSecretariaSaude(calculaPorcentagem(totalJudiciario          +
													                      totalMinisterioPublico   +
													                      totalSecretariaEducacao  +
													                      totalSecretariaSaude     +
													                      totalAssistenciaPromocao +
													                      totalSegurancaPublica    , totalSecretariaSaude));
				
				setorPublicoDTO.setQuantSecretariaAssistenciaPromocao(totalAssistenciaPromocao.toString());
				setorPublicoDTO.setPorcSecretariaAssistenciaPromocao(calculaPorcentagem(totalJudiciario          +
																	                    totalMinisterioPublico   +
																	                    totalSecretariaEducacao  +
																	                    totalSecretariaSaude     +
																	                    totalAssistenciaPromocao +
																	                    totalSegurancaPublica    , totalAssistenciaPromocao));
				
				setorPublicoDTO.setQuantSegurancaPublica(totalSegurancaPublica.toString());
				setorPublicoDTO.setPorcSegurancaPublica(calculaPorcentagem(totalJudiciario          +
													                       totalMinisterioPublico   +
													                       totalSecretariaEducacao  +
													                       totalSecretariaSaude     +
													                       totalAssistenciaPromocao +
													                       totalSegurancaPublica    , totalSegurancaPublica));
				
				setorPublicoDTO.setDataInicio(Helper.formatDate().format(dataIni));
				setorPublicoDTO.setDataFim(Helper.formatDate().format(dataFim));
				
				lista.add(setorPublicoDTO);			
				String caminho = "/WEB-INF/relatorios/setor_publico.jasper";

				ReportUtil.executarRelatorio(caminho, parametros, "Consulta-Setor-Público", lista);
				resetaData();
			}else{
				JSFUtil.addErrorMessage("Data final inferior a data incial, selecione novas datas");
			}						
			
		} catch (Exception e) {
			System.out.println("Erro...:"+e.getMessage());
		}
		
	}
	
	
	private Double calculaPorcentagem(Long total, Long quantidade){
				
		Double totalDle = Double.parseDouble(total.toString());
		Double quantidadeDle = Double.parseDouble(quantidade.toString());				
		
		return (quantidadeDle*100)/totalDle	;	
	}
	
	private void verificaParentescoVitimaViolador(Date inicio, Date fim) throws Exception{
		
		int daFamilia =  0;
		int terceiro =  0;
		
		try {
			List<Atendimento> listaAtendimentos = new AtendimentoDAO().buscaAtendimentosPorData(inicio, fim);
			MembroDao membroDao = new MembroDao();
			for (Atendimento atendimento : listaAtendimentos) {
				List<Violador> listaVioladores = atendimento.getListaAgenteViolador();
				List<Vitima> listaVitimas = atendimento.getListaVitimas();
				
				for (Vitima vitima : listaVitimas) {
					for (Violador violador : listaVioladores) {
						Familia familiaViolador = verificaFamiliaViolador(violador, membroDao);
						if(familiaViolador != null){
							if(familiaViolador.equals(vitima.getMembro().getFamilia())){
								daFamilia++;
							}else{
								terceiro++;
							}
						}else{
							terceiro++;
						}
					}
				}				
			}
			
			System.out.println("Da Familia: "+ daFamilia+" Terceiros: "+terceiro);
			
		} catch (Exception e) {
			System.out.println("ERRO:.."+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private Familia verificaFamiliaViolador(Violador violador, MembroDao membroDao) throws Exception{
		
		try {
			Membro membro = membroDao.buscaMembro(violador.getPessoa());
			
			if(membro.getFamilia() != null){
				return membro.getFamilia();
			}else{
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
			
		}
		
	}
	
	public void relatorioDireitosViolados(){
		
		RegistroDireitoVioladoDAO registroDireitoVioladoDAO = new RegistroDireitoVioladoDAO();
		
		try {
			
			Map<String, Object> parametros = new HashMap<>();			
			List<DireitoViolado> listaDireitoVioladoUtilizados = new ArrayList<DireitoViolado>();
			listaDireitoVioladoUtilizados = registroDireitoVioladoDAO.buscaDireitoVioladoUtilizados(dataIni, dataFim);								
			List<RegistroDireitoViolado> listaTodosRegistroDireitoViolados = registroDireitoVioladoDAO.buscaTodosRegistroDireitoViolado(dataIni, dataFim);
			List<RegistroDiretitoVioladoDTO> listaDiretitoVioladoDTO = new ArrayList<RegistroDiretitoVioladoDTO>();						
			
			if(listaDireitoVioladoUtilizados.isEmpty()){
				listaDiretitoVioladoDTO.add(new RegistroDiretitoVioladoDTO("Nenhum registro encontrado no período selecionado"));
			}else{
				RegistroDiretitoVioladoDTO registroDiretitoVioladoDTO = new RegistroDiretitoVioladoDTO();
				int cont = 0;
				for (DireitoViolado direitoViolado : listaDireitoVioladoUtilizados) {				
					registroDiretitoVioladoDTO = new RegistroDiretitoVioladoDTO();
					for (RegistroDireitoViolado registroDireitoViolado : listaTodosRegistroDireitoViolados) {
						if(registroDireitoViolado.getDireitoViolado().equals(direitoViolado)){
							cont = cont+1;
						}										
					}
					registroDiretitoVioladoDTO.setDireitoViolado(direitoViolado.getNome());
					registroDiretitoVioladoDTO.setQuantidade(String.valueOf(cont));
					registroDiretitoVioladoDTO.setPorcentagem(calculaPorcentagem(new Long(listaTodosRegistroDireitoViolados.size()), new Long(cont)));
					listaDiretitoVioladoDTO.add(registroDiretitoVioladoDTO);
					
					cont = 0;				
				}
			}								
			
			for (RegistroDiretitoVioladoDTO registroDiretitoVioladoDTO2 : listaDiretitoVioladoDTO) {
				System.out.println("Direito Violado: "+registroDiretitoVioladoDTO2.getDireitoViolado());
				System.out.println("Quantidade: "+registroDiretitoVioladoDTO2.getQuantidade());
				System.out.println("Porcentagem: "+registroDiretitoVioladoDTO2.getPorcentagem());
			}
			
			String caminho = "/WEB-INF/relatorios/registro_direito_violado.jasper";
			
			parametros.put("dataInicial", Helper.formatDate().format(dataIni));
			parametros.put("dataFim", Helper.formatDate().format(dataFim));
			
			ReportUtil.executarRelatorio(caminho, parametros, "Registros-Direito-Violado", listaDiretitoVioladoDTO);
			resetaData();
			
		} catch (Exception e) {
			System.out.println("ERRO busca direitos violados: "+e.getMessage());
		}
	}
	
	public void relatorioMedidasAplicadas(){
					
		try {
			RegistroMedidaAplicadaDAO registroMedidaAplicadaDAO = new RegistroMedidaAplicadaDAO();
			List<MedidaAplicada> medidaAplicadaUtilizadas = registroMedidaAplicadaDAO.buscaMedidaAplicadaUtilizados(dataIni, dataFim);
			List<RegistroMedidaAplicada> todasMedidasAplicadasUtilizadas = registroMedidaAplicadaDAO.buscaTodosRegistroMedidaAplicadaUtilizados(dataIni, dataFim);
			List<RegistroMedidaAplicadaDTO> listaMedidaAplicadaDTO = new ArrayList<RegistroMedidaAplicadaDTO>();			
			Map<String, Object> parametros = new HashMap<>();
			
//			for (MedidaAplicada medidaAplicada : medidaAplicadaUtilizadas) {
//				System.out.println("Medida Aplicada Utilizada: "+medidaAplicada.getNome());
//			}
//			
//			for (RegistroMedidaAplicada registroMedidaAplicada : todasMedidasAplicadasUtilizadas) {
//				System.out.println("Todos Medida Aplicada Utilizada: "+registroMedidaAplicada.getMedidaAplicada().getNome());
//			}
			
			if(medidaAplicadaUtilizadas.isEmpty()){
				listaMedidaAplicadaDTO.add(new RegistroMedidaAplicadaDTO("Nenhum registro encontrado no período selecionado"));
			}else{
				RegistroMedidaAplicadaDTO registroMedidaAplicadaDTO = new RegistroMedidaAplicadaDTO();
				int cont = 0;
				for (MedidaAplicada medidaAplicada : medidaAplicadaUtilizadas) {				
					registroMedidaAplicadaDTO = new RegistroMedidaAplicadaDTO();
					for (RegistroMedidaAplicada registroMedidaAplicada : todasMedidasAplicadasUtilizadas) {
						if(registroMedidaAplicada.getMedidaAplicada().equals(medidaAplicada)){
							cont = cont+1;
						}										
					}
					registroMedidaAplicadaDTO.setMedidaAplicada(medidaAplicada.getNome());
					registroMedidaAplicadaDTO.setQuantidade(String.valueOf(cont));
					registroMedidaAplicadaDTO.setPorcentagem(calculaPorcentagem(new Long(todasMedidasAplicadasUtilizadas.size()), new Long(cont)));
					listaMedidaAplicadaDTO.add(registroMedidaAplicadaDTO);
					
					cont = 0;				
				}
			}
			
			for (RegistroMedidaAplicadaDTO registroMedidaAplicadaDTO : listaMedidaAplicadaDTO) {
				System.out.println("Registro Medida Aplicada: medida: "+registroMedidaAplicadaDTO.getMedidaAplicada()+" quantidade: "+registroMedidaAplicadaDTO.getQuantidade()+" porcentagem: "+registroMedidaAplicadaDTO.getPorcentagem());
			}
			
			String caminho = "/WEB-INF/relatorios/registro_medida_aplicada.jasper";
			
			parametros.put("dataInicial", Helper.formatDate().format(dataIni));
			parametros.put("dataFim", Helper.formatDate().format(dataFim));
			
			ReportUtil.executarRelatorio(caminho, parametros, "Registros-Medidas-Aplicadas", listaMedidaAplicadaDTO);
			resetaData();
			
			
		} catch (Exception e) {
			System.out.println("ERRO Relatorio registro de medidas aplicadas: "+e.getMessage());
			e.printStackTrace();
		}
								
	}
	
	public void relatorioDireitoVioladoPorBairro(){
		
		try {											
			
			if(dataIni  == null || dataIni == null){
				JSFUtil.addErrorMessage("Informe o bairro para a busca");
				System.out.println("Data inicio e data fim incorretas");
				return;
			}
			
			if(bairroSelecionado == null){
				JSFUtil.addErrorMessage("Informe o bairro para a busca");
				System.out.println("Data inicio e data fim incorretas");
				return;
			}						
			
			if(dataFim.after(dataIni) || dataFim.equals(dataIni)){
				RegistroDireitoVioladoDAO registroDireitoVioladoDAO = new RegistroDireitoVioladoDAO();
				List<DireitoViolado> direitoVioladoUtilizados = registroDireitoVioladoDAO.buscaDireitoVioladoUtilizadosPorBairro(dataIni, dataFim, bairroSelecionado);
				List<RegistroDireitoViolado> registroDireitoVioladoUtilizados = registroDireitoVioladoDAO.buscaTodosRegistroDireitoVioladoPorBairro(dataIni, dataFim, bairroSelecionado);
				List<RegistroDiretitoVioladoDTO> listaDiretitoVioladoDTO = new ArrayList<RegistroDiretitoVioladoDTO>();
				Map<String, Object> parametros = new HashMap<>();
				RegistroDiretitoVioladoDTO direititoVioladoDTO; 
				
				int cont = 0;
				for (DireitoViolado direitoViolado : direitoVioladoUtilizados) {										
					for (RegistroDireitoViolado registroDireitoViolado : registroDireitoVioladoUtilizados) {
						if(registroDireitoViolado.getDireitoViolado().equals(direitoViolado)){
							cont++;
						}						
					}
					
					direititoVioladoDTO = new RegistroDiretitoVioladoDTO();
					direititoVioladoDTO.setDireitoViolado(direitoViolado.getNome());
					direititoVioladoDTO.setQuantidade(String.valueOf(cont));
					direititoVioladoDTO.setPorcentagem(calculaPorcentagem(new Long(registroDireitoVioladoUtilizados.size()), new Long(cont)));															
					cont = 0;
					
					listaDiretitoVioladoDTO.add(direititoVioladoDTO);
				}
				
				String caminho = "/WEB-INF/relatorios/direito_violado_bairro.jasper";
				
				parametros.put("dataInicial", Helper.formatDate().format(dataIni));
				parametros.put("dataFim", Helper.formatDate().format(dataFim));
				parametros.put("bairroSelecionado", bairroSelecionado.getNome());
				
				ReportUtil.executarRelatorio(caminho, parametros, "Registros-Medidas-Aplicadas", listaDiretitoVioladoDTO);
				
			}else{
				JSFUtil.addErrorMessage("Data inicio e data fim incorretas");
				System.out.println("Data inicio e data fim incorretas");
			}						
			
		} catch (Exception e) {
			System.out.println("ERRO ao executar relatorioDireitoVioladoPorBairro: "+e.getMessage());
		}
		
	}
	
	public void relatorioMedidaAplicadaPorBairro(){
		
		try {			
			RegistroMedidaAplicadaDAO registroMedidaAplicadaDAO = new RegistroMedidaAplicadaDAO();
			
			System.out.println("Bairro Selecionado: "+bairroSelecionado.getNome());
			List<RegistroMedidaAplicada> lista = registroMedidaAplicadaDAO.buscaTodosRegistroMedidaAplicadaPorBairro(dataIni, dataFim, bairroSelecionado);
			for (RegistroMedidaAplicada registroMedidaAplicada : lista) {
				System.out.println("MEDIDA APLICADA: "+registroMedidaAplicada.getMedidaAplicada().getNome());
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void resetaData(){			
		dataIni = null;
		dataFim = null;				
	}
	
	public void buscaCidade(){
		
		try {
			listaCidade = new CidadeDAO().buscaCidade(estadoSelecionado);
			
			if(!listaBairro.isEmpty()){
				listaBairro = new ArrayList<Bairro>();
			}					
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void buscaBairro(){									
					
		try {			
			listaBairro = new BairroDAO().buscaBairro(cidadeSelecionado);						
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
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
	
	
	
}
