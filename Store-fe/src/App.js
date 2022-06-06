
import './App.css';
import {useDispatch, useSelector} from "react-redux";
import Login from "./component/Login";
import Register from "./component/Register";
import ProductList from "./component/ProductList";
import ErrorList from "./component/ErrorList";
import {initiateProductView} from "./modules/product";
import {useEffect} from "react";
import {ownerExistsCheck} from "./modules/user";

function App({_useSelector=useSelector,
               LoginC=Login,
               RegisterC=Register,
                 ProductListC =ProductList,
                 ErrorListC =ErrorList,
               _useDispatch=useDispatch,
               _initiateProductView=initiateProductView})
{
    const token = _useSelector(state => state.user?.token)
    const isOwnerPresent = _useSelector(state => state.user?.isOwnerPresent)
    const error = _useSelector(state => state.user?.error)
    const dispatch = _useDispatch()

    useEffect(() => {
        dispatch(ownerExistsCheck())
    }, [])

    if (!token)
        return <>
            <ErrorListC />
            {isOwnerPresent ? <LoginC/> : <></>}
            <RegisterC/>
        </>
    else
        return <>
            <ErrorListC />
            <ProductListC/>
        </>
}

export default App;
