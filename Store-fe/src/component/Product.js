
import {Card} from "react-bootstrap";

export default function Product({product}) {
    const {productName} = product
    //const productList = _userSelector(state => state.productList)

    return<Card>
        <Card.Header><h4>{productName}</h4></Card.Header>
        <Card.Body>
            <h3>Buy Me!</h3>
        </Card.Body>

    </Card>
}