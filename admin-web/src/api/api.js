export const getToken = () => localStorage.getItem('dsa_admin_token');
export const setToken = (t) => localStorage.setItem('dsa_admin_token', t);
export const removeToken = () => localStorage.removeItem('dsa_admin_token');

export const ENVIRONMENTS = [
  { id: 'local', label: 'Local Server (5000)', url: 'http://localhost:5000' },
  { id: 'cloud_vercel', label: 'Cloud API (Vercel)', url: 'https://dsa-visualizer-api-nu.vercel.app' },
  { id: 'cloud_render', label: 'Cloud API (Render)', url: 'https://dsa-visualizer-api-latest.onrender.com' },
];

export const getApiBaseUrl = () => {
  const saved = localStorage.getItem('dsa_admin_api_url');
  if (saved) return saved;
  // Default to local server if running in desktop, fallback to local
  return 'http://localhost:5000';
};

export const setApiBaseUrl = (url) => {
  localStorage.setItem('dsa_admin_api_url', url);
};

export const API_BASE_URL = getApiBaseUrl();

export const api = async (endpoint, options = {}) => {
  const token = getToken();
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const baseUrl = getApiBaseUrl();
  const url = endpoint.startsWith('http') ? endpoint : `${baseUrl}${endpoint}`;

  const response = await fetch(url, { ...options, headers });
  const data = await response.json().catch(() => ({}));

  if (!response.ok) {
    const error = new Error(data.error?.message || data.message || `Request failed with status ${response.status}`);
    error.status = response.status;
    if (response.status === 401) {
      error.isAuthError = true;
      removeToken();
    }
    throw error;
  }
  return data;
};
