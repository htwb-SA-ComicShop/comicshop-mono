import { ReactElement, useEffect, useState } from 'react';
import {CartItem} from '../../types';
import { Button, Flex, Spinner, Text, VStack } from '@chakra-ui/react';
import CartItemGrid from '../components/CartItemGrid';
import { Link } from 'react-router-dom';
import AuthWrapper from '../../auth/components/AuthWrapper';

const ProductsListPage = (): ReactElement => {
    const [comics, setComics] = useState<CartItem[]>([]);
    useEffect(() => {
        async function fetchComics() {
            const response = await fetch('http://localhost:8082/cart/:id', { //TODO we need a getCartItems in the API
                mode: 'cors',
            });
            const receivedComics: CartItem[] = await response.cartItemsList.json(); //TODO: refactor after cart service is done
            setComics(receivedComics);
        }
        fetchComics();
    }, []);

    return (
        <main>
            <VStack w='80%' mx='auto' gap={12}>
                {comics.length > 0 ? (
                    <CartItemGrid products={comics} />
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
                        add Product
                    </Button>
                </AuthWrapper>
            </VStack>
            <Flex as='footer' pb={6} pt={12} justify='center' w='80%' mx='auto'>
                <Text fontSize='sm'>ComicShop© 2024</Text>
            </Flex>
        </main>
    );
};

export default ProductsListPage;
