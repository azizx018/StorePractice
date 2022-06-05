import {useDispatch, useSelector} from "react-redux";
import Product from "./Product";

export default function ProductList({_Product =Product, _userSelector=useSelector}) {
    const productList = _userSelector(state => state.product.productList)

    return<div>

        <div>
            {productList?.map((product,index) => <div key={index}>
                <_Product product={product}/>
            </div>)
            }

        </div>

    </div>
}