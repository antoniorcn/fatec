import { View, Text } from "react-native";
import { BleManager } from 'react-native-ble-plx' 

export const manager = new BleManager() 

function scan () { 
  manager.startDeviceScan(null, null, (error, device) => { 
    if (error) { 
      // Handle error (scanning will be stopped automatically) 
      return 
    } 
 
    // Check if it is a device, you are looking for based on advertisement data 
    // or other criteria. 
    if (device.name === 'TI BLE Sensor Tag' || device.name === 'SensorTag') { 
      // Stop scanning as it's not necessary if you are scanning for one device. 

      connect() 
      manager.stopDeviceScan() 
 
      // Proceed with connection. 
    } 
  }) 
} 

const connect = async () => {  
  try {  
    await manager.connectToDevice(deviceId).then(device=>{ 
      console.log('Connected to device:', device.name);  
      // Add your logic for handling the connected device 
      return device.discoverAllServicesAndCharacteristics(); 
    }).catch(error => { 
        // Handle errors 
    }) 
  } catch (error) {  
    console.error('Error connecting to device:', error);  
  }  
}; 

export default function App() {
  useEffect(() => { 
    const stateChangeListener = manager.onStateChange(state => { 
      console.log('onStateChange: ', state); 
      if (state === State.PoweredOn) {
        scan()
      } 
    });    
    return () => { 
      stateChangeListener?.remove(); 
    }; 
  }, [manager]); 

  useEffect(() => { 

    let locationSubscription = null; 
    const addLocationListener = async () => { 
      try { 
        const subscription = await SystemSetting.addLocationListener(data => { 
          console.log('Location: ', data); 
          setIsLocationOn(data); 
        }); 
        locationSubscription = subscription; 
      } catch (error) { 
        console.error('Error adding location listener:', error); 
      } 
    }; 
    addLocationListener(); 
    const stopLocationListener = () => { 
      if (locationSubscription) { 
        console.log('Listener stoped'); 
        locationSubscription.remove(); 
        locationSubscription = null; 
      } 
    }; 
    return () => { 
      stopLocationListener(); 
    }; 
  }, [isLocationOn]); 

  return (
    <View
      style={{
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Text>Universal React with Expo</Text>
    </View>
  );
}
