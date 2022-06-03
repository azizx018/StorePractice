import {render, screen} from "@testing-library/react";
import Register from "./Register";
import Login from "./Login";
import userEvent from "@testing-library/user-event";
import {UPDATE_CREDENTIALS} from "../modules/user";


it('should show username and password field', () => {
    render(<Register _useSelector={() => {}} _useDispatch={()=> {}}/>)
    expect(screen.getByPlaceholderText('Username')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Password')).toBeInTheDocument()
})
it('should update the username when user types in username field on register page', ()=>{
    const dispatch = jest.fn()
    const password = 'password'
    const state ={credentials: {username: '', password}}
    render(<Register _useSelector={fn =>fn(state)} _useDispatch={()=> dispatch}/>)
    const usernameElement = screen.getByPlaceholderText('Username')
    const username = 'username'
    userEvent.type(usernameElement, username)
    expect(dispatch).toHaveBeenCalledWith({type:UPDATE_CREDENTIALS, payload:{username:username, password:password}})

})
it('should update the password when user types in password field on register page', ()=>{
    const dispatch = jest.fn()
    const username = 'username'
    const state ={credentials: {username, password:''}}
    render(<Register _useSelector={fn =>fn(state)} _useDispatch={()=> dispatch}/>)
    const usernameElement = screen.getByPlaceholderText('Password')
    const password = 'password'
    userEvent.type(usernameElement, password)
    expect(dispatch).toHaveBeenCalledWith({type:UPDATE_CREDENTIALS, payload:{username:username, password:password}})

})

it('should dispatch initiateRegister when user clicks register', () => {
    const dispatch = jest.fn()
    render(<Register _useSelector={()=>{}} _useDispatch={() => dispatch}/>)
    userEvent.click(screen.getByText('Register'))
    expect(typeof dispatch.mock.calls[0][0]).toBe('function')
})

it('should enable the register button when register is not pending', () => {
    const state = {registerPending: false}
    render(<Register _useSelector={fn=>fn(state)} _useDispatch={() => {}}/>)
    expect(screen.queryByText('Register').getAttribute('disabled')).toBeNull()

})
it('should disable the register button when registerPending', () => {
    const state = {registerPending: true}
    render(<Register _useSelector={fn=>fn(state)} _useDispatch={() => {}}/>)
    expect(screen.getByText('Register').getAttribute('disabled')).not.toBeNull()

})