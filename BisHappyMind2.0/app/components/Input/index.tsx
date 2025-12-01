import React from "react";
import { View, Text, TextInput } from "react-native";
import styles from "./style";

interface InputProps {
  label: string;
  value: string;
  onChangeText: (text: string) => void;
  errorMessage?: string;
}

const Input: React.FC<InputProps> = ({
  label,
  value,
  onChangeText,
  errorMessage,
  ...rest
}) => {
  return (
    <View style={styles.inputContainer}>
      <Text style={styles.label}>{label}</Text>
      <TextInput
        style={styles.input}
        value={value}
        onChangeText={onChangeText}
        {...rest}
      />
      {errorMessage && <Text style={styles.errorText}>{errorMessage}</Text>}
    </View>
  );
};

export default Input;
