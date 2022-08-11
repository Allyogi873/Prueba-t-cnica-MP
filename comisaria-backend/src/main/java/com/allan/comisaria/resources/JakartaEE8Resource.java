package com.allan.comisaria.resources;

import com.allan.comisaria.controler.ComisariaControler;
import com.allan.comisaria.models.Comisaria;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author
 */
@Path("rest")
public class JakartaEE8Resource {

    @GET
@CrossOrigin(origins = "*")
    public Response ping() {
        return Response
                .ok("ping")
                .build();
    }

    @GET
    @Path("comisarias")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
@CrossOrigin(origins = "*")
    public List<Comisaria> postJson() {
        System.out.println("consultado comisarias");
        ComisariaControler hola = new ComisariaControler();
        return hola.comisariaList();
    }

    @GET
    @Path("comisaria")
    @Produces({MediaType.APPLICATION_JSON})
@CrossOrigin(origins = "*")
    /**
     * @param id
     */
    public Comisaria getIdComisaria(@QueryParam("id") Integer id) {
        System.out.println("consultado id" + id);
        ComisariaControler hola = new ComisariaControler();
        return hola.getComisaria(id);
    }

    @POST
    @Path("comisaria/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
@CrossOrigin(origins = "*")
    /**
     * @param id
     */
    public String crearComisaria(Comisaria parameters) {
        System.out.println("consultado id" + parameters.getNombre());
        ComisariaControler hola = new ComisariaControler();
        try {
            hola.crear(parameters);
            return "exito";
        } catch (Exception e) {
            return "error";
        }
    }

    @POST
    @Path("comisaria/editar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
@CrossOrigin(origins = "*")
    /**
     * @param id
     */
    public String editarComisaria(Comisaria parameters) {
        System.out.println("consultado id" + parameters.getNombre());
        if (parameters.getIdComisaria() != null) {
            ComisariaControler hola = new ComisariaControler();
            try {
                hola.editar(parameters);
                return "exito";
            } catch (Exception e) {
                return "error";
            }
        } else {
            return "id_vacio";
        }

    }
    
    
    @POST
    @Path("comisaria/eliminar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
@CrossOrigin(origins = "*")
    /**
     * @param id
     */
    public String eliminarComisaria(Comisaria parameters) {
        System.out.println("consultado id" + parameters.getNombre());
        if (parameters.getIdComisaria() != null) {
            ComisariaControler hola = new ComisariaControler();
            try {
                hola.eliminar(parameters);
                return "exito";
            } catch (Exception e) {
                return "error";
            }
        } else {
            return "id_vacio";
        }

    }
}
