import { emptyVacationStop } from "../../services/data";
import { useState, useEffect, useContext } from "react";
import { useHistory, useParams } from "react-router";
import { findByUsername } from "../../services/users";
import LoginContext from "../../contexts/LoginContext";
const history = useHistory();
const { id } = useParams();
import { findByUsername } from "../../services/users";
import LoginContext from "../../contexts/LoginContext";
import {emptyLocation} from "../../services/data";
import {findAllLocations} from "../../services/locations";
import { emptyVacation } from "../../services/data";
function StopForm(){

    const[vacationStop, setVacationStop] =useState(emptyVacationStop);
    const[vacation, setVacation]=useState(emptyVacation);
    const[location, setLocation]=useState(emptyLocation);

    useEffect(() => {
        if (id) {
            findById(id)
                .then(v => setVacation(v))
                .catch(() => history.push("/failure"));
        }
    }, [history, id]);

    useEffect(() => {
        findByUsername(username)
            .then(setUser)
            .catch(() => history.push("/failure"));
    }, [username, history]);

const cancel = evt => {
    evt.preventDefault();
    history.push("/profile");
};


return(
    <>
    <form onSubmit={onSubmit}>
            <h2>{`${(vacationStop.vacationId > 0 ? "Edit" : "Add")} a Vacation Stop`}</h2>
<div className="form-group">
<label htmlFor="location">Location</label>
<input type="text" className="form-control" id="location" name="location"
    value={location.description} onChange={onChange} required />
</div>

<div className="form-group">
<label htmlFor="startDate">Start Date</label>
<input type="date" className="form-control" id="startDate" name="startDate"
    value={vacationStop.startDate} onChange={onChange} required />
</div>
<div className="form-group">
<label htmlFor="endDate">End date</label>
<input type="date" className="form-control" id="endDate" name="endDate"
    value={vacationStop.endDate} onChange={onChange} required />
</div>
</form>

</>

)


}