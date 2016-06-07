package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.conselho.domain.Estado;
import br.com.conselho.util.HibernateUtil;

public class EstadoDAO {
	
	public List<Estado> listaEstados() throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Estado> listaEstado = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Estado.class);
			listaEstado = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return listaEstado;
		
	}

}
