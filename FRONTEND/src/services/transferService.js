const API_URL = "http://localhost:8080";

export async function getTransfers() {
  try {
    const response = await fetch(`${API_URL}/transfers`);
    if (!response.ok) {
      console.error("Erro HTTP:", response.status);
      return { status: response.status, message: "Erro ao buscar transferências", data: [] };
    }
    const data = await response.json();
    if (data && typeof data.status === 'number' && typeof data.message === 'string' && Array.isArray(data.data)) {
      return data;
    }
    console.error("Resposta da API inesperada:", data);
    return { status: 500, message: "Resposta inesperada do servidor", data: [] };
  } catch (error) {
    console.error("Erro ao buscar transferências:", error);
    return { status: 500, message: "Erro de conexão ou ao processar a requisição", data: [] };
  }
}

export async function scheduleTransfer(transferData) {
  try {
    const response = await fetch(`${API_URL}/transfers`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(transferData),
    });
    if (!response.ok) {
      console.error("Erro HTTP ao agendar:", response.status);
      return { success: false, message: `Erro ${response.status} ao agendar transferência.` };
    }
    return { success: true, message: "Transferência agendada com sucesso!" };
  } catch (error) {
    console.error("Erro ao agendar transferência:", error);
    return { success: false, message: "Erro de conexão ou ao processar o agendamento." };
  }
}

