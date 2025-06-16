export interface Evento {
  id: number; // ID do evento (bigint no banco)
  nome_evento: string; // Nome do evento
  data_evento: string; // Data do evento
  id_palestrante?: number | null; // ID do palestrante (opcional)
}
