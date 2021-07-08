import { useState, useEffect, useContext } from "react";
import { useHistory, useParams } from "react-router";
import { emptyVacation } from "../../services/data";
import {findById, add, update} from "../../services/vacations";
import { emptyUser } from "../../services/data";
import { emptyVacationStop } from "../../services/data";
import {emptyVacationUser} from "../../services/data";
import { findByUsername } from "../../services/users";
import LoginContext from "../../contexts/LoginContext";
import {addVacationUser, updateVacationUser} from "../../services/vacationuser";
import {emptyLocation} from "../../services/data";

function VacationForm(){
    const[vacation, setVacation] = useState(emptyVacation);
    const [user, setUser] = useState(emptyUser);
    const [location, setLocation] = useState(emptyLocation);
    const[vacationStop, setVacationStop] =useState(emptyVacationStop);
    const[vacationUser, setVacationUSer] =useState(emptyVacationUser);
    const {username} = useContext(LoginContext);
    const history = useHistory();
    const { id } = useParams();

    useEffect(() => {
        findByUsername(username)
            .then(setUser)
            .catch(() => history.push("/failure"));
    }, [username, history]);

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

    // const onSubmit = evt => {
    //     evt.preventDefault();
    //     (vacation.vacationId > 0 ? update(vacation) : add(vacation))
    //         .then(v=>{
    //             setVacation(v);
    //                 return v;
    //             })
    //         .then( v=> addVacationUser({
    //             vacationId: v.vacationId,
    //              user: user, identifier: `${v.vacationId} ${user.userId}`}))
    //         .then(() => history.push("/profile"))
    //         .catch();
    // };

    const onSubmit = evt => {
        evt.preventDefault();
        if(vacation.vacationId > 0){
            update(vacation)
            .then(() => history.push("/profile"))
            .catch(console.error);}
        else if(vacation.vacationId == 0){
            add(vacation)
            .then(v=>{
                setVacation(v);
                    return v;
                })
            .then( v=> addVacationUser({
                vacationId: v.vacationId,
                 user: user, identifier: `${v.vacationId} ${user.userId}`}))
            .then(() => history.push("/profile"))
            .catch(console.error);}
    };

    // const onSubmit = evt => {
    //     evt.preventDefault();

    //     register({ username: user.username, password: user.password })
    //         .then(() => add(user)
    //         .then(() => history.push("/login")))
        

    const cancel = evt => {
        evt.preventDefault();
        history.push("/profile");
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
                <label htmlFor="leisureLevel">Leisure Level</label>
                <input type="text" className="form-control" id="leisureLevel" name="leisureLevel"
                    value={vacation.leisureLevel} onChange={onChange} required />
            </div>
           

            <div className="form-group">
                <button type="submit" className="btn btn-primary me-2">Save</button>
                <button className="btn btn-secondary" onClick={cancel}>Cancel</button>
            </div> 

            
        </form>
    );


}

export default VacationForm;