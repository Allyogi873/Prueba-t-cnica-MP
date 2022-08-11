export const getHeroesByName = ( name = '', datos ) => {

    name = name.toLocaleLowerCase().trim();
    
    if ( name.length === 0 ) return [];

    return datos.filter(
        item => item.nombre.toLocaleLowerCase().includes( name ) 
    );

}



