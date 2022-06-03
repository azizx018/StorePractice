//Actions
export const LOGIN_SUCCESS = 'store/user/LOGIN_SUCCESS'
export const LOGIN_START = 'store/user/LOGIN_START'
export const LOGIN_FAILURE = 'store/user/LOGIN_FAILURE'
export const UPDATE_CREDENTIALS = 'store/user/UPDATE_CREDENTIALS'
export const LOGOUT ='store/user/LOGOUT'
//reducer

//initial state

const initialState= {
    token:null,
    loginPending:false,
    registerPending:false,
    credentials:{username:'', password:''},

}

export default function reducer(state=initialState, action) {
    switch (action?.type) {
        case LOGOUT:
            return {
                ...state,
                isLoggedIn: false,
                credentials: {username:'', password: ''},
                token: null
            }
        case UPDATE_CREDENTIALS:
            return {
                ...state,
                credentials: {username:action.payload.username, password: action.payload.password}
            }
        case LOGIN_FAILURE:
            return {
                ...state,
                loginPending: false
            }
        case LOGIN_START:
            return {
                ...state,
                loginPending: true
            }
        case LOGIN_SUCCESS:
            return {
                ...state,
                loginPending: false,
                token: action.payload
            }
        default:
            return{...state}
    }

}

//side effects

export function initiateLogin(_fetch=fetch) {
    return async function sideEffect(dispatch, getState) {
        dispatch({type:LOGIN_START})
        const {username,password} = getState().credentials
        const url =`http://localhost:8081/login?username=${username}&password=${password}`
        const response = await _fetch(url)

        if (response.ok){
            const token = await response.json()
            dispatch({type:LOGIN_SUCCESS, payload: token})
        } else
            dispatch({type:LOGIN_FAILURE})
    }
}

export function initiateRegister() {

}
