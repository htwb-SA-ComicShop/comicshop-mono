import {ReactElement, useEffect, useState} from 'react';
import {Cart, CartItem} from '../../types';
import {
    Accordion,
    Box,
    Button, Center,
    Divider,
    Flex,
    Heading,
    Image,
    Spacer,
    Spinner,
    Stack,
    Text,
    VStack
} from '@chakra-ui/react';
import CartItemStack from '../components/CartItemStack';
import {Link} from 'react-router-dom';
import AuthWrapper from '../../auth/components/AuthWrapper';
import useAuth from "../../auth/hooks/useAuth.hook";
import DeleteButton from "../components/CartItemCard/DeleteButton";
import CheckoutButton from "../components/CheckoutButton";

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
            /*
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
             */
            setCurrCartId("b5993af9-9eee-4f01-a279-3817ca7742e2");
        }
        fetchCartId();
    }, []);

    useEffect(() => {
        async function fetchCart() {
            if (currCartId != null && currCartId != "null" && currCartId != "") {
                const response = await fetch(`http://localhost:8082/cart/b5993af9-9eee-4f01-a279-3817ca7742e2`, { // TODO change back: http://localhost:8082/cart/${currCartId}
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
                //if (cart.isBought == false) {
                //alert("cart Items are: " + cart.cartItems);
                    setCurrCart(cart);
                    setCurrCartId(cart.id);
                //} else {
                  //  alert("CAN'T CHANGE BOUGHT SHOPPING CART!!!");
                //}
            }
        }
        fetchCart();
    }, [currCartId]);

    return (
        <main>
            <Center>
            <Heading as="h1" mb={4} spacing={5}>Shopping Cart</Heading>
            </Center>
            <Stack spacing={8} direction='column'>
                {currCart.cartItems.length > 0 ? (
                    <CartItemStack cartItems={currCart.cartItems}/>
                ) : (
                    <Stack borderWidth='1px'>
                        <Heading fontSize='l'>Wow! So empty...</Heading>
                    </Stack>
                )}
            </Stack>
            <Stack p={5} shadow='md' borderWidth='1px' direction='row'>
                <Box>
                <Box as="span" flex='1' textAlign='left'>
                    <Heading fontSize='xl'>Total Price</Heading>
                </Box>
                <Box fontSize='xl'>
                    ${Math.round(currCart.totalPrice * 100) / 100}
                </Box>
                </Box>
                <Spacer />
                <CheckoutButton cartId={"b5993af9-9eee-4f01-a279-3817ca7742e2"} />
            </Stack>
            <Flex as='footer' pb={6} pt={12} justify='center' w='80%' mx='auto'>
                <Text fontSize='sm'>ComicShop© 2024</Text>
            </Flex>
        </main>
    );
};

export default CartItemsListPage;
