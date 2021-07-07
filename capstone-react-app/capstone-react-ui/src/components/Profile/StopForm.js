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
function StopForm(){

    const[vacationStop, setVacationStop] =useState(emptyVacationStop);
    const[vacation, setVacation]=useState(emptyVacation);
    const[locations, setLocations]=useState([]);
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

    useEffect(()=>{
        findAllLocations()
        .then(setLocations)
        .catch(() => history.push("/failure"));
    },[history]);

    const onChangeStop = evt => {
        const nextVacationStop = { ...vacationStop };
            nextVacationStop[evt.target.name] = evt.target.value;
        setVacationStop(nextVacationStop);
    };

    const onChangeLocation = evt => {
        const nextLocation = { ...location };
            nextLocation[evt.target.name] = evt.target.value;
        setLocation(nextLocation);
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
    <>
    <form onSubmit={onSubmit}>
            <h2>{`${(vacationStop.vacationId > 0 ? "Edit" : "Add")} a Vacation Stop`}</h2>
<div className="form-group">
<label htmlFor="location">Location</label>
<input type="text" className="form-control" id="location" name="location"
    value={location.description} onChange={onChangeLocation} required />
</div>

<div className="dropdown show">
  <a className="btn btn-secondary dropdown-toggle"  role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Locations
  </a>

  {locations && locations.map(l => <div key= {l.locationId} 
     className="dropdown-menu" >
    <a className="dropdown-item">{l.description}</a>
  </div>)}
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
</form>

</>

);


}

export default StopForm;