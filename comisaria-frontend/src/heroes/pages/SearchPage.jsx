import { useLocation, useNavigate } from 'react-router-dom';
import queryString from 'query-string'

import { useForm } from '../../hooks/useForm';
import { ComisariaCard } from '../components';
import { getHeroesByName } from '../helpers';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { urlComisarias } from '../../utils/endpoints';

export const SearchPage = () => {

  const navigate = useNavigate();
  const location = useLocation();

  const [data, setData] = useState([])

    useEffect(() => {
        axios.get(`${urlComisarias}`).then((respuesta) => {
            setData(respuesta.data)
        }).catch(function (error) {
            console.log(error);
          });
    }, [])


  const { q = '' } = queryString.parse( location.search );
  const heroes = getHeroesByName(q,data);

  const showSearch = (q.length === 0);
  const showError  = (q.length > 0) && heroes.length === 0;


  const { searchText, onInputChange } = useForm({
    searchText: q
  });



  const onSearchSubmit = (event) =>{
    event.preventDefault();
    // if ( searchText.trim().length <= 1 ) return;

    navigate(`?q=${ searchText }`);
  }


  return (
    <>
      <h1>Busqueda</h1> 
      <hr />

      <div className="row">

          <div className="col-5">
            <h4>Buscando</h4>
            <hr />
            <form onSubmit={ onSearchSubmit }>
              <input 
                type="text"
                placeholder="Search a hero"
                className="form-control"
                name="searchText"
                autoComplete="off"
                value={ searchText }
                onChange={ onInputChange }
              />

              <button className="btn btn-outline-primary mt-1">
                Buscar
              </button>
            </form>
          </div>

          <div className="col-7">
            <h4>Resultados</h4>
            <hr />
            
            <div className="alert alert-primary animate__animated animate__fadeIn" 
                style={{ display: showSearch ? '' : 'none' }}>
              Buscar una Comisaria
            </div>

            <div className="alert alert-danger animate__animated animate__fadeIn" 
                style={{ display: showError ? '' : 'none' }}>
              No hay resultados <b>{ q }</b>
            </div>


            {
              heroes.map( item => (
                <ComisariaCard key={ item.idComisaria } {...item } />
              ))
            }

          </div>
      </div>
      

    </>
  )
}
