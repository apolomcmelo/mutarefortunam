package br.com.apolo.mc.melo.dao;

import javax.persistence.EntityManager;

import br.com.apolo.mc.melo.entidades.AnaliseEstatistica;

public class AnaliseEstatisticaDAO extends GenericDAO<Integer, AnaliseEstatistica> {
    public AnaliseEstatisticaDAO(EntityManager entityManager) {
        super(entityManager);
    }
}