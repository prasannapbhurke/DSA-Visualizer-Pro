import { useEffect, useState } from 'react';
import { createClient } from '@supabase/supabase-js';
import { api } from '../api/api';

export const useRealtime = (onEvent) => {
  const [status, setStatus] = useState('connecting');

  useEffect(() => {
    let channel = null;
    let client = null;
    let timer = null;

    const initRealtime = async () => {
      try {
        const res = await api('/api/admin/realtime-config');
        if (!res.data?.supabaseUrl || !res.data?.supabaseKey) {
          setStatus('polling');
          timer = setInterval(onEvent, 10000);
          return;
        }

        client = createClient(res.data.supabaseUrl, res.data.supabaseKey);
        
        let debounceTimer;
        const handleEvent = (payload) => {
          if (debounceTimer) clearTimeout(debounceTimer);
          debounceTimer = setTimeout(() => {
            onEvent();
            // Trigger native desktop notification if running in Electron
            if (window.electronAPI && payload) {
              window.electronAPI.showNotification(
                'Dashboard Update', 
                `New activity detected in ${payload.table}`
              );
            }
          }, 1000);
        };

        channel = client.channel('admin-dashboard')
          .on('postgres_changes', { event: '*', schema: 'public', table: 'progress_entries' }, handleEvent)
          .on('postgres_changes', { event: '*', schema: 'public', table: 'practice_sessions' }, handleEvent)
          .on('postgres_changes', { event: '*', schema: 'public', table: 'feedback_entries' }, handleEvent)
          .on('postgres_changes', { event: '*', schema: 'public', table: 'classes' }, handleEvent)
          .subscribe((state) => {
            if (state === 'SUBSCRIBED') setStatus('connected');
          });
      } catch (err) {
        console.error('Realtime init failed', err);
        setStatus('polling');
        timer = setInterval(onEvent, 10000);
      }
    };

    initRealtime();

    return () => {
      if (channel && client) client.removeChannel(channel);
      if (timer) clearInterval(timer);
    };
  }, [onEvent]);

  return status;
};
