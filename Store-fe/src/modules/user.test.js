import reducer, {
    initiateLogin,
    LOGIN_FAILURE,
    LOGIN_START,
    LOGIN_SUCCESS,
    LOGOUT,
    UPDATE_CREDENTIALS,
    VIEW_PRODUCTS
} from "./user";

it('should start with a null token', () =>{
    const state = reducer()
    expect(state.token).toBe(null)
})

// it('should start not logged in', () => {
//     const state = reducer();
//     expect(state.isLoggedIn).toBe(false);
// })

it('should start not pending a login', () => {
    const state = reducer()
    expect(state.loginPending).toBe(false)
})

it('should set loginPending true when LOGIN_START', () => {
    const initialState = reducer()
    const state = reducer(initialState, {type:LOGIN_START})
    expect(state.loginPending).toBe(true)
})

it('should set loginPening false and token and isLoggedIn true when LOGIN_SUCCESS',() => {
    const initialState = reducer();
    initialState.loginPending = true
    const token = 'some token'
    const state = reducer(initialState, {type:LOGIN_SUCCESS, payload:token})
    //expect(state.isLoggedIn).toBe(true);
    expect(state.loginPending).toBe(false);
    expect(state.token).toBe(token)
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
it('should set isLoggedIn to false and credentials to blank and token to null when LOGOUT', () =>{
    const initialState = reducer()
    initialState.token = 'some token'
    initialState.isLoggedIn = true
    initialState.credentials = {username: 'username', password: 'password'}
    const state = reducer(initialState, {type:LOGOUT})
    expect(state.isLoggedIn).toBe(false)
    expect(state.credentials).toStrictEqual({username:'', password:''})
    expect(state.token).toBe(null)
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
    const token= 'some token'
    const url =`http://localhost:8081/login?username=${username}&password=${password}`
    let _url

    const mockFetch = (url) =>{
        _url=url
        return new Promise(resolve => resolve({
            ok:true,
            //json of promise will respond with token
            json: () => new Promise(res => res(token))
        }))

    }
    const dispatch = jest.fn()
    const state = {credentials:{username, password}}
    const getState = () => state
    const sideEffect = initiateLogin(mockFetch)
    await sideEffect(dispatch, getState)
    expect(_url).toBe(url)
    expect(dispatch).toHaveBeenCalledWith({type:LOGIN_START})
    expect(dispatch).toHaveBeenCalledWith({type:LOGIN_SUCCESS, payload:token})

})

it('should display products', () => {
    const initialState = reducer()
    const payload = {productName:'Shoes'}
    const state = reducer(initialState, {type:VIEW_PRODUCTS, payload:payload})
    expect(state.productList).toStrictEqual(payload)
})