//Actions
export const LOGIN_SUCCESS = 'store/user/LOGIN_SUCCESS'
export const LOGIN_START = 'store/user/LOGIN_START'
export const LOGIN_FAILURE = 'store/user/LOGIN_FAILURE'
export const UPDATE_CREDENTIALS = 'store/user/UPDATE_CREDENTIALS'
export const LOGOUT ='store/user/LOGOUT'
export const REGISTER_START = 'store/user/REGISTER_START'
export const REGISTER_FAILURE = 'store/user/REGISTER_FAILURE'
export const REGISTER_SUCCESS = 'store/user/REGISTER_SUCCESS'
//reducer

//initial state

const initialState= {
    //userList:[],
    token:null,
    loginPending:false,
    registerPending:false,
    credentials:{username:'', password:''},
    failureMessage:null

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
        case REGISTER_START:
            return {
                ...state,
                registerPending: true
            }
        case REGISTER_FAILURE:
            return {
                ...state,
                registerPending: false,

            }
        case REGISTER_SUCCESS:
            return {
                ...state,
                registerPending: false,


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
// export function badLogin(_fetch=fetch) {
//     return async function sideEffect(dispatch, getState) {
//
//         if (response.ok){
//             const token = await response.json()
//             dispatch({type:LOGIN_SUCCESS, payload: token})
//         } else
//             dispatch({type:LOGIN_FAILURE})
//     }
// }


export function initiateRegisterOwner(_fetch=fetch) {
    return async function sideEffect(dispatch, getState) {
        dispatch({type:REGISTER_START})
        const {username, password} = getState().credentials
        const url =`http://localhost:8081/registerOwner?username=${username}&password=${password}`
        const response = await _fetch(url)

        if (response.ok) {
            dispatch({type:REGISTER_SUCCESS})
        } else
            dispatch({type:REGISTER_FAILURE})
    }

}
// export function firstOwner(_fetch=fetch) {
//     return async function sideEffect(dispatch, getState) {
//         dispatch({type:REGISTER_START})
//         const {username, password} = getState().credentials
//         const urlExistingUser ='http://localhost:8081/existingUserInDatabase'
//         const urlOwner =`http://localhost:8081/registerOwner?username=${username}&password=${password}`
//         const urlCustomer =`http://localhost:8081/registerCustomer?username=${username}&password=${password}`
//         const responseExistingUser = await _fetch(urlExistingUser)
//         const ownerResponse = await _fetch(urlOwner)
//         const customerResponse = await _fetch (urlCustomer)
//         if (responseExistingUser === false) {
//             if (ownerResponse.ok) {
//                 dispatch({type:REGISTER_SUCCESS})
//             } else {
//                 dispatch({type:REGISTER_FAILURE})
//             }
//
//         }
//     }
//
// }
// export function initiateFirstOwner(_fetch=fetch) {
//     return async function sideEffect(dispatch, getState) {
//         dispatch({type:REGISTER_START})
//         const {username, password} = getState().credentials
//         const url =`http://localhost:8081/registerOwner?username=${username}&password=${password}`
//         const response = await _fetch(url)
//
//         if (response.ok) {
//             dispatch({type:REGISTER_SUCCESS})
//         } else
//             dispatch({type:REGISTER_FAILURE})
//     }
//
// }
