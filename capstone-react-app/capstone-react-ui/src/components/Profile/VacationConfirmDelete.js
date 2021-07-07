import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router";
import { findById, deleteById } from "../../services/vacations";

function UserConfirmDelete() {

    const [vacation, setVacation] = useState({ description: "" });
    const history = useHistory();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            findById(id)
                .then(v => setVacation(v))
                .catch(() => history.push("/failure"));
        }
    }, [history, id]);

    const yesDelete = () => {
        deleteById(vacation.vacationId)
            .then(() => history.push("/profile"))
            .catch(() => history.push("/failure"));
    };

    const cancel = evt => {
        evt.preventDefault();
        history.push("/profile");
    };

    return (
        <div>
            <h2>Delete {vacation.description}?</h2>
            <div className="alert alert-danger">
                <p>
                    All data for {vacation.description} will be permanently deleted.
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