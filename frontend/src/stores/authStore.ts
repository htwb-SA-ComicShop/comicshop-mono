import { atom } from 'jotai';
import { AuthStatus } from '../types';

const AuthAtom = atom<AuthStatus>({ isLoggedIn: false, isAdmin: false });

export default AuthAtom;
