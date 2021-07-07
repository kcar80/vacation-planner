import { useState, useEffect } from "react";
import { useHistory, useParams } from "react-router";
import { emptyVacation } from "../../services/data";
import {findById, add, update} from "../../services/vacations";

function VacationForm(){
    const[vacation, setVacation] = useState(emptyVacation);
    const history = useHistory();
    const { id } = useParams();


 useEffect(() => {
        if (id) {
            findById(id)
                .then(v => setVacation(v))
                .catch(() => history.push("/failure"));
        }
    }, [history, id]);

    const onChange = evt => {
        const nextVacation = { ...vacation };
            nextVacation[evt.target.name] = evt.target.value;
        setVacation(nextVacation);
    };

    const onSubmit = evt => {
        evt.preventDefault();
        (vacation.vacationId > 0 ? update(vacation) : add(vacation))
            .then(() => history.push("/vacation"))
            .catch();
    };

    const cancel = evt => {
        evt.preventDefault();
        history.push("/vacation");
    };

    return (
        <form onSubmit={onSubmit}>
            <h2>{`${(vacation.vacationId > 0 ? "Edit" : "Add")} a Vacation`}</h2>
            <div className="form-group">
                <label htmlFor="description">Description</label>
                <input type="text" className="form-control" id="description" name="description"
                    value={vacation.description} onChange={onChange} required />
            </div>

            <div className="form-group">
                <label htmlFor="latitude">Leisure Level</label>
                <input type="text" className="form-control" id="leasureLevel" name="leasureLevel"
                    value={vacation.leasureLevel} onChange={onChange} required />
            </div>

            
        </form>
    );


}

export default VacationForm;