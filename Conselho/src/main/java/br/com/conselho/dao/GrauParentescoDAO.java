package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.conselho.domain.GrauParentesco;
import br.com.conselho.util.HibernateUtil;

public class GrauParentescoDAO {
	
	public List<GrauParentesco> listaGrauParentescos() throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<GrauParentesco> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(GrauParentesco.class);
			criteria.addOrder(Order.asc("nome"));
			lista = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}

	public Object listaGrauResponsabilidade() {
		// TODO Auto-generated method stub
		return null;
	}

}
