import { Navigate, Route, Routes } from 'react-router-dom';
import { Navbar } from '../../ui';
import { ComisariaPage, DetallePage, SearchPage } from '../pages';
import { EditarCrear } from '../pages/EditarCrear';

export const ComisariaRoutes = () => {
  return (
    <>
        <Navbar />

        <div className="container">
            <Routes>
                <Route path="comisaria" element={<ComisariaPage />} />
                
                <Route path="search" element={<SearchPage />} />
                <Route path="hero/:id" element={<DetallePage />} />
                <Route path="comisaria/crear" element={<EditarCrear accion='Crear' />} />
                <Route path="comisaria/editar/:id" element={<EditarCrear accion='Editar' />} />
                                

                <Route path="/" element={<Navigate to="/comisaria" />} />

            </Routes>
        </div>


    </>
  )
}
