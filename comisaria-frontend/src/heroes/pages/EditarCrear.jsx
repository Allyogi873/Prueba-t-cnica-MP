import { useEffect, useMemo, useState } from 'react';
import { Navigate, useNavigate, useParams } from 'react-router-dom';
import { urlComisaria } from '../../utils/endpoints';
import axios from 'axios';
import { useForm } from 'react-hook-form'


export const EditarCrear = ({ accion }) => {

  const { id=null } = useParams();
  const navigate = useNavigate();
  const { register, handleSubmit, formState: { errors }, setValue } = useForm();
  const [data, setData] = useState([])

  useEffect(() => {
    if (accion === 'Editar') {
      axios.get(`${urlComisaria}`, {
        params: { id },
      }).then((respuesta) => {
        setData(respuesta.data)
        setValue('nombre', respuesta.data.nombre)
        setValue('departamento', respuesta.data.departamento)
        setValue('telefono', respuesta.data.telefono)
        setValue('url', respuesta.data.url)
        setValue('direccion', respuesta.data.direccion)

      }).catch(function (error) {
        console.log(error);
      });
    }
  }, [])

  const onNavigateBack = () => {
    navigate(-1);
  }

  async function guardar(data) {
    if(accion === 'Editar' )data.idComisaria = id
    var config = {
      method: 'post',
      url: urlComisaria+'/'+accion.toLowerCase(),
      headers: { 
        'Content-Type': 'application/json'
      },
      data
    };
    
    axios(config)
    .then(function (response) {
      onNavigateBack()
    })
    .catch(function (error) {
      alert('Error al Guardar')
    });
    
  }



  return (
    <div className="row mt-5">


      <div className="d-flex justify-content-between bd-highlight mb-3">

        <div><h1>{accion}</h1></div>
        <div><button
          className="btn btn-outline-danger mt-2"
          onClick={onNavigateBack}
        >
          Cancelar
        </button></div>
      </div>



      <div className="col-4">
        <img
          src={`/assets/logos/logo.png`}
          alt={data.nombre}
          className="img-thumbnail animate__animated animate__fadeInLeft"
        />
      </div>
      <form onSubmit={handleSubmit(guardar)} className="col-8">
        <div >
          <div className="form-group">
            <label >Nombre de comisaria</label>
            <input type="text" className="form-control" id="nombre" placeholder="Nombre"
              {...register("nombre", {
                required: {
                  value: true,
                  message: 'El campo es requerido'
                },
                maxLength:{
                  value:45,
                  message:'El numero maximo es 45 caracteres'
                }
              })} />
              {errors.nombre && <span className='text-danger'>{errors.nombre.message}</span>}
          </div>
          <h3>{data.nombre}</h3>
          <ul className="list-group list-group-flush">
            <li className="list-group-item"> <b>Departamento:</b>
              <input type="text" className="form-control" id="dep" placeholder="Departamento"
              {...register("departamento", {
                required: {
                  value: true,
                  message: 'El campo es requerido'
                },
                maxLength:{
                  value:45,
                  message:'El numero maximo es 45 caracteres'
                }
              })} />
              {errors.departamento && <span className='text-danger'>{errors.departamento.message}</span>}
            </li>
            <li className="list-group-item"> <b>Telefono:</b>
              <input type="text" className="form-control" id="tel" placeholder="Telefono"
              {...register("telefono", {
                required: {
                  value: true,
                  message: 'El campo es requerido'
                },
                maxLength:{
                  value:8,
                  message:'El numero maximo es 8 caracteres'
                }
              })} />
              {errors.telefono && <span className='text-danger'>{errors.telefono.message}</span>}
            </li>
            <li className="list-group-item"> <b>Mapa:</b>
              <input type="text" className="form-control" id="map" placeholder="Url"
              {...register("url", {
                required: {
                  value: true,
                  message: 'El campo es requerido'
                },
                maxLength:{
                  value:500,
                  message:'El numero maximo es 500 caracteres'
                }
              })} />
              {errors.url && <span className='text-danger'>{errors.url.message}</span>}
            </li>
          </ul>

          <div className="form-group">
            <label >Direccion</label>
            <input type="text" className="form-control" id="dir" placeholder="Direccion"
              {...register("direccion", {
                required: {
                  value: true,
                  message: 'El campo es requerido'
                },
                maxLength:{
                  value:45,
                  message:'El numero maximo es 45 caracteres'
                }
              })} />
              {errors.direccion && <span className='text-danger'>{errors.direccion.message}</span>}
          </div>



        </div>
        <button
          className="btn btn-outline-success mt-2"
        >
          Guardar
        </button>
      </form>

    </div>
  )
}
