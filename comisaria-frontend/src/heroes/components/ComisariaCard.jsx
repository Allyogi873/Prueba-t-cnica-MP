import { Link } from 'react-router-dom';

const CharactersByHero = ({ alter_ego, characters }) => {
    // if ( alter_ego === characters ) return (<></>);
    // return <p>{ characters }</p>
    return (alter_ego === characters)
        ? <></>
        : <p>{characters}</p>;
}


export const ComisariaCard = ({
    idComisaria,
    departamento,
    nombre,
    telefono,
    url
}) => {

    const heroImageUrl = `/assets/logos/logo.png`;

    // const charactesByHero =  (<p>{ characters }</p>);


    return (
        <div className="col animate__animated animate__fadeIn">
            <div className="card">

                <div className="row no-gutters">

                    <div className="col-4">
                        <img src={heroImageUrl} className="card-img" alt={nombre} />
                    </div>

                    <div className="col-8">

                        <div className="card-body">

                            <h5 className="card-title">{nombre}</h5>
                            <p className="card-text">{telefono}</p>



                            <p className="card-text">
                                <small className="text-muted">{departamento}</small>
                            </p>

                            <Link to={`/hero/${idComisaria}`}>
                                Más..
                            </Link>
                            <Link to={`/Comisaria/Editar/${idComisaria}`}> <button className="btn btn-outline-warning mt-1 mx-2">
                                Editar
                            </button></Link>


                        </div>

                    </div>


                </div>

            </div>
        </div>
    )
}
