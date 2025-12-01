import Button from "@/app/components/Button";
import { Linking, Text, View } from "react-native";
import styles from "./style";
import { useNavigation } from "@react-navigation/native";
import { AppNavigatorRoutesProps } from "@/app/routes/app.routes";

const FormScreen = () => {
  const goToForm = () => {
    Linking.openURL("https://forms.gle/9nniYsWBxgPx65eYA");
  };

  const { navigate } = useNavigation<AppNavigatorRoutesProps>();

  const goBack = () => {
    navigate("heartScreen");
  };

  return (
    <View>
      <Text style={styles.instruction}>
        Por favor, responda o formulário abaixo para nos ajudar a coletar
        informações importantes sobre o seu acesso ao recurso de Musicoterapia.
        A sua participação é muito importante!
      </Text>

      <Button title="Acessar formulário" onPress={goToForm} />

      <Button title="Voltar para o início" onPress={goBack} />
    </View>
  );
};

export default FormScreen;
