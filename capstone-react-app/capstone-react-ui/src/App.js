import { useState } from "react";
import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";
import LoginContext from "./contexts/LoginContext";
import Fail from "./components/Fail";
import MainPage from "./MainPage";
import Login from "./components/Login";
import Register from "./components/Register";
import AdminTools from "./components/AdministratorTools/AdminTools";
import UserForm from "./components/AdministratorTools/UserForm";
import UserConfirmDelete from "./components/AdministratorTools/UserConfirmDelete";
import LocationConfirmDelete from "./components/AdministratorTools/LocationConfirmDelete";
import LocationForm from "./components/AdministratorTools/LocationForm";
import Nav from "./components/Nav";
import Location from "./components/Location/Location";
import Profile from "./components/Profile/Profile";
import Vacation from "./components/Vacation/Vacation";
import 'mapbox-gl/dist/mapbox-gl.css';
import VacationForm from "./components/Profile/VacationForm";

function App() {
  const [credentials, setCredentials] = useState({
    username: null,
    jwt: null
  });

  const afterAuth = token => {
    const firstDot = token.indexOf(".");
    const secondDot = token.indexOf(".", firstDot + 1);
    const jwtBody = token.substring(firstDot + 1, secondDot);
    const body = JSON.parse(atob(jwtBody));

    localStorage.setItem("jwt", token);
    setCredentials({
      username: body.sub,
      jwt: token
    });
  };

  const logout = () => setCredentials({ username: null, jwt: null })

  return (<div className="container">
    <LoginContext.Provider value={{ ...credentials, afterAuth, logout }}>
        <Router>
          <Nav/>
          <Switch>
          <Route path={["/admintools/user/edit/:id", "/admintools/user/add"]}>
            {credentials && credentials.username==="admin" ? <UserForm/> : <Redirect to="/login" />}
          </Route>
          <Route path={["/admintools/user/delete/:id"]}>
            {credentials && credentials.username==="admin" ? <UserConfirmDelete/> : <Redirect to="/login" />}
          </Route>
          <Route path={["/admintools/location/edit/:id", "/admintools/location/add"]}>
            {credentials && credentials.username==="admin" ? <LocationForm/> : <Redirect to="/login" />}
          </Route>
          <Route path={["/admintools/location/delete/:id"]}>
            {credentials && credentials.username==="admin" ? <LocationConfirmDelete/> : <Redirect to="/login" />}
          </Route>
          <Route path="/admintools">
          {credentials && credentials.username==="admin" ? <AdminTools/> : <Redirect to="/login" />}
          </Route>
          <Route path="/location/:description">
            <Location />
          </Route>
          <Route path={["/vacation/edit/:id", "/vacation/add"]}>
          {credentials && credentials.username ? <VacationForm />  : <Redirect to="/login" />}
          </Route>
          <Route path="/profile">
          {credentials && credentials.username ? <Profile/> : <Redirect to="/login" />}
          </Route>
          <Route path="/vacation:id">
            <Vacation />
          </Route>
          <Route path="/register">
            <Register />
          </Route>
          <Route path="/login">
            <Login />
          </Route>
          <Route path="/failure">
            <Fail />
          </Route>
          <Route path="/">
            <MainPage />
          </Route>
            <Route>
              <Fail />
            </Route>
          </Switch>
        </Router>
      </LoginContext.Provider>
    </div>
  );
}

export default App;