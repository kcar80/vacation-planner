import React, { useState, useEffect, useContext } from "react";
import LoginContext from "../../contexts/LoginContext";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import { findByUsername } from "../../services/users";
import { emptyUser } from "../../services/data";
import { findVacationsByUser } from "../../services/vacations";

const passwordType = {
    HIDDEN: "password",
    SHOWN: "text"
}

function Profile() {

    const [passwordState, setPasswordState] = useState(passwordType.HIDDEN);
    const [user, setUser] = useState(emptyUser);
    const {username} = useContext(LoginContext);
    const [vacations, setVacations] = useState([]);
    const history = useHistory();

    useEffect(() => {
        findByUsername(username)
            .then(setUser)
            .catch(() => history.push("/failure"));
    }, [username, history]);

    useEffect(() => {
        findVacationsByUser(user.userId)
            .then(setVacations)
    }, [history, user.userId]);

    
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
                <input type={passwordState} readOnly className="form-control-plaintext form-control-sm" id="password" value={user.password}/>
            </div>
            <button className="col-md-auto btn btn-sm btn-outline-secondary" onClick={changePasswordState}>hide/show</button>
        </div>
        
        <h3 className="mt-4">Vacations: <Link to="vacation/add" className="btn btn primary ms-3">Add Vacation</Link></h3>
        {vacations ? 
            <div>
            {vacations.map(v =>
                <div key={v.vacationId}>
                    <div>{v.description}</div>

                    <div>Users:</div>
                    {v.users.map(l => 
                        <li key={l.identifier}>
                            <div className="row">
                                <div className="col">{l.user.firstName} {l.user.lastName}</div>
                                <div className="col"></div>
                            </div>
                        </li>)}

                    <div>Stops:</div>
                    {v.locations.map(l => 
                        <li key={l.identifier}>
                            <div className="row">
                                <div className="col">{l.location.description}</div>
                                <div className="col">{l.startDate}</div>
                                <div className="col">{l.endDate}</div>
                                <div className="col"></div>
                            </div>
                        </li>)}
                </div>)} 
            </div> 
            : <div>No Vacations found</div>}
        
    </div>
    )
}



export default Profile;