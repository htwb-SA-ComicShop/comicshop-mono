import {
  Button,
  ButtonGroup,
  Card,
  CardBody,
  CardFooter,
  Heading,
  Image,
  Stack,
  Text,
} from '@chakra-ui/react';
import { ReactElement } from 'react';
import { Product } from '../../../types';
import { Link } from 'react-router-dom';
import AuthWrapper from '../../../auth/components/AuthWrapper';
import AddToCartButton from './AddToCartButton';
import DeleteButton from './DeleteButton';

const ProductCard = (product: Product): ReactElement => {
  const { name, id, author, publisher, pages, price, description, imgUrl } =
    product;
  return (
    <Card maxW='sm' variant='filled'>
      <CardBody>
        <Image
          minW='100%'
          src={imgUrl}
          alt={`Opened ${name} comic book`}
          borderRadius='lg'
        />
        <Stack mt='6' spacing='3'>
          <Heading size='md'>{name}</Heading>
          <Heading size='sm' color='teal.400'>
            {author}
          </Heading>
          <Text>
            Published by <strong>{publisher}</strong>
          </Text>
          <Text>{pages} Pages</Text>
          <Text>{description}</Text>
          <Text fontSize='2xl' fontWeight='bold'>
            ${price}
          </Text>
        </Stack>
      </CardBody>
      <CardFooter>
        <ButtonGroup spacing='2'>
          <AddToCartButton product={product} />
          <AuthWrapper role='admin'>
            <Button
              variant='outline'
              colorScheme='teal'
              as={Link}
              to={`/edit-product/${id}`}
            >
              Edit
            </Button>
          </AuthWrapper>
          <AuthWrapper role='admin'>
            <DeleteButton id={id ?? ''} />
          </AuthWrapper>
        </ButtonGroup>
      </CardFooter>
    </Card>
  );
};

export default ProductCard;
