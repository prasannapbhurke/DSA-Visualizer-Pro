import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, HashRouter } from 'react-router-dom'
import './index.css'
import App from './App.jsx'

const isElectron = window.location.protocol === 'file:' || !!window.electronAPI;
const Router = isElectron ? HashRouter : BrowserRouter;
const routerProps = isElectron ? {} : { basename: '/admin' };

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Router {...routerProps}>
      <App />
    </Router>
  </StrictMode>,
)
