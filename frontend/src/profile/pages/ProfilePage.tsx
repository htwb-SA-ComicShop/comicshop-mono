import { Box, Avatar, Text, VStack, HStack,} from '@chakra-ui/react';
import DeleteButton from "../components/DeleteProfileButton";
import EditProfileButton from "../components/EditProfileButton";
import {useEffect, useState} from "react";

function ProfilePage() {
    const [profile, setProfile] = useState<Profile>([]);
    useEffect(() => {
        async function fetchComics() {
            const response = await fetch('http://localhost:8082/cart/:id', { //TODO we need a getCartItems in the API
                mode: 'cors',
            });
            const receivedProfiles: Profile = await response.profile.json(); //TODO: refactor after cart service is done
            setProfile(receivedProfiles);
        }
        fetchComics();
    }, []);
    return (
        <Box p={4} borderWidth={1} borderRadius="md">
            <Avatar size="xl" name="John Doe" src="https://placekitten.com/200/200" />
            <VStack align="start" mt={4} spacing={2}>
                <Text fontSize="xl" fontWeight="bold">{profile.username}</Text>
                <Text>Email: {profile.email}</Text>
                <Text>Name: {profile.firstname}, {profile.lastname}</Text>
                <br></br>
            </VStack>
            <HStack mt={4}>
                <EditProfileButton>Edit Profile</EditProfileButton>
                <DeleteButton>Delete Profile</DeleteButton>
            </HStack>
        </Box>
    );
}

export default ProfilePage;