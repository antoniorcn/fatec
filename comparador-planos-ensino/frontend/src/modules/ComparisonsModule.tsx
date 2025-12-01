'use client';

import { useEffect, useState } from 'react';
import { SectionTabs } from '@/components/SectionTabs';
import { api } from '@/lib/api';
import type { CourseResponse, CourseComparisonResponse } from '@/types/api';

type ComparisonEntry = CourseComparisonResponse & {
  createdAt: string;
  courseALabel: string;
  courseBLabel: string;
};

export function ComparisonsModule() {
  const [activeTab, setActiveTab] = useState<'form' | 'list'>('form');
  const [courses, setCourses] = useState<CourseResponse[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [history, setHistory] = useState<ComparisonEntry[]>([]);
  const [formState, setFormState] = useState({
    courseA: '',
    courseB: ''
  });

  useEffect(() => {
    void loadCourses();
  }, []);

  async function loadCourses() {
    try {
      const data = await api.listCourses();
      setCourses(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro ao carregar cursos.');
    }
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!formState.courseA || !formState.courseB) {
      setError('Selecione os dois cursos para comparar.');
      return;
    }
    if (formState.courseA === formState.courseB) {
      setError('Escolha cursos diferentes para comparar.');
      return;
    }
    setLoading(true);
    setError(null);
    try {
      const courseAId = Number(formState.courseA);
      const courseBId = Number(formState.courseB);
      const response = await api.compareCourses(courseAId, courseBId);
      const courseALabel =
        courses.find((course) => course.id === courseAId)?.name ?? `Curso ${courseAId}`;
      const courseBLabel =
        courses.find((course) => course.id === courseBId)?.name ?? `Curso ${courseBId}`;
      setHistory((prev) => [
        {
          ...response,
          createdAt: new Date().toISOString(),
          courseALabel,
          courseBLabel
        },
        ...prev
      ]);
      setActiveTab('list');
    } catch (err) {
      setError(
        err instanceof Error ? err.message : 'Não foi possível gerar a comparação.'
      );
    } finally {
      setLoading(false);
    }
  }

  const latestComparison = history[0];

  return (
    <section className="module-card">
      <header>
        <div>
          <p className="muted">Módulo</p>
          <h2>Comparações</h2>
          <p className="muted">
            Gere relatórios de similaridade entre cursos com base nas disciplinas.
          </p>
        </div>
        <SectionTabs activeTab={activeTab} onChange={setActiveTab} />
      </header>
      {error && <div className="error-banner">{error}</div>}
      {activeTab === 'form' ? (
        <form onSubmit={handleSubmit}>
          <label>
            Curso A
            <select
              required
              value={formState.courseA}
              onChange={(event) =>
                setFormState((prev) => ({ ...prev, courseA: event.target.value }))
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
            Curso B
            <select
              required
              value={formState.courseB}
              onChange={(event) =>
                setFormState((prev) => ({ ...prev, courseB: event.target.value }))
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
          <button className="primary" type="submit" disabled={loading}>
            {loading ? 'Comparando...' : 'Comparar cursos'}
          </button>
          {latestComparison && (
            <p className="muted">
              Última comparação: {latestComparison.courseALabel} vs{' '}
              {latestComparison.courseBLabel}
            </p>
          )}
        </form>
      ) : (
        <div>
          {history.length === 0 && (
            <p className="muted">
              Ainda não há comparações solicitadas. Utilize o formulário para iniciar.
            </p>
          )}
          {history.map((item) => (
            <article
              key={`${item.courseAId}-${item.courseBId}-${item.createdAt}`}
              style={{
                border: '1px solid var(--border)',
                borderRadius: '0.9rem',
                padding: '1.25rem',
                marginBottom: '1.25rem',
                background: 'var(--surface-muted)'
              }}
            >
              <header style={{ marginBottom: '1rem' }}>
                <h3 style={{ margin: 0 }}>
                  {item.courseALabel} vs {item.courseBLabel}
                </h3>
                <p className="muted">
                  Solicitado em {new Date(item.createdAt).toLocaleString('pt-BR')}
                </p>
              </header>
              <div className="comparison-grid">
                <div className="stat-card">
                  <h4>Índice de sobreposição</h4>
                  <strong>{(item.overlapScore * 100).toFixed(1)}%</strong>
                </div>
                <div className="stat-card">
                  <h4>Palavras-chave compartilhadas</h4>
                  <div className="tag-list">
                    {item.sharedKeywords.length === 0 && (
                      <span className="muted">Nenhuma</span>
                    )}
                    {item.sharedKeywords.map((keyword) => (
                      <span className="tag" key={`${item.createdAt}-${keyword}`}>
                        {keyword}
                      </span>
                    ))}
                  </div>
                </div>
                <div className="stat-card">
                  <h4>Exclusivas do curso A</h4>
                  <p>{item.exclusiveToCourseA.join(', ') || 'Nenhuma'}</p>
                </div>
                <div className="stat-card">
                  <h4>Exclusivas do curso B</h4>
                  <p>{item.exclusiveToCourseB.join(', ') || 'Nenhuma'}</p>
                </div>
              </div>
              <div style={{ marginTop: '1.25rem', overflowX: 'auto' }}>
                <table className="list-table">
                  <thead>
                    <tr>
                      <th>Disciplina curso A</th>
                      <th>Disciplina curso B</th>
                      <th>Similaridade</th>
                      <th>Palavras compartilhadas</th>
                    </tr>
                  </thead>
                  <tbody>
                    {item.relatedDisciplines.map((relation) => (
                      <tr
                        key={`${relation.disciplineAId}-${relation.disciplineBId}`}
                      >
                        <td>{relation.disciplineATitle}</td>
                        <td>{relation.disciplineBTitle}</td>
                        <td>{(relation.similarityScore * 100).toFixed(1)}%</td>
                        <td>{relation.sharedKeywords.join(', ') || '—'}</td>
                      </tr>
                    ))}
                    {item.relatedDisciplines.length === 0 && (
                      <tr>
                        <td colSpan={4} className="muted">
                          Nenhuma relação encontrada.
                        </td>
                      </tr>
                    )}
                  </tbody>
                </table>
              </div>
            </article>
          ))}
        </div>
      )}
    </section>
  );
}
