import {
    AccordionButton, AccordionIcon,
    AccordionItem, AccordionPanel, Box,
    Button,
    ButtonGroup,
    Card,
    CardBody,
    CardFooter, Divider,
    Heading,
    Image, Spacer,
    Stack,
    Text,
} from '@chakra-ui/react';
import { ReactElement } from 'react';
import {CartItem, Product} from '../../../types';
import { Link } from 'react-router-dom';
import AuthWrapper from '../../../auth/components/AuthWrapper';
import DeleteButton from "./DeleteButton";
//import AddToCartButton from './AddToCartButton';
//import DeleteButton from './DeleteButton';

const CartItemCard = (cartItem: CartItem): ReactElement => {
    const { name, id, productId, author, price, imgUrl } =
        cartItem;
    return (
        <Box p={5} shadow='md' borderWidth='1px'>
            <Box as="span" flex='1' textAlign='left'>
                <Heading fontSize='xl'>{name}</Heading>
            </Box>
            <Divider orientation='horizontal' />

            <Stack direction='row'>
                <Box >
                        <Image src= {imgUrl} boxSize='100px'/>
                    </Box>
                    <Divider orientation='vertical' />
                    <Box >
                        {author}
                    </Box>
                    <Spacer />
                    <Box >
                        ${price}
                    </Box>
                    <Divider orientation='vertical' />
                    <Stack direction='column'>
                            <Button
                                variant='outline'
                                colorScheme='teal'
                                as={Link}
                                to={`/product/${productId}`}
                            >
                                Go to Product
                            </Button>
                            <DeleteButton itemId={id ?? ''} />
                    </Stack>
                </Stack>
        </Box>
        /*
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
                    <Text fontSize='2xl' fontWeight='bold'>
                        ${price}
                    </Text>
                </Stack>
            </CardBody>
            <CardFooter>
                <ButtonGroup spacing='2'>
                        <Button
                            variant='outline'
                            colorScheme='teal'
                            as={Link}
                            to={`/product/${productId}`}
                        >
                            Go to Product
                        </Button>
                        <DeleteButton itemId={id ?? ''} />
                </ButtonGroup>
            </CardFooter>
        </Card>

         */
    );
};

export default CartItemCard;
