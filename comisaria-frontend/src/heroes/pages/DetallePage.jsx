import { useEffect, useMemo, useState } from 'react';
import { Navigate, useNavigate, useParams } from 'react-router-dom';
import { urlComisaria } from '../../utils/endpoints';
import axios from 'axios';


export const DetallePage = () => {

  const { id } = useParams();
  const navigate = useNavigate();

  const [data, setData] = useState([])

  useEffect(() => {
    axios.get(`${urlComisaria}`, {
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

  async function eliminar() {
    var config = {
      method: 'post',
      url: urlComisaria+'/eliminar',
      headers: { 
        'Content-Type': 'application/json'
      },
      data
    };
    
    axios(config)
    .then(function (response) {
      $('#exampleModalCenter').modal('hide');
      onNavigateBack()
    })
    .catch(function (error) {
      alert('Error al Guardar')
    });
    
  }

  return (
    <div className="row mt-5">
      <div className="col-4">
        <img
          src={`/assets/logos/logo.png`}
          alt={data.nombre}
          className="img-thumbnail animate__animated animate__fadeInLeft"
        />
      </div>

      <div className="col-8">
        <h3>{data.nombre}</h3>
        <ul className="list-group list-group-flush">
          <li className="list-group-item"> <b>Departamento:</b> {data.departamento} </li>
          <li className="list-group-item"> <b>Telefono:</b> {data.telefono} </li>
          <li className="list-group-item"> <b>Mapa:</b> <a href={data.url}>Google Maps</a> </li>
        </ul>

        <h5 className="mt-3"> Direccion </h5>
        <p>{data.direccion}</p>

        <button
          className="btn btn-outline-primary"
          onClick={onNavigateBack}
        >
          Regresar
        </button>


        <button type="button" className="btn btn-outline-danger" data-toggle="modal" data-target="#exampleModalCenter">
          Elimiar
        </button>
        
        <div className="modal fade" id="exampleModalCenter" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="exampleModalLongTitle">Eliminar: {data.nombre}</h5>
              </div>
              <div className="modal-body">
              ¿Está seguro que desea eliminar este registro?
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                <button type="button" className="btn btn-danger" onClick={eliminar}>Eliminar</button>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>
  )
}
