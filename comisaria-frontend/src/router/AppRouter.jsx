import { Route, Routes } from 'react-router-dom';

import { ComisariaRoutes } from '../heroes';
import { LoginPage } from '../auth';



export const AppRouter = () => {
  return (
    <>

        <Routes>
            
            <Route path="login" element={<LoginPage />} />
            
            <Route path="/*" element={ <ComisariaRoutes />} />
            
        </Routes>
    
    </>
  )
}
