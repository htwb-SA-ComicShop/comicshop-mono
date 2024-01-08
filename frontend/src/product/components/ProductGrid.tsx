import { SimpleGrid } from '@chakra-ui/react';
import ProductCard from './ProductCard';
import { ProductGridProps } from '../../types';

const ProductGrid = ({ products }: ProductGridProps) => {
  return (
    <SimpleGrid columns={{ base: 1, md: 2, xl: 3 }} spacing={4}>
      {products.map(
        ({
          name,
          author,
          publisher,
          pages,
          price,
          description,
          imgUrl,
          id,
        }) => (
          <ProductCard
            id={id}
            key={id}
            name={name}
            author={author}
            publisher={publisher}
            pages={pages}
            price={price}
            description={description}
            imgUrl={imgUrl}
          />
        )
      )}
    </SimpleGrid>
  );
};

export default ProductGrid;
