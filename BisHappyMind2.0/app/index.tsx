import { AuthContextProvider } from "./contexts/AuthContext";
import { Routes } from "./routes";

export default function Index() {
  return (
    <AuthContextProvider>
      <Routes />
    </AuthContextProvider>
  );
}
