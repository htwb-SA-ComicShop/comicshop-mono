import {ReactElement, useEffect, useState} from 'react';
import {Cart, CartItem} from '../../types';
import {Button, Flex, Spinner, Text, VStack} from '@chakra-ui/react';
import CartItemGrid from '../components/CartItemGrid';
import {Link} from 'react-router-dom';
import AuthWrapper from '../../auth/components/AuthWrapper';
import useAuth from "../../auth/hooks/useAuth.hook";

const CartItemsListPage = (): ReactElement => {
    const dummyCart : Cart = {
        id: null,
        username: "",
        cartItems: [],
        totalPrice: 0.0,
        isBought: false,
    }
    const { token, cartId, username } = useAuth();

    const [currCart, setCurrCart] = useState<Cart>(dummyCart);

    const [currCartId, setCurrCartId] = useState<string>(null);

    useEffect(() => {
        async function fetchCartId(){
            const payload: Cart = {
                id: null,
                username: username,
            }

            if ((cartId == null || cartId == "null" || cartId == "") && currCartId==null){
                const response = await fetch(`http://localhost:8082/cart`, {
                    method: 'POST',
                    mode: 'cors',
                    cache: 'no-cache',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify(payload),
                });
                if (!response.ok) {
                    throw new Error('Something went wrong!');
                }
                const newId = await response.text();
                //token.cartId = newId; //TODO assign cartID to user
                setCurrCartId(newId);
            } else {
                setCurrCartId(cartId);
            }
        }
        fetchCartId();
    }, []);

    useEffect(() => {
        async function fetchCart() {
            if (currCartId != null && currCartId != "null" && currCartId != "") {
                const response = await fetch(`http://localhost:8082/cart/${currCartId}`, { //http://localhost:8082/cart/${currCartId}
                    mode: 'cors',
                    cache: 'no-cache',
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                if (!response.ok) {
                    throw new Error('Something went wrong!');
                }
                const cart : Cart = await response.json();
                alert("cart is: " + cart.id + cart.username + cart.totalPrice + cart.isBought);
                //if (cart.isBought == false) {
                    setCurrCart(cart);
                //} else {
                  //  alert("CAN'T CHANGE BOUGHT SHOPPING CART!!!");
                //}
            }
        }
        fetchCart();
    }, [currCartId]);

    return (
        <main>
            <VStack w='80%' mx='auto' gap={12}>
                {currCart.cartItems.length > 0 ? (
                    <CartItemGrid cartitems={currCart.cartItems} />
                ) : (
                    <Spinner
                        my={40}
                        size='xl'
                        emptyColor='teal.200'
                        color='teal.500'
                        speed='0.75s'
                        thickness='5px'
                    />
                )}
                <AuthWrapper role='admin'>
                    <Button
                        as={Link}
                        to='/add-product'
                        colorScheme='teal'
                        variant='outline'
                    >
                        Produkt hinzufügen
                    </Button>
                </AuthWrapper>
            </VStack>
            <Flex as='footer' pb={6} pt={12} justify='center' w='80%' mx='auto'>
                <Text fontSize='sm'>ComicShop© 2024</Text>
            </Flex>
        </main>
    );
};

export default CartItemsListPage;
