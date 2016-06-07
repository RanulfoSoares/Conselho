package br.com.conselho.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.conselho.domain.Orgao;
import br.com.conselho.util.HibernateUtil;

public class OrgaoDAO {
	
	public List<Orgao> busca() throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Orgao> lista = new ArrayList<Orgao>();;
		
		try{
			Criteria criteria = sessao.createCriteria(Orgao.class);			
			criteria.addOrder(Order.asc("nome"));
			lista =  criteria.list();				
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}

}
