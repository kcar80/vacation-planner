import { useState } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import LoginContext from "./contexts/LoginContext";
import Fail from "./components/Fail";
import MainPage from "./MainPage";
import Login from "./components/Login";
import Register from "./components/Register";


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
    setCredentials({
      username: body.sub,
      jwt: token
    });
  };

  const logout = () => setCredentials({ username: null, jwt: null })

  return (<div className="container">
    <LoginContext.Provider value={{ ...credentials, afterAuth, logout }}>
        <Router>
          <Switch>
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
