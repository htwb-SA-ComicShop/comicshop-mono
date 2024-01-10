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
import { Product } from '../../types';
import { Link } from 'react-router-dom';
import AuthWrapper from '../../auth/components/AuthWrapper';

const ProductCard = ({
  name,
  id,
  author,
  publisher,
  pages,
  price,
  description,
  imgUrl,
}: Product): ReactElement => {
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
          <Text color='teal.600' fontSize='2xl'>
            ${price}
          </Text>
        </Stack>
      </CardBody>
      <CardFooter>
        <ButtonGroup spacing='2'>
          <Button variant='solid' colorScheme='teal'>
            Add to cart
          </Button>
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
        </ButtonGroup>
      </CardFooter>
    </Card>
  );
};

export default ProductCard;
