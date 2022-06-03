import {render, screen} from "@testing-library/react";
import Login from "./Login";
import userEvent from "@testing-library/user-event";
import {LOGIN_START, UPDATE_CREDENTIALS} from "../modules/user";


it('should show username and password field', () => {
    render(<Login _useSelector={() => {}} _useDispatch={()=> {}}/>)
    expect(screen.getByPlaceholderText('Username')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Password')).toBeInTheDocument()
})

it('should update the username when user types in username field', ()=>{
    const dispatch = jest.fn()
    const password = 'password'
    const state ={credentials: {username: '', password}}
    render(<Login _useSelector={fn =>fn(state)} _useDispatch={()=> dispatch}/>)
    const usernameElement = screen.getByPlaceholderText('Username')
    const username = 'username'
    userEvent.type(usernameElement, username)
    expect(dispatch).toHaveBeenCalledWith({type:UPDATE_CREDENTIALS, payload:{username:username, password:password}})


})
it('should update the password when user types in password field', ()=>{
    const dispatch = jest.fn()
    const username = 'username'
    const state ={credentials: {username, password:''}}
    render(<Login _useSelector={fn =>fn(state)} _useDispatch={()=> dispatch}/>)
    const usernameElement = screen.getByPlaceholderText('Password')
    const password = 'password'
    userEvent.type(usernameElement, password)
    expect(dispatch).toHaveBeenCalledWith({type:UPDATE_CREDENTIALS, payload:{username:username, password:password}})

})

it('should dispatch LOGIN_START when user clicks login', () => {
    const dispatch = jest.fn()
    render(<Login _useSelector={()=>{}} _useDispatch={() => dispatch}/>)
    userEvent.click(screen.getByText('Login'))
    expect(dispatch).toHaveBeenCalledWith({type:LOGIN_START})
})

it('should enable the login button when loging not pending', () => {
    const state = {loginPending: false}
    render(<Login _useSelector={fn=>fn(state)} _useDispatch={() => {}}/>)
    expect(screen.queryByText('Login').getAttribute('disabled')).toBeNull()

})
it('should disable the login button when loginPending', () => {
    const state = {loginPending: true}
    render(<Login _useSelector={fn=>fn(state)} _useDispatch={() => {}}/>)
    expect(screen.getByText('Login').getAttribute('disabled')).not.toBeNull()

})