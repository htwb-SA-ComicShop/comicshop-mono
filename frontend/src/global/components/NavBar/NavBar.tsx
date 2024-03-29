import {
    Box,
    Collapse,
    Flex,
    Heading,
    IconButton,
    Stack,
    useBreakpointValue,
    useColorMode,
    useDisclosure,
} from '@chakra-ui/react';
import {ReactElement} from 'react';
import {MdClose, MdDarkMode, MdLightMode, MdOutlineMenu,} from 'react-icons/md';
import {DesktopNav} from './DesktopNav';
import {MobileNav} from './MobileNav';
import {NavItem} from '../../../types';
import LogInOutButton from './LoginOutButton';
import useAuth from '../../../auth/hooks/useAuth.hook';
import {Link} from 'react-router-dom';

export default function NavBar(): ReactElement {
    const {isOpen, onToggle} = useDisclosure();
    const {colorMode, toggleColorMode} = useColorMode();
    const {isLoggedIn} = useAuth();

    const loggedOutNavItems: NavItem[] = [
        {
            label: 'Products',
            href: '/',
        },
    ];

    const loggedInNavItems = [
        ...loggedOutNavItems,
        {
            label: 'Cart',
            href: '/shopping-cart',
        },
        {
            label: 'Profile',
            href: '/profile',
        },
    ];

    const navItems = isLoggedIn ? loggedInNavItems : loggedOutNavItems;

    return (
        <Box
            as='nav'
            role='navigation'
            zIndex={2}
            bgColor='var(--chakra-colors-chakra-body-bg)'
        >
            <Flex minH='60px' align='center' width='80%' mx='auto' my={5}>
                <Flex
                    flex={{base: 1, md: 'auto'}}
                    display={{base: 'flex', md: 'none'}}
                    alignItems='center'
                >
                    <IconButton
                        onClick={onToggle}
                        icon={
                            isOpen ? <MdClose w={3} h={3}/> : <MdOutlineMenu w={5} h={5}/>
                        }
                        aria-label='Toggle Navigation'
                    />
                </Flex>
                <Flex
                    flex={{base: 1}}
                    justify={{base: 'center', md: 'start'}}
                    alignItems='stretch'
                >
                    <Heading
                        size='lg'
                        as={Link}
                        to={'/'}
                        textAlign={useBreakpointValue({base: 'center', md: 'left'})}
                    >
                        ComicShop
                    </Heading>

                    <Flex display={{base: 'none', md: 'flex'}} ml={10}>
                        <DesktopNav items={navItems}/>
                    </Flex>
                </Flex>

                <Stack flex={{base: 1, md: 0}} justify='flex-end' direction='row'>
                    <LogInOutButton platform='desktop'/>
                    <IconButton
                        onClick={toggleColorMode}
                        borderRadius='full'
                        aria-label='toggle-theme'
                    >
                        {colorMode === 'light' ? <MdDarkMode/> : <MdLightMode/>}
                    </IconButton>
                </Stack>
            </Flex>

            <Collapse in={isOpen} animateOpacity>
                <MobileNav items={navItems}/>
            </Collapse>
        </Box>
    );
}
