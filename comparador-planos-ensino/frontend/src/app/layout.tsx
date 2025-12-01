import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: 'Comparador de Planos de Ensino',
  description:
    'Interface para cadastrar cursos, disciplinas e solicitar comparações entre planos de ensino.'
};

export default function RootLayout({
  children
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="pt-BR">
      <body>{children}</body>
    </html>
  );
}
