/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.allan.comisaria.controler;

//import gt.com.mediprocesos.ep2ejb.domain.AccountBank;
import com.allan.comisaria.models.Comisaria;
import com.allan.comisaria.service.ComisariaFacade;
import java.util.List;
/**
 *
 * @author allan
 */
public class ComisariaControler {
    
    private ComisariaFacade fachada = new ComisariaFacade();
    
    public List<Comisaria> comisariaList() {
        List<Comisaria> comisariaList = fachada.findAll();
        return comisariaList;
    }
    
    public Comisaria getComisaria(Integer idComisaria) {
        String findByIdComisaria = "SELECT c FROM Comisaria c WHERE c.idComisaria = :idComisaria";
        List<Comisaria> lst = fachada.findQueryByParametro(findByIdComisaria, "idComisaria", idComisaria);
        if(lst.size()>0) return lst.get(0);
        return new Comisaria();
    }
    
    public void crear(Comisaria newComisaria) {
        fachada.create(newComisaria);
    }

    public void editar(Comisaria newComisaria) {
        fachada.edit(newComisaria);
    }

    public void eliminar(Comisaria newComisaria) {
        fachada.remove(newComisaria);
    }
}
