'use client';

import { useState } from 'react';
import { Sidebar, type ModuleKey } from '@/components/Sidebar';
import { CoursesModule } from '@/modules/CoursesModule';
import { DisciplinesModule } from '@/modules/DisciplinesModule';
import { ComparisonsModule } from '@/modules/ComparisonsModule';

const MODULES: Record<ModuleKey, { title: string; element: React.ReactNode }> = {
  courses: {
    title: 'Cursos',
    element: <CoursesModule />
  },
  disciplines: {
    title: 'Disciplinas',
    element: <DisciplinesModule />
  },
  comparisons: {
    title: 'Comparações',
    element: <ComparisonsModule />
  }
};

export default function HomePage() {
  const [module, setModule] = useState<ModuleKey>('courses');

  return (
    <div className="app-shell">
      <Sidebar selected={module} onSelect={setModule} />
      <main className="main-content" aria-live="polite">
        <h1 style={{ marginTop: 0, marginBottom: '1.25rem' }}>
          {MODULES[module].title}
        </h1>
        {MODULES[module].element}
      </main>
    </div>
  );
}
