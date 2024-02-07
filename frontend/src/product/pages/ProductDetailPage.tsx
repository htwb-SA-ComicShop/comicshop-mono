import {Heading, Image, Text, VStack} from '@chakra-ui/react';
import {useLoaderData} from 'react-router';
import {Product} from '../../types';
import ProductCard from "../components/ProductCard/ProductCard";

function ProductDetailPage() {
    const {comic: defaults} = useLoaderData() as { comic: Product };

    return (
        <main>
            <VStack w='80%' mx='auto' align='start'>
                <Image
                    width='100%'
                    height={{base: 100, sm: 300}}
                    borderRadius='lg'
                    objectFit='cover'
                    src={defaults.imgUrl ?? './placeholder.png'}
                    alt={`Image of ${defaults.name}`}
                    mb={8}
                />
                <VStack
                    direction={{base: 'column', sm: 'row'}}
                    align='start'
                    overflow='hidden'
                    w='100%'
                    mb={{base: 8, sm: 4}}
                >
                    <VStack align='start' spacing={4} w='100%'>
                        <Heading>
                            <Text as='span' fontWeight={400}>
                                Edit{' '}
                            </Text>
                            {defaults.name}
                        </Heading>
                        <ProductCard
                            id={defaults.id}
                            key={defaults.id}
                            name={defaults.name}
                            author={defaults.author}
                            publisher={defaults.publisher}
                            pages={defaults.pages}
                            price={defaults.price}
                            description={defaults.description}
                            imgUrl={defaults.imgUrl}
                        />
                    </VStack>
                </VStack>
            </VStack>
        </main>
    );
}

export default ProductDetailPage;
