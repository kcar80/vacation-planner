import React, { useState, useEffect, useContext } from "react";
import LoginContext from "../../contexts/LoginContext";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import { findByUsername } from "../../services/users";
import { emptyUser } from "../../services/data";
import { findVacationsByUser } from "../../services/vacations";
import '../../css files/main.css';

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
    <div className="container white">
        <div className="row">
            <h2 className="col-sm-1">Profile</h2>
        </div>
        <div className="container row">
            <div className="col-sm-2">Name:</div>
            <div className="col-md-auto">{user.firstName} {user.lastName}</div>
        </div>
        <div className="container row">
            <div className="col-sm-2">Username:</div>
            <div className="col-md-auto">{user.username}</div>
        </div>
        <div className="container form-group row">
            <div className="col-sm-2">Password:</div>
            <div className="col-md-auto">
                <input type={passwordState} readOnly className="form-control-plaintext form-control-sm" id="password" value={user.password}/>
            </div>
            <button className="col-md-auto btn btn-sm btn-outline-secondary" onClick={changePasswordState}>hide/show</button>
        </div>
        
        <h3 className="mt-4">Vacations: <Link to="/vacation/add" className="btn btn-primary ms-3">Add Vacation</Link></h3>
        {vacations ? 
            <div>
            {vacations.map(v =>
                <div key={v.vacationId} className="container mb-3 alt-colors">
                    <div className="title">{v.description}</div>
                    <div>Users:</div>
                    {v.users.map(u => 
                        <li key={u.identifier} className="container no-bullets">
                            <div className="row">
                                <div className="col-5">{u.user.firstName} {u.user.lastName}</div>
                                <div className="col"></div>
                            </div>
                        </li>)}

                    <div>Stops:</div>
                    {v.locations.map(l => 
                        <li key={l.identifier} className="container md-3 no-bullets">
                            <div className="row">
                                <div className="col-2">{l.location.description}</div>
                                <div className="col">{l.startDate}  -  {l.endDate}</div>
                            </div>
                        </li>)}
                        <Link to={`/vacation/delete/${v.vacationId}`} className={`btn btn-danger me-2${(username ? "" : " disabled")}`}>Delete</Link>
                        <Link to={`/vacation/edit/${v.vacationId}`} className={`btn btn-info${(username ? "" : " disabled")}`}>Edit</Link>
                </div>)} 
            </div> 
            : <div>No Vacations found</div>}
        
    </div>
    )
}



export default Profile;