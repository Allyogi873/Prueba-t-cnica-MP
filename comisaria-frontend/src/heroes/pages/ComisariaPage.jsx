import { Link } from 'react-router-dom';
import { ComisariaList } from '../components';


export const ComisariaPage = () => {
  return (
    <>



      <div className="d-flex justify-content-between bd-highlight mb-3">

        <div><h1>Todas las Comisarias</h1></div>
        <div><Link to={'/Comisaria/Crear'}><button className="btn btn-outline-primary mt-1">Crear</button></Link></div>
      </div>
      <hr />

      <ComisariaList />
    </>
  )
}
