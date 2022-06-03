import {Button, Form, FormControl} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {initiateLogin, LOGIN_START, UPDATE_CREDENTIALS} from "../modules/user";

export default function Login({_useSelector=useSelector, _useDispatch=useDispatch}) {
    const dispatch = _useDispatch()
    const loginPending = _useSelector(state=> state.loginPending)
    const credentials= _useSelector(state => state.credentials)

    function updateUsername(username) {
        dispatch({type:UPDATE_CREDENTIALS, payload:{...credentials, username}})

    }
    function updatePassword(password) {
        dispatch({type:UPDATE_CREDENTIALS, payload:{...credentials, password}})

    }

    function handleSubmit(event) {
        event.preventDefault()
        dispatch(initiateLogin())

    }

    return<>
        <Form onSubmit={handleSubmit}>
            <Form.Control placeholder={'Username'} onChange={e => updateUsername(e.target.value)}></Form.Control>
            <Form.Control placeholder={'Password'} onChange={e => updatePassword(e.target.value)}></Form.Control>

            <Button type={'submit'} disabled={loginPending}>Login</Button>

        </Form>

    </>
}