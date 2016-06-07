package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.conselho.domain.TipoAtendimento;
import br.com.conselho.domain.ViaSolicitacao;
import br.com.conselho.util.HibernateUtil;

public class ViaSolicitacaoDAO {
	
	public List<ViaSolicitacao> busca() throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<ViaSolicitacao> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(ViaSolicitacao.class);			
			criteria.addOrder(Order.asc("nome"));
			lista =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}

}
