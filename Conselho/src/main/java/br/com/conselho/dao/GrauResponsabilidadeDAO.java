package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.GrauParentesco;
import br.com.conselho.domain.GrauResponsabilidade;
import br.com.conselho.util.HibernateUtil;

public class GrauResponsabilidadeDAO {
	
	public List<GrauResponsabilidade> listaGrauResponsabilidade() throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<GrauResponsabilidade> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(GrauResponsabilidade.class);
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
	
	
	public GrauResponsabilidade listaGrauResponsabilidade(String nome) throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		GrauResponsabilidade gResp = null;
		
		try{
			Criteria criteria = sessao.createCriteria(GrauResponsabilidade.class);
			criteria.add(Restrictions.ilike("nome", nome));			
			gResp = (GrauResponsabilidade) criteria.uniqueResult();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return gResp;
		
	}

}
