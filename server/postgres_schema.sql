create table if not exists app_users (
  id text primary key,
  google_id text unique not null,
  email text unique not null,
  name text not null default '',
  role text not null check (role in ('owner', 'teacher', 'student')),
  token text,
  refresh_token text,
  created_at timestamptz not null default now(),
  last_seen_at timestamptz not null default now()
);

create index if not exists idx_app_users_token on app_users(token);
create index if not exists idx_app_users_refresh_token on app_users(refresh_token);

create table if not exists classes (
  id text primary key,
  teacher_id text not null references app_users(id) on delete cascade,
  name text not null,
  description text not null default '',
  join_code text unique not null,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now()
);

create table if not exists enrollments (
  id text primary key,
  class_id text not null references classes(id) on delete cascade,
  student_id text not null references app_users(id) on delete cascade,
  joined_at timestamptz not null default now(),
  unique (class_id, student_id)
);

create table if not exists progress_entries (
  user_id text not null references app_users(id) on delete cascade,
  technique_id text not null,
  attempts integer not null default 0,
  best_score integer not null default 0,
  completed boolean not null default false,
  last_difficulty text not null default '',
  mastery_level text not null default '',
  last_played_at timestamptz,
  updated_at timestamptz not null default now(),
  primary key (user_id, technique_id)
);

create table if not exists practice_sessions (
  id text primary key,
  user_id text not null references app_users(id) on delete cascade,
  technique_id text not null,
  score integer not null default 0,
  difficulty text not null default '',
  duration_ms integer not null default 0,
  created_at timestamptz not null default now()
);

create table if not exists mistakes (
  id text primary key,
  user_id text not null references app_users(id) on delete cascade,
  technique_id text not null,
  question text not null,
  correct_answer text not null,
  user_answer text not null,
  payload jsonb not null default '{}'::jsonb,
  created_at timestamptz not null default now()
);

create table if not exists feedback_entries (
  id text primary key,
  user_id text not null references app_users(id) on delete cascade,
  type text not null default '',
  message text not null,
  technique_id text not null default '',
  screen text not null default '',
  difficulty text not null default '',
  reviewed_at timestamptz,
  reviewed_by text references app_users(id),
  created_at timestamptz not null default now()
);

create table if not exists shared_visualizations (
  id text primary key,
  technique_id text not null,
  technique_title text not null default '',
  category_id text not null default '',
  current_step integer not null default 0,
  note text not null default '',
  created_at timestamptz not null default now()
);

create table if not exists legacy_sessions (
  id text primary key,
  payload jsonb not null default '{}'::jsonb,
  created_at timestamptz not null default now()
);

create table if not exists app_preferences (
  key text primary key,
  value jsonb not null default '{}'::jsonb,
  updated_at timestamptz not null default now()
);

create table if not exists teacher_notes (
  id text primary key,
  class_id text not null references classes(id) on delete cascade,
  student_id text not null references app_users(id) on delete cascade,
  teacher_id text not null references app_users(id) on delete cascade,
  message text not null,
  status text not null default '',
  created_at timestamptz not null default now()
);

create table if not exists class_resources (
  id text primary key,
  class_id text not null references classes(id) on delete cascade,
  title text not null,
  type text not null default 'resource',
  file_name text not null,
  mime_type text not null,
  size_bytes integer not null default 0,
  uploaded_by text not null references app_users(id),
  download_path text not null,
  created_at timestamptz not null default now()
);

create table if not exists audit_logs (
  id text primary key,
  action text not null,
  actor_id text,
  actor_name text not null default '',
  target_id text not null default '',
  target_name text not null default '',
  details jsonb not null default '{}'::jsonb,
  created_at timestamptz not null default now()
);

create index if not exists idx_classes_teacher on classes(teacher_id);
create index if not exists idx_enrollments_student on enrollments(student_id);
create index if not exists idx_progress_user on progress_entries(user_id);
create index if not exists idx_sessions_user_created on practice_sessions(user_id, created_at desc);
create index if not exists idx_mistakes_user_created on mistakes(user_id, created_at desc);
create index if not exists idx_feedback_user_created on feedback_entries(user_id, created_at desc);
create index if not exists idx_resources_class_created on class_resources(class_id, created_at desc);
-- Supabase security hardening:
-- Android/web clients must go through the Node/Vercel API. Do not expose these
-- app tables through Supabase's public REST API.
alter table app_users enable row level security;
alter table classes enable row level security;
alter table enrollments enable row level security;
alter table progress_entries enable row level security;
alter table practice_sessions enable row level security;
alter table mistakes enable row level security;
alter table feedback_entries enable row level security;
alter table shared_visualizations enable row level security;
alter table legacy_sessions enable row level security;
alter table app_preferences enable row level security;
alter table teacher_notes enable row level security;
alter table class_resources enable row level security;
alter table audit_logs enable row level security;

revoke all on table
  app_users,
  classes,
  enrollments,
  progress_entries,
  practice_sessions,
  mistakes,
  feedback_entries,
  shared_visualizations,
  legacy_sessions,
  app_preferences,
  teacher_notes,
  class_resources,
  audit_logs
from anon, authenticated;

