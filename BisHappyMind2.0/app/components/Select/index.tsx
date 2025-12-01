import React from "react";
import { Picker } from "@react-native-picker/picker";
import { Text, View } from "react-native";
import styles from "./style";

type SelectProps = {
  label: string;
  selectedValue: string;
  onValueChange: (itemValue: string) => void;
  items: { label: string; value: string }[];
  errorMessage?: string;
};

const Select: React.FC<SelectProps> = ({
  label,
  selectedValue,
  onValueChange,
  items,
  errorMessage,
}) => {
  return (
    <View style={styles.inputContainer}>
      <Text style={styles.label}>{label}</Text>
      <View style={styles.pickerContainer}>
        <Picker
          selectedValue={selectedValue}
          onValueChange={(itemValue) => onValueChange(itemValue)}
          style={styles.picker}
        >
          <Picker.Item label={`Selecione ${label.toLowerCase()}`} value="" />
          {items.map((item, index) => (
            <Picker.Item key={index} label={item.label} value={item.value} />
          ))}
        </Picker>
      </View>
      {errorMessage && <Text style={styles.errorText}>{errorMessage}</Text>}
    </View>
  );
};

export default Select;
