import LoginContext from "../../contexts/LoginContext";
import { useState, useEffect, useContext } from "react";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import { findAllLocations } from "../AdministratorTools/locations";

function Locations(){
const[locations, setLocations] = useState();
const history = useHistory();
const {username} = useContext(LoginContext);


useEffect(() => {
    findAllUsers()
        .then(setUsers)
        .catch(() => history.push("./failure"));
}, [history]);

return(
    <>
   
<div className="row row-cols-4 g-2">
            <h1 className="col">Location</h1>
            
     </div>
     <div className="row grid-header">
            <div className="col">Description</div>
            <div className="col"></div>
        </div>
        {locations && locations.map(l => <div key={l.locationId} className="row mt-2">
            <div className="card" key = {l.locationId}>
                <div className = "card-body">
                    <h5 className = "card-title">{l.description}</h5>
                </div>
                </div>
            <div className="col">
            <Link to={`/admintools/location/delete/${l.locationId}`} className="btn btn-danger me-2">Delete</Link>
            <Link to={`/admintools/location/edit/${l.locationId}`} className="btn btn-info">Edit</Link></div>
        </div>)}

</div>
    
</div>
</>
)

}

export default Locations;