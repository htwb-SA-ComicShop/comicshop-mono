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
import { useNavigate } from 'react-router';

const DeleteButton = ({ id }: { id: string }) => {
  const { isOpen, onClose, onOpen } = useDisclosure();
  const navigate = useNavigate();
  const { token } = useAuth();
  const toast = useToast();

  const onDeleteClick = async () => {
    try {
      const response = await fetch(`http://localhost:8080/product/${id}`, {
        method: 'DELETE',
        mode: 'cors',
        headers: {
          Authorization: `Bearer ${token} `,
        },
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
      navigate(0);
    }
  };

  return (
    <>
      <Button variant='outline' colorScheme='red' onClick={onOpen}>
        Delete
      </Button>
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent mx={10}>
          <ModalHeader>Wait a second! 🚨</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>
              This action is irreversible. Are you sure you want to delete this
              product?
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
