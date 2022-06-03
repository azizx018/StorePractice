import reducer, {initiateLogin, LOGIN_FAILURE, LOGIN_START, LOGIN_SUCCESS, LOGOUT, UPDATE_CREDENTIALS} from "./user";


it('should start not logged in', () => {
    const state = reducer();
    expect(state.isLoggedIn).toBe(false);
})

it('should start not pending a login', () => {
    const state = reducer()
    expect(state.loginPending).toBe(false)
})

it('should set loginPending true when LOGIN_START', () => {
    const initialState = reducer()
    const state = reducer(initialState, {type:LOGIN_START})
    expect(state.loginPending).toBe(true)
})

it('should set loginPening false and isLoggedIn true when LOGIN_SUCCESS',() => {
    const initialState = reducer();
    const state = reducer(initialState, {type:LOGIN_SUCCESS})
    expect(state.isLoggedIn).toBe(true);
    expect(state.loginPending).toBe(false);
})

it('should set loginPening false when LOGIN_FAILURE',() => {
    const initialState = reducer();
    const state = reducer(initialState.loginPending=true, {type:LOGIN_FAILURE})
    expect(state.loginPending).toBe(false);
})

it('should start with blank credentials', () => {
    const state = reducer()
    expect(state.credentials).toStrictEqual({username:'', password:''})
})

it('should update credentials when UPDATE_CREDENTIALS', () =>{
    const initialState = reducer()
    const payload = {username:'username', password:'password'}
    const state = reducer(initialState, {type:UPDATE_CREDENTIALS, payload:payload})
    expect(state.credentials).toStrictEqual(payload)
})
it('should set isLoggedIn to false and credentials to blank when LOGOUT', () =>{
    const initialState = reducer()
    initialState.isLoggedIn = true
    initialState.credentials = {username: 'username', password: 'password'}
    const state = reducer(initialState, {type:LOGOUT})
    expect(state.isLoggedIn).toBe(false)
    expect(state.credentials).toStrictEqual({username:'', password:''})
})

it('should dispatch LOGIN_START then LOGIN_FAILURE when initiateLogin w/bad creds', async () =>{
    const username = 'username'
    const password = 'password'
    const url =`http://localhost:8081/login?username=${username}&password=${password}`
    let _url

    const mockFetch = (url) =>{
        _url=url
        return new Promise(resolve => resolve({ok:false}))

    }
    const dispatch = jest.fn()
    const state = {credentials:{username, password}}
    const getState = () => state
    const sideEffect = initiateLogin(mockFetch)
    await sideEffect(dispatch, getState)
    expect(_url).toBe(url)
    expect(dispatch).toHaveBeenCalledWith({type:LOGIN_START})
    expect(dispatch).toHaveBeenCalledWith({type:LOGIN_FAILURE})

})
it('should dispatch LOGIN_START then LOGIN_SUCCESS when initiateLogin w/good creds', async () =>{
    const username = 'username'
    const password = 'password'
    const url =`http://localhost:8081/login?username=${username}&password=${password}`
    let _url

    const mockFetch = (url) =>{
        _url=url
        return new Promise(resolve => resolve({ok:true}))

    }
    const dispatch = jest.fn()
    const state = {credentials:{username, password}}
    const getState = () => state
    const sideEffect = initiateLogin(mockFetch)
    await sideEffect(dispatch, getState)
    expect(_url).toBe(url)
    expect(dispatch).toHaveBeenCalledWith({type:LOGIN_START})
    expect(dispatch).toHaveBeenCalledWith({type:LOGIN_SUCCESS})

})