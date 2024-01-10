import { ReactElement, useEffect, useState } from 'react';
import { Product } from '../../types';
import { Flex, Spinner, Text, VStack } from '@chakra-ui/react';
import ProductGrid from '../components/ProductGrid';

const ProductsListPage = (): ReactElement => {
  const [comics, setComics] = useState<Product[]>([]);
  useEffect(() => {
    async function fetchComics() {
      const response = await fetch('http://localhost:8080/products');
      const receivedComics: Product[] = await response.json();
      setComics(receivedComics);
    }
    fetchComics();
  }, []);

  return (
    <main>
      <VStack w='80%' mx='auto'>
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
      </VStack>
      <Flex as='footer' pb={6} pt={12} justify='center' w='80%' mx='auto'>
        <Text fontSize='sm'>ComicShop© 2024</Text>
      </Flex>
    </main>
  );
};

export default ProductsListPage;
