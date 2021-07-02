import { useContext } from "react";
import { useHistory, Link } from "react-router-dom";
import LoginContext from "./contexts/LoginContext";

function MainPage() {

  const { username, logout } = useContext(LoginContext);
  const history = useHistory();

  const handleLogout = () => {
    logout();
    history.push("/");
  };

  return (<div className="row align-items-center">
        <h1 className="col">Trip Advisory Plus</h1>
        <div className="col d-flex justify-content-end">
          {username ? <button className="btn btn-primary" onClick={handleLogout}>Logout</button>
            : <Link to="/login" className="btn btn-light">Login</Link>}
          <Link to="/profile" className={`btn btn-primary${(username ? "": " disabled")}`}>Profile</Link>
          <Link to="/register" className={`btn btn-primary${(username ? " disabled": "")}`}>Register</Link>
        </div>
        <div>
        {username ? <p>Welcome {username} to Trip Advisory Plus!</p> : <p>Welcome to Trip Advisory Plus!</p>}
        </div>
    </div>);
}

export default MainPage;