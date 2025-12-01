'use client';

import { useEffect, useState } from 'react';
import { SectionTabs } from '@/components/SectionTabs';
import { api } from '@/lib/api';
import type { CourseResponse } from '@/types/api';

export function CoursesModule() {
  const [activeTab, setActiveTab] = useState<'form' | 'list'>('form');
  const [courses, setCourses] = useState<CourseResponse[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [formState, setFormState] = useState({
    name: '',
    description: ''
  });

  useEffect(() => {
    void refresh();
  }, []);

  async function refresh() {
    setLoading(true);
    setError(null);
    try {
      const data = await api.listCourses();
      setCourses(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro ao carregar cursos.');
    } finally {
      setLoading(false);
    }
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setLoading(true);
    setError(null);
    try {
      await api.createCourse({
        name: formState.name.trim(),
        description: formState.description.trim() || undefined
      });
      setFormState({ name: '', description: '' });
      await refresh();
      setActiveTab('list');
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Não foi possível salvar.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <section className="module-card">
      <header>
        <div>
          <p className="muted">Módulo</p>
          <h2>Cursos</h2>
          <p className="muted">
            Cadastre novos cursos e visualize o inventário existente.
          </p>
        </div>
        <SectionTabs activeTab={activeTab} onChange={setActiveTab} />
      </header>
      {error && <div className="error-banner">{error}</div>}
      {activeTab === 'form' ? (
        <form onSubmit={handleSubmit}>
          <label>
            Nome do curso
            <input
              required
              name="name"
              value={formState.name}
              onChange={(event) =>
                setFormState((prev) => ({
                  ...prev,
                  name: event.target.value
                }))
              }
              placeholder="Análise e Desenvolvimento de Sistemas"
            />
          </label>
          <label>
            Descrição
            <textarea
              name="description"
              value={formState.description}
              onChange={(event) =>
                setFormState((prev) => ({
                  ...prev,
                  description: event.target.value
                }))
              }
              placeholder="Curso superior tecnológico..."
            />
          </label>
          <button
            className="primary"
            type="submit"
            disabled={loading || !formState.name.trim()}
          >
            {loading ? 'Salvando...' : 'Cadastrar curso'}
          </button>
        </form>
      ) : (
        <div>
          <p className="muted">
            {loading
              ? 'Carregando lista de cursos...'
              : `Total: ${courses.length} curso(s)`}
          </p>
          <div style={{ overflowX: 'auto' }}>
            <table className="list-table">
              <thead>
                <tr>
                  <th>Nome</th>
                  <th>Descrição</th>
                  <th>Disciplinas cadastradas</th>
                </tr>
              </thead>
              <tbody>
                {courses.map((course) => (
                  <tr key={course.id}>
                    <td>{course.name}</td>
                    <td>{course.description || '—'}</td>
                    <td>{course.disciplines.length}</td>
                  </tr>
                ))}
                {courses.length === 0 && !loading && (
                  <tr>
                    <td colSpan={3} className="muted">
                      Nenhum curso cadastrado ainda.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </section>
  );
}
