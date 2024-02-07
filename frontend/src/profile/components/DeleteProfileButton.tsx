import {Button} from '@chakra-ui/react';

const DeleteButton = ({children}) => {
    const handleClick = () => {
    };

    return (<Button colorScheme="red" onClick={handleClick}>
            {children}
        </Button>);
};

export default DeleteButton;
