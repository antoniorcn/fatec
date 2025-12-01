'use client';

type ModuleKey = 'courses' | 'disciplines' | 'comparisons';

const MODULES: Record<
  ModuleKey,
  {
    title: string;
    description: string;
  }
> = {
  courses: {
    title: 'Cursos',
    description: 'Cadastre e visualize cursos disponíveis.'
  },
  disciplines: {
    title: 'Disciplinas',
    description: 'Mantenha as disciplinas vinculadas aos cursos.'
  },
  comparisons: {
    title: 'Comparações',
    description: 'Solicite comparações entre currículos.'
  }
};

interface SidebarProps {
  selected: ModuleKey;
  onSelect: (module: ModuleKey) => void;
}

export function Sidebar({ selected, onSelect }: SidebarProps) {
  return (
    <aside className="sidebar">
      <div>
        <p className="muted">Painel</p>
        <h1>Comparador de Planos</h1>
      </div>
      {Object.entries(MODULES).map(([module, meta]) => {
        const key = module as ModuleKey;
        const isActive = selected === key;
        return (
          <button
            key={module}
            className={`sidebar__item${isActive ? ' sidebar__item--active' : ''}`}
            onClick={() => onSelect(key)}
            type="button"
          >
            <h2>{meta.title}</h2>
            <p>{meta.description}</p>
          </button>
        );
      })}
    </aside>
  );
}

export type { ModuleKey };
