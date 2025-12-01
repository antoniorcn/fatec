import React from "react";
import { View, Text, TouchableOpacity, Linking } from "react-native";
import styles from "./style";
import { useNavigation } from "@react-navigation/native";
import { AppNavigatorRoutesProps } from "@/app/routes/app.routes";

const HeartRateAlert = () => {
  const { navigate } = useNavigation<AppNavigatorRoutesProps>();

  const handleAlert = () => {
    Linking.openURL(
      "https://open.spotify.com/playlist/3ccJAYoyLr9GxU3KJZ5LDp?si=8bCTRSvlSfCBkvBZtFZBaw&pi=8Vq4FPypQ-CRR&nd=1&dlsi=d99f0bf6edcb4162"
    );

    navigate("formScreen");
  };

  const goVirtualRealityVideo = () => {
    navigate("virtualRealityScreen");
  };

  const goBack = () => {
    navigate("heartScreen");
  };

  return (
    <View style={styles.alertContainer}>
      <View style={styles.heartIconWrapper}>
        <Text style={styles.heartIcon}>❤️</Text>
      </View>
      <Text style={styles.alertTitle}>Atenção!!</Text>
      <Text style={styles.alertMessage}>
        Seus batimentos cardíacos ultrapassaram 100bpm.
      </Text>
      <Text style={styles.alertPrompt}>Você gostaria...</Text>
      <View style={styles.buttonRow}>
        <TouchableOpacity onPress={handleAlert} style={styles.alertButton}>
          <Text style={styles.alertButtonText}>Escutar Playlist</Text>
        </TouchableOpacity>
        <TouchableOpacity
          onPress={goVirtualRealityVideo}
          style={styles.alertButton}
        >
          <Text style={styles.alertButtonText}>Participar de uma Imersão</Text>
        </TouchableOpacity>
      </View>
      <TouchableOpacity onPress={goBack} style={styles.alertButtonFullWidth}>
        <Text style={styles.alertButtonText}>
          Estou apenas realizando uma atividade física.
        </Text>
      </TouchableOpacity>
    </View>
  );
};

export default HeartRateAlert;
