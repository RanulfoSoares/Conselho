package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.conselho.domain.OrigemAtendimento;
import br.com.conselho.util.HibernateUtil;

public class OrigemAtendimentoDAO {
	
	public List<OrigemAtendimento> busca() throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<OrigemAtendimento> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(OrigemAtendimento.class);			
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
