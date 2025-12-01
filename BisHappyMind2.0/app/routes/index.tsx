import { AuthRoutes } from "./auth.routes";
import { useAuth } from "../hooks/useAuth";
import { AppRoutes } from "./app.routes";
import { NavigationContainer } from "@react-navigation/native";

export function Routes() {
  const { user } = useAuth();

  return (
    <NavigationContainer independent={true}>
      <AppRoutes />
    </NavigationContainer>
  );
}
