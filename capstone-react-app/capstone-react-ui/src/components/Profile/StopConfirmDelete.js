import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router";
import { findById} from "../../services/vacations";
import {deleteVacationStopById} from "../../services/vacationstop";
import { emptyVacation } from "../../services/data";
import {findByLocationId} from "../../services/locations"
import {emptyLocation} from "../../services/data";

function VacationConfirmDelete() {

 
    const [vacation, setVacation] = useState(emptyVacation)
    const[location, setLocation] = useState(emptyLocation);
    const history = useHistory();
    const { vacationId, locationId } = useParams();


    useEffect(() => {
        if (vacationId) {
            findById(vacationId)
                .then(v => setVacation(v))
                .catch(() => history.push("/failure"));
        }

        if(locationId){
            findByLocationId(locationId)
                .then(l=> setLocation(l))
                .catch(() => history.push("/failure"));
            }
        


    }, [history, vacationId, locationId]);

    const yesDelete = () => {
        deleteVacationStopById(vacation.vacationId, location.locationId)
            .then(() => history.push("/profile"))
            .catch(() => history.push("/failure"));
    };

    const cancel = evt => {
        evt.preventDefault();
        history.push("/profile");
    };

    return (
        <div>
            <h2>Delete stop for {location.description}?</h2>
            <div className="alert alert-danger">
                <p>
                    All data for {location.description} will be permanently deleted.
                </p>
                Are you sure?
            </div>
            <div className="mb-2">
                <button className="btn btn-danger me-2" onClick={yesDelete}>Delete Forever</button>
                <button className="btn btn-secondary" onClick={cancel}>Cancel</button>
            </div>
          
        </div>
    );
}

export default VacationConfirmDelete;