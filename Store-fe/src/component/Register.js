import {Button, Form} from "react-bootstrap";
import {initiateRegister, initiateRegisterOwner, ownerExistsCheck, UPDATE_CREDENTIALS} from "../modules/user";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";


export default function Register({_useSelector=useSelector, _useDispatch=useDispatch}) {
    const dispatch = _useDispatch()
    const registerPending = _useSelector(state => state.registerPending)
    const credentials= _useSelector(state => state.user.credentials)

    const isOwnerPresent = _useSelector(state => state.user?.isOwnerPresent)

    function handleSubmit(event) {
        event.preventDefault()
        dispatch(initiateRegister(isOwnerPresent))
        event.target.reset()
    }
    function updateUsername(username) {
        dispatch({type:UPDATE_CREDENTIALS, payload:{...credentials, username}})

    }
    function updatePassword(password) {
        dispatch({type:UPDATE_CREDENTIALS, payload:{...credentials, password}})

    }

    return <>
        <Form onSubmit={handleSubmit}>
            <Form.Control placeholder={'Username'} onChange={e => updateUsername(e.target.value)}></Form.Control>
            <Form.Control placeholder={'Password'} onChange={e => updatePassword(e.target.value)}></Form.Control>

            <Button type={'submit'} disabled={registerPending}>Register</Button>

        </Form>
    </>
}