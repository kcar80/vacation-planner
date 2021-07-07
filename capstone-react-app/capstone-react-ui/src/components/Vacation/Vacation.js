import { useState, useEffect, useParams } from "react";
import ReactMapGL, {Marker} from 'react-map-gl';
import { useHistory } from "react-router";
import { findAllLocations } from "../../services/locations";
import { findById } from "../../services/vacations";
import Icon from "../Location/city-pin";
import PolylineOverlay from "./PolylineOverlay";

function Vacation() {

    const [locations, setLocations] = useState([]);
    const [vacation, setVacation] = useState([]);
    const history = useHistory();
    const { id } = useParams();

    useEffect(() => {
        findById(id)
            .then(setVacation)
            .catch(() => history.push("/failure"));
    }, [id, history]);

    useEffect(() => {
        findAllLocations()
            .then(setLocations)
            .catch(() => history.push("/failure"));
    }, [history]);

    const [viewport, setViewport] = useState({
        latitude: 38.8,
        longitude: -96.4,
        zoom: 3.5
    });

    const onMapClick = description => {
        history.push(`/location/${description}`);
    };

    const markers = () => locations.map(
        location => (
        <Marker key={location.description} longitude={location.longitude} latitude={location.latitude} >
            <Icon onClick={() => onMapClick(location.description)}/>
        </Marker>
        )
    );

    const points = () => locations.map(
        location => [location.longitude, location.latitude]   
    );

    return (<div className="row align-items-center">
        <div className="container">
            <h3>View your vacation</h3>
        </div>
        <div className="mapbox-react">
        <ReactMapGL
            {...viewport}
            width="1300px"
            height="500px"
            mapStyle="mapbox://styles/mapbox/streets-v11"
            onViewportChange={nextViewport => setViewport(nextViewport)}
            mapboxApiAccessToken={`pk.eyJ1Ijoicm9iYmU4NyIsImEiOiJja3FtajIzY2owODFzMnZtem02OTJndjF3In0.xekIFGpFViXp6WPMgmSyhg`}>
            <PolylineOverlay points={points()} />
            {markers()}
        </ReactMapGL>
        </div>
        <div className="container">
            <h3>Vacation Comments</h3>
        </div>
    </div>);
}

export default Vacation;