import {Box, Button, Divider, Heading, Image, Spacer, Stack,} from '@chakra-ui/react';
import {ReactElement} from 'react';
import {CartItem} from '../../../types';
import {Link} from 'react-router-dom';
import DeleteButton from "./DeleteButton";

const CartItemCard = (cartItem: CartItem): ReactElement => {
    const {name, id, productId, author, price, imgUrl} =
        cartItem;
    return (
        <Box p={5} shadow='md' borderWidth='1px'>
            <Box as="span" flex='1' textAlign='left'>
                <Heading fontSize='xl'>{name}</Heading>
            </Box>
            <Divider orientation='horizontal'/>

            <Stack direction='row'>
                <Box>
                    <Image src={imgUrl} boxSize='100px'/>
                </Box>
                <Divider orientation='vertical'/>
                <Box>
                    {author}
                </Box>
                <Spacer/>
                <Box>
                    ${price}
                </Box>
                <Divider orientation='vertical'/>
                <Stack direction='column'>
                    <Button
                        variant='outline'
                        colorScheme='teal'
                        as={Link}
                        to={`/product/${productId}`}
                    >
                        Go to Product
                    </Button>
                    <DeleteButton itemId={id ?? ''}/>
                </Stack>
            </Stack>
        </Box>
    );
};

export default CartItemCard;
