import { useState, useEffect } from "react";
import ReactMapGL, {Marker} from 'react-map-gl';
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import { findAllLocations } from "./services/locations";
import Icon from "./components/Location/city-pin";

function MainPage() {

  const [locations, setLocations] = useState([]);
  const [description, setDescription] = useState([]);
  const history = useHistory();

  useEffect(() => {
      findAllLocations()
        .then(setLocations)
        .catch(() => history.push("/failure"));
  }, [history]);

  const [viewport, setViewport] = useState({
    latitude: 38.8,
    longitude: -96.4,
    zoom: 3.86
  });

  const onMapClick = description => {
    history.push(`/location/${description}`);
  };

  const markers = () => locations.map(
    location => (
      <Marker key={location.description} longitude={location.longitude} latitude={location.latitude} >
        <Icon onClick={()=> onMapClick(location.description)}/>
      </Marker>
    )
  );

  return (<div className="row align-items-center">
    <form>
      <div className="input-group">
        <input type="text" className="form-control" placeholder="Search for a location" name="description"
          value={description} onChange={e => setDescription(e.target.value)} required />
        <Link to={`/location/${description}`} className="btn btn-primary">Search</Link>
      </div>
    </form>

    <div className="mapbox-react">
      <ReactMapGL
        {...viewport}
        width="1300px"
        height="700px"
        mapStyle="mapbox://styles/mapbox/streets-v11"
        onViewportChange={nextViewport => setViewport(nextViewport)}
        mapboxApiAccessToken={`pk.eyJ1Ijoicm9iYmU4NyIsImEiOiJja3FtajIzY2owODFzMnZtem02OTJndjF3In0.xekIFGpFViXp6WPMgmSyhg`}>
        {markers()}
      </ReactMapGL>
    </div>
  </div>);
}

export default MainPage;