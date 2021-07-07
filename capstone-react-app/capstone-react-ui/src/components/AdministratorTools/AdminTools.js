import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import {findAllUsers} from "../../services/users";
import {findAllLocations} from "../../services/locations"

function AdminTools(){

    const[users, setUsers] = useState();
    const[locations, setLocations] = useState();
    const history = useHistory();

    useEffect(() => {
        findAllLocations()
            .then(setLocations)
            .catch(() => history.push("./failure"));
    }, [history]);

    useEffect(() => {
        findAllUsers()
            .then(setUsers)
            .catch(() => history.push("./failure"));
    }, [history]);

    return(<div>
        <div className="col d-flex justify-content-end">
          <Link to="/admintools/location/add" className={`btn btn-primary m-1`}>Add a Location</Link>
        </div>

        <div className="d-md-flex h-md-100 align-items-start">
            <div className="col-md-6">
                <h1 className="col">Users</h1>
                <div className="row grid-header">
                    <div className="col">First Name</div>
                    <div className="col">Last Name</div>
                    <div className="col">Usename</div>
                    <div className="col"></div>
                </div>
                {users && users.map(u => <div key={u.userId} className="row mt-2">
                    <div className="col">{u.firstName}</div>
                    <div className="col">{u.lastName}</div>
                    <div className="col">{u.username}</div>
                    <div className="col">
                    <Link to={`/admintools/user/delete/${u.userId}`} className="btn btn-danger me-2">Delete</Link>
                    <Link to={`/admintools/user/edit/${u.userId}`} className="btn btn-info">Edit</Link></div>
                </div>)}
            </div>

            <div className="col-md-6">
                <h1 className="col">Location</h1>
                <div className="row grid-header">
                    <div className="col">Description</div>
                    <div className="col"></div>
                </div>
                {locations && locations.map(l => <div key={l.locationId} className="row mt-2">
                    <div className="col">{l.description}</div>
                    <div className="col">
                    <Link to={`/admintools/location/delete/${l.locationId}`} className="btn btn-danger me-2">Delete</Link>
                    <Link to={`/admintools/location/edit/${l.locationId}`} className="btn btn-info">Edit</Link></div>
                </div>)}
            </div>
        </div>
    </div>);
}


export default AdminTools;