/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allan.comisaria.service;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author administrador
 */
public abstract class AbstractFacade<T> {

    private static final Logger log = Logger.getLogger(AbstractFacade.class.getName());

    private Class<T> entityClass;
    private EntityTransaction tx;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            log.info(e.getMessage());
            getEntityManager().getTransaction().rollback();
        }
    }

    public void edit(T entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().merge(entity);
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            log.log(Level.INFO, "Error {0}", e.getMessage());
            getEntityManager().getTransaction().rollback();
        }
    }
    
    public void editSinTrn(T entity) {
        try {
            //getEntityManager().getTransaction().begin();
            getEntityManager().merge(entity);
            //getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            log.log(Level.INFO, "Error {0}", e.getMessage());
            //getEntityManager().getTransaction().rollback();
        }
    }
    
    public void remove(T entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
        }
//        getEntityManager().remove(getEntityManager().merge(entity));

    }

    public T find(Class<T> entitycl, Object id) {
        return getEntityManager().find(entitycl, id);
    }

    public List<T> findAll() {
        //CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        //cq.select(cq.from(entityClass));
        Query q = getEntityManager().createNamedQuery(entityClass.getSimpleName() + ".findAll");
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return q.getResultList();
    }

    public List<T> findNamedQuery(String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return q.getResultList();
    }
    
    public List<T> createQuery(String query) {
        Query q = getEntityManager().createQuery(query);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return q.getResultList();
    }
    
    public List<T> createQueryByParametro(String query, String parametro, Object valorParametro) {
        Query q = getEntityManager().createQuery(query);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setParameter(parametro, valorParametro);
        return q.getResultList();
    }
    
    public Long countQueryByParametro(String countQuery, String parametro, Object valorParametro) {
        Query q = getEntityManager().createQuery(countQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setParameter(parametro, valorParametro);
        return (Long) q.getSingleResult();
    }
    
    

    public List<T> findNamedQueryRange(String namedQuery, int[] range) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findNamedQueryByParametro(String namedQuery, String tipoParametro, Object valorParametro) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setParameter(tipoParametro, valorParametro);
        return q.getResultList();
    }

    public List<T> findQuery(String query) {
        Query q = getEntityManager().createQuery(query);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return q.getResultList();
    }
    
    public List<T> findQueryRange(String query, int[] range) {
        Query q = getEntityManager().createQuery(query);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findQueryByParametro(String query, String tipoParametro, Object valorParametro) {
        Query q = getEntityManager().createQuery(query);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setParameter(tipoParametro, valorParametro);
        return q.getResultList();
    }

    public List<T> findQueryByParametroRange(String query, String tipoParametro, Object valorParametro, int[] range) {
        Query q = getEntityManager().createQuery(query);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setParameter(tipoParametro, valorParametro);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findQueryByParameters(String query, Map<String, Object> parameters) {
        Query q = getEntityManager().createQuery(query);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        Iterator it = parameters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            q.setParameter((String) e.getKey(), e.getValue());
        }
        return q.getResultList();
    }
    
    public Integer executeQueryByParametros(String query, Map<String, Object> parameters) {
        Query q = getEntityManager().createNativeQuery(query);
        Integer updateCount = 0;
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        Iterator it = parameters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            q.setParameter((String) e.getKey(), e.getValue());
        }
        
        try {
            getEntityManager().getTransaction().begin();
            updateCount = q.executeUpdate();
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
        }
        
        log.log(Level.INFO, "Regs Upd: {0}", updateCount);
        return updateCount;
    }
    
    public int idMaxByParametroQuery(String Query ,String tipoParametro, Object valorParametro) {
        Query q = getEntityManager().createQuery(Query);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setParameter(tipoParametro, valorParametro);
        return ((Integer) q.getSingleResult());
    }

    public List<T> findNamedQueryByParametroRange(String namedQuery, Map<String, Object> parameters) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        Iterator it = parameters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            q.setParameter((String) e.getKey(), e.getValue());
        }
        return q.getResultList();
    }

    public List<T> findNamedQueryByParametroRange(String namedQuery, String tipoParametro, Object valorParametro, int[] range) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setParameter(tipoParametro, valorParametro);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findNamedQueryByParametersRange(String namedQuery, Map<String, Object> parameters, int[] range) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        //q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        //q.setHint("eclipselink.query-results-cache", "FALSE");
        //q.setHint("eclipselink.maintain-cache", "FALSE");
        //q.setHint("eclipselink.refresh", "TRUE");
        Iterator it = parameters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            q.setParameter((String) e.getKey(), e.getValue());
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<Object[]> findNamedQueryByParametersRangeObj(String namedQuery, Map<String, Object> parameters, int[] range) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        //q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        //q.setHint("eclipselink.query-results-cache", "FALSE");
        //q.setHint("eclipselink.maintain-cache", "FALSE");
        //q.setHint("eclipselink.refresh", "TRUE");
        Iterator it = parameters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            //System.out.println("parametro "+(String) e.getKey());
            q.setParameter((String) e.getKey(), e.getValue());
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return (List<Object[]>) q.getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findRangeShortBy(String NamedQuery, int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createNamedQuery(NamedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findFilteredRange(String filterText, int[] range) {
        Query q = getEntityManager().createQuery(this.createSelectByGlobalFilter(filterText, false, ""));
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public List<T> findFilteredRange(String filterText, String orderBy, int[] range) {
        Query q = getEntityManager().createQuery(this.createSelectByGlobalFilter(filterText, false, orderBy));
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int filteredCount(String filterText) {
        Query q = getEntityManager().createQuery(this.createSelectByGlobalFilter(filterText, true, ""));
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return ((Long) q.getSingleResult()).intValue();
    }

    public int findNamedQueryByParametersCount(String namedQuery, Map<String, Object> parameters) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        for (Map.Entry e : parameters.entrySet()) {
            q.setParameter((String) e.getKey(), e.getValue());
        }
        return ((Long) q.getSingleResult()).intValue();
    }

    public int idMaxByParametro(String tipoParametro, Object valorParametro) {
        Query q = getEntityManager().createNamedQuery(entityClass.getSimpleName() + ".findIdMax");
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        q.setParameter(tipoParametro, valorParametro);
        return ((Integer) q.getSingleResult());
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return ((Long) q.getSingleResult()).intValue();
    }

    public BigInteger maxNamedQuery(String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        Object res = q.getSingleResult();
        if (res == null) {
            return BigInteger.ZERO;
        }
        Long resultLong = Long.valueOf(res.toString());
        return BigInteger.valueOf(resultLong);
    }

    public Date maxDateNamedQuery(String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        Object res = q.getSingleResult();
        if (res == null) {
            return null;
        }
        Date fecha = (Date) res;
        return fecha;
    }

    public BigInteger countNamedQueryByParameter(String namedQuery, String parameter, Object value) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        q.setParameter(parameter, value);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        Object res = q.getSingleResult();
        if (res == null) {
            return BigInteger.ZERO;
        }
        Long resultLong = Long.valueOf(res.toString());
        return BigInteger.valueOf(resultLong);
    }

    public List<T> getNamedQuery(String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<T> res = q.getResultList();
        return res;
    }

    public List<T> getQuery(Map<String, Object> filters, String filterText, Map<String, String> orderBy, int[] range) {
        Query q;
        String sql = this.createSelectByFixedAndGlobalFilter(filters, filterText, orderBy, false);
        q = getEntityManager().createQuery(sql);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        //q.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
        //q.setHint("eclipselink.query-results-cache", "FALSE");
        //q.setHint("eclipselink.maintain-cache", "FALSE");
        //q.setHint("eclipselink.refresh", "TRUE");
        for (Map.Entry e : filters.entrySet()) {
            if (sql.contains(":" + e.getKey())) {
                q.setParameter((String) e.getKey(), e.getValue());
            }
        }

        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);

        return q.getResultList();
    }

    public BigInteger getCount(Map<String, Object> filters, String filterText) {
        Query q;
        String sql;
        sql = this.createSelectByFixedAndGlobalFilter(filters, filterText, new HashMap<String, String>(), true);
        q = getEntityManager().createQuery(sql);
        //q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        for (Map.Entry e : filters.entrySet()) {
            if (sql.contains(":" + e.getKey())) {
                q.setParameter((String) e.getKey(), e.getValue());
            }
        }
        Object res = q.getSingleResult();
        if (res == null) {
            return BigInteger.ZERO;
        }
        Long resultLong = Long.valueOf(res.toString());
        return BigInteger.valueOf(resultLong);
    }

    public String callStoredProcedure(String spName, Map<String, Object> params, boolean isFunction) {
        String sql = "";
        String paramText = "";
        int count = 0;
        for (Map.Entry e : params.entrySet()) {
            if (count > 0) {
                paramText += ",";
            }
            //paramText += ":"+(String) e.getKey();
            paramText += "?";
            count++;
        }
        if (isFunction == false) {
            sql = "call " + spName + ((paramText.length() > 0) ? "(" + paramText + ")" : "");
        } else {
            sql = "SELECT " + spName + ((paramText.length() > 0) ? "(" + paramText + ")" : "") + " FROM DUAL";
        }
        Query q = getEntityManager().createNativeQuery(sql);
        count = 1;
        for (Map.Entry e : params.entrySet()) {
            q.setParameter(count, e.getValue());
            count++;
        }
        /*Object result = q.getSingleResult();
         if (result != null) {
         return result.toString();
         }*/
        q.executeUpdate();
        return "";
    }

    public Object callFunction(String funcName, Map<String, Object> params, boolean isFunction) throws SQLException {
        String sql = "";
        String paramText = "";
        int count = 0;
        for (Map.Entry e : params.entrySet()) {
            if (count > 0) {
                paramText += ",";
            }
            //paramText += ":"+(String) e.getKey();
            paramText += "?";
            count++;
        }
        if (isFunction == false) {
            sql = "call " + funcName + ((paramText.length() > 0) ? "(" + paramText + ")" : "");
        } else {
            sql = "{ ? = call " + funcName + ((paramText.length() > 0) ? "(" + paramText + ")" : "") + " }";
        }

        java.sql.Connection conn = this.getEntityManager().unwrap(java.sql.Connection.class);
        CallableStatement cs = conn.prepareCall(sql);

        //System.out.println("llamada: " + sql);
        cs.registerOutParameter(1, java.sql.Types.VARCHAR);
        count = 2;
        for (Map.Entry e : params.entrySet()) {
            cs.setInt(count, (Integer) e.getValue());
            count++;
        }
        cs.execute();
        Object result = cs.getObject(1);

        if (result != null) {
            return result.toString();
        }
        return "";
    }

    public String createSelectByGlobalFilter(String filterText, boolean countOn, String orderBy) {
        //crea un string con el sql filtrado por like
        String sql = "";
        if (countOn) {
            sql = "SELECT COUNT(e) ";
        } else {
            sql = "SELECT e ";
        }
        sql += "FROM " + entityClass.getSimpleName() + " e "
                + "WHERE ";
        int count = 0; //cuenta la cantidad de campos
        String orderBySql = "";
        String orderColumns[] = new String[]{};
        if (orderBy != null && orderBy.length() > 0) {
            if (orderBy.indexOf(",") >= 0) {
                orderColumns = orderBy.split(",");
            } else {
                orderColumns = new String[]{orderBy};
            }
        }
        //crea la lista del where
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(EmbeddedId.class)) { //verifica que se aun campo asociado a una columna

                //field.setAccessible(true);
                if (count > 0) {
                    sql += " OR ";
                }
                if (field.isAnnotationPresent(EmbeddedId.class)) {
                    Class entityClassPk = field.getClass();
                    //obtiene los campos de la llave compuesta
                    for (Field fieldPk : entityClassPk.getDeclaredFields()) {
                        if (fieldPk.isAnnotationPresent(Column.class)) {
                            sql += " LOWER(e." + entityClassPk.getSimpleName() + "." + fieldPk.getName() + ") LIKE '%" + filterText.toLowerCase() + "%' ";
                            count += 1;
                            for (String column : orderColumns) {
                                if (column.equalsIgnoreCase(field.getName()) == true) {
                                    orderBySql += " e." + entityClassPk.getSimpleName() + "." + fieldPk.getName() + ",";

                                }
                            }
                        }
                    }
                } else {
                    sql += " LOWER(e." + field.getName() + ") LIKE '%" + filterText.toLowerCase() + "%' ";
                    count += 1;
                    for (String column : orderColumns) {
                        if (column.equalsIgnoreCase(field.getName()) == true) {
                            orderBySql += " e." + field.getName() + ",";
                        }
                    }

                }

            }
        }

        if (orderBySql != null && orderBySql.length() > 0) {
            orderBySql = orderBySql.substring(0, orderBySql.length() - 1); //elimina la coma al final del string
            sql += " ORDER BY " + orderBySql;
        }
        //System.out.println("SQL: " + sql);
        return sql;
    }

    /*
     *  @Parameters:
     *  filters, filtros fijos o de comparación directa con =
     *  filterText, filtro general con like sobre campos base
     *  orderBy, ordenamiento (llave=nombreDelCampo,valor='ASC/DESC')
     */
    public String createSelectByFixedAndGlobalFilter(Map<String, Object> filters, String filterText, Map<String, String> orderBy, boolean countOn) {
        //crea un string con el sql filtrado por like
        String sql = "";
        if (countOn) {
            sql = "SELECT COUNT(e) ";
        } else {
            sql = "SELECT e ";
        }
        sql += "FROM " + entityClass.getSimpleName() + " e ";

        int fixedCount = 0;
        int globalCount = 0;
        int orderCount = 0;
        String fixedWhere = "";
        String globalWhere = "";
        String orderBySql = "";

        //crea el where por los campos fijos
        for (Field field : entityClass.getDeclaredFields()) {
            //verifica que se aun campo asociado a una columna
            if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(EmbeddedId.class)
                    || (field.isAnnotationPresent(JoinColumn.class) && field.isAnnotationPresent(ManyToOne.class))) {
                String simpleName = "";
                String pkName = "";

                //revisa si es una llave compuesta
                if (field.isAnnotationPresent(EmbeddedId.class)) {
                    Class entityClassPk = field.getClass();
                    //obtiene los campos de la llave compuesta
                    for (Field fieldPk : entityClassPk.getDeclaredFields()) {
                        simpleName = fieldPk.getName();
                        pkName = entityClassPk.getSimpleName() + "." + fieldPk.getName();
                    }
                } else {
                    simpleName = field.getName();
                    pkName = field.getName();
                }
                //System.out.println("simpleName " + simpleName);
                //System.out.println("pkName " + pkName);
                //revisa si el campo viene dentro de los filtros
                if (filters.get(field.getName()) != null) {
                    if (field.getName().contains("fechaGrabacion") == false
                            && field.getName().contains("fechaUltimaModificacion") == false
                            && field.getName().contains("fechaUltimaActualizacion") == false
                            && field.getName().contains("fechaIngreso") == false) {
                        if (fixedCount > 0) {
                            fixedWhere += " AND ";
                        }
                        fixedCount++;
                        //lo deja cargado como parametro
                        fixedWhere += "e." + pkName + " = :" + simpleName + " ";
                    }
                }
                //revisa si viene un filtro global
                if (filterText != null && filterText.length() > 0) {
                    if (field.isAnnotationPresent(JoinColumn.class) == false
                            && field.isAnnotationPresent(Id.class) == false
                            && field.isAnnotationPresent(EmbeddedId.class) == false) {
                        if (field.getName().contains("fechaGrabacion") == false
                                && field.getName().contains("fechaUltimaModificacion") == false
                                && field.getName().contains("fechaUltimaActualizacion") == false
                                && field.getName().contains("fechaIngreso") == false) {
                            if (globalCount > 0) {
                                globalWhere += " OR ";
                            }
                            globalCount++;
                            globalWhere += " LOWER(e." + pkName + ") LIKE '%" + filterText.toLowerCase() + "%' ";
                        }
                    }
                }
                //revisa si viene en el order by
                if (orderBy.get(field.getName()) != null) {
                    if (field.isAnnotationPresent(JoinColumn.class) == false) {
                        if (orderCount > 0) {
                            orderBySql += ", ";
                        }
                        orderCount++;
                        orderBySql += " e." + pkName + " " + orderBy.get(simpleName);
                    }
                }

            }
        }

        if (fixedCount > 0) {
            sql += " WHERE " + fixedWhere;

        }

        if (globalCount > 0) {
            if (sql.contains("WHERE")) {
                sql += " AND ";
            } else {
                sql += " WHERE ";
            }
            sql += " (" + globalWhere + ") ";
        }

        if (orderCount > 0) {
            sql += " ORDER BY " + orderBySql;
        }
        //System.out.println("SQL: " + sql);
        return sql;
    }
}
