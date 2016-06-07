package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import br.com.conselho.domain.TipoAtendimento;
import br.com.conselho.util.HibernateUtil;

public class TipoAtendimentoDAO {
	
	public List<TipoAtendimento> busca() throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<TipoAtendimento> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(TipoAtendimento.class);			
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
