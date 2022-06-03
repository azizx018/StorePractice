
import './App.css';
import {useSelector} from "react-redux";
import Login from "./component/Login";

function App({_useSelector=useSelector, LoginC=Login}) {
  const token = _useSelector(state => state.token)

  if (!token)
    return <LoginC/>
  else
    return <>
      You shall not pass
    </>
}

export default App;
