import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f2f2f2",
  },
  heartRateTitleWrapper: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  heartRateTitleText: {
    fontSize: 30,
    fontWeight: "bold",
    textAlign: "center",
    marginHorizontal: 20,
    color: "black",
  },
  heartRateText: {
    fontSize: 25,
    marginTop: 15,
  },
  ctaButton: {
    backgroundColor: "#FF6060",
    justifyContent: "center",
    alignItems: "center",
    height: 50,
    marginHorizontal: 20,
    marginBottom: 5,
    borderRadius: 8,
  },
  ctaButtonText: {
    fontSize: 18,
    fontWeight: "bold",
    color: "white",
  },
  alertContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#D9B3FF",
    padding: 20,
    borderRadius: 20,
    margin: 20,
  },
  heartIconWrapper: {
    marginBottom: 10,
  },
  heartIcon: {
    fontSize: 40,
  },
  alertTitle: {
    fontSize: 25,
    fontWeight: "bold",
    color: "#000000",
    marginBottom: 10,
  },
  alertMessage: {
    fontSize: 18,
    textAlign: "center",
    marginBottom: 10,
    color: "#000000",
  },
  alertPrompt: {
    fontSize: 16,
    marginBottom: 20,
    color: "#000000",
  },
  buttonRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    width: "100%",
    marginBottom: 10,
  },
  alertButton: {
    backgroundColor: "#b388ff",
    padding: 10,
    borderRadius: 10,
    width: "45%",
    justifyContent: "center",
    alignItems: "center",
  },
  alertButtonFullWidth: {
    backgroundColor: "#b388ff",
    padding: 10,
    borderRadius: 10,
    width: "100%",
    justifyContent: "center",
    alignItems: "center",
  },
  alertButtonText: {
    color: "#FFFFFF",
    fontWeight: "bold",
    textAlign: "center",
  },

  buttonWrapper: {
    flexDirection: "row",
    justifyContent: "space-around",
    marginTop: 20,
    paddingHorizontal: 20,
  },
  secondaryButton: {
    backgroundColor: "#b388ff",
    padding: 15,
    borderRadius: 10,
    shadowColor: "#000",
    shadowOffset: { width: 2, height: 2 },
    shadowOpacity: 0.5,
    shadowRadius: 2,
    width: "40%",
    justifyContent: "center",
    alignItems: "center",
  },
  secondaryButtonText: {
    color: "#000",
    fontSize: 18,
    fontWeight: "bold",
  },
});

export default styles;
