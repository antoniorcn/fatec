import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
    container: {
      flexGrow: 1,
      backgroundColor: "#A020F0",
      justifyContent: "center",
      padding: 20,
    },
    title: {
      fontSize: 32,
      fontWeight: "bold",
      textAlign: "center",
      color: "#000",
      marginBottom: 30,
    },
    inputContainer: {
      marginBottom: 20,
    },
    label: {
      fontSize: 18,
      color: "#000",
      marginBottom: 5,
    },
    input: {
      backgroundColor: "#d3b3e8",
      borderRadius: 10,
      height: 40,
      paddingHorizontal: 10,
    },
    dropdown: {
      backgroundColor: "#d3b3e8",
      borderColor: "gray",
      borderRadius: 10,
    },
    dropdownContainer: {
      backgroundColor: "#d3b3e8",
      borderColor: "gray",
    },
    button: {
      backgroundColor: "#3b0086",
      paddingVertical: 15,
      borderRadius: 10,
      marginTop: 20,
    },
    buttonText: {
      textAlign: "center",
      fontSize: 20,
      color: "#FFF",
      fontWeight: "bold",
    },
  });
  
  export default styles;
