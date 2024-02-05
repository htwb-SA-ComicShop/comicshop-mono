import { ReactElement, useEffect, useState } from 'react';
import {Cart, Product} from '../../types';
import { Button, Flex, Spinner, Text, VStack } from '@chakra-ui/react';
import ProductGrid from '../components/ProductGrid';
import { Link } from 'react-router-dom';
import AuthWrapper from '../../auth/components/AuthWrapper';
import useAuth from "../../auth/hooks/useAuth.hook";

const ProductsListPage = (): ReactElement => {
  const { token, cartId, username, isLoggedIn } = useAuth();

  const [comics, setComics] = useState<Product[]>([]);

  useEffect(() => {
    async function fetchComics() {
      const response = await fetch('http://localhost:8080/products', {
        mode: 'cors',
      });
      const receivedComics: Product[] = await response.json();
      setComics(receivedComics);
    }
    fetchComics();
  }, []);

  useEffect(() => {
    async function fetchCartId(){
      const payload: Cart = {
        id: null,
        username: `${username}`,
        cartItems: [],
        totalPrice: 0,
      }
      if (isLoggedIn && (cartId == null || cartId == "null" || cartId == "")){
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
        //token.cartId = response.text(); TODO assign cartId to user
      }
    }
    fetchCartId();
  }, [isLoggedIn]);

  return (
    <main>
      <VStack w='80%' mx='auto' gap={12}>
        {comics.length > 0 ? (
          <ProductGrid products={comics} />
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
            Add Product
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
