import { createContext, ReactNode, useState, useEffect } from "react";
import { UserDTO } from "../dtos/UserDTO";
import { storageUserGet, storageUserSave } from "../storage/storageUser";

export type AuthContextDataProps = {
  user: UserDTO;
  createUser: (userData: UserDTO) => Promise<void>;
  getUser: () => Promise<void>;
};

export const AuthContext = createContext<AuthContextDataProps>(
  {} as AuthContextDataProps
);

type AuthContextProviderProps = {
  children: ReactNode;
};

export const AuthContextProvider = ({ children }: AuthContextProviderProps) => {
  const [user, setUser] = useState<UserDTO>({} as UserDTO);

  const getUser = async () => {
    try {
      const userData = await storageUserGet();
      setUser(userData);
    } catch (error) {
      throw error;
    }
  };

  const createUser = async (userData: UserDTO) => {
    try {
      await storageUserSave(userData);
      setUser(userData);
    } catch (error) {
      throw error;
    }
  };

  useEffect(() => {
    getUser();
  }, []);

  return (
    <AuthContext.Provider
      value={{
        user,
        createUser,
        getUser,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
