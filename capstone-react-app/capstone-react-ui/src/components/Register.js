import { useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { register } from "../services/auth";
import { emptyUser } from "../services/data";
import { add } from "../services/users";

function Register() {

    const [user, setUser] = useState(emptyUser);
    const history = useHistory();
    
    const onChange = evt => {
        const nextUser = {...user };
        nextUser[evt.target.name] = evt.target.value;
        setUser(nextUser);
    };

    const onSubmit = evt => {
        evt.preventDefault();

        register({ username: user.username, password: user.password })
            .then(() => add(user)
            .then(() => history.push("/login")))
            .catch(err => alert(err));
    };

    return (
        <form onSubmit={onSubmit}>
            <h2>Register</h2>
            <div className="form-group">
                <label htmlFor="username">Username</label>
                <input type="text" id="username" name="username" className="form-control"
                    value={user.username} onChange={onChange} />
            </div>
            <div className="form-group mb-2">
                <label htmlFor="password">Password</label>
                <input type="password" id="password" name="password" className="form-control"
                    value={user.password} onChange={onChange} />
            </div>
            <div className="form-group">
                <label htmlFor="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" className="form-control"
                    value={user.firstName} onChange={onChange} />
            </div>
            <div className="form-group mb-2">
                <label htmlFor="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" className="form-control"
                    value={user.lastName} onChange={onChange} />
            </div>
            <div className="form-group">
                <button type="submit" className="btn btn-primary me-2">Submit</button>
                <Link to="/" className="btn btn-secondary">Cancel</Link>
            </div>
        </form>
    );
}

export default Register;