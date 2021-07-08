import { emptyVacationStop } from "../../services/data";
import { useState, useEffect, useContext } from "react";
import { useHistory, useParams } from "react-router";
import { findByUsername } from "../../services/users";
import {findById} from "../../services/vacations";
import LoginContext from "../../contexts/LoginContext";
import {emptyLocation} from "../../services/data";
import {findAllLocations} from "../../services/locations";
import { emptyVacation } from "../../services/data";
import {addVacationStop} from "../../services/vacationstop";
import { findByDescription } from "../../services/locations";

function StopForm(){

    const[vacationStop, setVacationStop] =useState(emptyVacationStop);
    const[vacation, setVacation]=useState(emptyVacation);
    const[description, setDescription]=useState([""]);
    const[location, setLocation] = useState(emptyLocation);
    const history = useHistory();
    const { id } = useParams();


    useEffect(() => {
        if (id) {
            findById(id)
                .then(v => setVacation(v))
                .catch(() => history.push("/failure"));
        }
    }, [history, id]);



    const onChangeStop = evt => {
        const nextVacationStop = { ...vacationStop };
            nextVacationStop[evt.target.name] = evt.target.value;
        setVacationStop(nextVacationStop);
    };

    const onChangeLocation = evt => {
        const nextLocation = { ...location };
            nextLocation[evt.target.name] = evt.target.value;
        setLocation(nextLocation);

        findByDescription(location.description)
                .then(l => setLocation(l))
                .catch();

    };




    const onSubmit = evt => {
        
         addVacationStop({
                vacationId: vacation.vacationId,
                 startDate : vacationStop.startDate,
                 endDate : vacationStop.endDate,
                  identifier: `${vacation.vacationId} ${location.locationId}`})
            .then(() => history.push("/profile"))
            .catch()};
    

const cancel = evt => {
    evt.preventDefault();
    history.push("/profile");
};


return(
    
    <form onSubmit={onSubmit}>
            <h2>{`Add a Vacation Stop`}</h2>

<div className="form-group">
    <label htmlFor="description">Location</label>
    <input type="text" className="form-control" placeholder="City Name, State abr   ex:  Dallas, TX" id="description" name="description"
        value={location.description} onChange={onChangeLocation} required />
</div>


<div className="form-group">
<label htmlFor="startDate">Start Date</label>
<input type="date" className="form-control" id="startDate" name="startDate"
    value={vacationStop.startDate} onChange={onChangeStop} required />
</div>

<div className="form-group">
<label htmlFor="endDate">End date</label>
<input type="date" className="form-control" id="endDate" name="endDate"
    value={vacationStop.endDate} onChange={onChangeStop} required />
</div>

<div className="form-group">
     <button type="submit" className="btn btn-primary me-2">Save</button>
     <button className="btn btn-secondary" onClick={cancel}>Cancel</button>
            </div> 

</form>



);


}

export default StopForm;