package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.AgenteVioladorClasse;
import br.com.conselho.domain.AgenteVioladorSubClasse;
import br.com.conselho.util.HibernateUtil;

public class AgenteVioladorSubClasseDAO {
	
	public List<AgenteVioladorSubClasse> busca(AgenteVioladorClasse agenteVioladorClasse) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<AgenteVioladorSubClasse> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(AgenteVioladorSubClasse.class);			
			criteria.add(Restrictions.eq("agenteVioladorClasse", agenteVioladorClasse));			
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
