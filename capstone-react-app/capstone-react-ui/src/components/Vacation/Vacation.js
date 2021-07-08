import { useState, useEffect } from "react";
import { useParams } from "react-router";
import ReactMapGL, {Marker, Popup} from 'react-map-gl';
import { useHistory } from "react-router";
import { emptyVacation } from "../../services/data";
import { findById } from "../../services/vacations";
import Icon from "../Location/city-pin";
import PolylineOverlay from "./PolylineOverlay";

function Vacation() {

    const [vacation, setVacation] = useState(emptyVacation);
    const history = useHistory();
    const { id } = useParams();
    
    useEffect(() => {
        findById(id)
            .then(setVacation)
            .catch(() => history.push("/failure"));
    }, [id, history]);

    const [viewport, setViewport] = useState({
        latitude: 38.8,
        longitude: -96.4,
        zoom: 3.5
    });

    const onMapClick = description => {
        history.push(`/location/${description}`);
    };

    const markers = () => vacation.locations.map(l => l.location).map(
        location => (
        <Marker key={location.description} longitude={location.longitude} latitude={location.latitude} >
            <Icon onClick={() => onMapClick(location.description)}/>
        </Marker>
        )
    );

    const points = () => vacation.locations.map(l => l.location).map(
        location => [location.longitude, location.latitude]   
    );


    const popups = () => vacation.locations.map(l => l.location).map(
        location => (
            <Popup key={location.locationId} longitude={location.longitude} latitude={location.latitude} closeButton={false} closeOnClick={false} anchor="bottom" >
                <div>{location.description}</div>
            </Popup>
        )
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
            {popups()}
            {markers()}
        </ReactMapGL>
        </div>
        <div className="container">
            <h3>Vacation Comments</h3>
        </div>
    </div>);
}

export default Vacation;