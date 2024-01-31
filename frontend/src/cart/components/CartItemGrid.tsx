import { SimpleGrid } from '@chakra-ui/react';
import CartItemCart from './CartItemCard/CartItemCard';
import { CartItemGridProps } from '../../types';

const CartItemGrid = ({ products }: CartItemGridProps) => {
    return (
        <SimpleGrid columns={{ base: 1, md: 2, xl: 3 }} spacing={4}>
            {products.map(
                ({
                     name,
                     author,
                     price,
                     imgUrl,
                     id,
                 }) => (
                    <CartItemCart
                        id={id}
                        key={id}
                        name={name}
                        author={author}
                        price={price}
                        imgUrl={imgUrl}
                    />
                )
            )}
        </SimpleGrid>
    );
};

export default CartItemGrid;
