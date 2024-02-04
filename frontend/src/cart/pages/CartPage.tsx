import {ReactElement, useEffect, useState} from 'react';
import {Cart, CartItem} from '../../types';
import {Accordion, Button, Flex, Heading, Spinner, Text, VStack} from '@chakra-ui/react';
import CartItemAccordion from '../components/CartItemAccordion';
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

    /*
    useEffect(() => {
        async function putItemsInCart(){
            const payload: Cart = {
                "id": "b5993af9-9eee-4f01-a279-3817ca7742e2",
                "username": "customer",
                "cartItems": [
                    {
                        "productId": "d3780516-f9f2-43f7-a75c-54d5ee7d5440",
                        "name": "Asterix in Britain",
                        "author": "René Goscinny & Albert Uderzo",
                        "price": 5.99,
                        "linkToProduct": "",
                        "imgUrl": "https://upload.wikimedia.org/wikipedia/en/4/48/Asterix_Britain.png"
                    },
                    {
                        "productId": "7579b00f-726f-408c-bbb2-47675ba30540",
                        "name": "Asterix in Switzerland",
                        "author": "René Goscinny & Albert Uderzo",
                        "price": 5.99,
                        "linkToProduct": "",
                        "imgUrl": "https://upload.wikimedia.org/wikipedia/en/0/06/Asterix_Switzerland.png"
                    },
                    {
                        "productId": "9be1cc05-0ab5-471d-a81e-c112a69c1ffa",
                        "name": "Asterix in Corsica",
                        "author": "René Goscinny & Albert Uderzo",
                        "price": 5.99,
                        "linkToProduct": "",
                        "imgUrl": "https://upload.wikimedia.org/wikipedia/en/7/7c/Asterixcover-20.jpg"
                    }
                ],
                "totalPrice": 17.97,
                "boughtAt": null,
                "isBought": false
            }

            const response = await fetch(`http://localhost:8082/cart/${"b5993af9-9eee-4f01-a279-3817ca7742e2"}`,  {
                    method: 'PUT',
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
            }
            putItemsInCart();
    }, []);
     */

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
                //} else {
                  //  alert("CAN'T CHANGE BOUGHT SHOPPING CART!!!");
                //}
            }
        }
        fetchCart();
    }, [currCartId]);

    return (
        <main>
            <Heading as="h2" mb={4}>Shopping Cart</Heading>
            <Accordion allowMultiple>
                {currCart.cartItems.length > 0 ? (
                    <CartItemAccordion cartItems={currCart.cartItems} />
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
            </Accordion>
            <Flex as='footer' pb={6} pt={12} justify='center' w='80%' mx='auto'>
                <Text fontSize='sm'>ComicShop© 2024</Text>
            </Flex>
        </main>
    );
};

export default CartItemsListPage;
