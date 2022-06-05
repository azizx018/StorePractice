import {render, screen} from "@testing-library/react";


import ProductList from "./ProductList";


it('should show a header labeled products',()=> {
    render(<ProductList _userSelector={()=>{}} _useDispatch={()=>{}}/>)
    expect(screen.getByTitle('Products')).toBeInTheDocument()
})