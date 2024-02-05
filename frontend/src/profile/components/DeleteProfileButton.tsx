import { Button } from '@chakra-ui/react';

const DeleteButton = ({ children }) => {
    const handleClick = () => {
        // Execute code when the button is pressed
        console.log('Delete Profile!');
    };

    return (
        <Button colorScheme="red" onClick={handleClick}>
            {children}
        </Button>
    );
};

export default DeleteButton;
