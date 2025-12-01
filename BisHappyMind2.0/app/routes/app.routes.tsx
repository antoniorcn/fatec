import HeartScreen from "../screens/HeartScreen";
import {
  createNativeStackNavigator,
  NativeStackNavigationProp,
} from "@react-navigation/native-stack";
import VirtualRealityScreen from "../screens/VirtualRealityScreen";
import FormScreen from "../screens/FormScreen";
import CadastroScreen from "../screens/CadastroScreen";

type AppRoutes = {
  heartScreen: undefined;
  virtualRealityScreen: undefined;
  formScreen: undefined;
};

export type AppNavigatorRoutesProps = NativeStackNavigationProp<AppRoutes>;

const { Navigator, Screen } = createNativeStackNavigator<AppRoutes>();

export function AppRoutes() {
  return (
    <Navigator screenOptions={{ headerShown: false }}>
      <Screen name="heartScreen" component={HeartScreen} />

      <Screen name="virtualRealityScreen" component={VirtualRealityScreen} />

      <Screen name="formScreen" component={FormScreen} />

      <Screen name="cadastroScreen" component={CadastroScreen} />
    </Navigator>
  );
}
