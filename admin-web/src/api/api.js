export const getToken = () => localStorage.getItem('dsa_admin_token');
export const setToken = (t) => localStorage.setItem('dsa_admin_token', t);
export const removeToken = () => localStorage.removeItem('dsa_admin_token');

const isElectron = typeof window !== 'undefined' && !!window.electronAPI;
export const API_BASE_URL = isElectron ? 'https://dsa-visualizer-api-nu.vercel.app' : '';

export const api = async (endpoint, options = {}) => {
  const token = getToken();
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const url = endpoint.startsWith('http') ? endpoint : `${API_BASE_URL}${endpoint}`;
  const response = await fetch(url, { ...options, headers });
  const data = await response.json();

  if (!response.ok) {
    const error = new Error(data.error?.message || data.message || 'API Request failed');
    error.status = response.status;
    if (response.status === 401) {
      error.isAuthError = true;
      removeToken();
    }
    throw error;
  }
  return data;
};
