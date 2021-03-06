import {useDispatch, useSelector} from "react-redux";
import {initiateRegisterOwner, UPDATE_CREDENTIALS} from "../modules/user";
import {Button, Form} from "react-bootstrap";


export default function LandingPage({_useSelector=useSelector, _useDispatch=useDispatch}) {
    const dispatch = _useDispatch()
    const registerPending = _useSelector(state => state.registerPending)
    const credentials= _useSelector(state => state.credentials)

    function handleSubmit(event) {
        event.preventDefault()
        dispatch(initiateRegisterOwner())
    }
    function updateUsername(username) {
        dispatch({type:UPDATE_CREDENTIALS, payload:{...credentials, username}})

    }
    function updatePassword(password) {
        dispatch({type:UPDATE_CREDENTIALS, payload:{...credentials, password}})

    }

    return <>
        <h3>Welcome! Let's setup your account!</h3>
        <Form onSubmit={handleSubmit}>
            <Form.Control placeholder={'Username'} onChange={e => updateUsername(e.target.value)}></Form.Control>
            <Form.Control placeholder={'Password'} onChange={e => updatePassword(e.target.value)}></Form.Control>

            <Button type={'submit'} disabled={registerPending}>Register</Button>

        </Form>
    </>
}