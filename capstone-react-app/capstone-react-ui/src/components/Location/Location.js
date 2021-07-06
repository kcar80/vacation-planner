import { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import { findByDescription } from "../../services/locations";
import { emptyLocation } from "../../services/data";
import ReactMapGL, {Marker} from 'react-map-gl';
import 'mapbox-gl/dist/mapbox-gl.css';
import mapicon from './map-marker.png';

function Location() {

    const [location, setLocation] = useState(emptyLocation);
    const history = useHistory();
    const { description } = useParams();

    const [viewport, setViewport] = useState({
        latitude: 38.8,
        longitude: -96.4,
        zoom: 3.86,
        bearing: 0,
        pitch: 0,
      });

    useEffect(() => {
        if (description) {
            findByDescription(description)
                .then(l => setLocation(l))
                .then(setViewport({
                    longitude: location.longitude,
                    latitude: location.latitude,
                    zoom: 14}))
                .catch(() => history.push("/failure"));
        }
    }, [history, description, location.longitude, location.latitude]);

    return (<div className="row align-items-center">
        <div className="col">
            <h3>Location: {location.description}</h3>
        </div>
        <div className="mapbox-react">
            <ReactMapGL
                {...viewport}
                width="1300px"
                height="700px"
                mapStyle="mapbox://styles/mapbox/streets-v11"
                onViewportChange={nextViewport => setViewport(nextViewport)}
                mapboxApiAccessToken={`pk.eyJ1Ijoicm9iYmU4NyIsImEiOiJja3FtajIzY2owODFzMnZtem02OTJndjF3In0.xekIFGpFViXp6WPMgmSyhg`}>
                <Marker latitude={location.latitude} longitude={location.longitude}>
                    <img src={mapicon} alt="map icon" />
                </Marker>
            </ReactMapGL>
        </div>
    </div>);
}

export default Location;