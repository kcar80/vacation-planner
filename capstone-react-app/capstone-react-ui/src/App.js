import { useState } from "react";
import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";
import LoginContext from "./contexts/LoginContext";


function App() {
  const [userName, setUserName] = useState();

  const logout = () => setUserName({userName: null});

  return (<div className="container">
      <LoginContext.Provider value={{ ...userName, logout }}>
        <Router>
          <Switch>
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
