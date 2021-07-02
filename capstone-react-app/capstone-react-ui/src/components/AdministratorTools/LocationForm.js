import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router";
import { emptyLocation } from "./data";
import {findById, add, update} from "./locations"

function LocationForm(){
    const[location, setLocation] = useState(emptyLocation);
    const history = useHistory();
    const { id } = useParams();


 useEffect(() => {
        if (id) {
            findById(id)
                .then(l => setLocation(l))
                .catch(() => history.push("/failure"));
        }
    }, [history, id]);

    const onChange = evt => {
        const nextLocation = { ...location };
            nextLocation[evt.target.name] = evt.target.value;
        setLocation(nextLocation);
    };

    const onSubmit = evt => {
        evt.preventDefault();
        (location.locationId > 0 ? update(location) : add(location))
            .then(() => history.push("/"))
            .catch();
    };

    const cancel = evt => {
        evt.preventDefault();
        history.push("/");
    };



    return (
        <form onSubmit={onSubmit}>
            <h2>{`${(location.locationId > 0 ? "Edit" : "Add")} a Location`}</h2>
            <div className="form-group">
                <label htmlFor="description">Description</label>
                <input type="text" className="form-control" id="description" name="description"
                    value={location.description} onChange={onChange} required />
            </div>
        
            
            <div className="form-group">
                <button type="submit" className="btn btn-primary me-2">Save</button>
                <button className="btn btn-secondary" onClick={cancel}>Cancel</button>
            </div> 
        </form>
    );


}

export default LocationForm;