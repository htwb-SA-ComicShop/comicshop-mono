import {Box, Avatar, Text, VStack, Button, Link, ButtonGroup,} from '@chakra-ui/react';
import DeleteButton from "../components/DeleteProfileButton";
import useAuth from "../../auth/hooks/useAuth.hook";
//import {useEffect, useState} from "react";
//import { Profile } from '../../types';


function ProfilePage() {
    const {token, username} = useAuth();
    return (
        <Box p={4} borderWidth={1} borderRadius="md">
            <Avatar size="xl" name="John Doe" src="https://placekitten.com/200/200"/>
            <VStack align="start" mt={4} spacing={2}>
                <Text fontSize="xl" fontWeight="bold">{username}</Text>
                <Text>Email: {token.email}</Text>
                <Text>Name: {token.firstname}, {token.lastname}</Text>
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