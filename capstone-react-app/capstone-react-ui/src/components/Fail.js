import { Link } from "react-router-dom";

function Fail() {
    return  (
    <div className="row align-items-center">
        <h1 className="col">Page Not Found</h1>
        <div className="col d-flex justify-content-end">
            <Link to="/" className="btn btn-primary">Main Menu</Link>
        </div>
    </div>
    );
}

export default Fail;