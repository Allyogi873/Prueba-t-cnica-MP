
import { useEffect, useMemo, useState } from 'react';
import { ComisariaCard } from '.';
import { urlComisarias } from '../../utils/endpoints';
import axios from 'axios';

export const ComisariaList = () => {

    const [data, setData] = useState([])

    useEffect(() => {
        axios.get(`${urlComisarias}`).then((respuesta) => {
            setData(respuesta.data)
        }).catch(function (error) {
            console.log(error);
          });
    }, [])
    

    return (
        <div className="row rows-cols-1 row-cols-md-3 g-3">
            {
                data.map( (hero) => (
                    <ComisariaCard 
                        key={ hero.idComisaria } 
                        { ...hero }
                    />
                ))
            }
        </div>
    )
}
