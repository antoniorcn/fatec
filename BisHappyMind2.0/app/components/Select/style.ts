import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
  inputContainer: {
    marginBottom: 20,
  },
  label: {
    fontSize: 18,
    color: "#000",
    marginBottom: 5,
  },
  pickerContainer: {
    backgroundColor: "#d3b3e8",
    borderRadius: 10,
    borderWidth: 1,
    borderColor: "gray",
    overflow: "hidden",
  },
  picker: {
    height: 50,
    color: "#000",
  },
  errorText: {
    color: "red",
    fontSize: 12,
    marginTop: 5,
  },
});

export default styles;
