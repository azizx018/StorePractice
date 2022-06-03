import logo from './logo.svg';
import './App.css';
import {useSelector} from "react-redux";
import Login from "./component/Login";

function App({_useSelector=useSelector, LoginC=Login}) {
  const isLoggedIn = _useSelector(state => state.isLoggedIn)

  if (!isLoggedIn)
    return <LoginC/>
  else
    return <>
      You shall not pass
    </>
}

export default App;
