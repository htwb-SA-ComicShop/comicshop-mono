import { Button } from '@chakra-ui/react';

const DeleteButton = ({ children }) => {
    const handleClick = () => {
        // Execute code when the button is pressed
        console.log('Edit Profile!');
    };

    return (
        <Button colorScheme="teal" onClick={handleClick}>
            {children}
        </Button>
    );
};

export default DeleteButton;
