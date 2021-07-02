import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router";

import { findById, deleteById } from "./locations" 

function LocationConfirmDelete() {

    const [location, setLocation] = useState({ description: "" });
    const [message, setMessage] = useState();
    const history = useHistory();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            findById(id)
                .then(l => setLocation(l))
                .catch(() => setMessage("Unable to find location."));
        }
    }, [history, id]);

    const yesDelete = () => {
        deleteById(location.locationId)
            .then(() => history.push("/"))
            .catch(() => setMessage("Delete failed."));
    };

    const cancel = () => history.push("/");

    return (
        <div>
            <h2>Delete {location.description}?</h2>
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

export default LocationConfirmDelete;