import {useState} from 'react';
import {
    Button,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
    Input,
    Text,
    useToast,
} from '@chakra-ui/react';
import useAuth from '../../auth/hooks/useAuth.hook';
import { Cart } from '../../types';

const CheckoutButton = ({ cartId }: { cartId: string }) => {
    const { token } = useAuth();
    const [isOpen, setIsOpen] = useState(false);
    const [creditCard, setCreditCard] = useState('');
    const [isPaymentSuccessful, setIsPaymentSuccessful] = useState(false);
    const toast = useToast();

    const handleAddToCartClick = () => {
        setIsOpen(true);
    };

    const handleCloseModal = () => {
        setIsOpen(false);
    };

    const handlePurchaseClick = async () => {
        //TODO handle credit card information
        console.log('cart bought successfully with credit card: ', creditCard);

        try {
            const response = await fetch('http://localhost:8080/add-to-cart', {
                method: 'GET',
                mode: 'cors',
                cache: 'no-cache',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(cartId),
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

        setIsPaymentSuccessful(true);
    };

    return (
        <>
            <Button variant='solid' colorScheme='teal' onClick={handleAddToCartClick}>
                Checkout
            </Button>
            <Modal isOpen={isOpen} onClose={handleCloseModal}>
                <ModalOverlay/>
                <ModalContent mx={10}>
                    <ModalHeader>Enter credit card information</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        {!isPaymentSuccessful ? (
                            <>
                                <Text>
                                    Please enter your credit card information in order to checkout the cart.
                                </Text>
                                <Input
                                    type='text'
                                    placeholder='credit card number'
                                    value={creditCard}
                                    onChange={(e) => setCreditCard(e.target.value)}
                                />
                            </>
                        ) : (
                            <Text>Checkout successful!</Text>
                        )}
                    </ModalBody>

                    <ModalFooter justifyContent='center' gap={4}>
                        {!isPaymentSuccessful ? (
                            <Button colorScheme='teal' onClick={handlePurchaseClick} minW={120}>
                                Buy
                            </Button>
                        ) : (
                            <Button colorScheme='teal' onClick={handleCloseModal} minW={120}>
                                cancel
                            </Button>
                        )}
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default CheckoutButton;
