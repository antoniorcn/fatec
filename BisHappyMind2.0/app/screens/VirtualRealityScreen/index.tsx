import { View } from "react-native";
import YoutubeIframe from "react-native-youtube-iframe";
import * as ScreenOrientation from "expo-screen-orientation";
import { useCallback } from "react";
import styles from "./style";
import Button from "@/app/components/Button";
import { useNavigation } from "expo-router";

const VirtualRealityScreen = () => {
  const { goBack } = useNavigation();

  const VIDEO_HEIGHT = 200;

  //   const onFullScreenChange = useCallback((isFullScreen: boolean) => {
  //     if (isFullScreen) {
  //       ScreenOrientation.lockAsync(ScreenOrientation.OrientationLock.LANDSCAPE);
  //     } else {
  //       ScreenOrientation.lockAsync(ScreenOrientation.OrientationLock.PORTRAIT);
  //     }
  //   }, []);

  return (
    <View style={styles.container}>
      <YoutubeIframe videoId="2v9PM5F-GVY" height={VIDEO_HEIGHT} />

      <Button
        title="Voltar"
        onPress={() => {
          goBack();
        }}
      />
    </View>
  );
};

export default VirtualRealityScreen;
