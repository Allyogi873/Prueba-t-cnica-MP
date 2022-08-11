package com.allan.comisaria.service;

import com.allan.comisaria.models.Comisaria;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ComisariaFacade extends AbstractFacade<Comisaria> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private EntityManager em;
    private EntityManagerFactory emf;
    
    
    @Override
    protected EntityManager getEntityManager() {
        if (em == null || em.isOpen() == false) {
            if (emf == null) {
                                                              
                emf = Persistence.createEntityManagerFactory("com.allan_comisaria_war_1.0-SNAPSHOTPU");
            }
            em = emf.createEntityManager();
        }
        return em;
    }
    
    //@Override
    //protected EntityManager getEntityManager() {
    //    return em;
    //}

    public ComisariaFacade() {
        super(Comisaria.class);
    }
    
}
