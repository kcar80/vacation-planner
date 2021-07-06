import { useState } from "react";
import ReactMapGL from 'react-map-gl';
import { Link } from "react-router-dom";

function MainPage() {

  const [description, setDescription] = useState([]);

  const [viewport, setViewport] = useState({
    latitude: 38.8,
    longitude: -96.4,
    zoom: 3.86,
    bearing: 0,
    pitch: 0,
  });

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
        mapboxApiAccessToken={`pk.eyJ1Ijoicm9iYmU4NyIsImEiOiJja3FtajIzY2owODFzMnZtem02OTJndjF3In0.xekIFGpFViXp6WPMgmSyhg`}
      />
    </div>
  </div>);
}

export default MainPage;