import { useContext } from "react";
import { useHistory, Link } from "react-router-dom";
import LoginContext from "../contexts/LoginContext";
import "./Nav.css";
import "../css files/main.css";


function Nav(){

    const { username, logout } = useContext(LoginContext);
    const history = useHistory();

    const handleLogout = () => {
        logout();
        history.push("/");
    }
    
    return (
    <div className="grey header-border border my-3">
      <div className="row align-items-center">
          <Link to="/" className="col pageheader">Trip Advisory Plus</Link>
          <div className="col d-flex justify-content-end">
            {username ? <button className="btn btn-primary me-2" onClick={handleLogout}>Logout</button>
              : <Link to="/login" className="btn btn-light me-2">Login</Link>}
            {username ? <Link to="/profile" className="btn btn-primary me-2">Profile</Link> : 
              <Link to="/register" className="btn btn-primary me-2">Register</Link>}
            {username==="admin" ? <Link to="/admintools" className="btn btn-primary">Admin</Link> : <div></div>}
          </div>
      </div>
      <div>
        {username ? <p>Welcome {username} to Trip Advisor Plus!</p> : <p>Welcome to Trip Advisor Plus!</p>}
      </div>
    </div>);

}

export default Nav;