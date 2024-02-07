import {
    Button,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay,
    Text,
    useDisclosure,
    useToast,
} from '@chakra-ui/react';
import useAuth from '../../../auth/hooks/useAuth.hook';
import {Product} from '../../../types';

const AddToCartButton = ({product}: { product: Product }) => {
    const {isLoggedIn, login, signup, token} = useAuth();
    const {isOpen, onClose, onOpen} = useDisclosure();
    const toast = useToast();

    const handleAddToCartClick = async () => {
        if (isLoggedIn) {
            console.log(`Adding product ${product.id} to cart...`);
            try {
                const response = await fetch('http://localhost:8080/add-to-cart', {
                    method: 'POST',
                    mode: 'cors',
                    cache: 'no-cache',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify(product),
                });
                if (!response.ok) {
                    throw new Error('Something went wrong!');
                }
                toast({
                    title: 'All right!',
                    description: 'Product was added to cart!',
                    position: 'top',
                    status: 'success',
                    duration: 5000,
                    isClosable: true,
                });
            } catch (error) {
                if (error instanceof Error) {
                    toast({
                        title: 'Ooops!',
                        description: 'Something went wrong!',
                        position: 'top',
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    });
                }
            }
        } else {
            onOpen();
        }
    };

    return (
        <>
            <Button variant='solid' colorScheme='teal' onClick={handleAddToCartClick}>
                Add to cart
            </Button>
            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay/>
                <ModalContent mx={10}>
                    <ModalHeader>You need to log in first! 🤓</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        <Text>
                            Only logged in customers can add products to their shopping carts.{' '}
                        </Text>
                        <br/>
                        <Text>
                            If you don't have an account yet, just register with us. I only
                            takes a minute! ❤️
                        </Text>
                    </ModalBody>

                    <ModalFooter justifyContent='center' gap={4}>
                        <Button colorScheme='teal' onClick={() => login()} minW={120}>
                            Login
                        </Button>
                        <Button
                            colorScheme='teal'
                            variant='outline'
                            onClick={signup}
                            minW={120}
                        >
                            Register
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default AddToCartButton;
