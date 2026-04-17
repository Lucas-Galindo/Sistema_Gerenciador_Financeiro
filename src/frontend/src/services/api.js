// ============================================================
//  services/api.js
//  Centraliza todas as chamadas ao backend Spring Boot.
//  BASE_URL aponta para o servidor local na porta 8080.
//  Se o backend não estiver rodando, as funções retornam
//  dados mock para que o frontend possa ser visualizado.
// ============================================================

const BASE_URL = 'http://localhost:8080';

// Helper: faz fetch e retorna JSON; em caso de erro retorna null
async function request(path, options = {}) {
  try {
    const res = await fetch(`${BASE_URL}${path}`, {
      headers: { 'Content-Type': 'application/json' },
      ...options,
    });
    if (!res.ok) return null;
    return await res.json();
  } catch {
    return null; // backend offline — componente usará mock data
  }
}

// ---- USUARIO ------------------------------------------------
export const usuarioApi = {
  getAll:     ()       => request('/usuario/get-all'),
  getById:    (id)     => request(`/usuario/get-by-id/${id}`),
  getByEmail: (email)  => request(`/usuario/get-by-email/${email}`),
  search:     (kw)     => request(`/usuario/get-by-keyword/${kw}`),
  save:       (data)   => request('/usuario', { method: 'POST', body: JSON.stringify(data) }),
  update:     (data)   => request('/usuario', { method: 'PUT',  body: JSON.stringify(data) }),
  delete:     (id)     => request(`/usuario/${id}`, { method: 'DELETE' }),
};

// ---- TRANSACAO ----------------------------------------------
export const transacaoApi = {
  getAll:        ()         => request('/transacao/get-all'),
  getById:       (id)       => request(`/transacao/get-by-id/${id}`),
  getByUsuario:  (uid)      => request(`/transacao/get-by-usuario/${uid}`),
  search:        (kw)       => request(`/transacao/get-by-keyword/${kw}`),
  save:          (data)     => request('/transacao', { method: 'POST', body: JSON.stringify(data) }),
  update:        (data)     => request('/transacao', { method: 'PUT',  body: JSON.stringify(data) }),
  delete:        (id)       => request(`/transacao/${id}`, { method: 'DELETE' }),
};

// ---- CONTAS -------------------------------------------------
export const contasApi = {
  getAll:       ()    => request('/contas/get-all'),
  getById:      (id)  => request(`/contas/get-by-id/${id}`),
  getByUsuario: (uid) => request(`/contas/get-by-usuario/${uid}`),
  save:         (d)   => request('/contas', { method: 'POST', body: JSON.stringify(d) }),
  update:       (d)   => request('/contas', { method: 'PUT',  body: JSON.stringify(d) }),
  delete:       (id)  => request(`/contas/${id}`, { method: 'DELETE' }),
};

// ---- METAS --------------------------------------------------
export const metasApi = {
  getAll:           ()            => request('/metas/get-all'),
  getByUsuario:     (uid)         => request(`/metas/get-by-usuario/${uid}`),
  save:             (data)        => request('/metas', { method: 'POST', body: JSON.stringify(data) }),
  update:           (data)        => request('/metas', { method: 'PUT',  body: JSON.stringify(data) }),
  delete:           (id)          => request(`/metas/${id}`, { method: 'DELETE' }),
  updateProgress:   (id, valor)   => request(`/metas/update-progress/${id}?valorAtual=${valor}`, { method: 'PUT' }),
};

// ---- CARTOES ------------------------------------------------
export const cartoesApi = {
  getAll:       ()    => request('/cartoes/get-all'),
  getByUsuario: (uid) => request(`/cartoes/get-by-usuario/${uid}`),
  save:         (d)   => request('/cartoes', { method: 'POST', body: JSON.stringify(d) }),
  delete:       (id)  => request(`/cartoes/${id}`, { method: 'DELETE' }),
};

// ---- CATEGORIA ----------------------------------------------
export const categoriaApi = {
  getAll:  () => request('/categoria/get-all'),
  search:  (kw) => request(`/categoria/get-by-keyword/${kw}`),
  save:    (d)  => request('/categoria', { method: 'POST', body: JSON.stringify(d) }),
  delete:  (id) => request(`/categoria/${id}`, { method: 'DELETE' }),
};

// ---- TIPO TRANSACAO -----------------------------------------
export const tipoTransacaoApi = {
  getAll: () => request('/tipo-transacao/get-all'),
};

// ---- BANCOS -------------------------------------------------
export const bancosApi = {
  getAll: () => request('/bancos/get-all'),
};

// ---- TIPO CONTAS --------------------------------------------
export const tipoContasApi = {
  getAll: () => request('/tipo-contas/get-all'),
};

// ---- ESTADOS / CIDADES --------------------------------------
export const estadosApi = {
  getAll: () => request('/estados/get-all'),
};
export const cidadesApi = {
  getAll: () => request('/cidades/get-all'),
};

// ============================================================
//  MOCK DATA — usados quando o backend não está disponível
// ============================================================
export const mockData = {
  usuario: { id: 1, nome: 'Alex Mercer', email: 'alex@example.com', cpf: '000.000.000-00' },

  transacoes: [
    { id: 1, descricao: 'Apple Store Soho', valor: -1299.00, dataMovimentacao: '2024-10-24', categoria: { nome: 'Tecnologia', tipo: 'DESPESA' }, tipoTransacao: { nome: 'Cartão' }, status: 'Pendente' },
    { id: 2, descricao: "The Butcher's Daughter", valor: -84.50, dataMovimentacao: '2024-10-24', categoria: { nome: 'Alimentação', tipo: 'DESPESA' }, tipoTransacao: { nome: 'Dinheiro' }, status: 'Processado' },
    { id: 3, descricao: "Standard & Poor's Dividend", valor: 412.00, dataMovimentacao: '2024-10-23', categoria: { nome: 'Investimentos', tipo: 'RECEITA' }, tipoTransacao: { nome: 'Transferência' }, status: 'Liquidado' },
    { id: 4, descricao: 'ConEd Utility Bill', valor: -210.15, dataMovimentacao: '2024-10-23', categoria: { nome: 'Utilidades', tipo: 'DESPESA' }, tipoTransacao: { nome: 'Débito' }, status: 'Processado' },
    { id: 5, descricao: 'Greystone Properties LLC', valor: -3200.00, dataMovimentacao: '2024-10-21', categoria: { nome: 'Aluguel', tipo: 'DESPESA' }, tipoTransacao: { nome: 'Transferência' }, status: 'Processado' },
    { id: 6, descricao: 'Global Tech Corp', valor: 8450.00, dataMovimentacao: '2024-10-20', categoria: { nome: 'Salário', tipo: 'RECEITA' }, tipoTransacao: { nome: 'Depósito' }, status: 'Liquidado' },
  ],

  contas: [
    { id: 1, nome: 'Chase Platinum ••••8821', saldoInicial: 24102.50, banco: { nome: 'Chase' }, tipoContas: { nome: 'Corrente' } },
    { id: 2, nome: 'Amex Reserve ••••1004', saldoInicial: 1420.10, banco: { nome: 'Amex' }, tipoContas: { nome: 'Crédito' } },
    { id: 3, nome: 'Goldman Sachs HYSA', saldoInicial: 89200.00, banco: { nome: 'Goldman Sachs' }, tipoContas: { nome: 'Poupança' } },
    { id: 4, nome: 'Fidelity Investment', saldoInicial: 41250.82, banco: { nome: 'Fidelity' }, tipoContas: { nome: 'Corretora' } },
  ],

  metas: [
    { id: 1, nome: 'Emergency Fund', valorObjetivo: 25000, valorAtual: '21800', dataLimite: '2024-12-31', tipo: 'emergency' },
    { id: 2, nome: 'New Car', valorObjetivo: 45000, valorAtual: '12450', dataLimite: '2026-06-30', tipo: 'car' },
    { id: 3, nome: 'House Fund', valorObjetivo: 150000, valorAtual: '85250', dataLimite: '2027-10-31', tipo: 'house' },
  ],

  cartoes: [
    { id: 1, nome: 'Amex Reserve', limite: 15000, fechamento: '2024-10-10', vencimento: '2024-10-20' },
  ],
};
