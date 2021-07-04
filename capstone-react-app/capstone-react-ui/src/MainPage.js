import { useRef, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import mapboxgl from 'mapbox-gl';
 
mapboxgl.accessToken = 
'pk.eyJ1Ijoicm9iYmU4NyIsImEiOiJja3FtajIzY2owODFzMnZtem02OTJndjF3In0.xekIFGpFViXp6WPMgmSyhg';

function MainPage() {

  const mapContainer = useRef(null);
  const map = useRef(null);
  const [lng, setLng] = useState(-96.4);
  const [lat, setLat] = useState(38.8);
  const [zoom, setZoom] = useState(3.86);

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

  return (<div className="row align-items-center">
        <div class="input-group">
          <input type="search" class="form-control rounded" placeholder="Search for a location" aria-label="Search"
            aria-describedby="search-addon" />
          <Link to="/" type="button" className="btn btn-primary">search</Link>
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