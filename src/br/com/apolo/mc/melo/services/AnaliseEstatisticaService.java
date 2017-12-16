package br.com.apolo.mc.melo.services;

import java.util.List;

import br.com.apolo.mc.melo.dao.AnaliseEstatisticaDAO;
import br.com.apolo.mc.melo.entidades.AnaliseEstatistica;
import br.com.apolo.mc.melo.enums.TipoEstatisticaEnum;
import br.com.apolo.mc.melo.managers.SimpleEntityManager;

public class AnaliseEstatisticaService {
    private AnaliseEstatisticaDAO analiseEstatisticaDAO;
    private SimpleEntityManager simpleEntityManager;
     
    public AnaliseEstatisticaService() {
    	this.simpleEntityManager = new SimpleEntityManager();
    	analiseEstatisticaDAO = new AnaliseEstatisticaDAO(simpleEntityManager.getEntityManager());
    }
    
    public AnaliseEstatisticaService(SimpleEntityManager simpleEntityManager){
        this.simpleEntityManager = simpleEntityManager;
        analiseEstatisticaDAO = new AnaliseEstatisticaDAO(simpleEntityManager.getEntityManager());
    }
     
    public void salvar(AnaliseEstatistica AnaliseEstatistica){
        try{
            simpleEntityManager.beginTransaction();
            analiseEstatisticaDAO.salvar(AnaliseEstatistica);
            simpleEntityManager.commit();
        }catch(Exception e){
            e.printStackTrace();
            simpleEntityManager.rollBack();
        }finally {
//        	simpleEntityManager.close();
        }
    }
    
    public void salvarTodos(List<AnaliseEstatistica> analisesEstatisticas){
    	try{
    		simpleEntityManager.beginTransaction();

    		for(int i=0; i<analisesEstatisticas.size(); i++) {
    			analiseEstatisticaDAO.salvar(analisesEstatisticas.get(i));
    			if(i%4 ==0){
    				simpleEntityManager.getEntityManager().flush();
    			}
    		}
    		
    		simpleEntityManager.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		simpleEntityManager.rollBack();
    	}finally {
    		simpleEntityManager.close();
    	}
    }
    
    public List<AnaliseEstatistica> listar(){
        return analiseEstatisticaDAO.listar();
    }
    
    @SuppressWarnings("unchecked")
	public List<AnaliseEstatistica> listarPorTipoEIntervalo(TipoEstatisticaEnum tipoEstatistica, int intervalo) {
		return simpleEntityManager.getEntityManager()
				.createQuery(
						(" FROM AnaliseEstatistica WHERE tipo = " + tipoEstatistica.getCodigo() +
								" AND intervalo = " + intervalo + 
								" ORDER BY numero_ultimo_concurso")
							).getResultList();
    }
}