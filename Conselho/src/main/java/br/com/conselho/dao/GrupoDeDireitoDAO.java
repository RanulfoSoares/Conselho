package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.conselho.domain.GrupoDeDireito;
import br.com.conselho.util.HibernateUtil;

public class GrupoDeDireitoDAO {
	
	public List<GrupoDeDireito> lista() throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<GrupoDeDireito> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(GrupoDeDireito.class);
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
