package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.conselho.domain.TipoInstituicao;
import br.com.conselho.util.HibernateUtil;

public class TipoInstituicaoDAO {
	
public List<TipoInstituicao> lista() throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<TipoInstituicao> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(TipoInstituicao.class);
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

}
