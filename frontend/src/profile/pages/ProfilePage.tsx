import { Box, Avatar, Text, VStack, HStack, Button} from '@chakra-ui/react';
import DeleteButton from "../components/DeleteProfileButton";
import EditProfileButton from "../components/EditProfileButton";

function ProfilePage() {
    const handleEditProfile = () => {
        // Implement logic for editing the profile
        console.log('Editing profile...');
    };
    return (
        <Box p={4} borderWidth={1} borderRadius="md">
            <Avatar size="xl" name="John Doe" src="https://placekitten.com/200/200" />
            <VStack align="start" mt={4} spacing={2}>
                <Text fontSize="xl" fontWeight="bold">JohnD69</Text>
                <Text>Email: john.doe@example.com</Text>
                <Text>Name: John, Doe</Text>
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