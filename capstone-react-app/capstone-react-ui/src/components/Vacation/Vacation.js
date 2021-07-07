import { useState, useEffect, useContext } from "react";
import ReactMapGL, {Marker} from 'react-map-gl';
import { useHistory, useParams } from "react-router";
import { findAllLocations } from "../../services/locations";
import { findById } from "../../services/vacations";
import Icon from "../Location/city-pin";
import PolylineOverlay from "./PolylineOverlay";
import { emptyComment, findByVacationId,add } from "../../services/comments";
import { findByUsername } from "../../services/users";
import LoginContext from "../../contexts/LoginContext";
import { emptyUser } from "../../services/data";
import '../../css files/main.css';

function Vacation() {

  const [locations, setLocations] = useState([]);
  const [newComment, setNewComment] = useState({ ...emptyComment });
  const [user, setUser] = useState(emptyUser);
  const [comments, setComments] = useState([]);
  const {username} = useContext(LoginContext);
  const { id } = useParams();
  const history = useHistory();

  useEffect(() => {
      findAllLocations()
        .then(setLocations)
        .catch(() => history.push("/failure"));
  }, [history]);

  useEffect(() => {
    findByVacationId(id)
      .then(setComments);
  })

  useEffect(() => {
    findByUsername(username)
        .then(setUser)
        .catch(() => history.push("/failure"));
}, [username, history]);

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

  const handleChange = evt => {
    const tempComment = { ...newComment };
    tempComment.text = evt.target.value;
    setNewComment(tempComment);
  }

  const submitComment = () => {
    const tempComment = { ...newComment };
    tempComment.userId = user.userId;
    tempComment.vacationId = id;
    setNewComment(tempComment);
    add(newComment);
  }

  return (<div className="row align-items-center white">
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
        <div className="container form-group" onSubmit={submitComment}>
          <div>Add a Comment:</div>
          <textarea id="new-comment" name="newComment" onChange={handleChange} rows="2" cols="100"></textarea>
          <div>
            <input type="submit" className="btn btn-primary"></input>
          </div>
        </div>
        <div>
          {comments.map(c => 
            <div key={c.commentId} className="container my-3 py-1 alt-colors">
              <div className="row">
                <div className="col-3">{c.userId} : </div>
                <div className="col">{c.text}</div>
              </div>
            </div>
            )}
        </div>
    </div>
  </div>);
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