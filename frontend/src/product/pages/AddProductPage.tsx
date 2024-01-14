import { Heading, VStack } from '@chakra-ui/react';
import ProductForm from '../components/ProductForm';

function AddProductPage() {
  return (
    <main>
      <VStack w='80%' mx='auto' align='start'>
        <VStack
          direction={{ base: 'column', sm: 'row' }}
          align='start'
          overflow='hidden'
          mb={{ base: 8, sm: 4 }}
          w='100%'
        >
          <VStack align='start' spacing={4} w='100%'>
            <Heading>Add new Product</Heading>
            <ProductForm method='POST' />
          </VStack>
        </VStack>
      </VStack>
    </main>
  );
}

export default AddProductPage;
