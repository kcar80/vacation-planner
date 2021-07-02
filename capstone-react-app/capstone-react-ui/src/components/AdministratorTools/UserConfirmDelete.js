import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router";

import { findById, deleteById } from "./users" 

function UserConfirmDelete() {

    const [user, setUser] = useState({ firstName: "" });
    const [message, setMessage] = useState();
    const history = useHistory();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            findById(id)
                .then(u => setUser(u))
                .catch(() => setMessage("Unable to find user."));
        }
    }, [history, id]);

    const yesDelete = () => {
        deleteById(user.userId)
            .then(() => history.push("/"))
            .catch(() => setMessage("Delete failed."));
    };

    const cancel = () => history.push("/");

    return (
        <div>
            <h2>Delete {user.firstName}?</h2>
            <div className="alert alert-danger">
                <p>
                    All data for {user.firstName} will be permanently deleted.
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

export default UserConfirmDelete;