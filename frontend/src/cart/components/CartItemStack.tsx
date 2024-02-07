import {Stack} from '@chakra-ui/react';
import CartItemCart from './CartItemCard/CartItemCard';
import {CartItemGridProps} from '../../types';

const CartItemStack = ({cartItems}: CartItemGridProps, {totalPrice}: number) => {
    return (
        <Stack p={5} shadow='md' borderWidth='1px' spacing={5}>
            {cartItems.map(
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
        </Stack>
    );
};

export default CartItemStack;
