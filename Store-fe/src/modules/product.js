import Product from "../component/Product";
import {forEach} from "react-bootstrap/ElementChildren";

export const VIEW_PRODUCTS_SUCCESS = 'store/user/VIEW_PRODUCTS'
export const VIEW_PRODUCT_FAIL = 'store/user/VIEW_PRODUCT_FAIL'
export const VIEW_PRODUCT_START = 'store/user/VIEW_PRODUCT_START'

const initialState= {
    productPending:false,
    productList:[]

}

export default function reducer(state=initialState, action) {
    switch (action?.type) {
        case VIEW_PRODUCTS_SUCCESS:
            return {
                ...state,
                productPending: false,
                productList: action.payload
            }
        case VIEW_PRODUCT_FAIL:
            return {
                ...state,
                productPending:false
            }
        case VIEW_PRODUCT_START:
            return {
                ...state,
                productPending: true
            }
        default:
            return{...state}
    }
}

export function initiateProductView(token, _fetch=fetch) {
    return async function sideEffect(dispatch) {
        dispatch({type:VIEW_PRODUCT_START})
        const url = `http://localhost:8080/viewAllProducts?token=${token}`
        const response = await _fetch(url)

        if (response.ok) {
            const productListJson = await response.json()
            dispatch({type:VIEW_PRODUCTS_SUCCESS, payload: productListJson})
        } else
            dispatch({type:VIEW_PRODUCT_FAIL})
    }
}