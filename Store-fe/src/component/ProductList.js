import {useDispatch, useSelector} from "react-redux";
import Product from "./Product";
import {initiateProductView} from "../modules/product";
import {useEffect} from "react";

export default function ProductList({
                                        _Product=Product,
                                        _useSelector=useSelector,
                                        _useDispatch=useDispatch,
                                        _initiateProductView=initiateProductView}) {
    const dispatch = _useDispatch()
    const token =_useSelector(state => state.user?.token)
    const productList = _useSelector(state => state.product?.productList)

    useEffect(() => {
        dispatch(_initiateProductView(token))
    },[])

    return<div >
        <div>
            {productList?.map((product,index) => <div key={index}>
                <_Product product={product}/>
            </div>)
            }

        </div>

    </div>
}