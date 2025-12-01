import React, { useState, useEffect, useRef } from 'react';
import classNames from 'classnames';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import CursoService from '../service/CursoService';
import FaculdadeService from '../service/FaculdadeService';
import DisciplinaService from '../service/DisciplinaService';
import { Toast } from 'primereact/toast';
import { Checkbox } from 'primereact/checkbox';
import { ListBox } from 'primereact/listbox';
import { Panel } from 'primereact/panel';
import { Button } from 'primereact/button';
import { FileUpload } from 'primereact/fileupload';
import { Rating } from 'primereact/rating';
import { Toolbar } from 'primereact/toolbar';
import { InputTextarea } from 'primereact/inputtextarea';
import { RadioButton } from 'primereact/radiobutton';
import { Dropdown } from 'primereact/dropdown';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';

const Match = () => {

    let emptyEntity = {
        id: null,
        nome: '',
        objetivo: '',
        ementa: '',
        metodologiaProposta: '',
        instrumentosAvaliacao: '',
        bibliografiaBasica: '',
        bibliografiaComplementar: '',
        descricao: '',
        faculdade: 0,
        curso: 0
    };

    const entidadeNome = "Disciplina";

    const dropdownCities = [
        { name: 'New York', code: 'NY' },
        { name: 'Rome', code: 'RM' },
        { name: 'London', code: 'LDN' },
        { name: 'Istanbul', code: 'IST' },
        { name: 'Paris', code: 'PRS' }
    ];

    const [entityList, setEntityList] = useState(null);
    const [entityDialog, setEntityDialog] = useState(false);
    const [deleteEntityDialog, setDeleteEntityDialog] = useState(false);
    const [deleteEntityListDialog, setDeleteEntityListDialog] = useState(false);
    const [entity, setEntity] = useState(emptyEntity);
    const [selectedEntities, setSelectedEntitites] = useState(null);
    const [submitted, setSubmitted] = useState(false);
    const [globalFilter, setGlobalFilter] = useState(null);
    const toast = useRef(null);
    const dt = useRef(null);
    const entityService = new DisciplinaService();
    const faculdadeService = new FaculdadeService();
    const cursoService = new CursoService();

    const [faculdades, setFaculdades] = useState(null);
    const [faculdade, setFaculdade] = useState(0);

    const [cursos, setCursos] = useState(null);
    const [curso, setCurso] = useState(0);    

    const [disciplinas, setDisciplinas] = useState(null);
    const [disciplina, setDisciplina] = useState(0);    


    const [tasksCheckbox, setTasksCheckbox] = useState([]);
    const [dropdownCity, setDropdownCity] = useState(null);

    useEffect(() => {
        entityService.getAll().then(data => setEntityList(data));
        faculdadeService.getAll().then(data => setFaculdades(data));
    }, []); // eslint-disable-line react-hooks/exhaustive-deps

    const formatCurrency = (value) => {
        return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
    }

    const openNew = () => {
        setEntity(emptyEntity);
        setSubmitted(false);
        setEntityDialog(true);
    }

    const hideDialog = () => {
        setSubmitted(false);
        setEntityDialog(false);
    }

    const hideDeleteEntityDialog = () => {
        setDeleteEntityDialog(false);
    }

    const hideDeleteEntityListDialog = () => {
        setDeleteEntityListDialog(false);
    }

    const saveEntity = () => {
        setSubmitted(true);

        if (entity.nome.trim()) {
            let _entity = {...entity};
            _entity.faculdade = faculdade;
            _entity.curso = curso;
            if (entity.id) {
                entityService.save(_entity);
                toast.current.show({ severity: 'success', summary: 'Sucesso', detail: `${entidadeNome} Atualizado`, life: 3000 });
            }
            else {
                _entity.image = 'product-placeholder.svg';
                entityService.add(_entity);
                toast.current.show({ severity: 'success', summary: 'Sucesso', detail: `${entidadeNome} Criado`, life: 3000 });
            }
            entityService.getAll().then(data => setEntityList(data));
            // setEntityList(_entities);
            setEntityDialog(false);
            setEntity(emptyEntity);
        }
    }

    const editEntity = (entidade) => {
        setEntity({...entidade});
        setEntityDialog(true);
    }

    const confirmDeleteEntity = (entidade) => {
        setEntity(entidade);
        setDeleteEntityDialog(true);
    }

    const deleteEntity = () => {
        let _entities = entityList.filter(val => val.id === entity.id);
        _entities.forEach((item) => {
            entityService.delete(item);
        });
        setDeleteEntityDialog(false);
        setEntity(emptyEntity);
        toast.current.show({ severity: 'success', summary: 'Sucesso', detail: `${entidadeNome} Apagado`, life: 3000 });
        entityService.getAll().then(data => setEntityList(data));
    }

    const findIndexById = (id) => {
        let index = -1;
        for (let i = 0; i < entityList.length; i++) {
            if (entityList[i].id === id) {
                index = i;
                break;
            }
        }
        return index;
    }

    const createId = () => {
        let id = '';
        let chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        for (let i = 0; i < 5; i++) {
            id += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return id;
    }

    const exportCSV = () => {
        dt.current.exportCSV();
    }

    const confirmDeleteSelected = () => {
        setDeleteEntityListDialog(true);
    }

    const deleteSelectedEntities = () => {
        let _entities = entityList.filter(val => selectedEntities.includes(val));
        _entities.forEach((item) => {
            entityService.delete(item);
        });
        // setEntityList(_entities);
        setDeleteEntityListDialog(false);
        setSelectedEntitites(null);
        toast.current.show({ severity: 'success', summary: 'Sucesso', detail: `${entidadeNome}s Apagados`, life: 3000 });
        entityService.getAll().then(data => setEntityList(data));
    }

    const onSelectChange = (e, name) => {
        let _entidade = {...entity};
        _entidade[`${name}`] = e.value;
        setEntity(_entidade);
    }

    const onInputChange = (e, name) => {
        const val = (e.target && e.target.value) || '';
        let _entidade = {...entity};
        _entidade[`${name}`] = val;
        setEntity(_entidade);
    }

    const onInputNumberChange = (e, name) => {
        const val = e.value || 0;
        let _entidade = {...entity};
        _entidade[`${name}`] = val;
        setEntity(_entidade);
    }

    const onTaskCheckboxChange = (e) => {
        let selectedTasks = [...tasksCheckbox];
        if (e.checked)
            selectedTasks.push(e.value);
        else
            selectedTasks.splice(selectedTasks.indexOf(e.value), 1);

        setTasksCheckbox(selectedTasks);
    };

    const leftToolbarTemplate = () => {
        return (
            <React.Fragment>
                <Button label="New" icon="pi pi-plus" className="p-button-success p-mr-2" onClick={openNew} />
                <Button label="Delete" icon="pi pi-trash" className="p-button-danger" onClick={confirmDeleteSelected} disabled={!selectedEntities || !selectedEntities.length} />
            </React.Fragment>
        )
    }

    const rightToolbarTemplate = () => {
        return (
            <React.Fragment>
                <FileUpload mode="basic" accept="image/*" maxFileSize={1000000} label="Import" chooseLabel="Import" className="p-mr-2 p-d-inline-block" />
                <Button label="Export" icon="pi pi-upload" className="p-button-help" onClick={exportCSV} />
            </React.Fragment>
        )
    }

    const imageBodyTemplate = (rowData) => {
        return <img src={`showcase/demo/images/product/${rowData.image}`} onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} alt={rowData.image} className="product-image" />
    }

    const priceBodyTemplate = (rowData) => {
        return formatCurrency(rowData.price);
    }

    const ratingBodyTemplate = (rowData) => {
        return <Rating value={rowData.rating} readOnly cancel={false} />;
    }

    const statusBodyTemplate = (rowData) => {
        return <span className={`product-badge status-${rowData.inventoryStatus.toLowerCase()}`}>{rowData.inventoryStatus}</span>;
    }

    const actionBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success p-mr-2" onClick={() => editEntity(rowData)} />
                <Button icon="pi pi-trash" className="p-button-rounded p-button-warning" onClick={() => confirmDeleteEntity(rowData)} />
            </React.Fragment>
        );
    }

    const header = (title) =>{ return (
        <div className="table-header">
            <h5 className="p-m-0">{title}</h5>
            <span className="p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => setGlobalFilter(e.target.value)} placeholder="Pesquisar..." />
            </span>
        </div>
    )};
    const entityDialogFooter = (
        <React.Fragment>
            <Button label="Cancelar" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
            <Button label="Salvar" icon="pi pi-check" className="p-button-text" onClick={saveEntity} />
        </React.Fragment>
    );
    const deleteEntityDialogFooter = (
        <React.Fragment>
            <Button label="Não" icon="pi pi-times" className="p-button-text" onClick={hideDeleteEntityDialog} />
            <Button label="Sim" icon="pi pi-check" className="p-button-text" onClick={deleteEntity} />
        </React.Fragment>
    );
    const deleteEntityListDialogFooter = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={hideDeleteEntityListDialog} />
            <Button label="Yes" icon="pi pi-check" className="p-button-text" onClick={deleteSelectedEntities} />
        </React.Fragment>
    );

    return (
        <React.Fragment>
            <div className="p-grid p-fluid dashboard">
                <div className="p-col-12 p-md-6 p-lg-6">
                    <Panel header="Disciplina a ser aproveitada" style={{ height: '100%' }}>
                        <div className="p-grid">
                            <div className="p-col-12 p-md-4">
                                <label>Faculdade</label>
                            </div>
                            <div className="p-col-12 p-md-8">
                                <div className="p-formgrid p-grid">
                                    <Dropdown optionLabel="nome" optionValue="id" value={faculdade} options={faculdades} onChange={(e) => setFaculdade(e.value)} placeholder="Selecione a Faculdade"/>
                                </div>
                            </div>
                            <div className="p-col-12 p-md-4">
                                <label>Curso</label>
                            </div>
                            <div className="p-col-12 p-md-8">
                                <div className="p-formgrid p-grid">
                                    <Dropdown optionLabel="nome" optionValue="id" value={faculdade} options={faculdades} onChange={(e) => setCurso(e.value)} placeholder="Selecione o Curso"/>
                                </div>
                            </div>           
                            <div className="p-col-12 p-md-4">
                                <label>Disciplina</label>
                            </div>
                            <div className="p-col-12 p-md-8">
                                <div className="p-formgrid p-grid">
                                    <ListBox  optionLabel="nome" optionValue="id" value={disciplina} options={disciplinas} onChange={(e) => setCurso(e.value)} placeholder="Selecione o Disciplina" multiple filter/>
                                </div>
                            </div>                                                 
                        </div>
                    </Panel>
                </div>

                <div className="p-col-12 p-md-6 p-lg-6 p-fluid contact-form">
                <Panel header="Disciplina a ser aproveitada" style={{ height: '100%' }}>
                        <div className="p-grid">
                            <div className="p-col-12 p-md-4">
                                <label>Faculdade</label>
                            </div>
                            <div className="p-col-12 p-md-8">
                                <div className="p-formgrid p-grid">
                                    <Dropdown optionLabel="nome" optionValue="id" value={faculdade} options={faculdades} onChange={(e) => setFaculdade(e.value)} placeholder="Selecione a Faculdade"/>
                                </div>
                            </div>
                            <div className="p-col-12 p-md-4">
                                <label>Curso</label>
                            </div>
                            <div className="p-col-12 p-md-8">
                                <div className="p-formgrid p-grid">
                                    <Dropdown optionLabel="nome" optionValue="id" value={faculdade} options={faculdades} onChange={(e) => setCurso(e.value)} placeholder="Selecione o Curso"/>
                                </div>
                            </div>           
                            <div className="p-col-12 p-md-4">
                                <label>Disciplina</label>
                            </div>
                            <div className="p-col-12 p-md-8">
                                <div className="p-formgrid p-grid">
                                    <ListBox  optionLabel="nome" optionValue="id" value={disciplina} options={disciplinas} onChange={(e) => setCurso(e.value)} placeholder="Selecione o Disciplina" multiple filter/>
                                </div>
                            </div>                                                 
                        </div>
                    </Panel>
                </div>
            </div>
            <div className="datatable-crud-demo">
                <Panel>
                    <div className="card card-w-title">
                        <h1>Relação entre disciplinas</h1>
                        <div className="p-grid">
                            <div className="p-col-12 p-md-2">
                                <label>Faculdade</label>
                            </div>
                            <div className="p-col-12 p-md-4">
                                <div className="p-formgrid p-grid">
                                    <Dropdown optionLabel="nome" optionValue="id" value={faculdade} options={faculdades} onChange={(e) => setFaculdade(e.value)} placeholder="Selecione a Faculdade"/>
                                </div>
                            </div>
                        </div>
                        <div className="p-field">
                            <label className="p-mb-3">Faculdade</label>
                            <div className="p-formgrid p-grid">
                                <Dropdown optionLabel="nome" optionValue="id" value={faculdade} options={faculdades} onChange={(e) => setFaculdade(e.value)} placeholder="Selecione a Faculdade"/>
                            </div>
                        </div>
                        <div className="p-field">
                            <label htmlFor="name">Nome</label>
                            <InputText id="name" value={entity.nome} onChange={(e) => onInputChange(e, 'nome')} required autoFocus className={classNames({ 'p-invalid': submitted && !entity.nome })} />
                            {submitted && !entity.nome && <small className="p-error">O preenchimento do Nome é obrigatório.</small>}
                        </div>
                        <div className="p-field">
                            <label htmlFor="description">Descrição</label>
                            <InputTextarea id="description" value={entity.descricao} onChange={(e) => onInputChange(e, 'descricao')} required rows={3} cols={20} />
                        </div>

                        <div className="p-field">
                            <label className="p-mb-3">Tipo da Universidade</label>
                            <div className="p-formgrid p-grid">
                                <div className="p-field-radiobutton p-col-6">
                                    <RadioButton inputId="tipo1" name="tipo" value="bacharelado" onChange={(e) => {onSelectChange(e, 'tipo')}} checked={entity.category === 'bacharelado'} />
                                    <label htmlFor="tipo1">Bacharelado</label>
                                </div>
                                <div className="p-field-radiobutton p-col-6">
                                    <RadioButton inputId="tipo2" name="tipo" value="licenciatura" onChange={(e) => {onSelectChange(e, 'tipo')}} checked={entity.category === 'licenciatura'} />
                                    <label htmlFor="tipo2">Licenciatura</label>
                                </div>
                                <div className="p-field-radiobutton p-col-6">
                                    <RadioButton inputId="tipo3" name="tipo" value="tecnologo" onChange={(e) => {onSelectChange(e, 'tipo')}} checked={entity.category === 'tecnologo'} />
                                    <label htmlFor="tipo3">Tecnologo</label>
                                </div>                        
                            </div>
                        </div>
                    </div>
                </Panel>

                <Toast ref={toast} />

                <div className="card">
                    <Toolbar className="p-mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

                    <DataTable ref={dt} value={entityList} selection={selectedEntities} onSelectionChange={(e) => setSelectedEntitites(e.value)}
                        dataKey="id" paginator rows={10} rowsPerPageOptions={[5, 10, 25]}
                        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                        currentPageReportTemplate="Mostrando {first} de {last} de {totalRecords} entidades"
                        globalFilter={globalFilter}
                        header={header(`Gerenciador de ${entidadeNome}`)}>

                        <Column selectionMode="multiple" headerStyle={{ width: '3rem' }}></Column>
                        <Column field="id" header="Id" sortable></Column>
                        <Column field="faculdade" header="Faculdade" sortable></Column>
                        <Column field="nome" header="Nome" sortable></Column>
                        <Column field="tipo" header="Tipo" sortable></Column>
                        <Column body={actionBodyTemplate}></Column>
                    </DataTable>
                </div>

                <Dialog visible={entityDialog} style={{ width: '450px' }} header={`Detalhes da ${entidadeNome}`} modal className="p-fluid" footer={entityDialogFooter} onHide={hideDialog}>
                    {entity.image && <img src={`showcase/demo/images/product/${entity.image}`} onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} alt={entity.image} className="product-image" />}

                </Dialog>

                <Dialog visible={deleteEntityDialog} style={{ width: '450px' }} header="Confirma" modal footer={deleteEntityDialogFooter} onHide={hideDeleteEntityDialog}>
                    <div className="confirmation-content">
                        <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem'}} />
                        {entity && <span>Confirma a exclusão <b>{entity.name}</b>?</span>}
                    </div>
                </Dialog>

                <Dialog visible={deleteEntityListDialog} style={{ width: '450px' }} header="Confirma" modal footer={deleteEntityListDialogFooter} onHide={hideDeleteEntityListDialog}>
                    <div className="confirmation-content">
                        <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem'}} />
                        {entity && <span>Você confirma a exclusão dos elementos Selecionados?</span>}
                    </div>
                </Dialog>
            </div>
        </React.Fragment>
    );
}

export default Match;