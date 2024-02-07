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

const DeleteButton = ({itemId}: { itemId: string }) => {
    const {isOpen, onClose, onOpen} = useDisclosure();
    const {token} = useAuth();
    const toast = useToast();

    const onDeleteClick = async () => {
        try {
            const response = await fetch(`http://localhost:8082/cart/items/${itemId}`, {
                method: 'DELETE',
                mode: 'cors',
                headers: {
                    Authorization: `Bearer ${token} `,
                },
                body: "b5993af9-9eee-4f01-a279-3817ca7742e2"
            });
            if (!response.ok) {
                throw new Error('Something went wrong!');
            }
        } catch (error) {
            if (error instanceof Error) {
                toast({
                    title: 'Ooops!',
                    description: 'The Product could not be deleted',
                    position: 'top',
                    status: 'error',
                    duration: 5000,
                    isClosable: true,
                });
            }
        } finally {
            window.location.reload();
        }
    };

    return (
        <>
            <Button variant='outline' colorScheme='red' onClick={onOpen}>
                Delete
            </Button>
            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay/>
                <ModalContent mx={10}>
                    <ModalHeader>Wait a second! 🚨</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        <Text>
                            Are you sure you want to delete this item from cart?
                        </Text>
                    </ModalBody>

                    <ModalFooter justifyContent='center' gap={4}>
                        <Button colorScheme='red' onClick={onDeleteClick} minW={120}>
                            Delete
                        </Button>
                        <Button
                            colorScheme='teal'
                            variant='outline'
                            onClick={onClose}
                            minW={120}
                        >
                            Cancel
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default DeleteButton;
