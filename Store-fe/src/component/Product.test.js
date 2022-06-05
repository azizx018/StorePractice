import {render, screen} from "@testing-library/react";

import Product from "./Product";


it('should display the name of the product', () => {
    const product = {productName:'shoes'}
    render(<Product product={product}/>)
    expect(screen.getByText(product.productName)).toBeInTheDocument()
})

