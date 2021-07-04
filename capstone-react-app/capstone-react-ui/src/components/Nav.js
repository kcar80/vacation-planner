import { useContext } from "react";
import { useHistory, Link } from "react-router-dom";
import LoginContext from "../contexts/LoginContext";


function Nav(){

    const { username, logout } = useContext(LoginContext);
    const history = useHistory();

    const handleLogout = () => {
        logout();
        history.push("/");
    }

    return (
    <div className="row align-items-center">
        
        <div className="col d-flex justify-content-end">
          {username ? <button className="btn btn-primary" onClick={handleLogout}>Logout</button>
            : <Link to="/login" className="btn btn-light m-1">Login</Link>}
          <Link to="/profile" className={`btn btn-primary m-1${(username ? "": " disabled")}`}>Profile</Link>
          <Link to="/register" className={`btn btn-primary m-1${(username ? " disabled": "")}`}>Register</Link>
          {username==="admin" ? <Link to="/admintools" className="btn btn-primary">Admin</Link> : <div></div>}
        </div>

    </div>
    );

}

export default Nav;