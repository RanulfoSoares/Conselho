package br.com.conselho.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.io.IOUtils;

import br.com.conselho.dao.AtendimentoDAO;
import br.com.conselho.dto.AtendimentoDTO;
import br.com.conselho.dto.NucleoFamiliarDTO;

public class ReportUtil {
	
	private ServletContext scontext = null;
	
	public ReportUtil(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.responseComplete();
        scontext = (ServletContext) facesContext.getExternalContext().getContext();
	}
	
	public static void executarRelatorio(String caminhoRelatorio, Map<String, Object> parametros, String nomeRelatorio, List<?> dados) {
		InputStream reportStream = null;
		ServletOutputStream servletOutputStream = null;

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

			reportStream = context.getExternalContext().getResourceAsStream(caminhoRelatorio);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename="+ nomeRelatorio);

			servletOutputStream = response.getOutputStream();

			if (parametros == null) {
				parametros = new HashMap<String, Object>();
			}
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parametros, new JRBeanCollectionDataSource(dados));

			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

			servletOutputStream.flush();
			context.responseComplete();
		} catch (JRException | IOException exception) {
			exception.printStackTrace();
		} finally {
			IOUtils.closeQuietly(reportStream);
			IOUtils.closeQuietly(servletOutputStream);
		}
	}
	
	public void executePrincipal(NucleoFamiliarDTO nucleoFamiliarDTO, List<AtendimentoDTO> listaAtendimentos , String nomeArquivo ) throws Exception {
        JasperPrint jasperNucleoFamiliar = null;
        JasperPrint jasperAtendimento = null;        
        List<JasperPrint> listaJasperPrint = new ArrayList<JasperPrint>();        
                
        try {
            
        	jasperNucleoFamiliar = this.getJasperPrintNucleo(nucleoFamiliarDTO);
        	listaJasperPrint.add(jasperNucleoFamiliar);
        	
        	if(!listaAtendimentos.isEmpty()){
        		for (AtendimentoDTO atendimentoDTO : listaAtendimentos) {
					jasperAtendimento = getJasperPrintAtendimento(atendimentoDTO);
					listaJasperPrint.add(jasperAtendimento);
				}
        	}
        	
//        	 if (cartaCorrecao != null && configuracao.getTipoCartaCorrecao() == 1) {                 
//             } else {
//                 jasperNfse = this.getJasperPrintNFSE(nfseSelecionada, scontext, Boolean.FALSE, consultaExterna, idCidade);
//             }
//             
//             if (cartaCorrecao != null && configuracao.getTipoCartaCorrecao() == 1) {
//                 jasperCarta = this.getCartaCorrecao(cartaCorrecao, cidadeEnum.getCodigo(), caminhoImagem, caminhoLogoPrefeitura);
//                 listaJasperPrint.add(jasperCarta);
//             }
        	

            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, listaJasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.exportReport();
            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {

                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                /*
                 * attachment =  para download.
                 * inline =  nova aba.
                 */
                response.setHeader("Content-disposition", "inline; filename=\"" + nomeArquivo.trim() + ".pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private JasperPrint getJasperPrintNucleo(NucleoFamiliarDTO nucleoFamiliarDTO) throws JRException, Exception {
//        NfseRelatorio nfseRelatorio = NFSeHelper(notaFiscal, scontext, isCarta, consultaExterna, idCidade);
        HashMap parNfse = new HashMap();
        //parNfse.put("SUBREPORT_DIR", JSFUtil.getRealPath("/WEB-INF/relatorios/"));
        List<NucleoFamiliarDTO> listaNucleoRelatorio = new ArrayList<NucleoFamiliarDTO>();
        listaNucleoRelatorio.add(nucleoFamiliarDTO);
        JRBeanCollectionDataSource jRBeanCollectionDataSourceNfse = new JRBeanCollectionDataSource(listaNucleoRelatorio);
        JasperPrint jasperPrintNfse = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/relatorios/NucleoFamiliar.jasper"), parNfse, jRBeanCollectionDataSourceNfse);
        return jasperPrintNfse;
    }
	
	private JasperPrint getJasperPrintAtendimento(AtendimentoDTO atendimentoDTO) throws JRException, Exception {
//      NfseRelatorio nfseRelatorio = NFSeHelper(notaFiscal, scontext, isCarta, consultaExterna, idCidade);
      HashMap parNfse = new HashMap();
      //parNfse.put("SUBREPORT_DIR", JSFUtil.getRealPath("/WEB-INF/relatorios/"));
      List<AtendimentoDTO> listaAtendimentoRelatorio = new ArrayList<AtendimentoDTO>();      
      listaAtendimentoRelatorio.add(atendimentoDTO);
      JRBeanCollectionDataSource jRBeanCollectionDataSourceNfse = new JRBeanCollectionDataSource(listaAtendimentoRelatorio);
      JasperPrint jasperPrintNfse = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/relatorios/Atendimento.jasper"), parNfse, jRBeanCollectionDataSourceNfse);
      return jasperPrintNfse;
  }
    
}
