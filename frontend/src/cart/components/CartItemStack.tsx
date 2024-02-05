import {Accordion, Box, Heading, SimpleGrid, Spacer, Stack} from '@chakra-ui/react';
import CartItemCart from './CartItemCard/CartItemCard';
import { CartItemGridProps } from '../../types';

const CartItemStack = ({ cartItems }: CartItemGridProps, { totalPrice }: number) => {
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
            <Stack p={5} shadow='md' borderWidth='1px' direction='row'>
                    <Box as="span" flex='1' textAlign='left'>
                        <Heading fontSize='xl'>Total Price</Heading>
                    </Box>
                <Spacer />
                    <Box >
                        {totalPrice}
                    </Box>
            </Stack>
        </Stack>
        /*
        <SimpleGrid columns={{ base: 1, md: 2, xl: 3 }} spacing={4}>
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
        </SimpleGrid>
         */
    );
};

export default CartItemStack;
