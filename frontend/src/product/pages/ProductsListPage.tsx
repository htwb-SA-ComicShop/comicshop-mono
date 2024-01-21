import { ReactElement, useEffect, useState } from 'react';
import { Product } from '../../types';
import { Button, Flex, Spinner, Text, VStack } from '@chakra-ui/react';
import ProductGrid from '../components/ProductGrid';
import { Link } from 'react-router-dom';
import AuthWrapper from '../../auth/components/AuthWrapper';

const ProductsListPage = (): ReactElement => {
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
            to='/add-order'
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

export default ProductsListPage;
