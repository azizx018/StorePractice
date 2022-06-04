
import './App.css';
import {useSelector} from "react-redux";
import Login from "./component/Login";
import Register from "./component/Register";

function App({_useSelector=useSelector, LoginC=Login, RegisterC=Register}) {
  const token = _useSelector(state => state.token)


  if (!token)
    return <>
      <LoginC/>
      <RegisterC/>

    </>


  else
    return <>
      You shall not pass
    </>
}

export default App;
