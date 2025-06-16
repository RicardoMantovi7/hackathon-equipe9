package felipe.nascimento.service;

import felipe.nascimento.dao.InscricaoDao;

public class InscricaoService {
    private final InscricaoDao dao = new InscricaoDao();

    public boolean inscrever(Long alunoId, Long eventoId) {
        return dao.inscrever(alunoId, eventoId);
    }
}