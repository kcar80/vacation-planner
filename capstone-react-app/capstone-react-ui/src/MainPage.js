import { useContext, useRef, useEffect, useState } from "react";
import { useHistory, Link } from "react-router-dom";
import LoginContext from "./contexts/LoginContext";
import mapboxgl from 'mapbox-gl';
 
mapboxgl.accessToken = 
'pk.eyJ1Ijoicm9iYmU4NyIsImEiOiJja3FtajIzY2owODFzMnZtem02OTJndjF3In0.xekIFGpFViXp6WPMgmSyhg';

function MainPage() {

  const mapContainer = useRef(null);
  const map = useRef(null);
  const [lng, setLng] = useState(-96.4);
  const [lat, setLat] = useState(38.8);
  const [zoom, setZoom] = useState(3.86);

  const { username, logout } = useContext(LoginContext);
  const history = useHistory();

  useEffect(() => {
    if (map.current) return; // initialize map only once
    map.current = new mapboxgl.Map({
      container: mapContainer.current,
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [lng, lat],
      zoom: zoom
    });
  });

  useEffect(() => {
    if (!map.current) return; // wait for map to initialize
    map.current.on('move', () => {
      setLng(map.current.getCenter().lng.toFixed(4));
      setLat(map.current.getCenter().lat.toFixed(4));
      setZoom(map.current.getZoom().toFixed(2));
    });
  });

  const handleLogout = () => {
    logout();
    history.push("/");
  };

  return (<div className="row align-items-center">
        <h1 className="col">Trip Advisory Plus</h1>
        <div className="col d-flex justify-content-end">
          {username ? <button className="btn btn-primary" onClick={handleLogout}>Logout</button>
            : <Link to="/login" className="btn btn-light">Login</Link>}
          {username ? <Link to="/profile" className="btn btn-primary">Profile</Link> : 
            <Link to="/register" className="btn btn-primary">Register</Link>}
          {username==="admin" ? <Link to="/admin" className="btn btn-primary">Admin</Link> : <div></div>}
        </div>
        <div>
        {username ? <p>Welcome {username} to Trip Advisory Plus!</p> : <p>Welcome to Trip Advisory Plus!</p>}
        </div>
        <div class="input-group">
          <input type="search" class="form-control rounded" placeholder="Search for a location" aria-label="Search"
            aria-describedby="search-addon" />
          <Link to="/" type="button" className="btn btn-outline-primary">search</Link>
        </div>
        <div className="container">
          <div ref={mapContainer} className="map-container" />
        </div>
        <div>
          {lng} {lat} {zoom}
        </div>
    </div>);
}

export default MainPage;