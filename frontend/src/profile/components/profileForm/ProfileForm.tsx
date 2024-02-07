import {SubmitHandler, useForm} from 'react-hook-form';
import ValidatedInput from './ValidatedInput';
import {Button, Stack, useToast} from '@chakra-ui/react';
import {useNavigate} from 'react-router';
import {Profile} from '../../../types';
import useAuth from '../../../auth/hooks/useAuth.hook';

interface FormProps {
    defaults?: Profile;
    method: 'PUT';
    id?: string;
}

const ProfileForm = ({defaults, method}: FormProps) => {
    const {
        handleSubmit,
        register,
        formState: {errors, isSubmitting},
    } = useForm<Profile>();

    const {token} = useAuth();

    const hasErrors = Object.keys(errors).length > 0;

    const navigate = useNavigate();

    const toast = useToast();

    const url = `http://localhost:8080/profile`;

    const onSubmit: SubmitHandler<Profile> = async (data: Profile) => {
        const payload: Profile = data;
        try {
            const response = await fetch(url, {
                method,
                mode: 'cors',
                cache: 'no-cache',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(payload),
            });
            if (!response.ok) {
                throw new Error('Something went wrong!');
            }
            navigate('/profile');
            toast({
                title: 'All right!',
                description: `Profile successfully updated`,
                position: 'top',
                status: 'success',
                duration: 5000,
                isClosable: true,
            });
        } catch (error) {
            if (error instanceof Error) {
                toast({
                    title: 'Ooops!',
                    description:
                        `${payload.username} could not been updated!`,
                    position: 'top',
                    status: 'error',
                    duration: 5000,
                    isClosable: true,
                });
            }
        }
    };

    return (
        <Stack as='form' onSubmit={handleSubmit(onSubmit)} w='100%' spacing={4}>
            <ValidatedInput
                id='username'
                label='Username'
                defaultValue={defaults?.username}
                errorMsg={errors?.username?.message}
                registerReturn={register('username', {
                    required: 'Required',
                    minLength: {
                        value: 3,
                        message: 'Username has to be at least 3 characters long',
                    },
                })}
            />
            <ValidatedInput
                id='imageSrc'
                label='Image URL'
                defaultValue={defaults?.imgUrl}
                errorMsg={errors?.imgUrl?.message}
                registerReturn={register('imgUrl', {
                    pattern: {
                        value:
                            /[-a-zA-Z0-9@:%._+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)?/gi,
                        message: 'Please enter a valid HTTP/HTTPS URL',
                    },
                    maxLength: {
                        value: 250,
                        message: 'The URL has to be at most 250 characters long',
                    },
                })}
            />
            <ValidatedInput
                id='firstname'
                label='Firstname'
                defaultValue={defaults?.firstname}
                errorMsg={errors?.firstname?.message}
                registerReturn={register('firstname', {required: 'Required'})}
            />
            <ValidatedInput
                id='lastname'
                label='Lastname'
                defaultValue={defaults?.lastname}
                errorMsg={errors?.lastname?.message}
                registerReturn={register('lastname', {required: 'Required'})}
            />
            <ValidatedInput
                id='email'
                label='Email'
                defaultValue={defaults?.email}
                errorMsg={errors?.email?.message}
                registerReturn={register('email', {
                    required: 'Required',
                    minLength: {
                        value: 5,
                        message: 'The description has to be at least 5 characters long',
                    },
                })}
            />
            <Button
                colorScheme='teal'
                isLoading={isSubmitting}
                isDisabled={hasErrors}
                type='submit'
            >
                Save
            </Button>
            <Button
                colorScheme='teal'
                variant='outline'
                onClick={() => navigate('/profile')}
            >
                Back
            </Button>
        </Stack>
    );
};

export default ProfileForm;
