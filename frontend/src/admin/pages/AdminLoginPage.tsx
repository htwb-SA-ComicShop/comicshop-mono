import {
  Button,
  Card,
  Center,
  Container,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Heading,
  Icon,
  Input,
  Stack,
  Text,
  useToast,
} from '@chakra-ui/react';
import { useAtom } from 'jotai';
import { SubmitHandler, useForm } from 'react-hook-form';
import { FaCheck } from 'react-icons/fa';
import { Link, useNavigate } from 'react-router-dom';
import AuthAtom from '../../stores/authStore';
import { LoginCredentials } from '../../types';

const AdminLoginPage = () => {
  const [authStatus, setAuthStatus] = useAtom(AuthAtom);
  const navigate = useNavigate();
  const {
    handleSubmit,
    register,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<LoginCredentials>();

  const toast = useToast();

  const onSubmit: SubmitHandler<LoginCredentials> = async (
    data: LoginCredentials
  ) => {
    // FIXME: Call real auth endpoint
    console.log(data);
    setAuthStatus({ isLoggedIn: true, isAdmin: true, username: data.username });
    reset();
    toast({
      title: 'Successfully logged in!',
      description: "You've been granted admin superpowers 💪",
      position: 'top',
      status: 'success',
      duration: 5000,
      isClosable: true,
    });
    navigate('/');
  };

  return (
    <Container
      maxW='lg'
      py={{ base: '12', md: '24' }}
      px={{ base: '0', sm: '8' }}
    >
      <Stack spacing='8'>
        <Center>
          <Heading size={{ base: 'md', md: 'xl' }}>Admin-Login</Heading>
        </Center>
        <Card py={8} px={10} variant={{ base: 'none', sm: 'outline' }}>
          {authStatus.isLoggedIn ? (
            <Stack align='center'>
              <Center bg='green' boxSize={12} borderRadius='full'>
                <Icon as={FaCheck} h={8} w={8} fill='white' bg='success' />
              </Center>
              <Text fontSize='lg'>Eingeloggt als {authStatus.username}</Text>
            </Stack>
          ) : (
            <form onSubmit={handleSubmit(onSubmit)}>
              <Stack spacing='5'>
                <FormControl isInvalid={Boolean(errors.username)}>
                  <FormLabel htmlFor='username'>Username</FormLabel>
                  <Input
                    id='username'
                    type='text'
                    defaultValue=''
                    {...register('username', { required: 'Required' })}
                  />
                  <FormErrorMessage>
                    {errors?.username?.message}
                  </FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={Boolean(errors.password)}>
                  <FormLabel htmlFor='password'>Passwort</FormLabel>
                  <Input
                    id='password'
                    type='password'
                    defaultValue=''
                    {...register('password', {
                      required: 'Required',
                      minLength: {
                        value: 8,
                        message: 'Password muss mindestens 8 Zeichen lang sein',
                      },
                    })}
                  />
                  <FormErrorMessage>
                    {errors?.password?.message}
                  </FormErrorMessage>
                </FormControl>
              </Stack>
              <Button
                w='full'
                mt={12}
                colorScheme='teal'
                isLoading={isSubmitting}
                type='submit'
                isDisabled={Object.keys(errors).length > 0}
              >
                Weiter als Admin
              </Button>
            </form>
          )}
          <Button w='full' mt={6} as={Link} to='/'>
            Zurück zur Homepage
          </Button>
        </Card>
      </Stack>
    </Container>
  );
};

export default AdminLoginPage;
