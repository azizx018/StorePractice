import { render, screen } from '@testing-library/react';
import App from './App';
import ProductList from "./component/ProductList";
import {VIEW_PRODUCTS} from "./modules/user";
import Product from "./component/Product";
import {VIEW_PRODUCTS_SUCCESS} from "./modules/product";

test('it should show the login component when not logged in', () => {
  const state = {
    token:null
  }
  const expectedText = "expextedText"
  const mock = () => <>{expectedText}</>
  render(<App _useSelector={fn => fn(state)} LoginC={mock}/>);

  expect(screen.getByText(expectedText)).toBeInTheDocument();

});

test('it should not show the login component when logged in', () => {
  const state = {
    token:'some token'
  }
  const expectedText = "expextedText"
  const mock = () => <>{expectedText}</>
  render(<App _useSelector={fn => fn(state)} LoginC={mock}/>);

  expect(screen.queryByText(expectedText)).not.toBeInTheDocument();

});

it('should display product names', () => {
  const dispatch = jest.fn()
  const productName = 'Shoes'
  const state = {product: {productList: {productName}}}
  render(<ProductList _useSelector={fn=>fn(state)} _useDispatch={()=> dispatch} _Product={Product}/>)
  expect(dispatch).toHaveBeenCalledWith({type:VIEW_PRODUCTS_SUCCESS, payload:{productName:productName}})
})

it('should dispatch initiateviewAllProducts with the token when the page loads', () => {
  const dispatch = jest.fn()
  const initProducts = jest.fn()
  const ret = 'some return'
  initProducts.mockImplementation(() => ret)
  const token = 'some token'
  const state = {user: {token}}
  render(<ProductList  _useSelector={fn => fn(state)} _useDispatch={()=> dispatch} _initiateProductView={initProducts}/>)
  expect(initProducts).toHaveBeenCalledWith(token)
  expect(dispatch).toHaveBeenCalledWith(ret)
})