package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.conselho.domain.GrauParentesco;
import br.com.conselho.util.HibernateUtil;

public class SituacaoMatrimonialDAO {
	
	public List<SituacaoMatrimonialDAO> listaSituacaoMatrimoniais() throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<SituacaoMatrimonialDAO> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(SituacaoMatrimonialDAO.class);
			lista = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}

	public Object listaSituacaoMatrimonial() {
		// TODO Auto-generated method stub
		return null;
	}

}
