package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.CaracterizacaoViolacaoDireito;
import br.com.conselho.domain.CaracterizarDireitoViolado;
import br.com.conselho.util.HibernateUtil;

public class CaracterizarViolacaoDireitoDAO {
	
	public List<CaracterizarDireitoViolado> lista(CaracterizacaoViolacaoDireito caracterizacaoViolacaoDireito) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<CaracterizarDireitoViolado> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(CaracterizarDireitoViolado.class);
			criteria.add(Restrictions.eq("caracterizacaoViolacaoDireito", caracterizacaoViolacaoDireito));			
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
