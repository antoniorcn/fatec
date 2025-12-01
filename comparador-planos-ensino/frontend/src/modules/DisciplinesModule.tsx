'use client';

import { useEffect, useMemo, useState } from 'react';
import { SectionTabs } from '@/components/SectionTabs';
import { api } from '@/lib/api';
import type { CourseResponse, DisciplineResponse } from '@/types/api';

export function DisciplinesModule() {
  const [activeTab, setActiveTab] = useState<'form' | 'list'>('form');
  const [disciplines, setDisciplines] = useState<DisciplineResponse[]>([]);
  const [courses, setCourses] = useState<CourseResponse[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [courseFilter, setCourseFilter] = useState<number | 'all'>('all');
  const [formState, setFormState] = useState({
    title: '',
    code: '',
    syllabus: '',
    complementaryContent: '',
    keywords: '',
    courseId: ''
  });

  useEffect(() => {
    void Promise.all([loadCourses(), loadDisciplines()]);
  }, []);

  async function loadCourses() {
    try {
      const data = await api.listCourses();
      setCourses(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro ao carregar cursos.');
    }
  }

  async function loadDisciplines(courseId?: number) {
    setLoading(true);
    setError(null);
    try {
      const data = await api.listDisciplines(courseId);
      setDisciplines(data);
    } catch (err) {
      setError(
        err instanceof Error ? err.message : 'Erro ao carregar disciplinas.'
      );
    } finally {
      setLoading(false);
    }
  }

  const filteredDisciplines = useMemo(() => {
    if (courseFilter === 'all') return disciplines;
    return disciplines.filter((discipline) => discipline.courseId === courseFilter);
  }, [disciplines, courseFilter]);

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!formState.courseId) {
      setError('Selecione um curso para vincular a disciplina.');
      return;
    }
    setLoading(true);
    setError(null);
    try {
      await api.createDiscipline({
        title: formState.title.trim(),
        code: formState.code.trim() || undefined,
        syllabus: formState.syllabus.trim() || undefined,
        complementaryContent: formState.complementaryContent.trim() || undefined,
        keywords: formState.keywords
          .split(',')
          .map((keyword) => keyword.trim())
          .filter(Boolean),
        courseId: Number(formState.courseId)
      });
      setFormState({
        title: '',
        code: '',
        syllabus: '',
        complementaryContent: '',
        keywords: '',
        courseId: ''
      });
      await loadDisciplines(courseFilter === 'all' ? undefined : courseFilter);
      setActiveTab('list');
    } catch (err) {
      setError(
        err instanceof Error ? err.message : 'Não foi possível salvar a disciplina.'
      );
    } finally {
      setLoading(false);
    }
  }

  return (
    <section className="module-card">
      <header>
        <div>
          <p className="muted">Módulo</p>
          <h2>Disciplinas</h2>
          <p className="muted">
            Cadastre e consulte disciplinas associadas aos cursos.
          </p>
        </div>
        <SectionTabs activeTab={activeTab} onChange={setActiveTab} />
      </header>
      {error && <div className="error-banner">{error}</div>}
      {activeTab === 'form' ? (
        <form onSubmit={handleSubmit}>
          <label>
            Curso
            <select
              required
              value={formState.courseId}
              onChange={(event) =>
                setFormState((prev) => ({ ...prev, courseId: event.target.value }))
              }
            >
              <option value="" disabled>
                Selecione um curso
              </option>
              {courses.map((course) => (
                <option key={course.id} value={course.id}>
                  {course.name}
                </option>
              ))}
            </select>
          </label>
          <label>
            Título
            <input
              required
              value={formState.title}
              onChange={(event) =>
                setFormState((prev) => ({ ...prev, title: event.target.value }))
              }
              placeholder="Estruturas de Dados"
            />
          </label>
          <label>
            Código
            <input
              value={formState.code}
              onChange={(event) =>
                setFormState((prev) => ({ ...prev, code: event.target.value }))
              }
              placeholder="ADS123"
            />
          </label>
          <label>
            Palavras-chave (separadas por vírgula)
            <input
              value={formState.keywords}
              onChange={(event) =>
                setFormState((prev) => ({ ...prev, keywords: event.target.value }))
              }
              placeholder="algoritmos, estruturas, pilhas"
            />
          </label>
          <label>
            Ementa
            <textarea
              value={formState.syllabus}
              onChange={(event) =>
                setFormState((prev) => ({ ...prev, syllabus: event.target.value }))
              }
            />
          </label>
          <label>
            Conteúdo complementar
            <textarea
              value={formState.complementaryContent}
              onChange={(event) =>
                setFormState((prev) => ({
                  ...prev,
                  complementaryContent: event.target.value
                }))
              }
            />
          </label>
          <button
            className="primary"
            type="submit"
            disabled={
              loading || !formState.title.trim() || !formState.courseId.trim()
            }
          >
            {loading ? 'Salvando...' : 'Cadastrar disciplina'}
          </button>
        </form>
      ) : (
        <div>
          <div
            style={{
              display: 'flex',
              gap: '1rem',
              alignItems: 'center',
              marginBottom: '1rem'
            }}
          >
            <label style={{ flexDirection: 'row', alignItems: 'center', gap: '0.5rem' }}>
              Curso
              <select
                value={courseFilter === 'all' ? '' : courseFilter}
                onChange={(event) => {
                  const value = event.target.value;
                  const nextFilter = value === '' ? 'all' : Number(value);
                  setCourseFilter(nextFilter);
                  void loadDisciplines(value === '' ? undefined : Number(value));
                }}
              >
                <option value="">Todos</option>
                {courses.map((course) => (
                  <option key={course.id} value={course.id}>
                    {course.name}
                  </option>
                ))}
              </select>
            </label>
            <span className="muted">
              {loading
                ? 'Carregando disciplinas...'
                : `${filteredDisciplines.length} disciplina(s)`}
            </span>
          </div>
          <div style={{ overflowX: 'auto' }}>
            <table className="list-table">
              <thead>
                <tr>
                  <th>Título</th>
                  <th>Curso</th>
                  <th>Código</th>
                  <th>Palavras-chave</th>
                </tr>
              </thead>
              <tbody>
                {filteredDisciplines.map((discipline) => (
                  <tr key={discipline.id}>
                    <td>{discipline.title}</td>
                    <td>
                      {courses.find((course) => course.id === discipline.courseId)?.name ||
                        '—'}
                    </td>
                    <td>{discipline.code || '—'}</td>
                    <td>
                      <span className="tag-list">
                        {discipline.keywords.map((keyword) => (
                          <span className="tag" key={keyword}>
                            {keyword}
                          </span>
                        ))}
                        {discipline.keywords.length === 0 && <span className="muted">Sem palavras-chave</span>}
                      </span>
                    </td>
                  </tr>
                ))}
                {filteredDisciplines.length === 0 && !loading && (
                  <tr>
                    <td colSpan={4} className="muted">
                      Nenhuma disciplina encontrada.
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
