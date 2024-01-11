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
} from '@chakra-ui/react';
import useAuth from '../../../auth/hooks/useAuth.hook';

const AddToCartButton = () => {
  const { isLoggedIn, login, signup } = useAuth();
  const { isOpen, onClose, onOpen } = useDisclosure();

  const handleAddToCartClick = () => {
    if (isLoggedIn) {
      console.log('add to cart');
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
        <ModalOverlay />
        <ModalContent mx={10}>
          <ModalHeader>You need to log in first! 🤓</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>
              Only logged in customers can add products to their shopping carts.{' '}
            </Text>
            <br />
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
