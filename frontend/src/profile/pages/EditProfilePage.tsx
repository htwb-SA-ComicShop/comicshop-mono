import {Heading, Image, Text, VStack} from '@chakra-ui/react';
import ProfileForm from '../components/profileForm/ProfileForm';
import useAuth from "../../auth/hooks/useAuth.hook";

function EditProductPage() {
    const {token, username} = useAuth();
    return (
        <main>
            <VStack w='80%' mx='auto' align='center'>
                <Image
                    width='50%'
                    height={{base: 100, sm: 300}}
                    borderRadius='lg'
                    objectFit='cover'
                    src={'https://placekitten.com/700/600'}
                    alt={`Image of ${username}`}
                    mb={8}
                />
                <VStack
                    direction={{base: 'column', sm: 'row'}}
                    align='center'
                    overflow='hidden'
                    w='100%'
                    mb={{base: 8, sm: 4}}
                >
                    <VStack align='start' spacing={4} w='80%'>
                        <Heading>
                            <Text as='span' fontWeight={400}>
                                Edit{' '}
                            </Text>
                            {username}
                        </Heading>
                        <ProfileForm
                            method='PUT'
                            id={token.id ?? ''}
                        />
                    </VStack>
                </VStack>
            </VStack>
        </main>
    );
}

export default EditProductPage;
