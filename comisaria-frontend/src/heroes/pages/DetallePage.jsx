import { useEffect, useMemo, useState } from 'react';
import { Navigate, useNavigate, useParams } from 'react-router-dom';
import { urlComisaria } from '../../utils/endpoints';
import axios from 'axios';


export const DetallePage = () => {

  const { id } = useParams();
  const navigate = useNavigate();

  const [data, setData] = useState([])

    useEffect(() => {
        axios.get(`${urlComisaria}`,{
          params: { id },
        }).then((respuesta) => {
            setData(respuesta.data)
        }).catch(function (error) {
            console.log(error);
          });
    }, [])

  const onNavigateBack = () => {
    navigate(-1);
  }


  
  return (
    <div className="row mt-5">
      <div className="col-4">
        <img 
          src={ `/assets/logos/logo.png` } 
          alt={ data.nombre }
          className="img-thumbnail animate__animated animate__fadeInLeft"
        />
      </div>

      <div className="col-8">
        <h3>{ data.nombre }</h3>
        <ul className="list-group list-group-flush">
          <li className="list-group-item"> <b>Departamento:</b> { data.departamento } </li>
          <li className="list-group-item"> <b>Telefono:</b> { data.telefono } </li>
          <li className="list-group-item"> <b>Mapa:</b> <a href={data.url}>Google Maps</a> </li>
        </ul>

        <h5 className="mt-3"> Direccion </h5>
        <p>{data.direccion}</p>

        <button 
          className="btn btn-outline-primary"
          onClick={ onNavigateBack }
        >
          Regresar
        </button>

      </div>

    </div>
  )
}
