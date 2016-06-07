package br.com.conselho.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import br.com.conselho.domain.Atendimento;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Responsavel;
import br.com.conselho.domain.Violador;
import br.com.conselho.domain.Vitima;
import br.com.conselho.util.HibernateUtil;

public class AtendimentoDAO {

	public void salvar(Atendimento atendimento) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(atendimento);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERRO ao salvar responsavel..." + e.getMessage());
			throw new Exception(e);
		} finally {
			sessao.close();
		}
	}
	
	public List<Atendimento> buscaAtendimentos(Familia familia) throws Exception{
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Atendimento> lista;
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Vitima.class);
		subCriteria.createAlias("membro", "m");
		subCriteria.createAlias("m.familia", "f");
		subCriteria.add(Restrictions.eq("f.id", familia.getId()));
		subCriteria.setProjection(Projections.property("atendimento.id"));		
		
		Criteria criteria = sessao.createCriteria(Atendimento.class);
		criteria.add(Subqueries.propertyIn("id", subCriteria));
		criteria.addOrder(Order.desc("dataFato"));
		lista = criteria.list();
		
		return lista;
				
	}
	
	public Atendimento buscaPorCodigo(Long id) throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria criteria = sessao.createCriteria(Atendimento.class);			
			criteria.add(Restrictions.idEq(id));
			return (Atendimento) criteria.uniqueResult();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			sessao.close();
		}
		
	}
	
	public List<Atendimento> buscaAtendimentosPorData(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			List<Atendimento> lista;					
			
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));		
			lista = criteria.list();		
			return lista;
				
	}
	
	public Long totalPlantao(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("ViaSolitacao", "v");
			criteria.add(Restrictions.eq("v.nome", "Plantão"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
    public Long totalPorEmail(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("ViaSolitacao", "v");
			criteria.add(Restrictions.eq("v.nome", "Por e-mail"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
	public Long totalPorEscrito(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("ViaSolitacao", "v");
			criteria.add(Restrictions.eq("v.nome", "Por escrito"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
	public Long totalPorSite(Date inicio, Date fim) throws Exception{
					
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("ViaSolitacao", "v");
			criteria.add(Restrictions.eq("v.nome", "Por site"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
	public Long totalPorTelefone(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("ViaSolitacao", "v");
			criteria.add(Restrictions.eq("v.nome", "Por telefone"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
	public Long totalSede(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("ViaSolitacao", "v");
			criteria.add(Restrictions.eq("v.nome", "Sede"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
	public Long totalAcompanhamento(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("TipoAtendimento", "t");
			criteria.add(Restrictions.eq("t.nome", "Acompanhamento"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
	public Long totalDireito(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("TipoAtendimento", "t");
			criteria.add(Restrictions.eq("t.nome", "Direito"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
	public Long totalEncaminhamento(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("TipoAtendimento", "t");
			criteria.add(Restrictions.eq("t.nome", "Encaminhamento"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
	public Long totalOrientacao(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("TipoAtendimento", "t");
			criteria.add(Restrictions.eq("t.nome", "Orientação"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
	public Long totalDenuncia(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("TipoAtendimento", "t");
			criteria.add(Restrictions.eq("t.nome", "Denuncia"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
				
	}
	
     public Long totalPropriaCrianca(Date inicio, Date fim) throws Exception{
				
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("OrigemAtendiemnto", "o");
			criteria.add(Restrictions.eq("o.nome", "A própria criança/adolescente"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
	
    }
     
     public Long totalAnonima(Date inicio, Date fim) throws Exception{
 		 		
	 		Session sessao = HibernateUtil.getSessionFactory().openSession();
	 		Criteria criteria = sessao.createCriteria(Atendimento.class);
	 		criteria.add(Restrictions.between("dataFato", inicio, fim));
	 		criteria.createAlias("OrigemAtendiemnto", "o");
	 		criteria.add(Restrictions.eq("o.nome", "Anônima"));
	 		criteria.setProjection(Projections.rowCount());
	 		Long result = (Long) criteria.uniqueResult();
	 		return result;
 	
     }
     
     
     public Long totalAutoridadeJudiciaria(Date inicio, Date fim) throws Exception{
 		 		
	 		Session sessao = HibernateUtil.getSessionFactory().openSession();
	 		Criteria criteria = sessao.createCriteria(Atendimento.class);
	 		criteria.add(Restrictions.between("dataFato", inicio, fim));
	 		criteria.createAlias("OrigemAtendiemnto", "o");
	 		criteria.add(Restrictions.eq("o.nome", "Autoridade judiciária"));
	 		criteria.setProjection(Projections.rowCount());
	 		Long result = (Long) criteria.uniqueResult();
	 		return result;
	 	
     }          
     
     public Long totalAutoridadePolicial(Date inicio, Date fim) throws Exception{
  		  		
	  		Session sessao = HibernateUtil.getSessionFactory().openSession();
	  		Criteria criteria = sessao.createCriteria(Atendimento.class);
	  		criteria.add(Restrictions.between("dataFato", inicio, fim));
	  		criteria.createAlias("OrigemAtendiemnto", "o");
	  		criteria.add(Restrictions.eq("o.nome", "Autoridade policial"));
	  		criteria.setProjection(Projections.rowCount());
	  		Long result = (Long) criteria.uniqueResult();
	  		return result;
  	
      }
     
     public Long totalConselhoTutelar(Date inicio, Date fim) throws Exception{
   		   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("OrigemAtendiemnto", "o");
	   		criteria.add(Restrictions.eq("o.nome", "Conselho tutelar"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;
   	
       }
     
     public Long totalDireitosHumano(Date inicio, Date fim) throws Exception{
    	    		
    		Session sessao = HibernateUtil.getSessionFactory().openSession();
    		Criteria criteria = sessao.createCriteria(Atendimento.class);
    		criteria.add(Restrictions.between("dataFato", inicio, fim));
    		criteria.createAlias("OrigemAtendiemnto", "o");
    		criteria.add(Restrictions.eq("o.nome", "Direitos humano(disque 100)"));
    		criteria.setProjection(Projections.rowCount());
    		Long result = (Long) criteria.uniqueResult();
    		return result;
    	
        }
     
     public Long totalEducacao(Date inicio, Date fim) throws Exception{
 		 		
	 		Session sessao = HibernateUtil.getSessionFactory().openSession();
	 		Criteria criteria = sessao.createCriteria(Atendimento.class);
	 		criteria.add(Restrictions.between("dataFato", inicio, fim));
	 		criteria.createAlias("OrigemAtendiemnto", "o");
	 		criteria.add(Restrictions.eq("o.nome", "Educação"));
	 		criteria.setProjection(Projections.rowCount());
	 		Long result = (Long) criteria.uniqueResult();
	 		return result;
 	
     }
     
     public Long totalOutroMembroFamilia(Date inicio, Date fim) throws Exception{
  		  		
	  		Session sessao = HibernateUtil.getSessionFactory().openSession();
	  		Criteria criteria = sessao.createCriteria(Atendimento.class);
	  		criteria.add(Restrictions.between("dataFato", inicio, fim));
	  		criteria.createAlias("OrigemAtendiemnto", "o");
	  		criteria.add(Restrictions.eq("o.nome", "Outro membro da família"));
	  		criteria.setProjection(Projections.rowCount());
	  		Long result = (Long) criteria.uniqueResult();
	  		return result;
  	
      }
     
     public Long totalPaisResponsavel(Date inicio, Date fim) throws Exception{
   		   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("OrigemAtendiemnto", "o");
	   		criteria.add(Restrictions.eq("o.nome", "Pais/responsável"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;
   	
       }
     
     public Long totalParente(Date inicio, Date fim) throws Exception{
    		    		
    		Session sessao = HibernateUtil.getSessionFactory().openSession();
    		Criteria criteria = sessao.createCriteria(Atendimento.class);
    		criteria.add(Restrictions.between("dataFato", inicio, fim));
    		criteria.createAlias("OrigemAtendiemnto", "o");
    		criteria.add(Restrictions.eq("o.nome", "Parente"));
    		criteria.setProjection(Projections.rowCount());
    		Long result = (Long) criteria.uniqueResult();
    		return result;
    	
        }
     
     public Long totalPromocaoSocial(Date inicio, Date fim) throws Exception{
 		 		
	 		Session sessao = HibernateUtil.getSessionFactory().openSession();
	 		Criteria criteria = sessao.createCriteria(Atendimento.class);
	 		criteria.add(Restrictions.between("dataFato", inicio, fim));
	 		criteria.createAlias("OrigemAtendiemnto", "o");
	 		criteria.add(Restrictions.eq("o.nome", "Assistência e Promoção social"));
	 		criteria.setProjection(Projections.rowCount());
	 		Long result = (Long) criteria.uniqueResult();
	 		return result;
 	
     }
     
     public Long totalPromotoria(Date inicio, Date fim) throws Exception{
  		  		
	  		Session sessao = HibernateUtil.getSessionFactory().openSession();
	  		Criteria criteria = sessao.createCriteria(Atendimento.class);
	  		criteria.add(Restrictions.between("dataFato", inicio, fim));
	  		criteria.createAlias("OrigemAtendiemnto", "o");
	  		criteria.add(Restrictions.eq("o.nome", "Promotoria"));
	  		criteria.setProjection(Projections.rowCount());
	  		Long result = (Long) criteria.uniqueResult();
	  		return result;
  	
      }
     
     public Long totalSaude(Date inicio, Date fim) throws Exception{
   		   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("OrigemAtendiemnto", "o");
	   		criteria.add(Restrictions.eq("o.nome", "Saúde"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;
   	
       }
     
     public Long totalTerceiro(Date inicio, Date fim) throws Exception{
    		    		
    		Session sessao = HibernateUtil.getSessionFactory().openSession();
    		Criteria criteria = sessao.createCriteria(Atendimento.class);
    		criteria.add(Restrictions.between("dataFato", inicio, fim));
    		criteria.createAlias("OrigemAtendiemnto", "o");
    		criteria.add(Restrictions.eq("o.nome", "Terceiro"));
    		criteria.setProjection(Projections.rowCount());
    		Long result = (Long) criteria.uniqueResult();
    		return result;
    	
        }
     
     public Long totalVizinha(Date inicio, Date fim) throws Exception{
 		 		
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			Criteria criteria = sessao.createCriteria(Atendimento.class);
			criteria.add(Restrictions.between("dataFato", inicio, fim));
			criteria.createAlias("OrigemAtendiemnto", "o");
			criteria.add(Restrictions.eq("o.nome", "Vizinha(o)"));
			criteria.setProjection(Projections.rowCount());
			Long result = (Long) criteria.uniqueResult();
			return result;
 	
     }
     
     public Long totalAldeia(Date inicio, Date fim) throws Exception{
	 		
	  		Session sessao = HibernateUtil.getSessionFactory().openSession();
	  		Criteria criteria = sessao.createCriteria(Atendimento.class);
	  		criteria.add(Restrictions.between("dataFato", inicio, fim));
	  		criteria.createAlias("tipoLocalFato", "l");
	  		criteria.add(Restrictions.eq("l.nome", "Aldeia"));
	  		criteria.setProjection(Projections.rowCount());
	  		Long result = (Long) criteria.uniqueResult();
	  		return result;
      }
     

     public Long totalAmbienteFamiliar(Date inicio, Date fim) throws Exception{  		
  		
	  		Session sessao = HibernateUtil.getSessionFactory().openSession();
	  		Criteria criteria = sessao.createCriteria(Atendimento.class);
	  		criteria.add(Restrictions.between("dataFato", inicio, fim));
	  		criteria.createAlias("tipoLocalFato", "l");
	  		criteria.add(Restrictions.eq("l.nome", "Ambiente familiar - Residência"));
	  		criteria.setProjection(Projections.rowCount());
	  		Long result = (Long) criteria.uniqueResult();
	  		return result;  		  	
      }
     
     public Long totalAmbienteShow(Date inicio, Date fim) throws Exception{  		
   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("tipoLocalFato", "l");
	   		criteria.add(Restrictions.eq("l.nome", "Ambiente de show"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;   
       }
	
     public Long totalBarSimilar(Date inicio, Date fim) throws Exception{
   		   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("tipoLocalFato", "l");
	   		criteria.add(Restrictions.eq("l.nome", "Bar e similar"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;   	
       }    
     
     public Long totalClubeLazer(Date inicio, Date fim) throws Exception{    		
    		
    		Session sessao = HibernateUtil.getSessionFactory().openSession();
    		Criteria criteria = sessao.createCriteria(Atendimento.class);
    		criteria.add(Restrictions.between("dataFato", inicio, fim));
    		criteria.createAlias("tipoLocalFato", "l");
    		criteria.add(Restrictions.eq("l.nome", "Clube de lazer"));
    		criteria.setProjection(Projections.rowCount());
    		Long result = (Long) criteria.uniqueResult();
    		return result;    	
        }
     
     public Long totalCreche(Date inicio, Date fim) throws Exception{
 		 		
	 		Session sessao = HibernateUtil.getSessionFactory().openSession();
	 		Criteria criteria = sessao.createCriteria(Atendimento.class);
	 		criteria.add(Restrictions.between("dataFato", inicio, fim));
	 		criteria.createAlias("tipoLocalFato", "l");
	 		criteria.add(Restrictions.eq("l.nome", "Creche"));
	 		criteria.setProjection(Projections.rowCount());
	 		Long result = (Long) criteria.uniqueResult();
	 		return result;
 	
     }
     
     public Long totalEmpresa(Date inicio, Date fim) throws Exception{  		
  		
	  		Session sessao = HibernateUtil.getSessionFactory().openSession();
	  		Criteria criteria = sessao.createCriteria(Atendimento.class);
	  		criteria.add(Restrictions.between("dataFato", inicio, fim));
	  		criteria.createAlias("tipoLocalFato", "l");
	  		criteria.add(Restrictions.eq("l.nome", "Empresa"));
	  		criteria.setProjection(Projections.rowCount());
	  		Long result = (Long) criteria.uniqueResult();
	  		return result;  	
      }
     
     public Long totalEscola(Date inicio, Date fim) throws Exception{
	   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("tipoLocalFato", "l");
	   		criteria.add(Restrictions.eq("l.nome", "Escola"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;   	
     }
     
     public Long totalEstabelecimentoComercial(Date inicio, Date fim) throws Exception{
   		   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("tipoLocalFato", "l");
	   		criteria.add(Restrictions.eq("l.nome", "Estabelecimento comercial"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;   	
       }
     
     public Long totalHospital(Date inicio, Date fim) throws Exception{
    		    		
    		Session sessao = HibernateUtil.getSessionFactory().openSession();
    		Criteria criteria = sessao.createCriteria(Atendimento.class);
    		criteria.add(Restrictions.between("dataFato", inicio, fim));
    		criteria.createAlias("tipoLocalFato", "l");
    		criteria.add(Restrictions.eq("l.nome", "Hospital"));
    		criteria.setProjection(Projections.rowCount());
    		Long result = (Long) criteria.uniqueResult();
    		return result;    	       
     }
     
     public Long totalImovelAbandonado(Date inicio, Date fim) throws Exception{ 		
 		
	 		Session sessao = HibernateUtil.getSessionFactory().openSession();
	 		Criteria criteria = sessao.createCriteria(Atendimento.class);
	 		criteria.add(Restrictions.between("dataFato", inicio, fim));
	 		criteria.createAlias("tipoLocalFato", "l");
	 		criteria.add(Restrictions.eq("l.nome", "Imóvel abandonado"));
	 		criteria.setProjection(Projections.rowCount());
	 		Long result = (Long) criteria.uniqueResult();
	 		return result; 	
     }
     
     public Long totalMeioRural(Date inicio, Date fim) throws Exception{
  		  		
	  		Session sessao = HibernateUtil.getSessionFactory().openSession();
	  		Criteria criteria = sessao.createCriteria(Atendimento.class);
	  		criteria.add(Restrictions.between("dataFato", inicio, fim));
	  		criteria.createAlias("tipoLocalFato", "l");
	  		criteria.add(Restrictions.eq("l.nome", "Meio rural"));
	  		criteria.setProjection(Projections.rowCount());
	  		Long result = (Long) criteria.uniqueResult();
	  		return result;  	
      }
     
     public Long totalPostoSaude(Date inicio, Date fim) throws Exception{
   		   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("tipoLocalFato", "l");
	   		criteria.add(Restrictions.eq("l.nome", "Posto de saúde"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;   	
       }
     
     public Long totalPostoPolicial(Date inicio, Date fim) throws Exception{
    		    		
    		Session sessao = HibernateUtil.getSessionFactory().openSession();
    		Criteria criteria = sessao.createCriteria(Atendimento.class);
    		criteria.add(Restrictions.between("dataFato", inicio, fim));
    		criteria.createAlias("tipoLocalFato", "l");
    		criteria.add(Restrictions.eq("l.nome", "Posto policial"));
    		criteria.setProjection(Projections.rowCount());
    		Long result = (Long) criteria.uniqueResult();
    		return result;    	
      }
     
     public Long totalPracaEsporte(Date inicio, Date fim) throws Exception{  		
  		
	  		Session sessao = HibernateUtil.getSessionFactory().openSession();
	  		Criteria criteria = sessao.createCriteria(Atendimento.class);
	  		criteria.add(Restrictions.between("dataFato", inicio, fim));
	  		criteria.createAlias("tipoLocalFato", "l");
	  		criteria.add(Restrictions.eq("l.nome", "Praça de esporte"));
	  		criteria.setProjection(Projections.rowCount());
	  		Long result = (Long) criteria.uniqueResult();
	  		return result;  	
      } 
     
     public Long totalPracaLazer(Date inicio, Date fim) throws Exception{
	  		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("tipoLocalFato", "l");
	   		criteria.add(Restrictions.eq("l.nome", "Praça de lazer"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;  	
     }                 
     
     public Long totalPracaPublica(Date inicio, Date fim) throws Exception{   		
   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("tipoLocalFato", "l");
	   		criteria.add(Restrictions.eq("l.nome", "Praça pública"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;   	
       }
     
     public Long totalProntoSocorro(Date inicio, Date fim) throws Exception{
    		    		
    		Session sessao = HibernateUtil.getSessionFactory().openSession();
    		Criteria criteria = sessao.createCriteria(Atendimento.class);
    		criteria.add(Restrictions.between("dataFato", inicio, fim));
    		criteria.createAlias("tipoLocalFato", "l");
    		criteria.add(Restrictions.eq("l.nome", "Pronto socorro"));
    		criteria.setProjection(Projections.rowCount());
    		Long result = (Long) criteria.uniqueResult();
    		return result;    	
       }
     
     public Long totalReparticaoPublica(Date inicio, Date fim) throws Exception{ 		
 		
	 		Session sessao = HibernateUtil.getSessionFactory().openSession();
	 		Criteria criteria = sessao.createCriteria(Atendimento.class);
	 		criteria.add(Restrictions.between("dataFato", inicio, fim));
	 		criteria.createAlias("tipoLocalFato", "l");
	 		criteria.add(Restrictions.eq("l.nome", "Repartição pública"));
	 		criteria.setProjection(Projections.rowCount());
	 		Long result = (Long) criteria.uniqueResult();
	 		return result; 	
     }
     
     public Long totalRioAcudeSimilar(Date inicio, Date fim) throws Exception{
  		  		
	  		Session sessao = HibernateUtil.getSessionFactory().openSession();
	  		Criteria criteria = sessao.createCriteria(Atendimento.class);
	  		criteria.add(Restrictions.between("dataFato", inicio, fim));
	  		criteria.createAlias("tipoLocalFato", "l");
	  		criteria.add(Restrictions.eq("l.nome", "Rio, açude e similar"));
	  		criteria.setProjection(Projections.rowCount());
	  		Long result = (Long) criteria.uniqueResult();
	  		return result;  	
      }
     
     public Long totalTrabalho(Date inicio, Date fim) throws Exception{
   		   		
	   		Session sessao = HibernateUtil.getSessionFactory().openSession();
	   		Criteria criteria = sessao.createCriteria(Atendimento.class);
	   		criteria.add(Restrictions.between("dataFato", inicio, fim));
	   		criteria.createAlias("tipoLocalFato", "l");
	   		criteria.add(Restrictions.eq("l.nome", "Trabalho"));
	   		criteria.setProjection(Projections.rowCount());
	   		Long result = (Long) criteria.uniqueResult();
	   		return result;   	
      }
     
     public Long totalViaPublica(Date inicio, Date fim) throws Exception{
    		    		
    		Session sessao = HibernateUtil.getSessionFactory().openSession();
    		Criteria criteria = sessao.createCriteria(Atendimento.class);
    		criteria.add(Restrictions.between("dataFato", inicio, fim));
    		criteria.createAlias("tipoLocalFato", "l");
    		criteria.add(Restrictions.eq("l.nome", "Via pública"));
    		criteria.setProjection(Projections.rowCount());
    		Long result = (Long) criteria.uniqueResult();
    		return result;    	
      }
     
     public Long totalComFundamento(Date inicio, Date fim) throws Exception{
 		
 		Session sessao = HibernateUtil.getSessionFactory().openSession();
 		Criteria criteria = sessao.createCriteria(Atendimento.class);
 		criteria.add(Restrictions.between("dataFato", inicio, fim)); 		
 		criteria.add(Restrictions.eq("fundamentoDenuncia", "S"));
 		criteria.setProjection(Projections.rowCount());
 		Long result = (Long) criteria.uniqueResult();
 		return result;    	
   }
     
     public Long totalSemFundamento(Date inicio, Date fim) throws Exception{
  		
  		Session sessao = HibernateUtil.getSessionFactory().openSession();
  		Criteria criteria = sessao.createCriteria(Atendimento.class);
  		criteria.add(Restrictions.between("dataFato", inicio, fim)); 		
  		criteria.add(Restrictions.eq("fundamentoDenuncia", "N"));
  		criteria.setProjection(Projections.rowCount());
  		Long result = (Long) criteria.uniqueResult();
  		return result;    	
    }
                    
     public Long totalAtendimentoVioladorNaoIdentificado(Date inicio, Date fim) throws Exception{
 		try {
 			
 			Session sessao = HibernateUtil.getSessionFactory().openSession();
 			Criteria criteria = sessao.createCriteria(Atendimento.class); 			
 			criteria.add(Restrictions.eq("violadorNaoIdentificado", true));
 			criteria.add(Restrictions.between("dataFato", inicio, fim));			
 			criteria.setProjection(Projections.rowCount());
 			Long result = (Long) criteria.uniqueResult();
 			return result;
 			
 		} catch (Exception e) {
 			System.out.println("ERRO...: "+e.getMessage());
 			throw new Exception(e.getMessage());
 		}				
 	} 
     
     
	public static void main(String[] args) {
		
		try {
			
			Familia f = new Familia();
			f.setId(9L);
			AtendimentoDAO dao = new AtendimentoDAO();		
			List<Atendimento> lista = dao.buscaAtendimentos(f);
			
			for (Atendimento atendimento : lista) {
				System.out.println("Atendimento: "+atendimento.getDescricaoAtendimento());
			}
			
		} catch (Exception e) {
			System.out.println("Erro: "+e.getMessage());
		}				
		
	}
	
}
