package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.conselho.domain.Determinacao;
import br.com.conselho.domain.Estado;
import br.com.conselho.util.HibernateUtil;

public class DeterminacoesDAO {
	
	public List<Determinacao> lista() throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Determinacao> listaDeterminacao = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Determinacao.class);
			listaDeterminacao = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return listaDeterminacao;
		
	}

}
