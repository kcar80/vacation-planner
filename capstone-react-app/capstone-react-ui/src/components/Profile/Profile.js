import React, { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { findVacationsByUser, findLocationById } from "../../services/UserServices";
import { Link } from "react-router-dom";

const passwordType = {
    HIDDEN: "password",
    SHOWN: "text"
}

function Profile({ user }) {

    const [passwordState, setPasswordState] = useState(passwordType.HIDDEN);
    const [vacations, setVacations] = useState([]);
    const history = useHistory();

    useEffect(() => {
        findVacationsByUser(user.userId)
            .then(setVacations)
            //.catch(() => history.push("/failure"));
    }, [history]);

    const changePasswordState = evt => {
        if (passwordState === passwordType.HIDDEN) {
            setPasswordState(passwordType.SHOWN);
        } else {
            setPasswordState(passwordType.HIDDEN);
        }
    }

    return (
    <div className="container">
        <div className="row">
            <h2 className="col-sm-1">Profile</h2>
        </div>
        <div className="row">
            <div className="col-sm-2">Name:</div>
            <div className="col-md-auto">{user.firstName} {user.lastName}</div>
        </div>
        <div className="row">
            <div className="col-sm-2">Username:</div>
            <div className="col-md-auto">{user.username}</div>
        </div>
        <div className="form-group row">
            <div className="col-sm-2">Password:</div>
            <div className="col-md-auto">
                <input type={passwordState} readonly className="form-control-plaintext form-control-sm" id="password" value={user.password}/>
            </div>
            <button className="col-md-auto btn btn-sm btn-outline-secondary" onClick={changePasswordState}>hide/show</button>
        </div>
        
        <h3 className="mt-4">Vacations: <Link to="vacation/add" className="btn btn primary ms-3">Add Vacation</Link></h3>
        {vacations ? 
            <div>
            {vacations.map(v =>
                <div key={v.vacationId}>
                    <div>{v.description}</div>
                    <div>Stops:</div>
                    {v.locations.map(l => 
                        <li>
                            <div className="row">
                                <div className="col">{findLocationById(l.locationId)}</div>
                                <div className="col">{l.startDate}</div>
                                <div className="col">{l.endDate}</div>
                            </div>
                        </li>)}
                </div>)} 
            </div> 
            : <div>No Vacations found</div>}
        
    </div>
    )
}



export default Profile;