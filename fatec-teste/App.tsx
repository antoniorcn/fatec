import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import CadastroScreen from './components/Cadastro';
import HeartScreen from './components/HeartScreen';
import DeviceScreen from './components/DeviceConnectionModal';

const {Navigator, Screen} = createBottomTabNavigator()

export default function App() {
  return (
    <View style={styles.container}>
      <NavigationContainer>
        <Navigator>
          <Screen name="Cadastro" component={CadastroScreen}/>
          <Screen name="Device" component={DeviceScreen}/>
          <Screen name="Heart" component={HeartScreen}/>
          
        </Navigator>
      </NavigationContainer>
      <StatusBar style="auto" />

    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'strecth',
    justifyContent: 'center',
  },
});
