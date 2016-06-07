package br.com.conselho.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.conselho.domain.AgenteVioladorClasse;
import br.com.conselho.util.HibernateUtil;

public class AgenteVioladorClasseDAO {
	
	public List<AgenteVioladorClasse> busca() throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<AgenteVioladorClasse> lista = new ArrayList<AgenteVioladorClasse>();;
		
		try{
			Criteria criteria = sessao.createCriteria(AgenteVioladorClasse.class);			
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
