import {Box, Avatar, Text, VStack, Button, ButtonGroup,} from '@chakra-ui/react';
import DeleteButton from "../components/DeleteProfileButton";
import useAuth from "../../auth/hooks/useAuth.hook";
//import {useEffect, useState} from "react";
//import { Profile } from '../../types';
import { Link } from 'react-router-dom';


function ProfilePage() {
    const { username, email, firstName, lastName} = useAuth();
    return (
        <Box p={4} borderWidth={1} borderRadius="md">
            <Avatar size="xl" name="John Doe" src="https://placekitten.com/700/600"/>
            <VStack align="start" mt={4} spacing={2}>
                <Text fontSize="xl" fontWeight="bold">{username}</Text>
                <Text>Email: {email}</Text>
                <Text>Name: {firstName} ,  {lastName}</Text>
                <br></br>
            </VStack>
            <ButtonGroup spacing='2'>
                <Button
                    colorScheme='teal'
                    as={Link}
                    to={`/edit-profile`}
                >
                    Edit
                </Button>
                <DeleteButton>Delete</DeleteButton>
            </ButtonGroup>
        </Box>
    );
}

export default ProfilePage;