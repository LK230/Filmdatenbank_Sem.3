import api from "../api/axiosConfig";

const checkBackend = async () => {
  try {
    const response = await api.get("/movies");
    if (response.status === 200) {
      return true;
    }
  } catch (error) {
    console.error("Backend-Check-Fehler:", error);
  }
  return false;
};

export default checkBackend;
