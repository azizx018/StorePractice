//Actions
export const LOGIN_SUCCESS = 'store/user/LOGIN_SUCCESS'
export const LOGIN_START = 'store/user/LOGIN_START'
export const LOGIN_FAILURE = 'store/user/LOGIN_FAILURE'
export const UPDATE_CREDENTIALS = 'store/user/UPDATE_CREDENTIALS'
export const LOGOUT ='store/user/LOGOUT'
//reducer

//initial state

const initialState= {
    isLoggedIn:false,
    loginPending:false,
    credentials:{username:'', password:''},

}

export default function reducer(state=initialState, action) {
    switch (action?.type) {
        case LOGOUT:
            return {
                ...state,
                isLoggedIn: false,
                credentials: {username:'', password: ''}
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
                isLoggedIn: true,
                loginPending: false
            }
        default:
            return{...state}
    }

}

//side effects