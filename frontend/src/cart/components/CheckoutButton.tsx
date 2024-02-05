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
import { Cart} from '../../types';

const CheckoutButton = ({cartId}: { cartId: string }) => {
    const {token} = useAuth();
    const [isOpen, setIsOpen] = useState(false);
    const [creditCard, setCreditCard] = useState('');
    const [isPaymentSuccessful, setIsPaymentSuccessful] = useState(false);
    const [isCreditCardValid, setIsCreditCardValid] = useState(true);
    const toast = useToast();

    const handleAddToCartClick = () => {
        setIsOpen(true);
    };

    const handleCloseModal = () => {
        setIsOpen(false);
        setIsCreditCardValid(true);
    };

    const handlePurchaseClick = async () => {
        //TODO handle credit card information
        if (creditCard.length >= 10) {
            alert("going to try")
            try {
                const response = await fetch(`http://localhost:8082/cart/buy-cart/${cartId}`, {
                    method: 'GET',
                    mode: 'cors',
                    cache: 'no-cache',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`,
                    },
                  //  body: JSON.stringify(cartId),
                });
                if (!response.ok) {
                    alert(`Something went wrong!${response}`);
                    throw new Error(`Something went wrong!${response}`);
                }
                toast({
                    title: 'All right!',
                    description: 'cart was checked out!',
                    position: 'top',
                    status: 'success',
                    duration: 5000,
                    isClosable: true,
                });
                alert(`cart bought successfully with credit card: ${creditCard}`);
                setIsPaymentSuccessful(true);
            } catch (error) {
                if (error instanceof Error) {
                    toast({
                        title: 'Ooops!',
                        description: `Something went wrong!${error}`,
                        position: 'top',
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    });
                }
            }
        } else {
            setIsCreditCardValid(false);
        }


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
                                    Your Credit card here:
                                </Text>
                                <Input
                                    type='text'
                                    placeholder='credit card number'
                                    value={creditCard}
                                    onChange={(e) => setCreditCard(e.target.value)}
                                    isInvalid={!isCreditCardValid}
                                    required
                                />
                                {!isCreditCardValid && (
                                    <Text color='red.500' fontSize='sm'>
                                        Enter at least 10 digits.
                                    </Text>
                                )}
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
                                Done
                            </Button>
                        )}
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default CheckoutButton;
