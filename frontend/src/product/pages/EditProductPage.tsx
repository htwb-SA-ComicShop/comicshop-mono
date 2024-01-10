import { Heading, Image, Text, VStack } from '@chakra-ui/react';
import { Navigate, useLoaderData } from 'react-router';
import { Product } from '../../types';
import ProductForm from '../components/ProductForm';

function EditProductPage() {
  const { comic: defaults } = useLoaderData() as {
    comic: Product;
  };

  // FIXME: Check admin status using keycloak
  const isAdmin = true;
  if (isAdmin) {
    return <Navigate to='/' replace />;
  }

  return (
    <main>
      <VStack w='80%' mx='auto' align='start'>
        <Image
          width='100%'
          height={{ base: 100, sm: 300 }}
          borderRadius='lg'
          objectFit='cover'
          src={defaults.imgUrl ?? './placeholder.png'}
          alt={`Image of ${defaults.name}`}
        />
        <VStack
          direction={{ base: 'column', sm: 'row' }}
          align='start'
          overflow='hidden'
          mb={{ base: 8, sm: 4 }}
        >
          <VStack align='start' spacing={4}>
            <Heading>
              {defaults.name}{' '}
              <Text as='span' fontWeight={400}>
                bearbeiten
              </Text>
            </Heading>
            <ProductForm method='PUT' defaults={defaults} id={defaults.id} />
          </VStack>
        </VStack>
      </VStack>
    </main>
  );
}

export default EditProductPage;
