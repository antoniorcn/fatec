'use client';

type TabKey = 'form' | 'list';

interface SectionTabsProps {
  activeTab: TabKey;
  onChange: (tab: TabKey) => void;
}

export function SectionTabs({ activeTab, onChange }: SectionTabsProps) {
  return (
    <div className="tab-bar">
      <button
        type="button"
        className={activeTab === 'form' ? 'active' : undefined}
        onClick={() => onChange('form')}
      >
        Formulário
      </button>
      <button
        type="button"
        className={activeTab === 'list' ? 'active' : undefined}
        onClick={() => onChange('list')}
      >
        Lista
      </button>
    </div>
  );
}

export type { TabKey };
